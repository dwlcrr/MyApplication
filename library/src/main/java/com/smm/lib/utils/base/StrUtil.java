package com.smm.lib.utils.base;

import android.content.Context;

import com.smm.lib.updateApp.utils.ToastUtils;

import java.util.List;

/**
 * 字符串工具类
 * Created by guizhen on 16/9/19.
 */
public class StrUtil {

    /**
     * 字符串为null，或长度为0
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Context context,CharSequence cs,String title) {
        boolean isEmpty = cs == null || cs.length() == 0;
        if(isEmpty){
            ToastUtils.showToast(context,title + "不能为空");
        }
        return isEmpty;
    }
    /**
     * 字符串不为 null,长度也不为0
     *
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 逗号分隔 list
     *
     * @param list
     * @return
     */
    public static String listToStr(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (String str : list) {
                sb.append(str);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
