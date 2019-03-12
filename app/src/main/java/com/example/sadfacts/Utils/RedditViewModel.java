package com.example.sadfacts.Utils;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import java.util.List;

public class RedditViewModel extends ViewModel {
    private RedditRepository mRepositorySadjokes;
    private RedditRepository mRepositoryCollapse;


    private MediatorLiveData<List<RedditAPIUtils.RedditPost>> mRedditPosts;
    private MediatorLiveData<LoadingStatus> mLoadingStatus;

    /*
    Initializes viewmodel's livedata and data repository.
     */
    public RedditViewModel() {
        mRepositorySadjokes = new RedditRepository("sadjokes");
        mRepositoryCollapse = new RedditRepository("collapse");

        mRedditPosts = new MediatorLiveData<>();
        mRedditPosts.addSource(mRepositorySadjokes.getPosts(), new Observer<List<RedditAPIUtils.RedditPost>>() {
            @Override
            public void onChanged(@Nullable List<RedditAPIUtils.RedditPost> value) {
                mRedditPosts.setValue(value);
            }
        });
        mRedditPosts.addSource(mRepositoryCollapse.getPosts(), new Observer<List<RedditAPIUtils.RedditPost>>() {
            @Override
            public void onChanged(@Nullable List<RedditAPIUtils.RedditPost> redditPosts) {
                mRedditPosts.setValue(redditPosts);
            }
        });

        mLoadingStatus = new MediatorLiveData<>();
        mLoadingStatus.addSource(mRepositorySadjokes.getLoadingStatus(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(@Nullable LoadingStatus loadingStatus) {
                mLoadingStatus.setValue(loadingStatus);
            }
        });
        mLoadingStatus.addSource(mRepositoryCollapse.getLoadingStatus(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(@Nullable LoadingStatus loadingStatus) {
                mLoadingStatus.setValue(loadingStatus);
            }
        });
    }

    /*
    Calls the loadPost function from mRepository.
     */
    public void loadPosts(int limit) {
        mRepositorySadjokes.loadPosts(limit);
        mRepositoryCollapse.loadPosts(limit);
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
