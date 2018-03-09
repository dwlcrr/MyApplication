package com.loveplusplus.update.net;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/8/9
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class BaseBodyRequest<R extends BaseBodyRequest> extends BaseRequest<R> {

    protected RequestBody requestBody;
    protected Map<String, String> bodyParams;
    protected Map<String, File> fileParams;

    public BaseBodyRequest(String url) {
        super(url);
    }


    public R requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return (R) this;
    }

    public R bodyParams(Map<String, String> bodyParams) {
        this.bodyParams = bodyParams;
        return (R) this;
    }

    public R addBodyParams(String key, String value) {
        if (bodyParams == null) bodyParams = new LinkedHashMap<>();
        bodyParams.put(key, value);
        return (R) this;
    }

    public R addFileParams(String key, File file) {
        if (fileParams == null) fileParams = new LinkedHashMap<>();
        fileParams.put(key, file);
        return (R) this;
    }

    @Override
    public RequestBody generateRequestBody() {
        if (requestBody != null)
            return requestBody;                                                //自定义的请求体
        return HttpUtils.generateFormRequestBody(bodyParams,fileParams);
    }
}