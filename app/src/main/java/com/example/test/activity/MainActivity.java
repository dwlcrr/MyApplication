package com.example.test.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.adapter.FragmentAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.fragment.FindFragment;
import com.example.test.fragment.index.IndexFragment;
import com.example.test.fragment.MineFragment;
import com.example.test.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

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

    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabLayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mTitles.add(titles[i]);
        }

        mFragments = new ArrayList<>();

        mFragments.add(IndexFragment.getInstance());
        mFragments.add(NewsFragment.getInstance());
        mFragments.add(FindFragment.getInstance());
        mFragments.add(MineFragment.getInstance());

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来

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

}