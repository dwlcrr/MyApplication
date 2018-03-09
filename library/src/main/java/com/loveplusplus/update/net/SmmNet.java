package com.loveplusplus.update.net;

import android.os.Handler;
import android.os.Looper;

import com.loveplusplus.update.BuildConfig;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class SmmNet {
    public static final int DEFAULT_MILLISECONDS = 15000;       //默认的超时时间

    private static SmmNet ins;
    private Handler mDelivery;                                  //用于在主线程执行的调度器
    private OkHttpClient okHttpClient;                          //ok请求的客户端
    private OkHttpClient.Builder okhttpBuilder;
    private Map<String, String> mCommonHeaders;                  //统一添加的 headers
    private int mRetryCount = 3;                                //全局超时重试次数

    private SmmNet() {
        initClientBuilder();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    private void initClientBuilder() {
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        okhttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslSocketFactory, xtm)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
        if (BuildConfig.DEBUG) {
            okhttpBuilder.addInterceptor(new HttpLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC));
        }
    }

    public static SmmNet ins() {
        if (ins == null) ins = new SmmNet();
        return ins;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient.Builder getOkhttpBuilder() {
        return okhttpBuilder;
    }

    public OkHttpClient getClient() {
        if (okHttpClient == null) okHttpClient = okhttpBuilder.build();
        return okHttpClient;
    }

    public void setClient(OkHttpClient client) {
        this.okHttpClient = client;
    }

    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    /**
     * post请求
     */
    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    /**
     * 添加全局公共请求参数
     */
    public SmmNet addCommonHeaders(String key, String value) {
        if (mCommonHeaders == null) mCommonHeaders = new LinkedHashMap<>();
        mCommonHeaders.put(key, value);
        return this;
    }

    public Map<String, String> getCommonHeaders() {
        return mCommonHeaders;
    }

    /**
     * 添加全局拦截器
     */
    public SmmNet addInterceptor(Interceptor interceptor) {
        okhttpBuilder.addInterceptor(interceptor);
        return this;
    }

    /**
     * 根据Tag取消请求
     */
    public void cancelRequest(Object tag) {
        for (Call call : getClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 取消所有请求请求
     */
    public void cancelAll() {
        for (Call call : getClient().dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : getClient().dispatcher().runningCalls()) {
            call.cancel();
        }
    }
}