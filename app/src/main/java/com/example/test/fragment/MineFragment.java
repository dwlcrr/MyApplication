package com.example.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.utils.base.SpfsUtil;
import com.example.test.utils.data.UserInfoManager;
import com.example.test.utils.rx.RxUtils;
import com.smm.lib.utils.base.StrUtil;
import rx.Subscription;

/**
 * Created by dwl on 2018/3/6. 研究框架
 * 我的
 */
public class MineFragment extends BaseFragment {

    public static Fragment getInstance() {
        MineFragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "title");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_layout, container, false);
        TextView tv = view.findViewById(R.id.tv_id);
        tv.setText(getArguments().getString("title"));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(StrUtil.isEmpty(SpfsUtil.USERTOKEN)){
            //隐藏
        }else {
            //显示
        }
    }

    @Override
    protected void initData() {

        Subscription s = UserInfoManager.INS().rxBehavior()
                .compose(RxUtils.subscribeInMain())
                .subscribe(RxUtils.subscribeNext(userInfoResult -> {

                }));
        addRx(s);

        Subscription s1 = UserInfoManager.INS()
                .rxBehavior()
                .filter(userInfoResult -> userInfoResult != null)
                .map(userInfoResult -> userInfoResult.data)
                .filter(userInfo -> userInfo != null && userInfo.user_id != 0)
                .compose(RxUtils.subscribeInMain())
                .subscribe(userInfo -> userInfo.notify());
        rx.add(s);
    }

    @Override
    protected void setListener() {

    }
}
