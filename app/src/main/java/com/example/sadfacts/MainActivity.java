package com.example.sadfacts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String ALL_POSTS_KEY = "allpostskey";
    private static final String CURRENT_POST_KEY = "currentpostkey";

    private RedditViewModel mRedditViewModel;

    private TextView mTextBubble;
    private GifImageButton mGifButton;
    private ProgressBar mLoadingPB;

    private ArrayList<RedditAPIUtils.RedditPost> mAllPosts;
    private String mCurrentPost;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAllPosts = new ArrayList<>();
        mCurrentPost = "";

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences.registerOnSharedPreferenceChangeListener(this);

        mGifButton = findViewById(R.id.main_gif);
        mGifButton.setImageResource(PreferenceAnimals.GetDrawableIDForAnimalPreference(mPreferences.getString(getString(R.string.pref_animal), "animal_1")));
        mTextBubble = findViewById(R.id.text_bubble);
        mTextBubble.setText("Click Me!");

        mLoadingPB = findViewById(R.id.pb_loading);

        mRedditViewModel = ViewModelProviders.of(this).get(RedditViewModel.class);
        mRedditViewModel.getPosts().observe(this, new Observer<List<RedditAPIUtils.RedditPost>>() {
            @Override
            public void onChanged(@Nullable List<RedditAPIUtils.RedditPost> redditPosts) {
                if(redditPosts == null)
                    return;

                synchronized (mAllPosts) {
                    Collections.shuffle(redditPosts);
                    mAllPosts.addAll(redditPosts);

                    if(!mCurrentPost.equals(mAllPosts.get(0).title)) {
                        mCurrentPost = mAllPosts.get(0).title;
                        mTextBubble.setText(mCurrentPost);
                    }
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
                    if(mAllPosts.size() > 1) {

                        mAllPosts.remove(0);
                        RedditAPIUtils.RedditPost post = mAllPosts.get(0);

                        mCurrentPost = post.title;
                        mTextBubble.setText(post.title);
                    }

                    // if there is only one post remaining load more
                    if(mAllPosts.size() <= 1) {
                        mCurrentPost = "";

                        int sad_facts = mPreferences.getInt("sad_facts", 5);
                        int happy_facts = mPreferences.getInt("happy_facts", 5);
                        int cool_facts = mPreferences.getInt("cool_facts", 5);

                        mRedditViewModel.loadPosts(sad_facts, happy_facts, cool_facts);
                    }
                }
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(ALL_POSTS_KEY) && savedInstanceState.containsKey(CURRENT_POST_KEY)) {
            mAllPosts = (ArrayList<RedditAPIUtils.RedditPost>)savedInstanceState.getSerializable(ALL_POSTS_KEY);
        }
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
            case R.id.action_share:
                String toShare = mTextBubble.getText().toString();

                ShareCompat.IntentBuilder.from(this)
                           .setType("text/plain")
                           .setText(toShare)
                           .setChooserTitle(R.string.share_chooser)
                           .startChooser();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mAllPosts != null) {
            outState.putSerializable(ALL_POSTS_KEY, mAllPosts);
            mCurrentPost = mTextBubble.getText().toString();
            outState.putString(CURRENT_POST_KEY, mCurrentPost);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_animal")) {
            String animalPref = sharedPreferences.getString(getString(R.string.pref_animal), "animal_1");

            mGifButton.setImageResource(PreferenceAnimals.GetDrawableIDForAnimalPreference(animalPref));
            Log.d(TAG, animalPref);
        }
    }
}






















