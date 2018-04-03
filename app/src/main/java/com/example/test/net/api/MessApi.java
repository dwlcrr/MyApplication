package com.example.test.net.api;

import com.example.test.entity.UnReadSizeResult;
import com.example.test.net.NetConfig;
import com.smm.lib.net.SmmNet;
import rx.Observable;

/**
 * 消息文档
 * https://github.com/smmit/design/blob/master/api_docs/user_center接口文档.md
 * Created by dwl on 2017/5/19.
 */

public class MessApi extends BaseRxApi {
    private static final String messUrl = NetConfig.baseUrl + "/msgcenter";

    /**
     * 获取消息未读数
     *
     * @return
     */
    public static Observable<UnReadSizeResult> getUnreadMsgSize(String msg_type) {
        return request(
                SmmNet.ins().get(messUrl + "/unread_count_filter?msg_type=" + msg_type),
                UnReadSizeResult.class);
    }
}
