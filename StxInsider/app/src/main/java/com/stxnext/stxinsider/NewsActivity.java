package com.stxnext.stxinsider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Łukasz Ciupa on 29.02.2016.
 */
public class NewsActivity extends AppCompatActivity {

    private View progressContainer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.imageBlog)
    public void onBlogImageClick(ImageView imageView) {
        Intent intent = new Intent(NewsActivity.this, BlogActivity.class);
        startActivity(intent);
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
