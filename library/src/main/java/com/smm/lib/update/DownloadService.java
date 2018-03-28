package com.smm.lib.update;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.FileProvider;
import android.util.Log;
import com.smm.lib.R;
import com.smm.lib.utils.base.FinalConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class DownloadService extends IntentService {

    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
    private static final String TAG = "DownloadService";
    private static final int NOTIFICATION_ID = 0;

    private NotificationManager mNotifyManager;
    private Builder mBuilder;
    private String version;
    private String urlStr;
    private File dir;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //如果本地找到了此版本的 apk 则直接安装
        version = intent.getStringExtra(FinalConstants.APK_VERSION_CODE);
        urlStr = intent.getStringExtra(FinalConstants.APK_DOWNLOAD_URL);
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = apkExist(version);
        if (file != null && file.exists() && file.length()!=0) {
            installAPk(this, file);
            return;
        }
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Builder(this);

        String appName = getString(getApplicationInfo().labelRes);
        int icon = getApplicationInfo().icon;

        mBuilder.setContentTitle(appName).setSmallIcon(icon);

        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(false);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");

            urlConnection.connect();
            long bytetotal = urlConnection.getContentLength();
            long bytesum = 0;
            int byteread = 0;

            in = urlConnection.getInputStream();
            File apkFile = null;
            File tmpFile = null;
            if (dir != null && dir.exists()) {
                Uri uri = Uri.withAppendedPath(Uri.fromFile(dir), "smm_v".concat(version).concat(".apk"));
                Uri tmpUri = Uri.withAppendedPath(Uri.fromFile(dir), "smm_v".concat(version).concat(".apk.tmp"));
                try {
                    apkFile = new File(new URI(uri.toString()));
                    if(!apkFile.exists()){
                        apkFile.createNewFile();
                    }
                    tmpFile = new File(new URI(tmpUri.toString()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (!tmpFile.getParentFile().exists()) {
                tmpFile.getParentFile().mkdirs();
            }
            if (!apkFile.getParentFile().exists()) {
                apkFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(tmpFile);
            byte[] buffer = new byte[BUFFER_SIZE];

            int oldProgress = 0;

            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);

                int progress = (int) (bytesum * 100L / bytetotal);
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                if (progress != oldProgress) {
                    updateProgress(progress);
                }
                oldProgress = progress;
            }
            // 如果下载完成了 改文件名后缀为.apk
            if(bytesum == bytetotal){
                tmpFile.renameTo(apkFile);
                installAPk(this, apkFile);
                mNotifyManager.cancel(NOTIFICATION_ID);
            }
        } catch (Exception e) {
            Log.e(TAG, "download apk file error:" + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 判断 apk 是否 缓存在本地
     *
     * @param version
     */
    private File apkExist(String version) {
        if (dir != null && dir.exists()) {
            Uri uri = Uri.withAppendedPath(Uri.fromFile(dir), "smm_v".concat(version).concat(".apk"));
            if (uri != null) {
                try {
                    File file = new File(new URI(uri.toString()));
                    if (file != null && file.exists()) {
                        return file;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void updateProgress(int progress) {
        //"正在下载:" + progress + "%"
        mBuilder.setContentText(this.getString(R.string.update_download_progress, progress)).setProgress(100, progress, false);
        //setContentInent如果不设置在4.0+上没有问题，在4.0以下会报异常
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingintent);
        mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    private void installAPk(Context context, File apkFile) {
        Intent installAPKIntent = getApkInStallIntent(context, apkFile);
        startActivity(installAPKIntent);
    }

    private Intent getApkInStallIntent(Context context, File apkFile) {
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

}
