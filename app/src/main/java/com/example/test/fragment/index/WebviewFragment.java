package com.example.test.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.activity.web.WebActivity;
import com.example.test.base.BaseFragment;

/**
 * Webview Fragment
 * Created by dwl on 2018/3/23.
 */
public class WebviewFragment extends BaseFragment {
    private TextView textView;

    public static WebviewFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        WebviewFragment fragment = new WebviewFragment();
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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WebActivity.class));
            }
        });
    }
}
