package com.example.sadfacts.Utils;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.android.lifecycleweather.utils.NetworkUtils.doHTTPGet;

public class RedditAsyncTask extends AsyncTask<Void, Void, String>{
    private String redditURL;
    private Callback mCallback;

    public RedditAsyncTask(String url, Callback callback) {
        redditURL = url;
        mCallback = callback;
    }

    /*
    Fetches json from reddit in background;
     */
    @Override
    protected String doInBackground(Void... voids) {
        String redditJSON = null;
        try {
            redditJSON = doHTTPGet(redditURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return redditJSON;
    }

    /*
    Parses and passes the redditPosts to the callback function. For the time being also logs the
    first selftext.
     */
    @Override
    protected void onPostExecute(String redditJSON) {
        ArrayList<RedditAPIUtils.RedditPost> redditPosts = null;
        if (redditJSON != null) {
            redditPosts = RedditAPIUtils.parseRedditJSON(redditJSON);
        }
        Log.d("Hey LISTEN",redditPosts.get(0).selftext);
        mCallback.HTTPGot(redditPosts);
    }

    /*
    Simple callback function implemented in RedditRepository
     */
    public interface Callback {
        void HTTPGot(ArrayList<RedditAPIUtils.RedditPost> redditPosts);
    }

}
