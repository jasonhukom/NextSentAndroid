package com.nextsent.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_HISTORY = "com.nextsent.android.EXTRA_HISTORY";
    private static final int CAMERA_REQUEST = 42;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(android.graphics.Color.rgb(0, 128, 105));
        getWindow().setNavigationBarColor(android.graphics.Color.rgb(248, 249, 250));

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.nextSentWebView);
        if (webView == null) {
            webView = new WebView(this);
            setContentView(webView);
        }

        webView.setBackgroundColor(android.graphics.Color.rgb(248, 249, 250));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new NextSentBridge(), "NextSentNative");
        webView.loadUrl("file:///android_asset/index.html");

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView != null) {
                    webView.evaluateJavascript("if(window.nextSentBack){window.nextSentBack();}else{history.back();}", null);
                } else {
                    finish();
                }
            }
        });
    }

    private class NextSentBridge {
        @JavascriptInterface
        public void openCamera() {
            runOnUiThread(() -> {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, CAMERA_REQUEST);
                    } else {
                        Toast.makeText(MainActivity.this, "No camera app is available.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Could not open camera.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @JavascriptInterface
        public void openMap(String query) {
            runOnUiThread(() -> {
                Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(query));
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Photo captured. Attachments are simulated in this demo.", Toast.LENGTH_SHORT).show();
        }
    }
}
