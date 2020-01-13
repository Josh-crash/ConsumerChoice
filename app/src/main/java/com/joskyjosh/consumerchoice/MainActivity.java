package com.joskyjosh.consumerchoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String ShowOrHideWebViewInitialUse = "show";
    private WebView webView;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //load url
        webView.loadUrl("https://consumerchoice.store/");

        webView.setWebViewClient(new CustomWebViewClient());


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }



    private  class  CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //only make it invisible the first time app is run
            if (ShowOrHideWebViewInitialUse.equals("show")) {
                webView.setVisibility(webView.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            ShowOrHideWebViewInitialUse = "hide";
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            //buttonTryAgain.setVisibility(View.GONE);

            //invisible();

            view.setVisibility(webView.VISIBLE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

            String summary = "<html><head><div style = padding-top:100px; margin: auto;> <center><div class = 'connection'><h4>An internet error occured, please try again</h4></div></center><head>" +
                    //style for text head and body
                    "<body class='body' >" +
                    "<style>.body{background-color: RGB(240,243,244);}</style>" +

                    "<style>.connection{font-size: 20px; color: RGB(102,99,98);}</style>"+

                    //this style is for button
                    "<style>.button{" +
                    "background-color: RGB(28,135,201);" +
                    "border: none;" +
                    "color: white;" +
                    "margin: auto;"+
                    "width: 56%;"+
                    "height: 10px;"+
                    "border-radius: 8px;"+
                    "padding: 20px 20px ;" +
                    "text-decoration: none;"+
                    "font-size: 16px"+
                    "display: inline-block;"+
                    "position: absolute;"+
                    "Top: 200px;"+
                    "Left: 10%;"+
                    "Right:10%;"+
                    //"bottom: 0;"+
                    // "height: 40px;"+<div class = 'connection'><h5 style= padding-top:55% ><center>An internet error occurred, Please try again.</center></h5></div>
                    "justify-content: center;"+
                    "text-align:center;}</style>" +

                    //"<center><div  style = padding-top:50% class='connection'><h5>An internet error occured, please try again.</h5></div></center>"+

                    "<a href='https://consumerchoice.store/' class='button'><center><b>Try again</b></center></a>" +

                    " </body><html>";

            //webView.loadUrl("asset/index.html");

            // webView.loadDataWithBaseURL("asset/index.html" + Environment.getExternalStorageDirectory(), res, "text/html", "utf-8", null );
            webView.loadData(summary, "text/html", null);

        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            String message = "SSl Certification error";
            switch (error.getPrimaryError()){
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;

                case  SslError.SSL_EXPIRED:
                    message = "The Certificate has expired.";
                    break;

                case SslError.SSL_IDMISMATCH:
                    message = "The Certificate Hostname mismatch";
                    break;

                case  SslError.SSL_NOTYETVALID:
                    message = "The Certificate is not yet valid";
            }
            message += " Do you want to continue anyway?";

            builder.setTitle("SSL Certification Error");
            builder.setMessage(message);
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });

            final  AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



}
