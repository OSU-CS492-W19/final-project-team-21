package com.example.sadfacts.Utils;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.Serializable;
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


    /*
    Below are a collection of classes used to parse the json returned from reddit.
     */
    static class RedditPost {
        public String title;
        public String selftext;
    }

    static class RedditChildren {
        public RedditPost data;
    }

    static class RedditData {
        public RedditChildren[] children;
    }

    static class RedditResults {
        public int dist;
        public RedditData data;
    }

    /*
    Simple function used to create a request url based off a given subreddit.
     */
    public static String buildRedditURL(String subreddit) {
       return buildRedditURL(subreddit, DEFAULT_LIMIT);
    }

    public static String buildRedditURL(String subreddit, int limit) {
        // return REDDIT_BASE_URL + subreddit + DEFAULT_LISTING_TYPE + RETURN_TYPE;
        return Uri.parse(REDDIT_BASE_URL + subreddit + DEFAULT_LISTING_TYPE + RETURN_TYPE)
                  .buildUpon()
                  .appendQueryParameter(LIMIT_PARAM, limit + "")
                  .build()
                  .toString();
    }

    /*
   Function to parse json, returns list of RedditPosts gotten from api request.
     */
    public static ArrayList<RedditPost> parseRedditJSON(String redditJSON) {
        Gson gson = new Gson();
        RedditResults results = gson.fromJson(redditJSON, RedditResults.class);
        if (results != null && results.data != null) {
            ArrayList<RedditPost> redditPosts = new ArrayList<>();
            for (RedditChildren child : results.data.children) {
                redditPosts.add(child.data);
            }
            return redditPosts;
        } else {
            return null;
        }
    }
}
