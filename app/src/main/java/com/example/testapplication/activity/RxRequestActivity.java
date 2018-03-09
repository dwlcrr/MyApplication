package com.example.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;
import com.example.testapplication.utils.base.BaseUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Rx异步请求类
 */
public class RxRequestActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_show);
        testOk();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request1:

                break;
            case R.id.btn_request2:

                break;
        }
    }

    private void testOk() {
        StringBuffer buffer = new StringBuffer();

        findViewById(R.id.btn_request1).setOnClickListener(v -> rxBusinessProtocol.getAppConfig()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    buffer.append(data.data.mall_close_time);
                    BaseUtils.showToast(this,data.data.mall_close_time);
                }, error -> BaseUtils.showToast(this,"")));

        Observable<String> sender = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hi，Weavey！");  //发送数据"Hi，Weavey！"
            }
        });
    }
}