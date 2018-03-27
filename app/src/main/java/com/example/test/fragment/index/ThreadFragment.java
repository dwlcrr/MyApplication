package com.example.test.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.activity.UpdateActivity;

/**
 * 线程测试 fragment
 * Created by dwl on 2018/3/23.
 */
public class ThreadFragment extends Fragment implements View.OnClickListener {

    private TextView tv_update;

    public static ThreadFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        ThreadFragment fragment = new ThreadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread, null);
        tv_update = view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        return view;
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
