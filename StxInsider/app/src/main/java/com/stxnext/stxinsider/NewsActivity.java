package com.stxnext.stxinsider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

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
        progressContainer = findViewById(R.id.progress_container);
        progressContainer.setVisibility(View.VISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("http://blog.stxnext.com");
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressContainer.setVisibility(View.GONE);
            }
        });
//        String summary = "<html><body>You scored <b>192</b> points.</body></html>";
//        webview.loadData(summary, "text/html", null);
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
