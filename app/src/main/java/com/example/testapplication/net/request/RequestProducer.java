package com.example.testapplication.net.request;

import com.example.testapplication.entity.base.BaseModel;
import com.example.testapplication.entity.greendao.HttpCache;
import com.example.testapplication.entity.greendao.SmmDbHelper;
import com.google.gson.Gson;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Call;
import okhttp3.Response;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by guizhen on 2017/5/18.
 */

public class RequestProducer<T> extends AtomicBoolean implements Subscription, Producer {
    private final Call call;
    private final Subscriber<? super SmmResponse<T>> subscriber;
    private final Class<T> tClass;
    private boolean keepCache;
    private boolean takeCache;

    RequestProducer(Call call, Subscriber<? super SmmResponse<T>> subscriber, Class<T> tClass, boolean keepCache, boolean takeCache) {
        this.call = call;
        this.subscriber = subscriber;
        this.tClass = tClass;
        this.keepCache = keepCache;
        this.takeCache = takeCache;
    }

    /**
     * 生产事件,将同步请求转化为Rx的事件
     */
    @Override
    public void request(long n) {
        if (n <= 0) return; // Nothing to do when requesting 0.
        if (!compareAndSet(false, true)) return; // Request was already triggered.
        String url = call.request().url().toString();
        boolean isProtobuf = MessageLite.class.isAssignableFrom(tClass);
        boolean hasCache = false;
        //获取缓存
        if (takeCache) {
            HttpCache httpCache = SmmDbHelper.ins().queryHttpCache(url);
            if (httpCache != null) {
                try {
                    T cache = null;
                    if (isProtobuf) {
                        Parser<T> parser;
                        Method method = tClass.getDeclaredMethod("parser");
                        //noinspection unchecked
                        parser = (Parser<T>) method.invoke(null);
                        cache = parser.parseFrom(httpCache.getResponseByte());
                    } else {
                        cache = new Gson().fromJson(httpCache.getResponse(), tClass);
                    }
                    if (cache != null) {
                        if (!subscriber.isUnsubscribed()) {
                            hasCache = true;
                            subscriber.onNext(SmmResponse.create(true, cache));
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        HttpCache httpCache = new HttpCache();
        httpCache.setUrl(url);
        //获取网络
        if (!call.isCanceled()) {
            try {
                Response response = call.execute();
                T result = null;
                try {
                    if (MessageLite.class.isAssignableFrom(tClass)) {
                        Parser<T> parser;
                        Method method = tClass.getDeclaredMethod("parser");
                        parser = (Parser<T>) method.invoke(null);
                        byte[] bytes = response.body().bytes();
                        result = parser.parseFrom(bytes);
                        httpCache.setResponseByte(bytes);
                    } else if (BaseModel.class.isAssignableFrom(tClass)) {
                        String json = response.body().string();
                        result = new Gson().fromJson(json, tClass);
                        httpCache.setResponse(json);
                    }
                } catch (Exception e) {
                }
                if (!subscriber.isUnsubscribed()) {
                }
                subscriber.onNext(SmmResponse.create(false, result));
                if (keepCache) {
                    SmmDbHelper.ins().addHttpCache(httpCache);
                }
            } catch (Throwable t) {
                if (!subscriber.isUnsubscribed() && !hasCache) {
                    subscriber.onError(t);
                }
            }
        }
        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }

    @Override
    public void unsubscribe() {

        if (!call.isExecuted()) {
            call.cancel();
        }
    }

    @Override
    public boolean isUnsubscribed() {
        return subscriber.isUnsubscribed();
    }
}
