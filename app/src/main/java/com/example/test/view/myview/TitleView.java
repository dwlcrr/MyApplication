package com.example.test.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.test.R;

/**
 * Created by dwl
 * on 2017/4/7.
 */

public class TitleView extends RelativeLayout {

    // 返回按钮控件
    private ImageView mLeftImg;
    // 标题Tv
    private TextView mTitleTv;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);

        // 获取控件
        mLeftImg = (ImageView) findViewById(R.id.left_img);
        mTitleTv = (TextView) findViewById(R.id.tv_titleText);
    }

    public void setLeftImgClick(OnClickListener listener){
        mLeftImg.setOnClickListener(listener);
    }
}
