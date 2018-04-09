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
 * 提示框
 *
 * @author dwl
 */
public class ConfirmDialog extends Dialog {
    private TextView tv_sure, tv_cancel, tv_content, tv_title;

    /**
     * @param context
     * @param message 弹窗上面的标题
     */
    public ConfirmDialog(Context context, String message) {
        super(context, R.style.AlertDialog);
        Window window = getWindow();
        WindowManager.LayoutParams lParams = window.getAttributes();
        lParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8f);
        window.setAttributes(lParams);
        setContentView(R.layout.dialog_comfirm);
        init();// 初始化界面
        tv_content.setText(message);
    }

    private void init() {
        tv_sure = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_content = findViewById(R.id.tv_information_comfirm);
        tv_title = findViewById(R.id.tv_title);
    }

    /**
     * 显示 title
     */
    public void setTitileVisible(String title) {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(title);
    }

    public void setCancelColor(int color) {
        tv_cancel.setTextColor(color);
    }

    /**
     * 确定按钮
     */
    public void setPositive(CharSequence str, View.OnClickListener onclick) {
        if (!TextUtils.isEmpty(str)) {
            tv_sure.setText(str);
        }
        tv_sure.setOnClickListener(onclick);
    }

    /**
     * 取消按钮
     */
    public void setCancel(CharSequence str, View.OnClickListener onclick) {
        if (!TextUtils.isEmpty(str)) {
            tv_cancel.setText(str);
        }
        tv_cancel.setOnClickListener(onclick);
    }
}
