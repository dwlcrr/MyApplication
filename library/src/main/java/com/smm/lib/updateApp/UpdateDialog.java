package com.smm.lib.updateApp;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.smm.lib.R;
import com.smm.lib.updateApp.utils.DebugUtils;
import com.smm.lib.updateApp.utils.Utils_Parse;
import com.smm.lib.utils.base.PhoneUtils;
import com.smm.lib.view.progressBar.NumberProgressBar;
import java.io.File;

/**
 * 更新软件提示框
 *
 * @author dwl
 */
public class UpdateDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView title;
    private Button btn_download;
    private NumberProgressBar numberProgressBar;

    private File tempFile, apkFile;
    private String tempName, apkName;
    private String downloadUrl;

    private static final String FIRST_ACTION = "download_helper_first_action";
    private DownloadHelper mDownloadHelper;
    private File dir;
    private static final String START = "开始";
    private static final String PAUST = "暂停";

    private static int textColor1 = Color.parseColor("#333333");
    private static int textColor2 = Color.parseColor("#666666");
    private static int textColor3 = Color.parseColor("#999999");
    private static int textColorBlock = Color.parseColor("#000000");
    private static int textColorRandarRed = Color.parseColor("#FF0000");
    private static int textColorGreen = Color.parseColor("#46BCFF");

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                switch (intent.getAction()) {
                    case FIRST_ACTION: {
                        FileInfo tempFileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
                        updateTextview(title, numberProgressBar, tempFileInfo, tempName, btn_download);
                    }
                    break;
                    default:
                }
            }
        }
    };

    /**
     * @param context 弹窗上面的标题
     */
    public UpdateDialog(Context context, String tempName, String apkName, String downloadUrl) {
        super(context, R.style.AlertDialog);
        this.setContentView(R.layout.update_dialog);
        this.context = context;
        init();// 初始化界面ne
        Window window = this.getWindow();
        int screenWidth = new PhoneUtils(context).getScreenWidth();

        WindowManager.LayoutParams lParams = window.getAttributes();
        lParams.width = screenWidth / 10 * 9;
        lParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lParams.gravity = Gravity.CENTER;
        lParams.alpha = 0.95f;
        window.setAttributes(lParams);
        this.setCanceledOnTouchOutside(true);
        this.tempName = tempName;
        this.apkName = apkName;
        this.downloadUrl = downloadUrl;
        init();// 初始化界面
    }

    private void init() {
        title = findViewById(R.id.title);
        btn_download = findViewById(R.id.btn_download);
        numberProgressBar = findViewById(R.id.update_progressBar);

        tempFile = new File(getDir(), tempName);
        apkFile = new File(getDir(), apkName);

        mDownloadHelper = DownloadHelper.getInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FIRST_ACTION);
        context.registerReceiver(receiver, filter);
        btn_download.setOnClickListener(this);
    }

    private File getDir() {
        if (dir != null && dir.exists()) {
            return dir;
        }

        dir = new File(context.getExternalCacheDir(), "download");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private void updateTextview(TextView textView, NumberProgressBar progressBar, FileInfo fileInfo, String fileName, Button btn) {
        float pro = (float) (fileInfo.getDownloadLocation() * 1.0 / fileInfo.getSize());
        int progress = (int) (pro * 100);
        float downSize = fileInfo.getDownloadLocation() / 1024.0f / 1024;
        float totalSize = fileInfo.getSize() / 1024.0f / 1024;

        // 我们将字体颜色设置的好看一些而已
        int count = 0;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(fileName);
        sb.setSpan(new ForegroundColorSpan(textColorBlock), 0, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();
        sb.append("\t  ( " + progress + "% )" + "\n");
        sb.setSpan(new ForegroundColorSpan(textColor3), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();
        sb.append("状态:");
        sb.setSpan(new ForegroundColorSpan(textColor2), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();

        sb.append(DebugUtils.getStatusDesc(fileInfo.getDownloadStatus()) + " \t \t\t \t\t\t");
        sb.setSpan(new ForegroundColorSpan(textColorGreen), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();

        sb.append(Utils_Parse.getTwoDecimalsStr(downSize) + "M/" + Utils_Parse.getTwoDecimalsStr(totalSize) + "M\n");
        sb.setSpan(new ForegroundColorSpan(textColorRandarRed), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(sb);
        progressBar.setProgress(progress);

        if (fileInfo.getDownloadStatus() == DownloadStatus.COMPLETE) {
            btn.setText("下载完成");
            btn.setBackgroundColor(0xff5c0d);
            //temp文件名去掉改成apk文件
            tempFile.renameTo(apkFile);
            if (apkFile.exists()) {
                installAPk(context, apkFile);
            }
        }

    }

    private void installAPk(Context context, File apkFile) {
        Intent installAPKIntent = getApkInStallIntent(context, apkFile);
        context.startActivity(installAPKIntent);
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

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_download) {
            //apkName = "smm_v1.0.apk";
            String apkTempName = apkName.replace("smm_v", "").replace(".apk", "");
            if (apkFile.exists() && apkTempName.equals("1.0")) {
                installAPk(context, apkFile);
            } else {
                apkClick();
            }
        }
    }

    private void apkClick() {
        String firstContent = btn_download.getText().toString().trim();
        if (TextUtils.equals(firstContent, START)) {
            mDownloadHelper.addTask(downloadUrl, tempFile, FIRST_ACTION).submit(context);
            btn_download.setText(PAUST);
            btn_download.setBackgroundResource(R.drawable.shape_btn_orangle);

        } else {
            mDownloadHelper.pauseTask(downloadUrl, tempFile, FIRST_ACTION).submit(context);
            btn_download.setText(START);
            btn_download.setBackgroundResource(R.drawable.shape_btn_blue);
        }
    }
}
