package com.vanir.in;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.vanir.in.utils.DialogUtilities;

public class MainActivity extends AppCompatActivity implements NoInternetRetryInteracter{

    Dialog progressBar;
    WebView webView;
    String failedUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppClass.currentAct = this;
        progressBar = DialogUtilities.showProgressBar();
        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new MyWebViewClient());
        if (isNetworkConnected()){
            webView.loadUrl("https://vanir.in/");
            progressBar.show();
        }
        else {
            DialogUtilities.getRetry("Please Check Your Internet Connection",webView,"https://vanir.in/").show();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);



    }

    @Override
    public void retryApiCall(WebView webView2, String url) {
        webView2.loadUrl(url);
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("test44","onPageStarted");
            if (isNetworkConnected()){
                Log.d("test44","onPageStarted if");
            }
            else {
                Log.d("test44","onPageStarted else");
                DialogUtilities.getRetry("Please Check Your Internet Connection",view,url).show();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("test44","shouldOverrideUrlLoading");
            if (isNetworkConnected()){
                Log.d("test44","shouldOverrideUrlLoading if");
                view.loadUrl(url);
                if (!progressBar.isShowing()) {
                    progressBar.show();
                }
            }
            else {
                Log.d("test44","shouldOverrideUrlLoading else");
                DialogUtilities.getRetry("Please Check Your Internet Connection",view,url).show();
            }



            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }

        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}

