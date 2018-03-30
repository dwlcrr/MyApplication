
package com.example.test.activity.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.utils.Image.RxSaveBitmap;
import com.example.test.utils.rx.RxUtils;
import com.example.test.view.myview.dialog.SavePhotoDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import io.github.lijunguan.imgselector.album.widget.HackyViewPager;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 多图片展示界面
 */
public class PhotoViewPagerActivity extends BaseActivity {
    private List<String> photos;
    private TextView tv_count;
    private ViewPager mViewPager;
    private String name;
    private String photoUrl;
    private int position;
    private RelativeLayout rl_download;
    private CompositeSubscription rx = new CompositeSubscription();

    public static void start(Context context, List<String> photos, String name, int position) {
        Intent starter = new Intent(context, PhotoViewPagerActivity.class);
        starter.putExtra("photos", (Serializable) photos);
        starter.putExtra("position", position);
        starter.putExtra("name", name);
        context.startActivity(starter);
    }

    @Override
    protected void loadXml(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_pager);
    }

    /**
     * 越简单，越正确.
     */
    @Override
    protected void initView() {
        photos = (List<String>) getIntent().getSerializableExtra("photos");
        name = getIntent().getStringExtra("name");
        rl_download = (RelativeLayout) findViewById(R.id.rl_download);
        position = getIntent().getIntExtra("position", 0);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        tv_count = (TextView) findViewById(R.id.tv_count);
        mViewPager.setAdapter(new SamplePagerAdapter());
        tv_count.setText(position + 1 + "/" + photos.size());
        if (photos.size() > 0 && position < photos.size()) {
            photoUrl = photos.get(position);
        }
        mViewPager.setCurrentItem(position); //默认到当前选中的图片
    }

    @Override
    protected void initData() {

    }

    public void alertSavePhoto(final String url) {
        SavePhotoDialog dialdog = new SavePhotoDialog(baseActivity);
        dialdog.show();
        dialdog.setNamekListener(new SavePhotoDialog.OnNameCListener() {
            @Override
            public void onClick(String name) {
                if (name.equals("保存到手机")) {
                    new RxPermissions(PhotoViewPagerActivity.this)
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .filter(granted -> granted)
                            .subscribe(granted -> {
                                Glide.with(PhotoViewPagerActivity.this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        Subscription s = Observable.create(new RxSaveBitmap(resource, url))
                                                .compose(RxUtils.<File>subscribeInMain())
                                                .map(file -> file.getAbsolutePath())
                                                .subscribe(
                                                        filePath -> Toast.makeText(PhotoViewPagerActivity.this, "图片已保存至" + filePath, Toast.LENGTH_LONG).show(),
                                                        error -> {
                                                            error.printStackTrace();
                                                            Toast.makeText(PhotoViewPagerActivity.this, "图片保存失败", Toast.LENGTH_LONG).show();
                                                        });
                                        rx.add(s);
                                    }
                                });
                            });
                }
            }
        });
    }

    @Override
    protected void setListener() {
        rl_download.setOnClickListener(view -> alertSavePhoto(photoUrl));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_count.setText((position + 1) + "/" + photos.size());
                photoUrl = photos.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(baseActivity).load(photos.get(position))
                    .placeholder(R.mipmap.ic_launcher).into(photoView);
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    finish();
                }

                @Override
                public void onOutsidePhotoTap() {
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rx.unsubscribe();
    }

}
