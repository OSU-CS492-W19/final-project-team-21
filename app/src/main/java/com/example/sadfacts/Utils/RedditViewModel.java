package com.example.sadfacts.Utils;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import java.util.List;

public class RedditViewModel extends ViewModel {
    private RedditRepository mRepositorySad;
    private RedditRepository mRepositoryHappy;
    private RedditRepository mRepositoryCool;


    private MediatorLiveData<List<RedditAPIUtils.RedditPost>> mRedditPosts;
    private MediatorLiveData<LoadingStatus> mLoadingStatus;

    /*
    Initializes viewmodel's livedata and data repository.
     */
    public RedditViewModel() {
        mRepositorySad = new RedditRepository("sad");
        mRepositoryHappy = new RedditRepository("happy");
        mRepositoryCool = new RedditRepository("cool");

        mRedditPosts = new MediatorLiveData<>();
        mRedditPosts.addSource(mRepositorySad.getPosts(), new Observer<List<RedditAPIUtils.RedditPost>>() {
            @Override
            public void onChanged(@Nullable List<RedditAPIUtils.RedditPost> value) {
                mRedditPosts.setValue(value);
            }
        });
        mRedditPosts.addSource(mRepositoryHappy.getPosts(), new Observer<List<RedditAPIUtils.RedditPost>>() {
            @Override
            public void onChanged(@Nullable List<RedditAPIUtils.RedditPost> redditPosts) {
                mRedditPosts.setValue(redditPosts);
            }
        });
        mRedditPosts.addSource(mRepositoryCool.getPosts(), new Observer<List<RedditAPIUtils.RedditPost>>() {
            @Override
            public void onChanged(@Nullable List<RedditAPIUtils.RedditPost> redditPosts) {
                mRedditPosts.setValue(redditPosts);
            }
        });

        mLoadingStatus = new MediatorLiveData<>();
        mLoadingStatus.addSource(mRepositorySad.getLoadingStatus(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(@Nullable LoadingStatus loadingStatus) {
                mLoadingStatus.setValue(loadingStatus);
            }
        });
        mLoadingStatus.addSource(mRepositoryHappy.getLoadingStatus(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(@Nullable LoadingStatus loadingStatus) {
                mLoadingStatus.setValue(loadingStatus);
            }
        });
        mLoadingStatus.addSource(mRepositoryCool.getLoadingStatus(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(@Nullable LoadingStatus loadingStatus) {
                mLoadingStatus.setValue(loadingStatus);
            }
        });
    }

    /*
    Calls the loadPost function from mRepository.
     */
    public void loadPosts(int sad, int happy, int cool) {
        mRepositorySad.loadPosts(sad);
        mRepositoryHappy.loadPosts(happy);
        mRepositoryCool.loadPosts(cool);
    }

    /*
    Simple getter for the livedata.
     */
    public LiveData<List<RedditAPIUtils.RedditPost>> getPosts() {
        return mRedditPosts;
    }

    public MutableLiveData<LoadingStatus> getLoadingStatus() {
        return mLoadingStatus;
    }
}
