package com.example.testapplication.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * Created by guizhen on 2017/11/20.
 */

public class WsLiveMsg {
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_CONNECT_ERROR = 1;
    public static final int TYPE_COMMON = 2;


    public static final String CMD_LOGIN = "login";
    public static final String CMD_MSG = "msg";
    public static final String CMD_ENTER_ROOM = "enter_room";
    public static final String CMD_ROOM_INFO_NOTICE = "room_info_notice";
    public static final String CMD_DEL_NOTICE = "del_notice";
    public static final String CMD_CLOSE_ROOM_NOTICE = "close_room_notice";
    public static final String CMD_SWITCH_CUSTOM_STATUS = "switch_custom_status";

    public int type;
    public String cmd;
    public BaseMsg loginMsg;
    public LiveWsMsg liveMsg;
    public EnterRoomMsg enterRoomMsg;
    public RoomInfoNoticeMsg roomInfoNoticeMsg;
    public DelNoticeMsg delNoticeMsg;
    public CloseNoticeMsg closeNoticeMsg;
    public SwitchCustomStatusMsg switchCustomStatusMsg;

    public WsLiveMsg(int type) {
        this.type = type;
    }

    public static WsLiveMsg fromJson(String json) {
        WsLiveMsg wsLiveMsg = new WsLiveMsg(TYPE_COMMON);
        try {
            Gson gson = new Gson();
            String cmd = new JsonParser().parse(json).getAsJsonObject().get("cmd").getAsString();
            wsLiveMsg.cmd = cmd;
            JsonObject data = new JsonParser().parse(json).getAsJsonObject().get("data").getAsJsonObject();
            switch (cmd) {
                case CMD_LOGIN:
                    wsLiveMsg.loginMsg = gson.fromJson(data, new TypeToken<BaseMsg>() {
                    }.getType());
                    break;
                case CMD_ENTER_ROOM:
                    wsLiveMsg.enterRoomMsg = gson.fromJson(data, new TypeToken<EnterRoomMsg>() {
                    }.getType());
                    break;
                case CMD_MSG:
                    wsLiveMsg.liveMsg = gson.fromJson(data, new TypeToken<LiveWsMsg>() {
                    }.getType());
                    break;
                case CMD_DEL_NOTICE:
                    wsLiveMsg.delNoticeMsg = gson.fromJson(data, new TypeToken<DelNoticeMsg>() {
                    }.getType());
                    break;
                case CMD_CLOSE_ROOM_NOTICE:
                    wsLiveMsg.closeNoticeMsg = gson.fromJson(data, new TypeToken<CloseNoticeMsg>() {
                    }.getType());
                    break;
                case CMD_ROOM_INFO_NOTICE:
                    wsLiveMsg.roomInfoNoticeMsg = gson.fromJson(data, new TypeToken<RoomInfoNoticeMsg>() {
                    }.getType());
                    break;
                case CMD_SWITCH_CUSTOM_STATUS:
                    wsLiveMsg.switchCustomStatusMsg = gson.fromJson(data, new TypeToken<SwitchCustomStatusMsg>() {
                    }.getType());
                    break;
            }
        } catch (Exception e) {
            wsLiveMsg.type = TYPE_UNKNOWN;
        }
        return wsLiveMsg;
    }

    public static class BaseMsg {
        public int code;
        public String msg;
    }

    public static class EnterRoomMsg extends BaseMsg{

        /**
         * live_room_info : {"live_room_id":154,"live_room_auth":0,"live_room_name":"anranceshi","online_count":1}
         */

        public LiveRoomInfoBean live_room_info;

        public static class LiveRoomInfoBean {
            /**
             * live_room_id : 154
             * live_room_auth : 0
             * live_room_name : anranceshi
             * online_count : 1
             */

            public int live_room_id;
            public int live_room_auth;
            public String live_room_name;
            public int online_count;
        }
    }

    public static class LiveWsMsg {
        public MsgHead msg_head;
        public String msg_body;

        public class MsgHead {
            public int uid;
            public int msg_id;
            public int msg_type;
            public int sub_type;
            public long live_room_id;
            public long msg_time;
            public String local_id;
            public int receiver_id;
        }
    }

    public static class RoomInfoNoticeMsg {
        public long live_room_id;
        public int online_cout;
        public int max_online_count;
    }

    public static class DelNoticeMsg {
        public long operation_time;
        public long origin_msg_id;
    }

    public static class CloseNoticeMsg {
        public long operation_time;
        public long live_room_id;
    }

    public static class SwitchCustomStatusMsg {
        public long operation_time;
        public long live_room_id;
        public int custom_status; //1:直播中  2休息中
    }

}
