package com.example.test.net.webSocket;

import android.os.Handler;

import com.example.test.base.MyApplication;
import com.example.test.base.MySp;
import com.example.test.entity.WsLiveMsg;
import com.example.test.net.NetConfig;
import com.google.gson.Gson;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.smm.lib.utils.base.Logger;
import com.smm.lib.utils.base.NetWorkUtils;
import com.smm.lib.utils.base.StrUtil;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * websocket 管理类
 * Created by guizhen on 16/9/19.
 */
public class WebSocketManager {

    public static final int WEBSCOKET_LIVE = 1;
    private static final int DELAY_TIME = 10 * 1000;
    private static WebSocketManager INS;
    private String liveUrl;
    private Handler handler;
    private WebSocket websocketLive;
    private boolean isQuoConnecting = false;
    private boolean isLiveConnecting = false;
    private PublishSubject<WsLiveMsg> liveObserver;
    private Gson gson;

    private WebSocketManager() {
        init();
    }

    public static WebSocketManager ins() {
        if (INS == null) INS = new WebSocketManager();
        return INS;
    }

    private void init() {
        liveObserver = PublishSubject.create();
        handler = new Handler();
        gson = new Gson();
    }

    private Runnable reconnectLive = new ReconnectTask(WEBSCOKET_LIVE);

    private void connect(int type) {
        //type==1,报价直播
        if (type == WEBSCOKET_LIVE) {
            if (isLiveActive()) return;
            if (isLiveConnecting) return;
            isLiveConnecting = true;
            AsyncHttpClient.getDefaultInstance().websocket(NetConfig.LIVEWS, null, connectLive);
        }
    }


    public void tryConnectLive() {
        if (!isLiveActive()) connect(WEBSCOKET_LIVE);
    }


    private AsyncHttpClient.WebSocketConnectCallback connectLive = new AsyncHttpClient.WebSocketConnectCallback() {
        @Override
        public void onCompleted(Exception ex, WebSocket webSocket) {
            isLiveConnecting = false;
            if (ex != null || webSocket == null || !webSocket.isOpen()) {
                Logger.info("报价直播  websocket  链接失败----");
                handler.postDelayed(reconnectLive, DELAY_TIME);
                liveObserver.onNext(new WsLiveMsg(WsLiveMsg.TYPE_CONNECT_ERROR));
                return;
            }
            Logger.info("websocket== " + "报价直播连接成功");
            websocketLive = webSocket;
            websocketLive.setStringCallback(new WebSocket.StringCallback() {
                @Override
                public void onStringAvailable(String msg) {
                    if (StrUtil.isNotEmpty(msg)) {
                        Logger.info("报价直播 收到  websocket== " + msg);
                        liveObserver.onNext(WsLiveMsg.fromJson(msg));
                    }
                }
            });
            websocketLive.setClosedCallback(new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    Logger.info("报价直播 setClosedCallback  websocket== ");
                    if (ex != null) {
                        handler.postDelayed(reconnectLive, DELAY_TIME);
                    }
                }
            });
            websocketLive.setEndCallback(new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    Logger.info("报价直播 setEndCallback  websocket== ");
                    if (ex != null) {
                        handler.postDelayed(reconnectLive, DELAY_TIME);
                    }
                }
            });
            sendLive("{\"cmd\":\"login\",\"data\":{\"token\":\"" + MySp.token + "\",\"client_type\":\"app\",\"device_token\":\"" + MySp.uuid + "\"}}");
        }
    };


    /**
     * 给报价直播 websocket 发送消息
     *
     * @param text
     */
    public void sendLive(String text) {
        if (isLiveActive()) {
            websocketLive.send(text);
            Logger.info("报价直播 发送  websocket== " + text);
        }
    }

    /**
     * 报价直播websocket是否正常连接
     *
     * @return
     */
    public boolean isLiveActive() {
        return websocketLive != null && websocketLive.isOpen();
    }

    /**
     * 断开连接,并置为空
     */
    public void destroy() {
        handler.removeCallbacksAndMessages(null);
        logoutLive();
        INS = null;
    }

    /**
     * 登出报价直播 websocket 并断开
     */
    public void logoutLive() {
        if (isLiveActive()) {
            websocketLive.close();
            websocketLive = null;
            handler.removeCallbacks(reconnectLive);
        }
    }

    public Observable<WsLiveMsg> observerLive() {
        return liveObserver;
    }


    private class ReconnectTask implements Runnable {

        private int type;

        public ReconnectTask(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            if (NetWorkUtils.checkNetState(MyApplication.ins().getApplicationContext()))
                connect(type);
            else
                handler.postDelayed(new ReconnectTask(type), DELAY_TIME);
        }
    }


}
