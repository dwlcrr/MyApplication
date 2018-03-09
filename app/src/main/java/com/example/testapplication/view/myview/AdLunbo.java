package com.example.testapplication.view.myview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.example.testapplication.R;
import com.example.testapplication.entity.AdLunboData;
import com.example.testapplication.utils.base.BaseUtils;
import com.example.testapplication.utils.base.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌轮播控件
 * Created by dwl
 * on 2016/12/7.
 */
public class AdLunbo extends FrameLayout {
    private static final int DISPLAY_TIME = 5 * 1000;
    private int DOT_MARGIN;
    private int count;
    private List<ViewHolder> viewHolders;
    private Context context;
    private ViewPager vp;
    private boolean isAutoPlay;
    private boolean isAutoPlayPause;
    private int currentItem;
    private int delayTime;
    private LinearLayout ll_dot;
    private List<ImageView> iv_dots;
    private LunboPagerAdapter lunboPagerAdapter;
    private Handler handler = new Handler();
    private OnAdClickListener listener;
    private List<AdLunboData> datas = new ArrayList<>();

    public AdLunbo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initData();
        initLayout();
    }

    public AdLunbo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdLunbo(Context context) {
        this(context, null);
    }

    private void initData() {
        viewHolders = new ArrayList<>();
        iv_dots = new ArrayList<>();
    }

    /**
     * 设置 图片数据
     *
     * @param imagesInfo
     */
    public void setImagesInfo(List<AdLunboData> imagesInfo) {
        if (imagesInfo == null || imagesInfo.size() <= 0) return;
        count = imagesInfo.size();
        //防止 adapter 的destroyItem越界
        vp.setAdapter(null);
        initDot();
        initImg();
        showTime();
        datas.clear();
        datas.addAll(imagesInfo);
        for (int i = 0; i < count + 2; i++) {
            if (i == 0) {
                updateIvInfo(count - 1, viewHolders.get(i));
            } else if (i == count + 1) {
                updateIvInfo(0, viewHolders.get(i));
            } else {
                updateIvInfo(i - 1, viewHolders.get(i));
            }

        }
        lunboPagerAdapter.notifyDataSetChanged();
    }

    private void updateIvInfo(int dataIndex, ViewHolder viewHolder) {
        viewHolder.dataIndex = dataIndex;
        viewHolder.itemView.setTag(datas.get(dataIndex));
    }

    private void initLayout() {
        viewHolders.clear();
        View view = LayoutInflater.from(context).inflate(
                R.layout.ad_lunbo_layout, this, true);
        vp = (ViewPager) view.findViewById(R.id.vp);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        vp.setFocusable(true);
    }

    private void initDot() {
        DOT_MARGIN = DisplayUtils.dip2px(context, 5);
        ll_dot.removeAllViews();
        iv_dots.clear();
        for (int i = 0; i < count; i++) {
            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = DOT_MARGIN;
            iv_dot.setImageResource(R.drawable.dot_selector);
            ll_dot.addView(iv_dot, params);
            iv_dot.setTag(i);
            iv_dot.setOnClickListener(clickDotListener);
            iv_dots.add(iv_dot);
        }
        if (iv_dots.size() > 0) iv_dots.get(0).setSelected(true);
    }

    private void initImg() {
        viewHolders.clear();
        for (int i = 0; i < count + 2; i++) {

            View contentView = LayoutInflater.from(context).inflate(
                    R.layout.ad_lunbo_content, null);
            ImageView iv = (ImageView) contentView.findViewById(R.id.iv_lunbo);
//            TextView tv = (TextView) contentView.findViewById(R.id.tv_lunbo);
            contentView.setOnClickListener(onItemClickListener);
            viewHolders.add(new ViewHolder(contentView, iv));
        }
    }

    private void showTime() {
        handler.removeCallbacksAndMessages(null);
        lunboPagerAdapter = new LunboPagerAdapter();
        vp.setAdapter(lunboPagerAdapter);
        currentItem = 1;
        vp.setCurrentItem(currentItem);
//        vp.setOffscreenPageLimit(count);
        if (count > 0) startPlay();
    }

    private void startPlay() {
        isAutoPlay = true;
        isAutoPlayPause = false;
        handler.postDelayed(task, DISPLAY_TIME);
    }

    private void loadNetImg(String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);

    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay && !isAutoPlayPause) {
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    vp.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    vp.setCurrentItem(currentItem);
                    handler.postDelayed(task, DISPLAY_TIME);
                }
            } else {
                handler.postDelayed(task, DISPLAY_TIME);
            }
        }
    };

    class LunboPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewHolders.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (viewHolders.get(position).dataIndex >= 0 && viewHolders.get(position).dataIndex < datas.size()) {
                AdLunboData data = datas.get(viewHolders.get(position).dataIndex);
                if (data != null && !BaseUtils.isEmpty(data.getPic_url())) {
                    loadNetImg(data.getPic_url(), viewHolders.get(position).iv);
                }
            }

            container.addView(viewHolders.get(position).itemView);

            return viewHolders.get(position).itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewHolders.get(position).itemView);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isAutoPlay = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    isAutoPlay = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (vp.getCurrentItem() == 0) {
                        vp.setCurrentItem(count, false);
                    } else if (vp.getCurrentItem() == count + 1) {
                        vp.setCurrentItem(1, false);
                    }
                    currentItem = vp.getCurrentItem();
                    isAutoPlay = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < iv_dots.size(); i++) {
                iv_dots.get(i).setSelected(i == position - 1);
            }
        }

    }

    private OnClickListener clickDotListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            handler.removeCallbacks(task);
            handler.postDelayed(task, DISPLAY_TIME);
            vp.setCurrentItem(((Integer) v.getTag()) + 1);
        }
    };

    public void removeCallbacksAndMessages() {
        handler.removeCallbacksAndMessages(null);
        context = null;
    }

    public void pauseAutoPlay(boolean isAutoPlayPause) {
        this.isAutoPlayPause = isAutoPlayPause;
    }

    public void setOnItemClickListener(OnAdClickListener listener) {
        this.listener = listener;
    }

    private OnClickListener onItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onAdClick(v, (AdLunboData) v.getTag());
        }
    };

    public interface OnAdClickListener {
        void onAdClick(View v, AdLunboData adData);
    }


    public class ViewHolder {
        public View itemView;
        public ImageView iv;
        int dataIndex = -1;

        public ViewHolder(View itemView, ImageView iv) {
            this.itemView = itemView;
            this.iv = iv;
//            this.tv = tv;
        }
    }

}
