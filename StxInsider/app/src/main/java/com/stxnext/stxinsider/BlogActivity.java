package com.stxnext.stxinsider;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Łukasz Ciupa on 01.03.2016.
 */
public class BlogActivity extends AppCompatActivity {

    private View progressContainer;
    private ProgressBar progressBar;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressContainer = findViewById(R.id.progress_container);
        progressContainer.setVisibility(View.VISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressContainer.setVisibility(View.GONE);
            }
        });
        webview.setInitialScale(getScale());
        webview.loadUrl("http://blog.stxnext.com");

    }

    private int getScale() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        Double val = new Double(point.x)/new Double(992);
        val = val * 100d * 1.5;
        return val.intValue();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
