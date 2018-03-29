package com.example.test.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.test.BuildConfig;
import com.example.test.R;
import com.example.test.activity.MainActivity;
import com.example.test.base.BaseActivity;
import com.example.test.base.MyApplication;
import com.example.test.entity.AdList;
import com.example.test.utils.base.SpfsUtil;
import com.example.test.utils.rx.RxUtils;
import com.google.gson.Gson;
import com.smm.lib.utils.base.Logger;
import com.smm.lib.utils.base.StrUtil;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity implements OnClickListener {

    //SharedPreferences对应的 key
    public static final String SPLASH_AD_TIME = "splash_ad_time";
    public static final String SPLASH_AD_INFO = "splash_ad_info";
    public static final String SPLASH_AD_pic = "splash_ad_pic_37";
    public static final String SPLASH_AD_link = "splash_ad_link_value";

    //显示 smm 欢迎页的时间(单位毫秒)
    private static final int TIME_DISPLAY_SMM = 1000;
    //等待广告接口和图片下载的时间(单位秒)
    private static final int TIME_WAIT_NET = 3;
    //广告倒计时(单位秒)
    private static final int TIME_DISPLAY_AD = 5;
    //广告再次播放间隔（单位毫秒）
//    private static final int TIME_DISPLAY_AD_AGAIN = 3 * 60 * 60 * 1000;
    //TODO 产品说这个版本要每次启动都显示广告，但我觉得下次还会改，所以先把广告间隔设为1毫秒好了。
    private static final int TIME_DISPLAY_AD_AGAIN = 1;

    private String action;

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        setContentView(R.layout.splash);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        action = getIntent().getStringExtra("action");
        if (BuildConfig.DEBUG) {
            startMain();
            return;
        }
        if (SpfsUtil.getInt(SpfsUtil.GUIDE) == 0) {
            SpfsUtil.save(SpfsUtil.GUIDE, 1);
            Intent intent = new Intent(baseActivity, IndexGuideActivity.class);
            startActivity(intent);
            finish();
        } else if (MyApplication.ins().isMainRunning && StrUtil.isEmpty(action)) {
            startMain();
        } else {
            long preAdTime = SpfsUtil.getLong(SPLASH_AD_TIME);
            long now = System.currentTimeMillis();
            if (now - preAdTime > TIME_DISPLAY_AD_AGAIN) {
                startAd();
            } else {
                startMainDelay(TIME_DISPLAY_SMM);
            }
        }
    }
    private void startMainDelay(long delayTime) {
        Handler handler = new Handler();
        handler.postDelayed(() -> startMain(), delayTime);
    }

    private void startMain() {
        if (StrUtil.isNotEmpty(action) && "finish".equals(action)) {
            finish();
        } else {
            Intent intent = new Intent(baseActivity, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    /**
     * 启动广告
     * 我要彻底改变 优柔寡断！！！心里素质太差了！！！
     */
    private void startAd() {
        final String adJson = SpfsUtil.get(SPLASH_AD_INFO);
        AdList.Ad ad = null;
        try {
            ad = new Gson().fromJson(adJson, AdList.Ad.class);
        } catch (Exception e) {
        }

        if (ad == null || StrUtil.isEmpty(ad.pic_url)) {
            startMainDelay(TIME_DISPLAY_SMM);
            return;
        }

        View adView = findViewById(R.id.ad);
        View adSkip = adView.findViewById(R.id.ad_skip);
        TextView tvSkipCount = (TextView) adView.findViewById(R.id.tv_skip_count);
        ImageView ivAd = (ImageView) adView.findViewById(R.id.iv_ad);

        AdList.Ad finalAd = ad;
        Logger.error("测试启动广告", "显示===" + finalAd.title);
        Glide.with(SplashActivity.this).load(finalAd.pic_url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                startMain();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Logger.error("splash", "onResourceReady");
                adSkip.setVisibility(View.VISIBLE);
                tvSkipCount.setText(String.valueOf(TIME_DISPLAY_AD));
                adSkip.setOnClickListener(v -> startMain());

                if (StrUtil.isNotEmpty(finalAd.link_type) && StrUtil.isNotEmpty(finalAd.link_addr)) {

                }

                SpfsUtil.saveLong(SPLASH_AD_TIME, System.currentTimeMillis());
                Subscription ss = RxUtils.countdownSeconds(TIME_DISPLAY_AD)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                RxUtils.subscribeNext(count -> {
                                    if (count > 0) {
                                        tvSkipCount.setText(String.valueOf(count));
                                    } else {
                                        startMain();
                                    }
                                })
                        );
                rx.add(ss);
                return false;
            }
        }).into(ivAd);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request1:

                break;
            case R.id.btn_request2:

                break;
        }
    }

}