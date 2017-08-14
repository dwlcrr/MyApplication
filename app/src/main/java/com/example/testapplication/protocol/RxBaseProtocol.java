package com.example.testapplication.protocol;

import android.text.TextUtils;
import com.example.testapplication.net.XgoHttpClient;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import java.util.Map;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by dongwanlin on 2016/12/27.
 */

public abstract class RxBaseProtocol {

    private static final Gson mGson;

    static {
        mGson = new Gson();
    }
    /**
     *  创建一个工作在IO线程的被观察者(被订阅者)对象
     *  @param url
     *  @param method
     *  @param params
     */
    protected <T> Observable<T> createObservable(final String url, final String method, final Map<String,Object> params, final Class<T> clazz){
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                Request request = XgoHttpClient.getClient().getRequest(url,method,params);
                String  json    = XgoHttpClient.getClient().execute2String(request);
                setData(subscriber,json,clazz);
            }
        }).subscribeOn(Schedulers.io());
    }

    protected <T> void setData(Subscriber<? super T> subscriber, String json, Class<T> clazz){
        if(TextUtils.isEmpty(json)){
            subscriber.onError(new Throwable("not data"));
            return;
        }
        T data = mGson.fromJson(json, clazz);
        subscriber.onNext(data);
        subscriber.onCompleted();
    }
}
