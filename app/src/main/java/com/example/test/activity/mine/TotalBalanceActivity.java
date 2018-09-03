package com.example.test.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.view.listview.RefreshLoadLayout;
import com.example.test.view.listview.SimpleLoadingLayout;
import com.example.test.view.myview.other.Titlebar;

/**
 * 累计消费界面
 */
public class TotalBalanceActivity extends BaseActivity implements OnClickListener {
    /**
     * titlebar
     */
    private Titlebar titlebar;
    private RefreshLoadLayout swipe_list;
    private SimpleLoadingLayout loading_simple;
    private ListView lv_baseist;

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.base_list);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        titlebar = (Titlebar) findViewById(R.id.titlebar);
        titlebar.setTitleText("累计消费");
        lv_baseist = (ListView) findViewById(R.id.lv_baseist);
        swipe_list = (RefreshLoadLayout) findViewById(R.id.swipe_list);
        loading_simple = (SimpleLoadingLayout) findViewById(R.id.loading_simple);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}