package com.example.test.view.myview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.smm.lib.utils.base.PhoneUtils;

/**
 * 保存图片对话框
 * @author dwl
 */
public class SavePhotoDialog extends Dialog implements View.OnClickListener {

	private TextView tv_savePhoto,tv_cancel;
	private OnNameCListener OnNameCListener;

	public SavePhotoDialog(BaseActivity context) {
		super(context, R.style.ShareDialog);
		Window window   = this.getWindow();
		int screenWidth = new PhoneUtils(context).getScreenWidth();

		WindowManager.LayoutParams lParams = window.getAttributes();
		lParams.width  = screenWidth;
		lParams.height = lParams.WRAP_CONTENT;
		lParams.gravity = Gravity.BOTTOM;
		lParams.alpha = 0.95f;
		window.setAttributes(lParams);
		this.setCanceledOnTouchOutside(true);// 点击非有效区域隐藏
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_save_photo);
		tv_cancel = findViewById(R.id.tv_cancel);
		tv_savePhoto = findViewById(R.id.tv_savePhoto);
		tv_cancel.setOnClickListener(this);
		tv_savePhoto.setOnClickListener(this);
	}

	/**
	 * 回调接口
	 * @author Administrator
	 */
	public interface OnNameCListener {
		 void onClick(String name);
	}
	public void setNamekListener(OnNameCListener OnNameCListener) {
		this.OnNameCListener = OnNameCListener;
	}

	@Override
	public void onClick(View v) {
		if (v == tv_savePhoto) {
			if (OnNameCListener != null) {
				OnNameCListener.onClick("保存到手机");
			}
		}
		dismiss();
	}

}