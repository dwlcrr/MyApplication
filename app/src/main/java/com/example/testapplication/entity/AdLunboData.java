package com.example.testapplication.entity;

public class AdLunboData {
    /**
     * pic_url : http://xxx.png
     * name : 广告名字
     * link_url : http://xxx.com?xxx=1
     */

    private long id;
    private String pic_url;

    public AdLunboData(long id, String pic_url, String name, String link_url) {
        this.id = id;
        this.pic_url = pic_url;
        this.name = name;
        this.link_url = link_url;
    }

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String link_url;

    public AdLunboData() {
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
}
