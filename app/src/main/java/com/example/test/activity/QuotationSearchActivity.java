package com.example.test.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.adapter.ProductAdapter;
import com.example.test.adapter.QuotationSearchAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.entity.Product;
import com.example.test.entity.Quo_search_Bean;
import com.example.test.utils.base.FinalConstant;
import com.example.test.view.listview.LoadingLayout;
import com.example.test.view.listview.RefreshLayout;
import com.example.test.view.listview.SimpleLoadingLayout;
import com.smm.lib.view.other.ClearEditText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by dwl
 * 行情 搜索界面
 */
public class QuotationSearchActivity extends BaseActivity {

    private ClearEditText et_search;
    private RecyclerView rv_history;
    private SimpleLoadingLayout loadingLayout;
    private List<Quo_search_Bean.DataBean.ValueBean> historyList;
    private QuotationSearchAdapter searchAdapter;
    private RecyclerView recyQuoHotSearch;
    private List<Product.Classify> classifies = new ArrayList<>();
    private RefreshLayout refreshQuoSearch;
    private ListView lvQuoSearch;
    private String keyword;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, QuotationSearchActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.quotation_search);
    }

    @Override
    protected void initView() {
        et_search = (ClearEditText) findViewById(R.id.et_search);
        rv_history = (RecyclerView) findViewById(R.id.rv_history);
        rv_history.setLayoutManager(new LinearLayoutManager(this));
        loadingLayout = (SimpleLoadingLayout) findViewById(R.id.loading_quo_search);
        loadingLayout.setViewState(LoadingLayout.VIEW_STATE_CONTENT);
        recyQuoHotSearch = (RecyclerView)findViewById(R.id.recyQuoHotSearch);
        recyQuoHotSearch.setLayoutManager(new LinearLayoutManager(this));
        lvQuoSearch = (ListView)findViewById(R.id.lvQuoSearch);
        refreshQuoSearch = (RefreshLayout)findViewById(R.id.refreshQuoSearch);
    }

    @Override
    protected void initData() {
        historyList = new ArrayList<>();
        searchAdapter = new QuotationSearchAdapter(this, historyList);
        rv_history.setAdapter(searchAdapter);

        classifies.add(new Product.Classify("热门搜索",
                Arrays.asList(new Product.Classify.Des("铜"),
                new Product.Classify.Des("铝"))));
        recyQuoHotSearch.setAdapter(new ProductAdapter(this,classifies));
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        searchAdapter.setOnItemClickLitener((view, position) -> {

        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = et_search.getText().toString().trim();
                rv_history.smoothScrollToPosition(0);
//                rv_history.scrollToPosition(0);
                if (TextUtils.isEmpty(keyword)) {
                    historyList.clear();
                    loadingLayout.setViewState(LoadingLayout.VIEW_STATE_EMPTY);
                    loadingLayout.setEmptyText(FinalConstant.NO_SEARCH);
                    searchAdapter.notifyDataSetChanged();
                } else {
                    getQuoInfoByKeyword(keyword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = et_search.getText().toString().trim();
                    rv_history.smoothScrollToPosition(0);
                    if (TextUtils.isEmpty(keyword)) {
                        historyList.clear();
                        loadingLayout.setViewState(LoadingLayout.VIEW_STATE_EMPTY);
                        loadingLayout.setEmptyText(FinalConstant.NO_SEARCH);
                        searchAdapter.notifyDataSetChanged();
                    } else {
                        getQuoInfoByKeyword(keyword);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 实时搜索
     */
    private void getQuoInfoByKeyword(String keyword) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 20) {

            }
        }
    }
}
