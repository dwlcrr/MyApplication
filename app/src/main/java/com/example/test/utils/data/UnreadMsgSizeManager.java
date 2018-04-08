package com.example.test.utils.data;

import android.support.v4.util.ArrayMap;
import com.example.test.net.api.MessApi;
import java.util.Map;
import rx.Observable;

/**
 * Created by guizhen on 2016/10/20.
 */

public class UnreadMsgSizeManager extends DataManager<Map<Integer, Integer>> {

    public static final int MSG_TYPE_MALL = 1;
    public static final int MSG_TYPE_BUSINESS = 2;
    public static final int MSG_TYPE_SYS = 3;
    public static final int MSG_TYPE_WAREHOUSE = 4;
    public static final int MSG_TYPE_ZXKD = 5;
    public static final int MSG_TYPE_BOSS = 6;

    private Map<Integer, Integer> map;

    private static UnreadMsgSizeManager ins;

    public static UnreadMsgSizeManager INS() {
        if (ins == null) ins = new UnreadMsgSizeManager();
        return ins;
    }

    public UnreadMsgSizeManager() {
        map = new ArrayMap<>();
    }

    @Override
    protected Observable<Map<Integer, Integer>> getData() {
        //1商城 2交易 3系统 4电子仓单
        return MessApi.getUnreadMsgSize("1,2,3,4,5,6")
                .filter(readSizeResult -> readSizeResult != null && readSizeResult.code == 0 && readSizeResult.data != null)
                .map(readSizeResult -> {
                    map.clear();
                    for (int i = 0; i < readSizeResult.data.size(); i++) {
                        map.put(readSizeResult.data.get(i).msg_type, readSizeResult.data.get(i).unread_amount);
                    }
                    return map;
                });
    }

    public void clear() {
        map.clear();
        set(map);
    }

    public int getCountByMsgtype(int msgtype) {
        Integer i = map.get(msgtype);
        return i == null ? 0 : i;
    }

    //资讯快递+boss
    public int get56Unread() {
        return getCountByMsgtype(MSG_TYPE_ZXKD) + getCountByMsgtype(MSG_TYPE_BOSS);
    }

    /**
     * webSocket 如果有消息 添加
     * @param msgtype
     */
    public void addOneMsg(int msgtype) {
        Integer i = map.get(msgtype);
        if (i == null)
            i = 1;
        else
            i++;
        map.put(msgtype, i);
        set(map);
    }

}
