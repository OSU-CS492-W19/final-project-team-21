package com.example.sadfacts.Utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class RedditRepository implements RedditAsyncTask.Callback{

    private static final String TAG = RedditRepository.class.getSimpleName();

    private static final String DEFAULT_SUBREDDIT = "sadjokes";

    private MutableLiveData<ArrayList<RedditAPIUtils.RedditPost>> mRedditPosts;
    private String mSubreddit;


    /*
    Initialize mutabledata mRedditPosts and set to null.
     */
    public RedditRepository() {
        this(DEFAULT_SUBREDDIT);
    }

    public RedditRepository(String subreddit) {
        mRedditPosts = new MutableLiveData<>();
        mRedditPosts.setValue(null);
        mSubreddit = subreddit;
    }



    /*
    Simple getter for mRedditPosts
     */
    public LiveData<ArrayList<RedditAPIUtils.RedditPost>> getPosts() {
        return mRedditPosts;
    }


    /*
    Creates and executes the REdditAsyncTask;
     */
    public void loadPosts() {
        mRedditPosts.setValue(null);
        String redditURL = RedditAPIUtils.buildRedditURL(mSubreddit);
        Log.d(TAG, "got reddit url: " + redditURL);
        new RedditAsyncTask(redditURL, this).execute();
    }

    /*
    Implements HTTPGot, setting mRedditPosts the the redditPosts from the asynctask.
     */
    @Override
    public void HTTPGot(ArrayList<RedditAPIUtils.RedditPost> redditPosts) {
        mRedditPosts.setValue(redditPosts);
    }
}
