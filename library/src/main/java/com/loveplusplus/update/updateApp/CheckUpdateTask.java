package com.loveplusplus.update.updateApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.loveplusplus.update.DownloadService;
import com.loveplusplus.update.HttpUtils;
import com.loveplusplus.update.R;
import com.loveplusplus.update.UpdateConstants;
import com.loveplusplus.update.updateApp.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2016-07-05 19:21
 */
public class CheckUpdateTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;
    private Context mContext;
    private int mType;
    private boolean mShowProgressDialog;
    private static final String url = UpdateConstants.UPDATE_URL;

    public CheckUpdateTask(Context context, int type, boolean showProgressDialog) {

        this.mContext = context;
        this.mType = type;
        this.mShowProgressDialog = showProgressDialog;

    }

    @Override
    protected void onPreExecute() {
        if (mShowProgressDialog) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
            dialog.show();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (!TextUtils.isEmpty(result)) {
            parseJson(result);
        }
    }

    private void parseJson(String result) {
        try {

            JSONObject obj = new JSONObject(result);
            String updateMessage = obj.getString(UpdateConstants.APK_UPDATE_CONTENT);
            String apkUrl = obj.getString(UpdateConstants.APK_DOWNLOAD_URL);
            int apkCode = obj.getInt(UpdateConstants.APK_VERSION_CODE);

            int versionCode = AppUtils.getVersionCode(mContext);

            if (apkCode > versionCode) {
                if (mType == UpdateConstants.TYPE_NOTIFICATION) {
                    showNotification(mContext, updateMessage, apkUrl, apkCode);
                } else if (mType == UpdateConstants.TYPE_DIALOG) {
                    showDialog(mContext, updateMessage, apkUrl, apkCode);
                }
            } else if (mShowProgressDialog) {
                Toast.makeText(mContext, mContext.getString(R.string.android_auto_update_toast_no_new_update), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e(UpdateConstants.TAG, "parse json error");
        }
    }

    /**
     * Show dialog
     */
    private void showDialog(Context context, String content, String apkUrl ,int apkCode) {
        show(context, content, apkUrl, apkCode);
    }

    /**
     * Show Notification
     */
    private void showNotification(Context context, String content, String apkUrl, int apkCode) {
        Intent myIntent = new Intent(context, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(UpdateConstants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = context.getApplicationInfo().icon;
        Notification notify = new NotificationCompat.Builder(context)
                .setTicker(context.getString(R.string.android_auto_update_notify_ticker))
                .setContentTitle(context.getString(R.string.android_auto_update_notify_content))
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        notify.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notify);
    }

    @Override
    protected String doInBackground(Void... args) {
        return HttpUtils.get(url);
    }

    private void show(final Context context, String content, final String downloadUrl, final int apkCode) {
        if (isContextValid(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.android_auto_update_dialog_title);
            builder.setMessage(Html.fromHtml(content))
                    .setPositiveButton(R.string.android_auto_update_dialog_btn_download, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            goToDownload(context, downloadUrl, apkCode);
                        }
                    })
                    .setNegativeButton(R.string.android_auto_update_dialog_btn_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog dialog = builder.create();
            //点击对话框外面,对话框不消失
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }

    private static void goToDownload(Context context, String downloadUrl,int apkCode) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(UpdateConstants.APK_DOWNLOAD_URL, downloadUrl);
        intent.putExtra(UpdateConstants.APK_VERSION_CODE, apkCode);
        context.startService(intent);
    }
}
