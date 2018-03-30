package com.example.test.utils.base;

import com.example.test.net.NetConfig;

/**
 * 常量类
 */
public class FinalConstant {

    public final static int PAGE_SIZE = 10;

    public final static String Mallcenter_status = "2,3";

    public final static int[] MESSAGE_TYPE = {1, 2, 3, 4};

    public static final String DOWNLOAD_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.smm";
    public static final int Dialog_Maxsize = 16;
    public static final int Dialog_Minsize = 13;

    // 提示语
    public static final String NO_RESPONSE = "暂无信息";
    public static final String NO_SEARCH = "暂无相关搜索结果";
    public static final String NO_SELLQUOTATION = "暂无产品";
    public static final String NO_ORDER = "你还没有相关订单";
    public static final String NO_PUBLISHLIST = "此项分类暂时没有信息,请查看其它分类";
    public static final String NO_MESSAGE = "你暂时还没有消息";
    public static final String NO_PHOTO = "该商家还未上传相册";
    public static final String NO_QUOTATION = "暂无卖盘信息,如有需求,\n可联系“撮合”或“卖家”帮你找货";
    public static final String NO_INTRODUCE = "该商家还未填写企业简介";

    public static final String NO_CARDLIST = "你还没有保存的名片,快去保存名片吧";

    public static final String PIC_VERCODE_URL =
            NetConfig.online ? "https://captcha.smm.cn/showimage?key=" : "https://testcaptcha.smm.cn/showimage?key=";

    //客服热线
    public static String BOSS_SHARE_URL = NetConfig.online ? "https://boss.smm.cn/posts/" : "https://testboss.smm.cn/posts/";

    public static final String LAST_PAGE = "亲,已经是最后一页了";

    public static String SMALL_IMAGE = "?imageView2/3/w/300/h/300";
    //客服热线
    public static String KEFU_PHONE = "021-31330333";
    //商城热线
    public static String SHOP_PHONE = "021-51666777";

    //商城开市时间
    public static String MALL_OPEN_TIME = "9:00";
    //商城闭市时间
    public static String MALL_CLOSE_TIME = "17:00";

    public static String JR_URL = NetConfig.online ? "https://jr.smm.cn/activity/jrwh52" : "https://testjr.smm.cn/activity/jrwh52";

    public static String CUS_TEL = "021-51595961";

    public static String HELP_URL = NetConfig.online ? "https://question.smm.cn" : "https://testquestion.smm.cn";

    public static String MONEY_URL = NetConfig.online ? "https://m.smm.cn/money" : "https://testm.smm.cn/money";

    public static String NEWS_SET_URL = NetConfig.online ? "https://news.smm.cn/topic/" : "https://testnews.smm.cn/topic/";


}
