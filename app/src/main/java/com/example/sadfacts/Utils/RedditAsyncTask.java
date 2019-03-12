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
        RedditAPIUtils.PageData pd = null;
        if (redditJSON != null) {
            pd = RedditAPIUtils.parseRedditJSON(redditJSON);
        }
        Log.d("Hey LISTEN",pd.posts.get(0).selftext);
        mCallback.HTTPGot(pd);
    }

    /*
    Simple callback function implemented in RedditRepository
     */
    public interface Callback {
        void HTTPGot(RedditAPIUtils.PageData pageData);
    }

}
