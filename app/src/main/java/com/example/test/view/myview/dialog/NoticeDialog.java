package com.example.test.view.myview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.test.R;

/**
 * 单个提醒提示框
 *
 * @author dwl
 */
public class NoticeDialog extends Dialog {
    private TextView tv_submit, tv_content;

    /**
     * @param context
     * @param content 弹窗上面的标题
     */
    public NoticeDialog(Context context, String content) {
        super(context, R.style.AlertDialog);
        setContentView(R.layout.dialog_notice);
        Window window = getWindow();
        WindowManager.LayoutParams lParams = window.getAttributes();
        lParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8f);
        window.setAttributes(lParams);
        init(content);// 初始化界面
    }

    private void init(String content) {
        tv_submit = findViewById(R.id.tv_submit);
        tv_content = findViewById(R.id.tv_content);
        tv_content.setText(content);
    }

    /**
     * 确定按钮
     */
    public void setPositive(CharSequence str, View.OnClickListener onclick) {
        if (!TextUtils.isEmpty(str)) {
            tv_submit.setText(str);
        }
        tv_submit.setOnClickListener(onclick);
    }
}
