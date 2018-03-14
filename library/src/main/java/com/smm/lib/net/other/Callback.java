
package com.smm.lib.net.other;


public interface Callback {

    /** 对返回数据进行操作的回调， UI线程 */
    void onSuccess(Response response);

    /** 缓存成功的回调,UI线程 */
    void onCacheSuccess(Response response);

    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    void onError(Response<Response> response);
}
