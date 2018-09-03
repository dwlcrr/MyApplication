package com.example.test.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.activity.login.LoginActivity;
import com.example.test.activity.mine.MyDataActivity;
import com.example.test.activity.login.UpdatePassActivity;
import com.example.test.activity.mine.WithdrawActivity;
import com.example.test.base.BaseFragment;
import com.example.test.utils.base.SpfsUtil;
import com.example.test.utils.data.UserInfoManager;
import com.example.test.utils.rx.RxUtils;
import com.smm.lib.utils.base.StrUtil;

import rx.Subscription;

/**
 * Created by dwl on 2018/8/29
 * 我的
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout rl_mine_login,rl_updatePass,rl_myInfo,rl_withdraw;
    private TextView tv_loginName;
    private TextView tv_balance;
    private int balance;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_loginName = view.findViewById(R.id.tv_loginName);
        rl_mine_login = view.findViewById(R.id.rl_mine_login);
        rl_updatePass = view.findViewById(R.id.rl_updatePass);
        rl_myInfo = view.findViewById(R.id.rl_myInfo);
        rl_withdraw = view.findViewById(R.id.rl_withdraw);
        tv_balance = view.findViewById(R.id.tv_balance);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(StrUtil.isEmpty(SpfsUtil.USERTOKEN)){
            tv_loginName.setText("未登录");
            tv_balance.setText("");
        }
    }

    @Override
    protected void initData() {
        getUserInfo();
    }

    //获取用户 个人的信息
    public void getUserInfo() {
        Subscription s = UserInfoManager.INS()
                .rxBehavior()
                .map(userInfoResult -> userInfoResult.data)
                .filter(userInfo -> userInfo != null )
                .compose(RxUtils.subscribeInMain())
                .subscribe(userInfo -> {
                    if (!TextUtils.isEmpty(userInfo.name)) {
                        tv_loginName.setText(userInfo.name);
                    }
                });
        rx.add(s);
    }

    @Override
    protected void setListener() {
        rl_mine_login.setOnClickListener(this);
        rl_updatePass.setOnClickListener(this);
        rl_myInfo.setOnClickListener(this);
        rl_withdraw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(SpfsUtil.USERTOKEN)){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        switch (v.getId()){
            case R.id.rl_mine_login:
                startActivity(new Intent(getActivity(), MyDataActivity.class));
                break;
            case R.id.rl_updatePass:
                //修改密码
                startActivity(new Intent(getActivity(), UpdatePassActivity.class));
                break;
            case R.id.rl_myInfo:
                startActivity(new Intent(getActivity(), MyDataActivity.class));
                break;
            case R.id.rl_withdraw:
                startActivity(new Intent(getActivity(), WithdrawActivity.class));
                break;
        }
    }
}
