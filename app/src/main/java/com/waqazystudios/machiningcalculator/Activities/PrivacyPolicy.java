package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.waqazystudios.machiningcalculator.R;

public class PrivacyPolicy extends AppCompatActivity {
    private static final String URL = "https://www.waqazystudios.com/privacy-policy/";
    private WebView webView;
    private ProgressBar progressBar;
    Button retry;
    RelativeLayout noConnection, webViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        setTitle("Privacy Policy");
        webView = findViewById(R.id.webView);
        webViewLayout = findViewById(R.id.webViewLayout);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        retry = findViewById(R.id.retry);
        noConnection = findViewById(R.id.noInternet);
        if(!isNetworkAvailable(this)){
            noConnection.setVisibility(View.VISIBLE);
            webViewLayout.setVisibility(View.INVISIBLE);
        }
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);

            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable(getApplicationContext())){
                   noConnection.setVisibility(View.INVISIBLE);
                   webViewLayout.setVisibility(View.VISIBLE);
                   webView.loadUrl(URL);
                   progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}