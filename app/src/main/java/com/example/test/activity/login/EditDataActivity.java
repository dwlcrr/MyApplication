package com.example.test.activity.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.entity.user.UserInfoResult;
import com.example.test.utils.data.UserInfoManager;
import com.example.test.utils.rx.RxUtils;
import rx.Subscription;

/**
 * 编辑个人资料
 */
public class EditDataActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.edit_data);
    }

    @Override
    protected void initData() {
        getUserInfo();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        UserInfoManager.INS().refresh();
    }

    public void getUserInfo() {
        Subscription s = UserInfoManager.INS()
                .rxBehavior()
                .filter(userInfoResult -> userInfoResult != null)
                .map(userInfoResult -> userInfoResult.data)
                .filter(userInfo -> userInfo != null )
                .compose(RxUtils.subscribeInMain())
                .subscribe(userInfo -> setData(userInfo));
        rx.add(s);
        UserInfoManager.INS().refresh();
    }

    private void setData(UserInfoResult.UserInfo userInfo) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}