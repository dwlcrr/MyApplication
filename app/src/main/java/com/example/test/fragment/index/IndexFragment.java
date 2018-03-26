package com.example.test.fragment.index;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.adapter.FragmentAdapter;
import com.example.test.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dwl on 2018/3/6.
 * 首页
 */
public class IndexFragment extends BaseFragment{

    private String[] titles = new String[]{"net", "view", "rx", "recyclerview", "webview", "thread", "other"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public static Fragment getInstance() {
        IndexFragment fragment = new IndexFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "title");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment, container, false);
        mViewPager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tablayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        mFragments.add(NetFragment.newInstance(0));
        mFragments.add(ViewFragment.newInstance(1));
        mFragments.add(RxFragment.newInstance(2));
        mFragments.add(RecyclerviewFragment.newInstance(3));
        mFragments.add(WebviewFragment.newInstance(4));
        mFragments.add(ThreadFragment.newInstance(5));
        mFragments.add(OtherFragment.newInstance(6));

        adapter = new FragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        return view;
    }


    @Override
    protected void initData() {

    }

}
