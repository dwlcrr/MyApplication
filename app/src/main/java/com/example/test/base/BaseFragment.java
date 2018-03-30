/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.test.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 若把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 * -注1: 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 * -可以调用mViewPager.setOffscreenPageLimit(size),若设置了该属性 则viewpager会缓存指定数量的Fragment
 * -注2: 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * -注3: 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 */
public abstract class BaseFragment extends Fragment {

    protected String fragmentTitle;             //fragment标题
    private boolean isVisible;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;         //是否第一次加载
    protected LayoutInflater inflater;
    protected CompositeSubscription rx = new CompositeSubscription();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        isFirstLoad = true;
        View view = initView(inflater, container, savedInstanceState);
        isPrepared = true;
        initData();
        setListener();
//        lazyLoad();
        return view;
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     * Android应用开发过程中，ViewPager同时加载多个fragment，以实现多tab页面快速切换,
     * 但是fragment初始化时若加载的内容较多，就可能导致整个应用启动速度缓慢，影响用户体验。
     * 为了提高用户体验，我们会使用一些懒加载方案，实现加载延迟。
     * 这时我们会用到getUserVisibleHint()与setUserVisibleHint()这两个方法。
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
        setListener();
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void setListener();

    public String getTitle() {
        return TextUtils.isEmpty(fragmentTitle) ? "" : fragmentTitle;
    }

    public void setTitle(String title) {
        fragmentTitle = title;
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rx.unsubscribe();
    }

    public void addRx(Subscription s) {
        rx.add(s);
    }

    public void clearRx() {
        rx.clear();
    }
}
