package com.example.test.fragment.index;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.utils.share.ShareUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;

import io.github.lijunguan.imgselector.ImageSelector;

/**
 * 其他测试 fragment
 * Created by dwl on 2018/3/23.
 */
public class OtherFragment extends BaseFragment {

    private TextView textView;
    private LiveVideoListener mLiveVideoListener;

    public static OtherFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * IndexFragment之间的回调接口
     */
    public interface LiveVideoListener {
        boolean playVideo(String url);

        boolean playFirstVideo(String url);
    }

    public void setLiveVideoListener(LiveVideoListener liveVideoListener) {
        mLiveVideoListener = liveVideoListener;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, null);
        textView = view.findViewById(R.id.text);
        textView.setText(String.valueOf((char) getArguments().getInt("index")));
        return view;
    }

    @Override
    protected void initData() {
        mLiveVideoListener.playFirstVideo("url");
    }

    @Override
    protected void setListener() {
        textView.setOnClickListener(view -> {
            share();
            selectPicture();
        });
    }

    /**
     * 选择图片
     */
    private void selectPicture() {
        new RxPermissions(getActivity())
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        ImageSelector.newInstance()
                                .setSelectModel(ImageSelector.SINGLE_MODE)
                                .setCrop(false)
                                .setMaxCount(1)
                                .setGridColumns(3)
                                .setShowCamera(true)
                                .setToolbarColor(getResources().getColor(R.color.black))
                                .startSelect(getActivity());
                    } else {
                        Toast.makeText(getActivity(), "请打开相机权限", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                });
    }

    public void share() {
        String title = "掌握期现价格，交易轻松搞定";
        String url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.smm";
        ShareUtils.oneKeyShare(getActivity(), "  ", title, url, R.mipmap.ic_launcher, ShareUtils.defaultUMShareListener(getActivity()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            //从图片选择器 选择多张图片
            if (requestCode == ImageSelector.REQUEST_SELECT_IMAGE) {
                ArrayList<String> imagesPaths = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
                if (imagesPaths.size() > 0) {

                }
            }
        }
    }

}
