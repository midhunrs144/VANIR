package com.vanir.in;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.vanir.in.utils.DialogUtilities;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NoInternetRetryInteracter{

    Dialog progressBar;
    WebView webView;
    boolean hasError = false;
    String URL = "https://vanir.in/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppClass.currentAct = this;
        progressBar = DialogUtilities.showProgressBar();
        webView = findViewById(R.id.webView);

        webView.clearCache(true);

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);

        webView.setWebViewClient(new MyWebViewClient());
        if (isConnected()){
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
        if (isConnected()){
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
            if (isConnected()){
                Log.d("test44","onPageStarted if");
            }
            else {
                Log.d("test44","onPageStarted else");
                //DialogUtilities.getRetry("Please Check Your Internet Connection",view,url).show();
                //Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("test44","shouldOverrideUrlLoading");
            if (isConnected()){
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


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webView.setVisibility(View.INVISIBLE);
            //webView.goBack();
            //webView.clearHistory();
            hasError = true;
            Log.d("test555","onReceivedError "+view.getUrl() + "- "+error.getErrorCode());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");

            if (isConnected()&&!hasError){
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                webView.setVisibility(View.VISIBLE);
                Log.d("test555","onPageFinished visible");
            }
            else {
                //webView.setVisibility(View.VISIBLE);
                webView.loadUrl(URL);
                hasError = false;
                Log.d("test555","onPageFinished has error made false");
            }
            Log.d("test555","onPageFinished "+ url +"  has error -"+hasError);

        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.getUrl().equals(URL)){
                        finish();
                    }
                    if (webView.canGoBack()) {
                        webView.goBack();
                        //webView.setVisibility(View.VISIBLE);
                        Log.d("test555","backPresses ");
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isConnected(){
        final String command = "ping -c 1 google.com";
        try {
            return Runtime.getRuntime().exec(command).waitFor() == 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

