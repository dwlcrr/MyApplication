package com.example.test.utils.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.Html;
import com.example.test.entity.CheckUpdate;
import com.example.test.net.api.AppApi;
import com.example.test.net.callback.DialogCallback;
import com.smm.lib.okgo.model.Response;
import com.smm.lib.updateApp.UpdateDialog;
import java.io.File;

/**
 * 更新工具类
 */
public class UpdateUtil {
    //    String url = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
    //    private static final String downloadUrl = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
    private static String downloadUrl = "https://raw.githubusercontent.com/feicien/android-auto-update/develop/extras/android-auto-update-v1.2.apk";

    private static String tempName, apkName;
    private static File apkFile;
    private static File dir;

    public static void checkUpdate(Activity context) {
        AppApi.check(context, new DialogCallback<CheckUpdate>(context) {
            @Override
            public void onSuccess(Response<CheckUpdate> response) {
                CheckUpdate.DataBean dataBean = response.body().data;
                if (dataBean.update) {
                    show(context, dataBean.desc, downloadUrl, dataBean.version);
                }
            }

            @Override
            public void onError(Response<CheckUpdate> response) {

            }
        });
    }

    public static void show(final Context context, String content, final String downloadUrl, final String apkCode) {

        tempName = "smm_v1.0.apk.temp";
        apkName = "smm_v1.0.apk";
        apkFile = new File(getDir(context), apkName);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(com.smm.lib.R.string.android_auto_update_dialog_title);
        builder.setMessage(Html.fromHtml(content))
                .setPositiveButton(com.smm.lib.R.string.android_auto_update_dialog_btn_download, (
                        dialog, id) -> {
//                    goToDownload(context, downloadUrl, apkCode);
                    //如果已经存在最新的apk 则安装  apkName = "smm_v1.0.apk";
                    String apkTempName = apkName.replace("smm_v", "").replace(".apk", "");
                    if (apkFile.exists() && apkTempName.equals("1.0")) {
                        installAPk(context, apkFile);
                    } else {
                        UpdateDialog updateDialog = new UpdateDialog(context,tempName,apkName,downloadUrl);
                        updateDialog.show();
                    }
                })
                .setNegativeButton(com.smm.lib.R.string.android_auto_update_dialog_btn_cancel,
                        (dialog, id) -> {
                        });
        AlertDialog dialog = builder.create();
        //点击对话框外面,对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void installAPk(Context context, File apkFile) {
        Intent installAPKIntent = getApkInStallIntent(context, apkFile);
        context.startActivity(installAPKIntent);
    }

    public static Intent getApkInStallIntent(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".update.provider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        return intent;
    }

    private static File getDir(Context context) {
        if (dir != null && dir.exists()) {
            return dir;
        }

        dir = new File(context.getExternalCacheDir(), "download");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

}
