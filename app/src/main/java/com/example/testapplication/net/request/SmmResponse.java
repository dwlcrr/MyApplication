package com.example.testapplication.net.request;

/**
 * Created by guizhen on 2017/5/18.
 */

public class SmmResponse<T> {
    public boolean isCache;
    public T result;

    public SmmResponse(T result) {
        this(false, result);
    }

    public SmmResponse(boolean isCache, T result) {
        this.isCache = isCache;
        this.result = result;
    }

    public static <T> SmmResponse<T> create(T result) {
        return new SmmResponse<T>(result);
    }

    public static <T> SmmResponse<T> create(boolean isCache, T result) {
        return new SmmResponse<T>(isCache, result);
    }
}
