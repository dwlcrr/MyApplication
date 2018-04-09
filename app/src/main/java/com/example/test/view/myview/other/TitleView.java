package com.example.test.view.myview.other;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.test.R;

/**
 * Created by dwl
 * on 2017/4/7.
 */

public class TitleView{

    // 返回按钮控件
    private ImageView mLeftImg;
    // 标题Tv
    private TextView mTitleTv;
    private Activity activity;

    public TitleView(Activity activity) {
        // 加载布局
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_title, null);
        this.activity = activity;
        // 获取控件
        mLeftImg = view.findViewById(R.id.left_img);
        mTitleTv = view.findViewById(R.id.tv_titleText);
    }

    public void setLeftImgClick(View.OnClickListener listener){
        mLeftImg.setOnClickListener(listener);
    }

    /**
     * 是否显示返回按钮
     */
    public TitleView showLeftBtn(boolean isShow){
        if(isShow){
            mLeftImg.setOnClickListener(view -> activity.finish());
        }else {
            mLeftImg.setVisibility(View.GONE);
        }
        return this;
    }
    /**
     * 更改中间标题的内容
     */
    public TitleView setTitleText(String titleContent) {
        if (mTitleTv != null) {
            mTitleTv.setText(titleContent);
        }
        return this;
    }

}
