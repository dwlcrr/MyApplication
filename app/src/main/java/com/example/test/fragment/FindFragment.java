package com.example.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseFragment;

/**
 * Created by dwl on 2018/3/6.
 * 发现
 */
public class FindFragment extends BaseFragment{

    public static Fragment getInstance() {
        FindFragment fragment = new FindFragment();
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

    }

    @Override
    protected void setListener() {

    }

}
