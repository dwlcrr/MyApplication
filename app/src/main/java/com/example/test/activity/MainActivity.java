package com.example.test.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.test.R;
import com.example.test.activity.login.SplashActivity;
import com.example.test.adapter.FragmentAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.base.MyApplication;
import com.example.test.entity.AdList;
import com.example.test.fragment.FoundFragment;
import com.example.test.fragment.MineFragment;
import com.example.test.fragment.NewsFragment;
import com.example.test.fragment.index.IndexFragment;
import com.example.test.net.api.AdApi;
import com.example.test.utils.base.SpfsUtil;
import com.example.test.utils.data.UnreadMsgSizeManager;
import com.example.test.utils.data.UserInfoManager;
import com.example.test.utils.other.RandomUtil;
import com.example.test.utils.rx.RxUtils;
import com.google.gson.Gson;
import com.smm.lib.utils.base.Logger;
import com.smm.lib.utils.base.StrUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends BaseActivity{

    private String[] titles = new String[]{"首页", "资讯", "发现", "我"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter adapter;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private int[] mImgs = new int[]{
            R.drawable.index_selector,
            R.drawable.news_selector,
            R.drawable.found_selector,
            R.drawable.mine_selector};

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.main);
    }

    @Override
    protected void initData() {
        initStartAd();
        //更新软件
//        UpdateUtil.checkUpdate(this);
    }

    @Override
    protected void initView() {
        MyApplication.ins().isMainRunning = true;
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mTitles.add(titles[i]);
        }

        mFragments = new ArrayList<>();
        mFragments.add(IndexFragment.getInstance());
        mFragments.add(NewsFragment.getInstance());
        mFragments.add(FoundFragment.getInstance());
        mFragments.add(MineFragment.getInstance());

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setSelectedTabIndicatorHeight(0);

        for (int i = 0; i < mTitles.size(); i++) {
            //获得到对应位置的Tab
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
            if (itemTab != null) {
                //设置自定义的标题
                itemTab.setCustomView(R.layout.item_tab);
                TextView textView = itemTab.getCustomView().findViewById(R.id.tv_name);
                textView.setText(mTitles.get(i));
                ImageView imageView = itemTab.getCustomView().findViewById(R.id.iv_img);
                imageView.setImageResource(mImgs[i]);
            }
        }
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    @Override
    protected void setListener() {
        Vector v = new Vector(10);
        for (int i = 1; i < 100; i++) {
            Object o = new Object();
            v.add(o);
            o = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.ins().isMainRunning = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (StrUtil.isNotEmpty(SpfsUtil.USERTOKEN)) {
            UserInfoManager.INS().refresh();
            UnreadMsgSizeManager.INS().refresh();
        } else {
            SpfsUtil.clearUserInfoSpf();
        }
    }

    private void initStartAd() {
        AdApi.getAdPic(AdApi.AD_NAME_START)
                .flatMap(adList -> {
                    if (adList != null && adList.code == 0 && adList.data != null && adList.data.size() > 0) {
                        return Observable.create(new LoadImg(adList.data.get(RandomUtil.contain0(adList.data.size()))));
                    } else {
                        return Observable.error(new Exception("返回数据异常"));
                    }
                })
                .compose(RxUtils.subscribeInMain())
                .subscribe(
                        RxUtils.subscribeNext(ad -> {
                            SpfsUtil.save(SplashActivity.SPLASH_AD_INFO, new Gson().toJson(ad));
                            Logger.error("测试启动广告", "保存===" + ad.title);
                        }));
    }

    private class LoadImg implements Observable.OnSubscribe<AdList.Ad> {

        private AdList.Ad ad;

        private LoadImg() {
        }

        private LoadImg(AdList.Ad ad) {
            this.ad = ad;
        }

        @Override
        public void call(Subscriber<? super AdList.Ad> subscriber) {
            if (ad == null || StrUtil.isEmpty(ad.pic_url)) {
                subscriber.onError(new Exception("错误的图片地址"));
            } else {
                Glide.with(MainActivity.this).load(ad.pic_url).downloadOnly(new SimpleTarget<File>() {

                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        subscriber.onNext(ad);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        subscriber.onError(e == null ? new Exception("Glide::onLoadFailed") : e);
                    }
                });
            }
        }
    }
}