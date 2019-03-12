package com.example.sadfacts.Utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class RedditRepository implements RedditAsyncTask.Callback{

    private static final String TAG = RedditRepository.class.getSimpleName();

    private static final String DEFAULT_SUBREDDIT = "sadjokes";

    private MutableLiveData<List<RedditAPIUtils.RedditPost>> mRedditPosts;
    private MutableLiveData<LoadingStatus> mLoadingStatus;

    private String mSubreddit;
    private int mCount;
    private String mAfter;

    /*
    Initialize mutabledata mRedditPosts and set to null.
     */
    public RedditRepository() {
        this(DEFAULT_SUBREDDIT);
    }

    public RedditRepository(String subreddit) {
        mRedditPosts = new MutableLiveData<>();
        mRedditPosts.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(null);

        mSubreddit = subreddit;
        mCount = 0;
        mAfter = "";
    }

    /*
    Simple getter for mRedditPosts
     */
    public LiveData<List<RedditAPIUtils.RedditPost>> getPosts() {
        return mRedditPosts;
    }

    public MutableLiveData<LoadingStatus> getLoadingStatus() {
        return mLoadingStatus;
    }

    /*
    Creates and executes the RedditAsyncTask;
     */
    public void loadPosts(int limit) {
        mRedditPosts.setValue(null);
        mLoadingStatus.setValue(LoadingStatus.LOADING);
        String redditURL = RedditAPIUtils.buildRedditURL(mSubreddit, limit, mCount, mAfter);
        mCount += limit;
        Log.d(TAG, "got reddit url: " + redditURL);
        new RedditAsyncTask(redditURL, this).execute();
    }

    @Override
    public void HTTPGot(RedditAPIUtils.PageData results) {
        mAfter = results.after;
        mRedditPosts.setValue(results.posts);

        if(mAfter == null)
            mCount = 0;

        if(results != null) {
            mLoadingStatus.setValue(LoadingStatus.SUCCESS);
        } else {
            mLoadingStatus.setValue(LoadingStatus.ERROR);
        }
    }
}
