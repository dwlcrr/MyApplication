package com.example.testapplication.entity.greendao;

import android.database.sqlite.SQLiteDatabase;
import com.example.testapplication.base.MyApplication;

/**
 * Created by guizhen on 2017/5/22.
 */

public class SmmDbHelper {
    private static final String SMM_HTTP_CACHE_DB = "smm_http_cache.db";
    private static SmmDbHelper ins;
    private DaoSession session;
    private HttpCacheDao mHttpCacheDao;

    public static SmmDbHelper ins() {
        if (ins == null) ins = new SmmDbHelper();
        return ins;
    }

    private SmmDbHelper() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), SMM_HTTP_CACHE_DB);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        session = daoMaster.newSession();
        mHttpCacheDao = session.getHttpCacheDao();
    }

    public void addHttpCache(String url, String response) {
        HttpCache httpCache = new HttpCache();
        httpCache.setUrl(url);
        httpCache.setResponse(response);
        mHttpCacheDao.insertOrReplace(httpCache);
    }

    public void addHttpCache(HttpCache httpCache) {
        mHttpCacheDao.insertOrReplace(httpCache);
    }

    public HttpCache queryHttpCache(String url) {
        return mHttpCacheDao.queryBuilder()
                .where(HttpCacheDao.Properties.Url.eq(url))
                .limit(1).unique();
    }
}
