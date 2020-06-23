package com.vanir.in;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.vanir.in.utils.DialogUtilities;

public class MainActivity extends AppCompatActivity implements NoInternetRetryInteracter{

    Dialog progressBar;
    WebView webView;
    String URL = "https://vanir.in/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppClass.currentAct = this;
        progressBar = DialogUtilities.showProgressBar();
        webView = findViewById(R.id.webView);


        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);

        webView.setWebViewClient(new MyWebViewClient());
        if (isNetworkConnected()){
            webView.loadUrl(URL);
            progressBar.show();
            webView.setVisibility(View.VISIBLE);
        }
        else {
            webView.setVisibility(View.INVISIBLE);
            DialogUtilities.getRetry("Please Check Your Internet Connection",webView,URL).show();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);



    }

    @Override
    public void retryApiCall(WebView webView2, String url) {
        if (isNetworkConnected()){
            webView2.loadUrl(URL);
            progressBar.show();
            webView2.setVisibility(View.VISIBLE);
        }
        else {
            webView2.setVisibility(View.INVISIBLE);
            DialogUtilities.getRetry("Please Check Your Internet Connection",webView,URL).show();
        }
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
                //DialogUtilities.getRetry("Please Check Your Internet Connection",view,url).show();
                //Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
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
                webView.stopLoading();
                //DialogUtilities.getRetry("Please Check Your Internet Connection",view,url).show();
                Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
            }



            return true;
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webView.setVisibility(View.INVISIBLE);
            webView.loadUrl(URL);
            webView.clearHistory();
            Log.d("test555","onReceivedError");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }
            if (isNetworkConnected()){
                webView.setVisibility(View.VISIBLE);
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

