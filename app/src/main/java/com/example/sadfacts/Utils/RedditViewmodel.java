package com.example.sadfacts.Utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import java.util.ArrayList;
import java.util.List;

public class RedditViewmodel extends ViewModel {
    private RedditRepository mRepository;
    private LiveData<ArrayList<RedditAPIUtils.RedditPost>> mRedditPosts;

    /*
    Initializes viewmodel's livedata and data repository.
     */
    public RedditViewmodel() {
        mRepository = new RedditRepository();
        mRedditPosts = mRepository.getPosts();
    }

    /*
    Calls the loadPost function from mRepository.
     */
    public void loadPosts() {
        mRepository.loadPosts();
    }

    /*
    Simple getter for the livedata.
     */
    public LiveData<ArrayList<RedditAPIUtils.RedditPost>> getPosts() {
        return mRedditPosts;
    }
}
