package com.example.test.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.activity.UpdateActivity;
import com.example.test.base.BaseFragment;

/**
 * 线程测试 fragment
 * Created by dwl on 2018/3/23.
 */
public class ThreadFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_update;

    public static ThreadFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        ThreadFragment fragment = new ThreadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread, null);
        tv_update = view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update:
                startActivity(new Intent(getActivity(),UpdateActivity.class));
                break;
        }
    }
}
