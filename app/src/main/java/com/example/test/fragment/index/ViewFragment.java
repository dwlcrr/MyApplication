package com.example.test.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.activity.SimpleViewActivity;
import com.example.test.base.BaseFragment;
import com.smm.lib.view.progressCheck.SwitchButton;

/**
 * 自定义view fragment
 * Created by dwl on 2018/3/23.
 */
public class ViewFragment extends BaseFragment {

    private TextView textView;
    private SwitchButton switchButton;

    public static ViewFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        ViewFragment fragment = new ViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment, null);
        textView = view.findViewById(R.id.text);
        textView.setText(String.valueOf((char) getArguments().getInt("index")));
        switchButton = view.findViewById(R.id.switch_button);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

        switchButton.setChecked(true);
        switchButton.toggle(false);//switch without animation
        switchButton.setShadowEffect(true);//disable shadow effect
        switchButton.setOnCheckedChangeListener((view, isChecked) -> {
            if(isChecked){

            }else {

            }
        });
        textView.setOnClickListener(view -> startActivity(new Intent(getActivity(), SimpleViewActivity.class)));
    }
}
