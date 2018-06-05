package com.example.test.activity.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.BuildConfig;
import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.utils.base.SpfsUtil;
import com.example.test.utils.other.IntentUtil;
import com.smm.lib.utils.base.DisplayUtils;
import com.smm.lib.utils.base.Logger;
import com.smm.lib.utils.base.StrUtil;
import com.smm.lib.utils.validate.TelCallUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.lijunguan.imgselector.AlbumConfig;
import io.github.lijunguan.imgselector.ImageSelector;

public class WebSmmActivity extends BaseActivity {

    private static final String SMMAPPJS = "smmappjs";

    public static final String WEB_SMM_URL = "web_smm_url";
    public static final String WEB_TYPE = "web_type";
    public static final int WEB_TYPE_JR = 1;
    public static final int WEB_TYPE_HELP = 2;

    private WebView webView;
    private TextView webTitle;
    private ProgressBar pb;
    private View back;
    private View close;
    private ValueCallback<Uri> imgSelectCallback4;
    private ValueCallback<Uri[]> imgSelectCallback5;
    private int webType;

    public static Intent webSmmIntent(Context context, String url) {
        Intent intent = new Intent(context, WebSmmActivity.class);
        intent.putExtra(WEB_SMM_URL, url);
        return intent;
    }

    public static Intent webSmmIntent(Context context, String url, int shareType) {
        Intent intent = new Intent(context, WebSmmActivity.class);
        intent.putExtra(WEB_SMM_URL, url);
        intent.putExtra(WEB_TYPE, shareType);
        return intent;
    }

    public static void startActivity(Context context, String url) {
        context.startActivity(webSmmIntent(context, url));
    }

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_smm);
    }

    @Override
    protected void initView() {
        webView = (WebView) findViewById(R.id.web);
        webTitle = (TextView) findViewById(R.id.tv_web_title);
        pb = (ProgressBar) findViewById(R.id.pb_web);
        back = findViewById(R.id.iv_back);
        close = findViewById(R.id.tv_close);

        WebSettings ws = webView.getSettings();
        ws.setUseWideViewPort(true);
        //是否可以缩放，默认true
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(false);
//        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);  //  缩放
        ws.setJavaScriptEnabled(true);
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
        ws.setAllowFileAccess(true);
        ws.setSaveFormData(false);
        ws.setLoadsImagesAutomatically(true);
        ws.setUserAgentString(ws.getUserAgentString() + " appsmm/" + BuildConfig.VERSION_NAME);
        ws.setDomStorageEnabled(true);
        Logger.info("websmm", "user-agent:" + ws.getUserAgentString());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress == 100 ? 0 : newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                webTitle.setText(title);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                imgSelectCallback4 = uploadMsg;
                ImageSelector.newInstance()
                        .setSelectModel(ImageSelector.SINGLE_MODE)
                        .setCrop(false)
                        .setMaxCount(1)
                        .setGridColumns(3)
                        .setShowCamera(true)
                        .setToolbarColor(getResources().getColor(R.color.black))
                        .startSelect(WebSmmActivity.this);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                imgSelectCallback5 = filePathCallback;
                ImageSelector.newInstance()
                        .setSelectModel(fileChooserParams.getMode() == FileChooserParams.MODE_OPEN ? ImageSelector.SINGLE_MODE : ImageSelector.MULTI_MODE)
                        .setCrop(false)
                        .setGridColumns(3)
                        .setMaxCount(AlbumConfig.UNLIMIT_MAX_COUNT)
                        .setShowCamera(true)
                        .setToolbarColor(getResources().getColor(R.color.black))
                        .startSelect(WebSmmActivity.this);
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                Logger.info("websmm", "onPageFinished:" + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.info("websmm", "url:" + url);
                String replaceUrl = IntentUtil.overrideMallUrl(url);
                if (StrUtil.isNotEmpty(replaceUrl)) url = replaceUrl;

                if (url.startsWith("smmapp://")) {
                    IntentUtil.startAd(WebSmmActivity.this, url);
                } else if (url.startsWith("tel:")) {
                    String tel = url.substring(4);
                    TelCallUtils.callTelWithDialog(tel,WebSmmActivity.this);
                } else {
                    return false;
                }
                return true;
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }
        });
        webView.addJavascriptInterface(new SmmWebJs(webView), SMMAPPJS);

        back.setOnClickListener(v -> onBackPressed());
        close.setOnClickListener(v -> finish());
        webType = getIntent().getIntExtra(WEB_TYPE, 0);
        switch (webType) {
            case 0:
                webView.loadDataWithBaseURL(null,"htmlcontent","text/html","UTF-8",null);
                break;
        }

    }

    @Override
    protected void initData() {
//        String url = getIntent().getStringExtra(WEB_SMM_URL);
        String url = "file:///android_asset/test.html";
        if (StrUtil.isEmpty(url)) url = "http://www.smm.cn/";
        //加载url
        webView.loadUrl(url);
        //加载html内容
        webView.loadDataWithBaseURL(null, addChangeImgJs("html content"), "text/html", "UTF-8", null);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageSelector.REQUEST_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    List<String> imagesPath = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
                    if (imagesPath != null && imagesPath.size() > 0) {
                        if (imgSelectCallback5 != null) {
                            List<Uri> uris = new ArrayList<>();
                            for (String filePath : imagesPath) {
                                uris.add(Uri.fromFile(new File(filePath)));
                            }
                            imgSelectCallback5.onReceiveValue(uris.toArray(new Uri[]{}));
                            imgSelectCallback5 = null;
                        } else if (imgSelectCallback4 != null) {
                            imgSelectCallback4.onReceiveValue(Uri.fromFile(new File(imagesPath.get(0))));
                            imgSelectCallback4 = null;
                        }
                    } else {
                        imgSelectCancel();
                    }
                    break;
                } else {
                    imgSelectCancel();
                }

        }

    }

    private void imgSelectCancel() {
        if (imgSelectCallback5 != null) {
            imgSelectCallback5.onReceiveValue(null);
            imgSelectCallback5 = null;
        } else if (imgSelectCallback4 != null) {
            imgSelectCallback4.onReceiveValue(null);
            imgSelectCallback4 = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    private class SmmWebJs {

        private WebView webView;

        public SmmWebJs(WebView webView) {
            this.webView = webView;
        }

        @JavascriptInterface
        public void showToast(String text){
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void showJsText(String text){
            webView.loadUrl("javascript:jsText('"+text+"')");
        }
        @JavascriptInterface
        public void getToken(String tokenName) {
            if (webView != null) {
                webView.post(() -> {
                    if (!Uri.parse(webView.getUrl()).getHost().endsWith(".smm.cn")) return;
                    webView.loadUrl("javascript:smmappToken('" + SpfsUtil.USERTOKEN + "')");
                });
            }
        }
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
    }
}
