package com.example.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.test.R;
import com.example.test.base.BaseFragment;

/**
 * Created by dwl on 2018/3/6.
 * 我的
 */
public class MineFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static Fragment getInstance() {
        MineFragment fragment = new MineFragment();
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
        View view = inflater.inflate(R.layout.fragmnet_layout, container, false);
        TextView tv = view.findViewById(R.id.tv_id);
        tv.setText(getArguments().getString("title"));
        return view;
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
