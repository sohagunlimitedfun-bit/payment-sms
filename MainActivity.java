package com.noapara.paymentsms;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.text.Html;
import android.widget.Toast;

public class MainActivity extends Activity {
    private WebView webView;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        webView = new WebView(this);
        setContentView(webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");
        webView.loadUrl("file:///android_asset/index.html");
    }

    public class AndroidBridge {
        @JavascriptInterface public void copyText(String text) {
            runOnUiThread(() -> {
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText("Payment SMS", text));
                Toast.makeText(MainActivity.this, "SMS কপি হয়েছে", Toast.LENGTH_SHORT).show();
            });
        }
        @JavascriptInterface public void shareText(String text) {
            runOnUiThread(() -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "SMS শেয়ার করুন"));
            });
        }
        @JavascriptInterface public void printText(String text) {
            runOnUiThread(() -> {
                WebView printView = new WebView(MainActivity.this);
                String safeText = Html.escapeHtml(text).replace("\n", "<br>");
                String html = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><style>body{font-family:sans-serif;padding:24px;font-size:18px;line-height:1.7}h2{color:#14532d}</style></head><body><h2>নোয়াপাড়া বাজার</h2><div>" + safeText + "</div></body></html>";
                printView.setWebViewClient(new WebViewClient() {
                    @Override public void onPageFinished(WebView view, String url) {
                        PrintManager manager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
                        manager.print("Noapara Payment SMS", view.createPrintDocumentAdapter("Payment SMS"), new PrintAttributes.Builder().build());
                    }
                });
                printView.loadDataWithBaseURL(null, html, "text/HTML", "UTF-8", null);
            });
        }
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
