package com.example.test.view.listview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.test.R;
import com.smm.lib.utils.base.StrUtil;

/**
 * @Author: dwl.
 * @Date: 2016/5/27 13:47
 * @E-mail: wl386123298@qq.com
 */
public class SimpleLoadingLayout extends LoadingLayout implements View.OnClickListener {
    private OnReloadClickListener mOnReloadClickListener;


    public SimpleLoadingLayout(Context context) {
        this(context, null);
    }

    public SimpleLoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setViewForState(R.layout.load_ing, VIEW_STATE_LOADING);
        setViewForState(R.layout.load_empty, VIEW_STATE_EMPTY);
        setViewForState(R.layout.network_error_view_layout, VIEW_STATE_ERROR);

        getView(VIEW_STATE_ERROR).setOnClickListener(this);
        setViewState(VIEW_STATE_LOADING);
    }


    public void setOnReloadClickListener(@NonNull OnReloadClickListener onReloadClickListener) {
        mOnReloadClickListener = onReloadClickListener;
    }

    /**
     * 设置加载提示文字
     *
     * @param loadingTextValue
     */
    public SimpleLoadingLayout setLoadingText(@NonNull String loadingTextValue) {
        View view = getView(VIEW_STATE_LOADING);
        if (null != view) {
            TextView loadingTextView = (TextView) view.findViewById(R.id.textView1);
            loadingTextView.setText(Html.fromHtml(loadingTextValue));
        }

        return this;
    }


    /**
     * 设置空白loading的提示文案
     *
     * @param emptyTextValue 空白提示文字
     */
    public SimpleLoadingLayout setEmptyText(@NonNull String emptyTextValue) {
        setEmptyText(emptyTextValue, -1);

        return this;
    }

    public SimpleLoadingLayout setLodingBackGround(int color) {
        View view = getView(VIEW_STATE_LOADING);
        if (null != view) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    //  设置为 问答 样式
    public SimpleLoadingLayout setQaStyle() {
        setLodingBackGround(Color.WHITE);
        setViewForState(R.layout.load_empty_qa, LoadingLayout.VIEW_STATE_EMPTY);
        return this;
    }

    public SimpleLoadingLayout setEmptyText(@NonNull String emptyTextValue, @DrawableRes int idImg) {
        View view = getView(VIEW_STATE_EMPTY);
        if (null != view) {
            ImageView iv_empty = (ImageView) view.findViewById(R.id.iv_emptyImg);
            if (idImg != -1) {
                iv_empty.setImageResource(idImg);
            } else {
                iv_empty.setVisibility(GONE);
            }
            TextView tv_empty = (TextView) view.findViewById(R.id.tv_empty);
            tv_empty.setText(emptyTextValue);
            Button btn_reSubmit = (Button) view.findViewById(R.id.btn_reSubmit);
            if (btn_reSubmit != null) {
                btn_reSubmit.setVisibility(View.GONE);
            }
        }

        return this;
    }

    public SimpleLoadingLayout setEmptyText(String emptyTextValue, int idImg, String btnText, View.OnClickListener clickListener) {
        View view = getView(VIEW_STATE_EMPTY);
        if (null != view) {
            ImageView iv_empty = (ImageView) view.findViewById(R.id.iv_emptyImg);
            iv_empty.setImageResource(idImg);
            TextView tv_empty = (TextView) view.findViewById(R.id.tv_empty);
            tv_empty.setText(emptyTextValue);
            Button btn_reSubmit = (Button) view.findViewById(R.id.btn_reSubmit);
            btn_reSubmit.setText(btnText);
            btn_reSubmit.setOnClickListener(clickListener);
        }

        return this;
    }

    /**
     * 设置错误的提示文案
     *
     * @param errorTextValue 错误提示文案
     */
    public SimpleLoadingLayout setErrorText(String errorTextValue, View.OnClickListener clickListener) {
        setErrorText(errorTextValue, 0, clickListener);
        return this;
    }


    /**
     * 设置errorLoadingText的文字显示
     *
     * @param errorTextValue
     * @param top
     */
    public SimpleLoadingLayout setErrorText(String errorTextValue, @DrawableRes int top, View.OnClickListener clickListener) {
        View view = getView(VIEW_STATE_ERROR);
        if (null != view) {
            TextView errorTextView = (TextView) view.findViewById(R.id.errorLoadingText);
            errorTextView.setCompoundDrawablesWithIntrinsicBounds(0, top, 0, 0);
            if(StrUtil.isNotEmpty(errorTextValue)){
                errorTextView.setText(Html.fromHtml(errorTextValue));
            }
            errorTextView.setOnClickListener(clickListener);
        }

        return this;
    }

    @Override
    public void onClick(View v) {
        if (mOnReloadClickListener != null) {
            mOnReloadClickListener.onReload();
        }
    }


    public interface OnReloadClickListener {
        void onReload();
    }
}
