package com.stxnext.stxinsider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Łukasz Ciupa on 29.02.2016.
 */
public class NewsActivity extends AppCompatActivity {

    private View progressContainer;
    private ProgressBar progressBar;
    private View blog;
    private View twitter;
    private View facebook;
    private View youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        blog = (View) findViewById(R.id.imageBlog);
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsActivity.this, BlogActivity.class);
                startActivity(intent);
            }
        });

        twitter = (View) findViewById(R.id.imageTwitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/STXNext"));
                startActivity(intent);
            }
        });

        facebook = (View) findViewById(R.id.imageFacebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/StxNext"));
                startActivity(intent);
            }
        });

        youtube = (View) findViewById(R.id.imageFacebook);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCI1AvU1piMZ80LXboJmRroQ"));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }
}
