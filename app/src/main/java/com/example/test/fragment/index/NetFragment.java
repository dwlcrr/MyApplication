package com.example.test.fragment.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.example.test.R;
import com.example.test.adapter.NewsAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.entity.base.GankModel;
import com.example.test.entity.base.GankResponse;
import com.example.test.net.api.AppApi;
import com.example.test.net.callback.NewsCallback;
import com.smm.lib.okgo.model.Response;
import com.smm.lib.view.dialog.SimpleHUD;
import java.util.List;

/**
 * 网络测试 fragment
 * Created by dwl on 2018/3/23.
 */
public class NetFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private NewsAdapter newsAdapter;
    private boolean isInitCache = false;
    private int page = 1;

    public static NetFragment newInstance(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        NetFragment fragment = new NetFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_refresh, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        return view;
    }

    @Override
    protected void initData() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsAdapter = new NewsAdapter(null);
        newsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        newsAdapter.isFirstOnly(false);
        recyclerView.setAdapter(newsAdapter);

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        refreshLayout.setOnRefreshListener(this);
        newsAdapter.setOnLoadMoreListener(this);

        //开启loading,获取数据
        refreshLayout.post(() -> refreshLayout.setRefreshing(true));
        SimpleHUD.showLoadingMessage(getActivity(),"正在加载", true);
        onRefresh();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onRefresh() {
        AppApi.cacheList(page, getActivity(), new NewsCallback<GankResponse<List<GankModel>>>() {
            @Override
            public void onSuccess(Response<GankResponse<List<GankModel>>> response) {
                List<GankModel> results = response.body().results;
                if (results != null) {
                    page = 2;
                    newsAdapter.setNewData(results);
                }
            }

            @Override
            public void onCacheSuccess(Response<GankResponse<List<GankModel>>> response) {
                //一般来说,只需呀第一次初始化界面的时候需要使用缓存刷新界面,以后不需要,所以用一个变量标识
                if (!isInitCache) {
                    //一般来说,缓存回调成功和网络回调成功做的事情是一样的,所以这里直接回调onSuccess
                    onSuccess(response);
                    isInitCache = true;
                }
            }

            @Override
            public void onError(Response<GankResponse<List<GankModel>>> response) {
                //网络请求失败的回调,一般会弹个Toast
                showToast(response.getException().getMessage());
            }

            @Override
            public void onFinish() {
                SimpleHUD.dismiss();
                //可能需要移除之前添加的布局
                newsAdapter.removeAllFooterView();
                //最后调用结束刷新的方法
                refreshLayout.post(() -> refreshLayout.setRefreshing(false));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoadMoreRequested() {
        AppApi.cacheList(page, getActivity(), new NewsCallback<GankResponse<List<GankModel>>>() {
            @Override
            public void onSuccess(Response<GankResponse<List<GankModel>>> response) {
                List<GankModel> results = response.body().results;
                if (results != null && results.size() > 0) {
                    page++;
                    newsAdapter.addData(results);
                } else {
                    //显示没有更多数据
                    newsAdapter.loadMoreComplete();
                    View noDataView = LayoutInflater.from(getActivity()).inflate(R.layout.item_no_data, (ViewGroup) recyclerView.getParent(), false);
                    newsAdapter.addFooterView(noDataView);
                }
            }

            @Override
            public void onError(Response<GankResponse<List<GankModel>>> response) {
                //显示数据加载失败,点击重试
//                newsAdapter.showLoadMoreFailedView();
                SimpleLoadMoreView simpleLoadMoreView = new SimpleLoadMoreView();
                simpleLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_FAIL);
                newsAdapter.setLoadMoreView(simpleLoadMoreView);
                showToast(response.getException().getMessage());
            }
        });

    }
}
