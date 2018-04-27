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
import com.example.test.net.api.AppApi;
import com.example.test.utils.base.UpdateUtil;
import com.example.test.utils.rx.RxUtils;
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
        findViewById(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                UpdateUtil.checkUpdate(this);
                break;
        }
    }

    private void update() {
        AppApi.checkUpdate().subscribe(RxUtils.subscribeNext(result -> {
            if (result.code == 0 && result.data != null) {
                CheckUpdate.DataBean data = result.data;
                if (data.update) {
                    showDialog(data.desc, data.link, data.version);
                }
            }
        }));
    }

    private void showDialog(String desc, String downloadUrl, String apkCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(com.smm.lib.R.string.update_dialog_title);
        builder.setMessage(Html.fromHtml(desc))
                .setPositiveButton(R.string.update_btn_download, (
                        dialog, id) -> goToDownload(this, downloadUrl, apkCode))
                .setNegativeButton(R.string.update_btn_cancel,
                        (dialog, id) -> {});
        AlertDialog dialog = builder.create();
        //点击对话框
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