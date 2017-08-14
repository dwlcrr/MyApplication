package com.example.testapplication.utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: Rx的工具类
 * @Copyright: Copyright (c) 2016 chexiang.com. All right reserved.
 * @Author: guizhen
 * @Date: 2016/4/8 10:44
 * @Modifier: guizhen
 * @Update: 2016/4/8 10:44
 */
public class RxUtils {

    /**
     * io线程执行，主线程观察。
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> subscribeInMain() {

        return Observable -> Observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 倒计时，如传入3，将收到3，2，1，0
     *
     * @param time 单位秒
     * @return
     */
    public static Observable<Integer> countdownSeconds(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1);
    }

}
