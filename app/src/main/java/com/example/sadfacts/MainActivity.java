package com.example.sadfacts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageButton;

import com.example.sadfacts.Utils.LoadingStatus;
import com.example.sadfacts.Utils.RedditAPIUtils;
import com.example.sadfacts.Utils.RedditViewModel;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();


    private RedditViewModel mRedditViewModel;

    private TextView mTextBubble;
    private GifImageButton mGifButton;
    private ProgressBar mLoadingPB;

    private List<RedditAPIUtils.RedditPost> mAllPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAllPosts = new ArrayList<>();

        mGifButton = findViewById(R.id.main_gif);
        mGifButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // PASS
            }
        });

        mTextBubble = findViewById(R.id.text_bubble);
        mLoadingPB = findViewById(R.id.pb_loading);

        mRedditViewModel = ViewModelProviders.of(this).get(RedditViewModel.class);
        mRedditViewModel.getPosts().observe(this, new Observer<List<RedditAPIUtils.RedditPost>>() {
            @Override
            public void onChanged(@Nullable List<RedditAPIUtils.RedditPost> redditPosts) {
                if(redditPosts == null)
                    return;

                synchronized (mAllPosts) {
                    mAllPosts.addAll(redditPosts);
                }
            }
        });

        mRedditViewModel.getLoadingStatus().observe(this, new Observer<LoadingStatus>() {
            @Override
            public void onChanged(@Nullable LoadingStatus loadingStatus) {
                if(loadingStatus == LoadingStatus.LOADING) {
                    mLoadingPB.setVisibility(View.VISIBLE);
                } else if(loadingStatus == LoadingStatus.SUCCESS) {
                    mLoadingPB.setVisibility(View.INVISIBLE);
                } else {
                    // TODO: implement
                }
            }
        });

        mGifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (mAllPosts) {
                    if(mAllPosts.size() > 0) {
                        RedditAPIUtils.RedditPost post = mAllPosts.get(0);
                        mAllPosts.remove(0);

                        mTextBubble.setText(post.selftext);
                    } else {
                        mRedditViewModel.loadPosts(10);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
