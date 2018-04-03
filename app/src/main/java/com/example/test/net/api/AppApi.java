package com.example.test.net.api;

import android.content.Context;
import com.example.test.BuildConfig;
import com.example.test.entity.CheckUpdate;
import com.example.test.entity.base.GankModel;
import com.example.test.entity.base.GankResponse;
import com.example.test.net.NetConfig;
import com.example.test.net.callback.DialogCallback;
import com.example.test.net.callback.NewsCallback;
import com.example.test.utils.base.Urls;
import com.smm.lib.net.SmmNet;
import com.smm.lib.okgo.OkGo;
import com.smm.lib.okgo.cache.CacheMode;
import com.smm.lib.okgo.request.base.Request;
import com.smm.lib.utils.base.FinalConstants;
import java.util.List;
import rx.Observable;

/**
 * Created by dwl
 * 17/11/16 广告
 */

public class AppApi extends BaseRxApi {
    private static final String appUrl = NetConfig.BASEURL + "/appcenter";
    /**
     * 检查更新
     */
    public static Observable<CheckUpdate> checkUpdate() {
        return request(
                SmmNet.ins().post(appUrl + "/check_update")
                        .addBodyParams("device_type", "android")
                        .addBodyParams("app_version", BuildConfig.VERSION_NAME)
                , CheckUpdate.class);
    }

    public static void check(Context context, DialogCallback<CheckUpdate> callback) {
        OkGo.<CheckUpdate>post(appUrl + "/check_update")
                .tag(context)
                .params("device_type", "android")
                .params("app_version", BuildConfig.VERSION_NAME)
                .execute(callback);
    }

    public static void dialogRequest(Context context, DialogCallback<CheckUpdate> callback) {
        Request request = OkGo.<CheckUpdate>get(FinalConstants.UPDATE_URL)
                .tag(context)
                .params("device_type", "android")
                .params("app_version", BuildConfig.VERSION_NAME)
                .cacheMode(CacheMode.NO_CACHE);
        request.execute(callback);
    }

    public static void cacheList(int page, Context context, NewsCallback<GankResponse<List<GankModel>>> callback) {
        String  url = Urls.URL_GANK_BASE + "fresh1" + "/" + 10 + "/" + page;
        Request request = OkGo.<GankResponse<List<GankModel>>>get(url).tag(context);
        if (page == 1) {
            request.cacheTime(5000)
                    .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                    .cacheKey(FinalConstants.TEST_CACHE_LIST);
        }else {
            request.cacheMode(CacheMode.NO_CACHE);
        }
        request.execute(callback);
    }

    public static Request isCache(int page ,Request request){
        if (page == 1) {
            request.cacheTime(5000)
                    .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                    .cacheKey(FinalConstants.TEST_CACHE_LIST);
        }else {
            request.cacheMode(CacheMode.NO_CACHE);
        }
        return request;
    }
}
