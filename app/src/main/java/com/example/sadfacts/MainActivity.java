package com.example.sadfacts;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sadfacts.Utils.RedditViewmodel;

public class MainActivity extends AppCompatActivity {
    private RedditViewmodel mRedditViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRedditViewmodel = ViewModelProviders.of(this).get(RedditViewmodel.class);
        mRedditViewmodel.loadPosts();
        setContentView(R.layout.activity_main);
    }
}
