package com.example.testapplication.view.myview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.testapplication.R;

/**
 * Created by zhangdi on 17/4/7.
 */

public class MyCheckBox extends RelativeLayout{
    private Context context;
    private ImageView iv;
    private CheckBox cb;
    private OnCheckedChangeListener onCheckedChangeListener;
    public interface OnCheckedChangeListener{
        public void onCheckedChange(CompoundButton buttonView, boolean isChecked);
    }
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        this.onCheckedChangeListener = listener;
    }

    public MyCheckBox(Context context) {
        super(context);
        init(context);
    }

    public MyCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        measureChild(cb,widthMeasureSpec,widthMeasureSpec);
//        Logger.info("cb.getmeasuer : " + cb.getMeasuredHeight() + "   " + cb.getMeasuredWidth());
        setMeasuredDimension(cb.getMeasuredWidth(),cb.getMeasuredHeight());
    }


    private void init(Context context){
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.mycheckbox,this);
//        View vi = LayoutInflater.from(context).inflate(R.layout.mycheckbox,null);
        iv = (ImageView) findViewById(R.id.mycheckbox_iv);
        cb = (CheckBox) findViewById(R.id.mycheckbox_cb);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(onCheckedChangeListener != null){
                    onCheckedChangeListener.onCheckedChange(buttonView,isChecked);
                }
                if(isChecked){
                    ObjectAnimator.ofFloat(iv,"translationX",-(cb.getWidth() * 0.33f),0)
                            .setDuration(200)
                            .start();
                }else{
                    ObjectAnimator.ofFloat(iv,"translationX",0,-(cb.getWidth() * 0.33f))
                            .setDuration(200)
                            .start();
                }
            }
        });
    }
    public boolean isChecked(){
        if(cb != null){
            return cb.isChecked();
        }
        return true;
    }
    public void setFocusable(boolean isFocuable){
        if(cb != null){
            cb.setFocusable(isFocuable);
        }
    }

}
