package com.example.test.fragment.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.adapter.FragmentAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.entity.WsLiveMsg;
import com.example.test.net.webSocket.WebSocketManager;
import com.example.test.utils.rx.RxUtils;
import com.smm.lib.utils.base.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by dwl on 2018/3/6.
 * 首页
 */
public class IndexFragment extends BaseFragment {

    private String[] titles = new String[]{"net", "view", "rx", "recyclerview", "webview", "thread", "other"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private WebSocketManager webSocketManager;

    public static Fragment getInstance() {
        IndexFragment fragment = new IndexFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "title");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment, container, false);
        mViewPager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tablayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        mFragments.add(NetFragment.newInstance(0));
        mFragments.add(ViewFragment.newInstance(1));
        mFragments.add(RxFragment.newInstance(2));
        mFragments.add(RecyclerviewFragment.newInstance(3));
        mFragments.add(WebviewFragment.newInstance(4));
        mFragments.add(ThreadFragment.newInstance(5));
        OtherFragment otherFragment = OtherFragment.newInstance(6);
        mFragments.add(otherFragment);
        otherFragment.setLiveVideoListener(liveVideoListener);

        adapter = new FragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        return view;
    }

    private OtherFragment.LiveVideoListener liveVideoListener = new OtherFragment.LiveVideoListener() {
        @Override
        public boolean playVideo(String url) {
            if (url.startsWith("https:")) {
                //improve your life  from heart

            }
            return true;
        }

        @Override
        public boolean playFirstVideo(String url) {
            return false;
        }
    };

    @Override
    protected void initData() {
        webSocketManager = WebSocketManager.ins();
        listenWs();
        webSocketManager.logoutLive();
        webSocketManager.tryConnectLive();
        //监听网络变化
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(netReceiver, filter);
    }

    /**
     * 监听 webSocket
     */
    private void listenWs() {
        Subscription s = webSocketManager.observerLive()
                .compose(RxUtils.subscribeInMain())
                .filter(msg -> msg != null)
                .subscribe(msg -> {
                            if (msg.type == WsLiveMsg.TYPE_CONNECT_ERROR) {
                                //连接失败，自动重连
                            } else if (msg.type == WsLiveMsg.TYPE_COMMON) {
                                switch (msg.cmd) {
                                    case WsLiveMsg.CMD_LOGIN:
                                        if (msg.loginMsg != null && msg.loginMsg.code == 0) {
                                            sendEnterRoom();
                                        }
                                        break;
                                }
                            }
                        },
                        error -> {

                        });
        addRx(s);
    }

    private void sendEnterRoom() {
        int roomId = 1;
        webSocketManager.sendLive("{\"cmd\":\"enter_room\",\"data\":{\"live_room_id\":" + roomId + "}}");
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webSocketManager.logoutLive();
        if (netReceiver != null) {
            getActivity().unregisterReceiver(netReceiver);
        }
    }
    /**
     * 检查网络广播
     */
    private BroadcastReceiver netReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                if (NetWorkUtils.isMobileNet(context)) {
                    showToast("正在使用流量");
                }
            }
        }
    };
}
