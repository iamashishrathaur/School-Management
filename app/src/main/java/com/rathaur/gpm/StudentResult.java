package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

public class StudentResult extends AppCompatActivity {
 WebView webView;
    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);
        Objects.requireNonNull(getSupportActionBar()).hide();
        webView=findViewById(R.id.student_result_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://result.bteevaluation.co.in/EVEN/main/");
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new myWebClient());

    }
        public static class myWebClient extends WebViewClient {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                view.loadUrl(url);
                return true;

            }
        }

}