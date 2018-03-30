
package com.example.test.activity.web;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.smm.lib.utils.base.DisplayUtils;

/**
 * webview
 */
public class WebActivity extends BaseActivity {

    public final static String URL = "url";
    public final static String TITLE = "title";

    private ProgressBar pb;
    private WebView webView;

    public static void runActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void initData() {
        webView.loadDataWithBaseURL(null, addChangeImgJs("ddddd + content"),
                "text/html", "UTF-8", null);
    }

    @Override
    protected void initView() {
        pb = (ProgressBar) findViewById(R.id.pb);
        webView = (WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra(URL);
//        String title = getIntent().getStringExtra(TITLE);
//        initToolBar(toolbar, true, title);

        pb.setMax(100);
        WebSettings ws = webView.getSettings();
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        ws.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        ws.setSupportZoom(true);//是否可以缩放，默认true
        ws.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        ws.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        ws.setAppCacheEnabled(true);//是否使用缓存
        ws.setDomStorageEnabled(true);//DOM Storage
        ws.setBlockNetworkImage(false);
        ws.setAllowFileAccess(true);
        ws.setSaveFormData(false);
        ws.setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if (newProgress >= 100) {
                    pb.setVisibility(View.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(url);
    }

    @Override
    protected void setListener() {

    }

    private String addChangeImgJs(String str) {
        return str + "<script type=\"text/javascript\">" +
                "function click(e){" +
                "e.preventDefault();" +
                "window.location.href='smmapp://img/single?url='+e.target.src" +
                "};" +
                "  var w = " + DisplayUtils.px2dip(this, DisplayUtils.getScreenWidth(this)) + ";" +
                "w = w-20;" +
                "var imgs = document.getElementsByTagName('img');" +
                "for(var i=0;i<imgs.length;i++){" +
                "  var img = imgs[i];" +
                "      var width = parseInt(img.style.width);" +
                "var height = parseInt(img.style.height);" +
                "if(width > w){" +
                "height = height*w/width;" +
                "width = w;" +
                "img.setAttribute('style','height:'+height+'px;width:'+width+'px');" +
                "}" +
                "if(img.onclick ==null || img.onclick == undefined){" +
                "img.onclick = click" +
                "}" +
                "}" +
                "document.getElementsByTagName('body')[0].style.marginLeft= '12px';" +
                "document.getElementsByTagName('body')[0].style.marginRight= '12px';" +
                "document.getElementsByTagName('body')[0].style.textAlign= 'justify';" +
                "document.getElementsByTagName('body')[0].style.wordBreak= 'break-word';" +
                "function enlarge(large){" +
                "if(large == 1){" +
//                "document.getElementsByTagName('body')[0].style.webkitTextSizeAdjust = '100%%';" +
                "document.getElementsByTagName('body')[0].style.fontSize= '20px';" +
                "document.getElementsByTagName('body')[0].style.lineHeight= '33px';" +
                "}else{" +
//                "document.getElementsByTagName('body')[0].style.webkitTextSizeAdjust = '90%%';" +
                "document.getElementsByTagName('body')[0].style.fontSize= '17px';" +
                "document.getElementsByTagName('body')[0].style.lineHeight= '28px';" +
                "}" +
                "}" +
                "</script>";
       /* return str + "<script type=\"text/javascript\">" +
                "  var w = " + DisplayUtils.px2dip(this, mScreenWidth) + ";" +
                "  w = w -20;" +
                "  var imgs = document.getElementsByTagName('img');" +
                "  for(var i=0;i<imgs.length;i++){" +
                "    var img = imgs[i];" +
                "    var width = parseInt(img.style.width);" +
                "    var height = parseInt(img.style.height);" +
                "    if(width > w){" +
                "      height = height*w/width;" +
                "      width = w;" +
                "      img.setAttribute('style','height:'+height+'px;width:'+width+'px');" +
                "    }" +
                "  }" +
                "</script>";*/
    }
}
