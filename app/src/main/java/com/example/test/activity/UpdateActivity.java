package com.example.test.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.entity.CheckUpdate;
import com.example.test.net.api.AppApi;
import com.example.test.net.callback.DialogCallback;
import com.example.test.utils.rx.RxUtils;
import com.smm.lib.okgo.model.Response;
import com.smm.lib.update.DownloadService;
import com.smm.lib.utils.base.FinalConstants;

/**
 * 更新软件
 *
 * @author dongwanlin
 */
public class UpdateActivity extends BaseActivity implements OnClickListener {


    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.update);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        findView(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                testUpdate();
                break;
        }
    }

    private void update() {
        AppApi.checkUpdate().subscribe(RxUtils.subscribeNext(result -> {
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
        AppApi.check(this, new DialogCallback<CheckUpdate>(this) {
            @Override
            public void onSuccess(Response<CheckUpdate> response) {
                CheckUpdate.DataBean dataBean = response.body().data;
                if (dataBean.update) {
                    Toast.makeText(baseActivity, dataBean.desc, Toast.LENGTH_LONG).show();
                    String url = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
                    show(UpdateActivity.this, dataBean.desc, url, dataBean.version);
                }
            }

            @Override
            public void onError(Response<CheckUpdate> response) {

            }
        });
    }

    private void show(final Context context, String content, final String downloadUrl, final String apkCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(com.smm.lib.R.string.android_auto_update_dialog_title);
        builder.setMessage(Html.fromHtml(content))
                .setPositiveButton(com.smm.lib.R.string.android_auto_update_dialog_btn_download, (
                        dialog, id) -> {
                    goToDownload(context, downloadUrl, apkCode);
                })
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