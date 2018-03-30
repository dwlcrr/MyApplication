package com.example.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseFragment;

/**
 *
 * Created by dwl on 2018/3/23.
 */
public class TabFragment extends BaseFragment {
    private TextView textView;

    public static TabFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, null);
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
