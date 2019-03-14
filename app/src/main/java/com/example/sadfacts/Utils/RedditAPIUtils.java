package com.example.sadfacts.Utils;

import android.graphics.pdf.PdfDocument;
import android.net.Uri;

import com.google.gson.Gson;

import java.io.Serializable;
import java.nio.file.attribute.PosixFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class RedditAPIUtils {
    /*
    Base URL To Query, will be changed later.
     */
    private final static String REDDIT_BASE_URL = "https://www.reddit.com/r/";
    private final static String DEFAULT_LISTING_TYPE = "/hot";
    private final static String RETURN_TYPE = ".json";
    private final static String LIMIT_PARAM = "limit";
    private final static int DEFAULT_LIMIT = 2;
    private final static String COUNT_PARAM = "count";
    private final static int DEFAULT_COUNT = 0;
    private final static String AFTER_PARAM = "after";
    private final static String DEFAULT_AFTER = "";

    /*
    Below are a collection of classes used to parse the json returned from reddit.
     */

    public static class PageData {
        public ArrayList<RedditPost> posts;
        public String after;
    }


    public static class RedditPost implements Serializable {
        public String title;
        public String selftext;
    }

    static class RedditChildren {
        public RedditPost data;
    }

    static class RedditData {
        public RedditChildren[] children;
        public String after;
    }

    static class RedditResults {
        public int dist;
        public RedditData data;
    }

    /*
    Simple function used to create a request url based off a given subreddit.
     */
    public static String buildRedditURL(String subreddit) {
       return buildRedditURL(subreddit, DEFAULT_LIMIT, DEFAULT_COUNT, DEFAULT_AFTER);
    }

    public static String buildRedditURL(String subreddit, int limit, int count, String after) {
        // return REDDIT_BASE_URL + subreddit + DEFAULT_LISTING_TYPE + RETURN_TYPE;
        return Uri.parse(REDDIT_BASE_URL + subreddit + DEFAULT_LISTING_TYPE + RETURN_TYPE)
                  .buildUpon()
                  .appendQueryParameter(AFTER_PARAM, after)
                  .appendQueryParameter(COUNT_PARAM, count + "")
                  .appendQueryParameter(LIMIT_PARAM, limit + "")
                  .build()
                  .toString();
    }

    /*
   Function to parse json, returns list of RedditPosts gotten from api request.
     */
    public static PageData parseRedditJSON(String redditJSON) {
        Gson gson = new Gson();
        RedditResults results = gson.fromJson(redditJSON, RedditResults.class);
        PageData pd = null;
        if (results != null && results.data != null) {
            ArrayList<RedditPost> redditPosts = new ArrayList<>();
            for (RedditChildren child : results.data.children) {
                redditPosts.add(child.data);
            }
            pd = new PageData();
            pd.after = results.data.after;
            pd.posts = redditPosts;
        }
        return pd;
    }
}
