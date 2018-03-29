package com.example.test.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.utils.share.ShareUtils;

/**
 * 其他测试 fragment
 * Created by dwl on 2018/3/23.
 */
public class OtherFragment extends BaseFragment {
    private TextView textView;

    public static OtherFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater,container,savedInstanceState);
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

    public void share(){
        String title = "掌握期现价格，交易轻松搞定";
        String url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.smm";
        ShareUtils.oneKeyShare(getActivity(), "  ", title, url, R.mipmap.ic_launcher, ShareUtils.defaultUMShareListener(getActivity()));
    }
}
