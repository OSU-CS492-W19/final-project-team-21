package com.example.sadfacts;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifImageButton;

import com.example.sadfacts.Utils.RedditViewmodel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private RedditViewmodel mRedditViewmodel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRedditViewmodel = ViewModelProviders.of(this).get(RedditViewmodel.class);
        mRedditViewmodel.loadPosts();
        setContentView(R.layout.activity_main);

        final GifImageButton gif_button = (GifImageButton)findViewById(R.id.main_gif);
        final TextView text_bubble = findViewById(R.id.text_bubble);
        final LinearLayout speech_bubble = findViewById(R.id.speech_bubble);


        gif_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                speech_bubble.setVisibility(v.VISIBLE);
                text_bubble.setVisibility(v.VISIBLE);
                text_bubble.setText("New text");
                gif_button.setImageResource(R.drawable.huskie);

            }
        });

        //Intent intent = new Intent(this, SettingsActivity.class);
        //startActivity(intent);
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
