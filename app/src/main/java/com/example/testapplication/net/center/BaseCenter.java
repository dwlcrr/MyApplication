package com.example.testapplication.net.center;


import com.example.testapplication.net.request.RxNetOnSubscribe;
import com.example.testapplication.net.request.SmmResponse;
import com.loveplusplus.update.net.BaseRequest;
import rx.Observable;

/**
 * Created by guizhen on 2017/5/19.
 */

public class BaseCenter {

    public static <T> Observable<T> request(BaseRequest request, Class<T> tClass) {
        return RxNetOnSubscribe.create(request.getCall(), tClass).toRxMain().map(response -> response.result);
    }

    public static <T> Observable<SmmResponse<T>> cacheRequest(BaseRequest request, Class<T> tClass) {
        return RxNetOnSubscribe.create(request.getCall(), tClass).enableCache(true).toRxMain();
    }

    public static <T> Observable<SmmResponse<T>> cacheRequest(BaseRequest request, Class<T> tClass, boolean keepCache, boolean takeCache) {
        return RxNetOnSubscribe.create(request.getCall(), tClass).keepCache(keepCache).takeCache(takeCache).toRxMain();
    }

}
