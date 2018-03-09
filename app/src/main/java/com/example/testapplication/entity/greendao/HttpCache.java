package com.example.testapplication.entity.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by guizhen on 2017/5/22.
 */
@Entity
public class HttpCache {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private String url;
    private String response;
    private byte[] responseByte;

    @Generated(hash = 1746119921)
    public HttpCache(Long id, String url, String response, byte[] responseByte) {
        this.id = id;
        this.url = url;
        this.response = response;
        this.responseByte = responseByte;
    }

    @Generated(hash = 462169823)
    public HttpCache() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public byte[] getResponseByte() {
        return this.responseByte;
    }

    public void setResponseByte(byte[] responseByte) {
        this.responseByte = responseByte;
    }
}
