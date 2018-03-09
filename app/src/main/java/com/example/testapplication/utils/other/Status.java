package com.example.testapplication.utils.other;

import  android.support.v4.util.ArrayMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dongwanlin on 2016/11/22.
 */

public class Status {

    private Map<Integer, String> kvMap;

    public Status(KV... kvs) {
        kvMap = new ArrayMap();
        for (KV kv : kvs) {
            kvMap.put(kv.k, kv.v);
        }
    }
    /**取出所有数据*/
    public List<String> getList(){
        List<String> list = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : kvMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }
    public int getKeyByValue(String value) {

        for (Map.Entry<Integer, String> entry : kvMap.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return -1;
//        Set<Map.Entry<Integer, String>> set = kvMap.entrySet();
//        Iterator<Map.Entry<Integer, String>> iterator = set.iterator();
//        while (iterator.hasNext()){
//            Map.Entry<Integer, String> entry =  iterator.next();
//            if(entry.getValue().equals(value)){
//                return  entry.getKey();
//            }
//        }
//        return -1;
    }

    public String get(int status) {
        String result = kvMap.get(status);
        return result == null ? "" : result;
    }

    public static class KV {
        int k;
        String v;

        public KV(int k, String v) {
            this.k = k;
            this.v = v;
        }
    }

    private static KV add(int status, String value) {
        return new KV(status, value);
    }
}
