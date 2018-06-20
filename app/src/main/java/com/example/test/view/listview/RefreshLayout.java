package com.example.test.view.listview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

public class RefreshLayout extends SwipeRefreshLayout {

    private final int mTouchSlop;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;

    private float firstTouchY;
    private float lastTouchY;

    private boolean isLoading = false;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    //set the child view of RefreshLayout,ListView

    public void setChildView(final ListView mListView) {
        this.mListView = mListView;
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        if (canLoadMore()) {
                            loadData();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                lastTouchY = event.getRawY();
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoadMore() {
        return isBottom() && !isLoading && isPullingUp();
    }

    private boolean isBottom() {
        if (mListView.getCount() > 0) {
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {

                return true;
            }
        }

        return false;
    }

    private boolean isUp() {
        if (mListView.getCount() > 0) {
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPullingUp() {
        return (firstTouchY - lastTouchY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
        }
    }

    public void setLoading(boolean loading) {
        if (mListView == null) return;
        isLoading = loading;
        if (loading) {
            if (isRefreshing()) {
                setRefreshing(false);
            }
            mListView.setSelection(mListView.getAdapter().getCount() - 1);
            mOnLoadListener.onLoad();
        } else {
            firstTouchY = 0;
            lastTouchY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    public interface OnLoadListener {
        public void onLoad();
    }


    int mLastMotionY;
    int mLastMotionX;

    //解决与横向 viewpager 冲突
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean superResult = super.onInterceptTouchEvent(e);
        int y = (int) e.getRawY();
        int x = (int) e.getRawX();
        boolean resume = false;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 发生down事件时,记录y坐标
                mLastMotionY = y;
                mLastMotionX = x;
                resume = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;
                int deleaX = x - mLastMotionX;
//                Logger.error("onTouchEvent", "refresh===deleaX===" + deleaX);
//                Logger.error("onTouchEvent", "refresh===deltaY===" + deltaY);
                if (Math.abs(deleaX) > Math.abs(deltaY)) {
                    resume = false;
                } else {
//                    Logger.error("onTouchEvent", "refresh===isRefreshViewScroll===" + "start");
                    if (isRefreshViewScroll(deltaY)) {
//                        Logger.error("onTouchEvent", "refresh===isRefreshViewScroll===" + "ok");
                        resume = superResult;
                    }
//                    Logger.error("onTouchEvent", "refresh===isRefreshViewScroll===" + "fail");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
//        Logger.error("onTouchEvent", "refresh===onInterceptTouchEvent===" + resume);
        return resume;
    }

    private boolean isRefreshViewScroll(int deltaY) {
        if (isRefreshing()) {
            return false;
        }
        // 对于ListView
        if (mListView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 0) {
                View child = mListView.getChildAt(0);
                if (child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (mListView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    return true;
                }
                int top = child.getTop();
                int padding = mListView.getPaddingTop();
                if (mListView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) <= 8) {// 这里之前用3可以判断,但现在不行,还没找到原因
                    return true;
                }
            } else if (deltaY < 0) {
                View lastChild = mListView.getChildAt(mListView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight()
                        && mListView.getLastVisiblePosition() == mListView
                        .getCount() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

}