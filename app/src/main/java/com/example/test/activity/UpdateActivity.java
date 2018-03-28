package com.example.test.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        findView(R.id.btn_update).setOnClickListener(this);
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
                    String url = "https://raw.githubusercontent.com/feicien/android-auto-update/develop/extras/android-auto-update-v1.2.apk";
                    UpdateUtil.show(UpdateActivity.this, data.desc, url, data.version);
                }
            }
        }));
    }

    private static void goToDownload(Context context, String downloadUrl, String apkCode) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(FinalConstants.APK_DOWNLOAD_URL, downloadUrl);
        intent.putExtra(FinalConstants.APK_VERSION_CODE, apkCode);
        context.startService(intent);
    }
}