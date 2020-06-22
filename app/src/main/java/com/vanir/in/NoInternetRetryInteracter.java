package com.vanir.in;

import android.webkit.WebView;

public interface NoInternetRetryInteracter {
    void retryApiCall(WebView webView,String url);
}
