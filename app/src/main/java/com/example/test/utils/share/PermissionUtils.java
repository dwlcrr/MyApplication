package com.example.test.utils.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import com.example.test.view.myview.dialog.ConfirmDialog;


/**
 * Created by guizhen on 2017/8/31.
 */

public class PermissionUtils {
    public static final String WRITE_EXTERNAL_STORAGE = "存储";


    public static void showSettingDialog(Activity activity, String permissionName) {
        StringBuilder sb = new StringBuilder();
        sb.append("为了您的正常使用，掌上有色需要")
                .append(permissionName)
                .append("权限.");
        ConfirmDialog confirmDialog = new ConfirmDialog(activity, sb.toString());
        confirmDialog.setPositive("去设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }catch (Exception e){
                }

            }
        });
        confirmDialog.setCancel("稍后再说", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });
        confirmDialog.show();
    }

}
