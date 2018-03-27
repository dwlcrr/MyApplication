package com.example.test.entity;


import android.support.v4.util.ArrayMap;

import com.example.test.entity.base.BaseEntity;
import com.google.gson.Gson;
import com.smm.lib.utils.base.StrUtil;
import java.util.List;
import java.util.Map;

/**
 * Created by guizhen on 2016/10/12.
 */

public class AdList extends BaseEntity {
    public List<Ad> data;

    public static class Ad {
        public static final String LINK_TYPE_SMMAPP = "smmapp";
        public static final String LINK_TYPE_WEBURL = "weburl";
        public long id;
        public String name;
        public String pic_url;
        public String description;
        public String title;
        public String link_url;
        public String link_type;
        public String link_addr;
        public String link_value;//smmapp，IntentUtil.startAd(context, link_value)

        public LinkAddr getLinkAddr() {
            LinkAddr linkAddr = null;
            try {
                if (StrUtil.isNotEmpty(link_addr)) {
                    linkAddr = new Gson().fromJson(link_addr, LinkAddr.class);
                }
            } catch (Throwable t) {

            }
            return linkAddr;
        }


    }

    public class LinkAddr {
        public static final String SKIP_TYPE_MALLSHOP = "mallshop";
        public String skip_type;
        public List<KV> params;

        public Map<String, String> getParamsMap() {
            Map<String, String> map = new ArrayMap<>();
            for (KV kv : params) {
                if (StrUtil.isNotEmpty(kv.k))
                    map.put(kv.k, kv.v);
            }
            return map;
        }
    }

    public class KV {
        public String k;
        public String v;
    }


}
