package com.example.test.view.listview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.example.test.R;


public class RefreshLoadLayout extends SwipeRefreshLayout {

    private final int mTouchSlop;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;

    private float firstTouchY;
    private float lastTouchY;

    private boolean isLoading = false;

    private boolean loadEnable = false;
    private View footerView;
    private ProgressBar progressBar;
    private AbsListView.OnScrollListener onScrollListener;
    private FrameLayout footer_layout;

    public RefreshLoadLayout(Context context) {
        this(context, null);
    }

    public RefreshLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
    }

    public void setScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setLoadEnable(boolean enable) {
        if (mListView == null) {
            throw new RuntimeException("RefreshLayout::setLoadEnable必须在setChildView之后调用");
        }
        this.loadEnable = enable;
        if (loadEnable) {
            if (footerView == null) {
                footerView = LayoutInflater.from(getContext()).inflate(R.layout.listview_footer, null);
                progressBar = footerView.findViewById(R.id.load_progress_bar);
                footer_layout = footerView.findViewById(R.id.footer_layout);
                mListView.addFooterView(footerView);
            }
            footerView.setVisibility(VISIBLE);
        } else {
            if (footerView != null) {
                footerView.setVisibility(GONE);
            }
            setLoading(false);
        }
    }

    public void setBgColor(int color){
        footer_layout.setBackgroundColor(color);
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
                if (onScrollListener != null)
                    onScrollListener.onScrollStateChanged(view, scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null)
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
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
        return loadEnable && isBottom() && !isLoading && isPullingUp();
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
        if (loadEnable && mOnLoadListener != null) {
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
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        } else {
            firstTouchY = 0;
            lastTouchY = 0;
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    public interface OnLoadListener {
        void onLoad();
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
                if (Math.abs(deleaX) > Math.abs(deltaY)) {
                    resume = false;
                } else {
                    if (isRefreshViewScroll(deltaY)) {
                        resume = superResult;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return resume ;
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
        }else{
            return true;
        }
        return false;
    }

}