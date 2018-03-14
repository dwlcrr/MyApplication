package com.example.test.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.entity.CheckUpdate;
import com.example.test.net.center.AppCenter;
import com.example.test.utils.rx.RxUtils;
import com.google.gson.Gson;
import com.smm.lib.update.DownloadService;
import com.smm.lib.utils.base.FinalConstants;

import java.io.IOException;

import okhttp3.Call;

/**
 * 更新软件
 *
 * @author dongwanlin
 */
public class UpdateActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        findView(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                update();
                break;
        }
    }

    private void update() {
        AppCenter.checkUpdate().subscribe(RxUtils.subscribeNext(result -> {
            if (result.code == 0 && result.data != null) {
                CheckUpdate.DataBean data = result.data;
                if (data.update) {
                    String url = "https://raw.githubusercontent.com/feicien/android-auto-update/develop/extras/android-auto-update-v1.2.apk";
                    show(UpdateActivity.this, data.desc, url, data.version);
                }
            }
        }));
    }

    private void testUpdate() {
        AppCenter.check(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) {
                CheckUpdate checkUpdate = new Gson().fromJson(response.body().toString(),CheckUpdate.class);
                if (checkUpdate.data.update) {
                    String url = "https://raw.githubusercontent.com/feicien/android-auto-update/develop/extras/android-auto-update-v1.2.apk";
                    show(UpdateActivity.this, checkUpdate.data.desc, url, checkUpdate.data.version);
                }
            }
        });
    }

    private void show(final Context context, String content, final String downloadUrl, final String apkCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(com.smm.lib.R.string.android_auto_update_dialog_title);
        builder.setMessage(Html.fromHtml(content))
                .setPositiveButton(com.smm.lib.R.string.android_auto_update_dialog_btn_download, (
                        dialog, id) -> goToDownload(context, downloadUrl, apkCode))
                .setNegativeButton(com.smm.lib.R.string.android_auto_update_dialog_btn_cancel,
                        (dialog, id) -> {
                        });
        AlertDialog dialog = builder.create();
        //点击对话框外面,对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private static void goToDownload(Context context, String downloadUrl, String apkCode) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(FinalConstants.APK_DOWNLOAD_URL, downloadUrl);
        intent.putExtra(FinalConstants.APK_VERSION_CODE, apkCode);
        context.startService(intent);
    }
}