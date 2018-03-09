package com.loveplusplus.update.net;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by guizhen on 2017/5/18.
 */

public class HttpUtils {
    /**
     * 将传递进来的参数拼接成 url
     */
    public static String createUrlFromParams(String url, Map<String, String> params) {
        if (params == null || params.size() == 0) return url;
        if (params != null && params.size() > 0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
                else sb.append("?");
                for (Map.Entry<String, String> urlParams : params.entrySet()) {
                    //对参数进行 utf-8 编码,防止头信息传中文
                    String urlValue = URLEncoder.encode(urlParams.getValue(), "UTF-8");
                    sb.append(urlParams.getKey()).append("=").append(urlValue).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            } catch (UnsupportedEncodingException e) {
            }
        }
        return url;
    }

    /**
     * 通用的拼接请求头
     */
    public static Headers createHeaders(Map<String, String> headers) {
        //添加 commonHeaders;
        Map<String, String> commonHeaders = SmmNet.ins().getCommonHeaders();
        if (commonHeaders != null) {
            if (headers == null) headers = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : commonHeaders.entrySet()) {
                headers.put(entry.getKey(), entry.getValue());
            }
        }
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null && headers.size() > 0) {
            try {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    //对头信息进行 utf-8 编码,防止头信息传中文,这里暂时不编码,可能出现未知问题,如有需要自行编码
//                String headerValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                    headerBuilder.add(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
            }
        }
        return headerBuilder.build();
    }

    /**
     * 生成类似表单的请求体
     */
    public static RequestBody generateFormRequestBody(Map<String, String> bodyParams, Map<String, File> fileParams) {
        if (fileParams == null || fileParams.isEmpty()) {
            //表单提交，没有文件
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            if (bodyParams != null && bodyParams.size() > 0) {
                for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                    bodyBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            return bodyBuilder.build();
        } else {
            //表单提交，有文件
            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //拼接键值对
            if (bodyParams != null && bodyParams.size() > 0) {
                for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                    multipartBodybuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
            //拼接文件
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), entry.getValue());
                multipartBodybuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(), fileBody);
            }
            return multipartBodybuilder.build();
        }


    }
}
