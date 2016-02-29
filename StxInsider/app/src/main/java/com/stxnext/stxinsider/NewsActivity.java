package com.stxnext.stxinsider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Łukasz Ciupa on 29.02.2016.
 */
public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl("http://blog.stxnext.com/");
    }
}
