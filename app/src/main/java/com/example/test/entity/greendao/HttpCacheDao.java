package com.example.test.entity.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "HTTP_CACHE".
*/
public class HttpCacheDao extends AbstractDao<HttpCache, Long> {

    public static final String TABLENAME = "HTTP_CACHE";

    /**
     * Properties of entity HttpCache.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property Response = new Property(2, String.class, "response", false, "RESPONSE");
        public final static Property ResponseByte = new Property(3, byte[].class, "responseByte", false, "RESPONSE_BYTE");
    }


    public HttpCacheDao(DaoConfig config) {
        super(config);
    }
    
    public HttpCacheDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HTTP_CACHE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"URL\" TEXT," + // 1: url
                "\"RESPONSE\" TEXT," + // 2: response
                "\"RESPONSE_BYTE\" BLOB);"); // 3: responseByte
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_HTTP_CACHE_URL ON \"HTTP_CACHE\"" +
                " (\"URL\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HTTP_CACHE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HttpCache entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        String response = entity.getResponse();
        if (response != null) {
            stmt.bindString(3, response);
        }
 
        byte[] responseByte = entity.getResponseByte();
        if (responseByte != null) {
            stmt.bindBlob(4, responseByte);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HttpCache entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        String response = entity.getResponse();
        if (response != null) {
            stmt.bindString(3, response);
        }
 
        byte[] responseByte = entity.getResponseByte();
        if (responseByte != null) {
            stmt.bindBlob(4, responseByte);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public HttpCache readEntity(Cursor cursor, int offset) {
        HttpCache entity = new HttpCache( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // response
            cursor.isNull(offset + 3) ? null : cursor.getBlob(offset + 3) // responseByte
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HttpCache entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setResponse(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setResponseByte(cursor.isNull(offset + 3) ? null : cursor.getBlob(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HttpCache entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HttpCache entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HttpCache entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}