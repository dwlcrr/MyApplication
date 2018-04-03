package com.example.test.utils.rx.ObsetverUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dongwanlin on 2016/6/2.
 * 观察者工具类
 */


public class NotificationCenter {

    //static reference for singleton
    private static NotificationCenter _instance;
    private static NotificationCenter _instance2;
    private HashMap<String, ArrayList<NotificationDelegate>> registredObjects;

    //default c'tor for singleton
    private NotificationCenter() {
        registredObjects = new HashMap<>();
    }

    //returning the reference
    public static synchronized NotificationCenter defaultCenter() {
        if (_instance == null)
            _instance = new NotificationCenter();
        return _instance;
    }

    //returning the reference
    public static synchronized NotificationCenter defaultCenterNew() {
        if (_instance2 == null)
            _instance2 = new NotificationCenter();
        return _instance2;
    }

    public synchronized void addFucntionForNotification(String notificationName, NotificationDelegate delegate) {
        ArrayList<NotificationDelegate> list = registredObjects.get(notificationName);
        if (list == null) {
            list = new ArrayList<>();
            registredObjects.put(notificationName, list);
        }
        list.add(delegate);
    }

    public synchronized void removeFucntionForNotification(String notificationName, NotificationDelegate r) {
        if (registredObjects == null) {
            return;
        }
        ArrayList<NotificationDelegate> list = registredObjects.get(notificationName);
        if (list != null) {
            list.remove(r);
        }
    }

    public synchronized void postNotificationName(String notificationName, Object obj) {
        ArrayList<NotificationDelegate> list = registredObjects.get(notificationName);
//        LogUtils.showLog("==========","  " + list);
        if (list != null) {
            for (NotificationDelegate r : list) {
                if (r != null)
                    r.update(notificationName, obj);
//                LogUtils.showLog("=========="," upadate ");
            }

        }
    }
//    public synchronized void postNotification(Object obj){
//        for(String name: registredObjects.keySet()){
//            ArrayList<NotificationDelegate> list = registredObjects.get(name);
//            if(list!= null){
//                for(NotificationDelegate r: list)
//                    r.update("",obj);
//            }
//        }
//    }

}
