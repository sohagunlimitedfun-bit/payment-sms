package com.noapara.paymentsms;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView webView;
    private WebView printWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.addJavascriptInterface(
            new AndroidBridge(), "AndroidBridge"
        );

        webView.loadUrl("file:///android_asset/index.html");
    }

    public class AndroidBridge {

        @JavascriptInterface
        public void copyText(String text) {
            runOnUiThread(() -> {
                ClipboardManager clipboard =
                    (ClipboardManager) getSystemService(
                        Context.CLIPBOARD_SERVICE
                    );

                clipboard.setPrimaryClip(
                    ClipData.newPlainText("Payment SMS", text)
                );

                Toast.makeText(
                    MainActivity.this,
                    "SMS কপি হয়েছে",
                    Toast.LENGTH_SHORT
                ).show();
            });
        }

        @JavascriptInterface
        public void shareText(String text) {
            runOnUiThread(() -> {
                Intent intent =
                    new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text);

                startActivity(
                    Intent.createChooser(intent, "SMS শেয়ার করুন")
                );
            });
        }

        @JavascriptInterface
        public void printHtml(String html) {
            runOnUiThread(() -> {
                printWebView = new WebView(MainActivity.this);

                printWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(
                        WebView view, String url
                    ) {
                        PrintManager manager =
                            (PrintManager) getSystemService(
                                Context.PRINT_SERVICE
                            );

                        PrintAttributes attributes =
                            new PrintAttributes.Builder()
                                .setMediaSize(
                                    PrintAttributes.MediaSize.ISO_A4
                                )
                                .build();

                        manager.print(
                            "Noapara Payment Receipt",
                            printWebView.createPrintDocumentAdapter(
                                "Noapara Payment Receipt"
                            ),
                            attributes
                        );
                    }
                });

                printWebView.loadDataWithBaseURL(
                    null,
                    html,
                    "text/html",
                    "UTF-8",
                    null
                );
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
