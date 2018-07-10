package com.example.test.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.utils.base.FinalConstant;
import com.example.test.utils.rx.ObsetverUtil.NotificationCenter;
import com.example.test.utils.rx.ObsetverUtil.NotificationDelegate;

/**
 * 其他测试 fragment
 * Created by dwl on 2018/3/23.
 */
public class Other1Fragment extends BaseFragment {

    private TextView textView;
    private NotificationDelegate delegate;

    public static Other1Fragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        Other1Fragment fragment = new Other1Fragment();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initData() {
        testObserver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter().removeFucntionForNotification(FinalConstant.MESS_BROADCAST, delegate);
    }

    /**
     * 两个无关联的类之间传递数据 观察者模式
     */
    private void testObserver() {
        delegate = new NotificationDelegate() {
            @Override
            public void update(String name, Object obj) {
                if(name.equals(FinalConstant.MESS_BROADCAST)){
                    String name1 = obj.toString();
                }
            }
        };
        NotificationCenter.defaultCenter().addFucntionForNotification(FinalConstant.MESS_BROADCAST,delegate);
    }

    @Override
    protected void setListener() {
        textView.setOnClickListener(view -> {
            NotificationCenter.defaultCenter().addFucntionForNotification(FinalConstant.MESS_BROADCAST,delegate);
        });
    }

}
