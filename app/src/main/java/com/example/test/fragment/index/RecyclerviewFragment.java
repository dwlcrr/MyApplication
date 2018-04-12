package com.example.test.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseFragment;

/**
 * recyclerview测试 fragment
 * Created by dwl on 2018/3/23.
 */
public class RecyclerviewFragment extends BaseFragment {
    private TextView textView;

    public static RecyclerviewFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        RecyclerviewFragment fragment = new RecyclerviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_fragment, null);
        textView = view.findViewById(R.id.text);
        textView.setText(String.valueOf((char) getArguments().getInt("index")));
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
