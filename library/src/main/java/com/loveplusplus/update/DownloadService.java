package com.loveplusplus.update;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.FileProvider;
import android.util.Log;
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
    private int version;
    private String urlStr;
    private  File dir;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //如果本地找到了此版本的 apk 则直接安装
        version = intent.getIntExtra(UpdateConstants.APK_VERSION_CODE, 0);
        urlStr = intent.getStringExtra(UpdateConstants.APK_DOWNLOAD_URL);
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = apkExist(version);
        if (file != null) {
            installAPk(this, file);
            return;
        }
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);

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
            if (dir != null && dir.exists()) {
                Uri uri = Uri.withAppendedPath(Uri.fromFile(dir), "smm_v".concat(String.valueOf(version)).concat(".apk"));
                try {
                    apkFile = new File(new URI(uri.toString()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (!apkFile.getParentFile().exists()) {
                apkFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(apkFile);
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
            // 下载完成
            installAPk(this, apkFile);
            mNotifyManager.cancel(NOTIFICATION_ID);
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
    private File apkExist(int version) {
        if (dir != null && dir.exists()) {
            Uri uri = Uri.withAppendedPath(Uri.fromFile(dir), "smm_v".concat(String.valueOf(version)).concat(".apk"));
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
        mBuilder.setContentText(this.getString(R.string.android_auto_update_download_progress, progress)).setProgress(100, progress, false);
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
            Uri uri = getApkUri(apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        return intent;
    }


    private Uri getApkUri(File apkFile) {
        Log.d(TAG, apkFile.toString());

        //如果没有设置 SDCard 写权限，或者没有 SDCard,apk 文件保存在内存中，需要授予权限才能安装
        try {
            String[] command = {"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ignored) {
        }
        Uri uri = Uri.fromFile(apkFile);
        Log.d(TAG, uri.toString());

        return uri;
    }

}
