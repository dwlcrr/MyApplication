package com.smm.lib.view.other;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import com.smm.lib.R;

/**
 * Created by zhangdi on 17/8/18.
 */

public class RefreshLoadLayoutForRecyleView extends SwipeRefreshLayout {
    private ListView childView;
    private boolean isFlase = false;

    public RefreshLoadLayoutForRecyleView(Context context) {
        super(context);
    }

    public RefreshLoadLayoutForRecyleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.royalblue,
                R.color.gray_ae,
                R.color.steelblue,
                R.color.red);
    }

    public void setisFlase(boolean isFlase) {
        this.isFlase = isFlase;
    }

    public void setChildView(ListView childView) {
        this.childView = childView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isFlase) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
