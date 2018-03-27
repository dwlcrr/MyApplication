package com.example.test.view.myview.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import com.example.test.R;
import java.util.List;

/**
 * 资讯选择品目
 * Created by dongwanlin on 2017/1/17.
 */

public class InfoPop extends PopupWindow {

    private View conentView;
    private Context mContext;
    public WindowManager.LayoutParams lParams;
    private OnNameCListener OnNameCListener;
    private GridView gv;
    private List<String> type_list;

    public InfoPop(final Context context, List<String> type_list) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.item_album_folder_list, null);
        mContext = context;
        setSize();
        this.type_list = type_list;
        initView();
        setListener();
    }

    private void setListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnNameCListener.onClick(type_list.get(position));
            }
        });
    }

    /**
     * 回调接口
     *
     * @author DWL
     */
    public interface OnNameCListener {
        void onClick(String newsMetals);
    }

    public void setNameListener(OnNameCListener OnNameCListener) {
        this.OnNameCListener = OnNameCListener;
    }

    private void initView() {

    }

    public void setSize() {
        int w = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        int height = RecyclerView.LayoutParams.WRAP_CONTENT;
//        int height = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(height);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置popWindow的显示和消失动画
        this.setAnimationStyle(R.style.info_pop_anim_style);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.background));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            InfoPop.this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

}
