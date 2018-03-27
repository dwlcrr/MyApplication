package com.example.test.net.api;

import com.example.test.entity.AdList;
import com.example.test.net.NetConfig;
import com.smm.lib.net.SmmNet;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import rx.Observable;

/**
 * rx 版广告接口
 * Created by guizhen on 16/9/13.
 */
public class AdApi extends BaseRxApi{

    private static final String adUrl = NetConfig.baseUrl + "/adcenter";
    public static final String AD_NAME_START = "掌上有色app启动广告";
    public static final String AD_NEWS_TEXT = "新闻详情页文字广告";

    /**
     * 检查更新
     */
    public static Observable<AdList> getAdPic(String adName) {
        try {
            return request(
                    SmmNet.ins().get(adUrl + "/position/get/position?name=" + URLEncoder.encode(adName, "UTF8"))
                    , AdList.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取启动广告
//    public static Observable<AdList> getAd(String adName, boolean cache) {
//        String url = "";
//        try {
//            url = adUrl + "/position/get/position?name=" + URLEncoder.encode(adName, "UTF8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return Observable.create(new DoStringRequest<AdList>(AdList.class, Request.Method.GET, url, null).setUseCache(cache));
//    }
//
//    public static Observable<AdList> getAd(String adName) {
//        return getAd(adName, false);
//    }

}
