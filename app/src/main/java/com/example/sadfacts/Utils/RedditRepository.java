package com.example.sadfacts.Utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class RedditRepository implements RedditAsyncTask.Callback{
    private MutableLiveData<ArrayList<RedditAPIUtils.RedditPost>> mRedditPosts;

    private static final String TAG = RedditRepository.class.getSimpleName();

    /*
    Initialize mutabledata mRedditPosts and set to null.
     */
    public RedditRepository() {
        mRedditPosts = new MutableLiveData<>();
        mRedditPosts.setValue(null);
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
        String redditURL = RedditAPIUtils.buildRedditURL("sadjokes");
        Log.d(TAG, "got redit url: " + redditURL);
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
