package com.example.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.utils.data.UnreadMsgSizeManager;
import com.example.test.utils.rx.RxUtils;

import rx.Subscription;

/**
 * Created by dwl on 2018/3/6.
 * 发现
 */
public class FoundFragment extends BaseFragment {

    private TextView tv_found_noreed;

    public static Fragment getInstance() {
        FoundFragment fragment = new FoundFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "title");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_found, container, false);
        tv_found_noreed = view.findViewById(R.id.tv_found_noreed);
        return view;
    }

    @Override
    protected void initData() {
        getUserUnReadMessage();
//        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.fragmnet_found);
//        User user = new User("Test", "User");
//        binding.setUser(user);
    }

    /**
     * 取未读消息数
     */
    private void getUserUnReadMessage() {
        Subscription s = UnreadMsgSizeManager.INS()
                .rxBehavior()
                .compose(RxUtils.subscribeInMain())
                .subscribe(unread ->
                        tv_found_noreed.setVisibility(UnreadMsgSizeManager.INS().getCountByMsgtype(UnreadMsgSizeManager.MSG_TYPE_ZXKD) > 0 ? View.VISIBLE : View.INVISIBLE)
                );
        addRx(s);
    }

    @Override
    protected void setListener() {

    }
}
