package io.github.lijunguan.imgselector.album;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;

import io.github.lijunguan.imgselector.R;
import io.github.lijunguan.imgselector.base.BaseActivity;
import uk.co.senab.photoview.PhotoView;

/**
 * 单一图片展示界面
 * Created by guizhen on 17/3/17.
 */
public class ShowOnePhotoActivity extends BaseActivity{

    public static final String SINGLE_RESULT = "single_result";
    private PhotoView photo;
    private Button mSubmitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageselector_single_photo);
        initViews();
    }

    protected void initViews() {
        photo = (PhotoView) findViewById(R.id.photo);
        final String imagePath = getIntent().getStringExtra("imagePath");
        Glide.with(this).load(imagePath).into(photo);
        mSubmitBtn = (Button)  findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(SINGLE_RESULT, imagePath);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
