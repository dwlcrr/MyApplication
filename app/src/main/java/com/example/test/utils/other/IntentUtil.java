package com.example.test.utils.other;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Pair;
import android.widget.Toast;

import com.example.test.activity.web.WebActivity;
import com.example.test.net.NetConfig;
import com.example.test.utils.base.SpfsUtil;
import com.smm.lib.utils.base.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by guizhen on 2016/12/1.
 */

public class IntentUtil {

    private static final List<Pair<String, String>> overrides = new ArrayList<>();

    static {
        overrides.clear();
        overrides.add(Pair.create("https://m.smm.cn/mall/products/", "smmapp://mall/products?id="));
        overrides.add(Pair.create("https://mall.smm.cn/products/", "smmapp://mall/products?id="));
        overrides.add(Pair.create("https://mall.smm.cn/shops/", "smmapp://mallshop?companyid="));
        if (!NetConfig.online) {
            overrides.add(Pair.create("https://testm.smm.cn/mall/products/", "smmapp://mall/products?id="));
            overrides.add(Pair.create("https://testmall.smm.cn/products/", "smmapp://mall/products?id="));
            overrides.add(Pair.create("https://testmall.smm.cn/shops/", "smmapp://mallshop?companyid="));
        }
    }

    public static Intent getUriIntent(String uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        return intent;
    }

    public static boolean startUriIntentSafe(Context context, String uri) {
        return startUriIntentSafe(context, Uri.parse(uri));
    }

    /**
     * @param context
     * @param uri
     * @return true代表解析成功跳转，false 代表不支持
     */
    public static boolean startUriIntentSafe(Context context, Uri uri) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(uri);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            } else {
                Toast.makeText(context, "当前版本不支持，请升级到最新版本！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Throwable t) {
            return false;
        }

    }

    public static boolean startAd(Context context, String link) {
        if (StrUtil.isEmpty(link)) return true;
        if (link.startsWith("smmapp://")) {
            return startUriIntentSafe(context, link);
        } else {
            WebActivity.runActivity(context,"22",link);
            return true;
        }
    }

    public static void handleQrCode(Context context, String qrCode) {
        String login = "login||";
        if (qrCode.startsWith(login)) {
            if (StrUtil.isNotEmpty(SpfsUtil.USERTOKEN)) {
                qrLogin(context, qrCode.substring(login.length()));
            } else {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (qrCode.startsWith("smmapp://") || qrCode.startsWith("http://") || qrCode.startsWith("https://")) {
            IntentUtil.startAd(context, qrCode);
        } else {
//            PasteActivity.startPaste(context, qrCode);
        }
    }

    private static void qrLogin(Context context, String qrResult) {

    }

    /**
     * @param url
     * @return 如果需要重载，则返回 smmapp 协议，否则返回原url;
     */
    public static String overrideMallUrl(String url) {
        if (StrUtil.isEmpty(url)) return null;
        String result = null;
        for (Pair<String, String> overrideUrl : overrides) {
            if (Pattern.compile(overrideUrl.first + "\\d{1,}").matcher(url).matches()) {
                result = url.replaceFirst(overrideUrl.first, overrideUrl.second);
                break;
            }
        }
        return result;
    }
}
