package com.jiangpingwei.cowpea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangpingwei on 2017/3/10.
 */

public class WebActivity extends AppCompatActivity {
    @BindView(R.id.pb_web)
    NumberProgressBar pbWeb;
    @BindView(R.id.wv_web)
    WebView wvWeb;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("URL");


        WebSettings settings = wvWeb.getSettings();
        //不开启视频无法播放
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        wvWeb.setWebChromeClient(new ChromeClient());
        wvWeb.setWebViewClient(new LoveClient());

        wvWeb.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wvWeb.canGoBack()) {
                        wvWeb.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wvWeb != null) wvWeb.destroy();
    }


    @Override
    protected void onPause() {
        if (wvWeb != null) wvWeb.onPause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (wvWeb != null) wvWeb.onResume();
    }


    private class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            pbWeb.setProgress(newProgress);
            if (newProgress == 100) {
                pbWeb.setVisibility(View.GONE);
            } else {
                pbWeb.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private class LoveClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("mtmv://")) {
                return false;
            } else {
                if (url != null) view.loadUrl(url);
            }

            return true;
        }
    }
}
