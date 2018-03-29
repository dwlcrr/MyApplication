package com.example.test.utils.share;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * 分享工具类
 *
 * @Author: guizhen
 * @Date: 2016/10/09
 */
public class ShareUtils {

    private static ShareBoardConfig shareBoardConfig;

    public static UMShareListener defaultUMShareListener(Context context) {
        return new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(context, "分享成功啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(context, "分享失败啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show();
            }
        };
    }

    static {
        shareBoardConfig = new ShareBoardConfig();
        shareBoardConfig.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM)
                .setCancelButtonVisibility(false)
                .setTitleVisibility(false)
                .setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE)
                .setIndicatorVisibility(false);
    }

    private static final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.SINA
            };

    /**
     * 友盟一键分享
     */
    public static void oneKeyShare(Activity activity, String text, String title, String url, int piId, UMShareListener umShareListener) {
        oneKeyShare(activity, text, title, url, new UMImage(activity, piId), umShareListener);
    }

    public static void oneKeyShare(Activity activity, String text, String title, String url, Bitmap bitmap, UMShareListener umShareListener) {
        oneKeyShare(activity, text, title, url, new UMImage(activity, bitmap), umShareListener);
    }

    public static void oneKeyShare(Activity activity, String text, String title, String url, UMImage umImage, UMShareListener umShareListener) {
        new RxPermissions(activity)
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        new ShareAction(activity).setDisplayList(displaylist)
                                .withMedia(new UMWeb(url, title, text, umImage))
                                .setCallback(umShareListener)
                                .open(shareBoardConfig);
                    } else if (!permission.shouldShowRequestPermissionRationale) {
                        PermissionUtils.showSettingDialog(activity, PermissionUtils.WRITE_EXTERNAL_STORAGE);
                    }
                });

    }

    //   添加了一个复制按钮  用于行情-详情中的分享
    public static void oneKeyShareQuotation(final Activity activity, final String text, final String title, final String url, final Bitmap umImage, final UMShareListener umShareListener, final String copytext) {

        new RxPermissions(activity)
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        final String finalText = "".equals(text) ? "   " : text;
                        new ShareAction(activity).setDisplayList(displaylist)
                                .addButton("umeng_sharebutton_copy", "umeng_sharebutton_copy", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                                .setShareboardclickCallback(new ShareBoardlistener() {
                                    @Override
                                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                        if (share_media == null) {
                                            if (snsPlatform.mKeyword.equals("umeng_sharebutton_copy")) {
                                                Toast.makeText(activity, "复制成功!", Toast.LENGTH_LONG).show();
                                                ClipboardManager myClipboard;
                                                myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                                                myClipboard.setPrimaryClip(ClipData.newPlainText("text", copytext));
                                            }
                                        } else {
                                            new ShareAction(activity).setPlatform(snsPlatform.mPlatform)
                                                    .withMedia(new UMWeb(url, title, finalText, new UMImage(activity, umImage)))
                                                    .setCallback(umShareListener).share();
                                        }
                                    }
                                })
                                .open(shareBoardConfig);
                    } else if (!permission.shouldShowRequestPermissionRationale) {
                        PermissionUtils.showSettingDialog(activity, PermissionUtils.WRITE_EXTERNAL_STORAGE);
                    }
                });


    }

    public static void login(Activity activity, SHARE_MEDIA platform, UMAuthListener umAuthListener) {
        UMShareAPI.get(activity).doOauthVerify(activity, platform, umAuthListener);
    }

    public static void oneKeyCustomShare(final Activity activity, final String text, final String title, final String url, final UMImage umImage, final UMShareListener umShareListener) {
        oneKeyCustomShare(activity, text, title, url, umImage, umShareListener, false);
    }

    public static void oneKeyCustomShare(final Activity activity, final String text, final String title, final String url, final UMImage umImage, final UMShareListener umShareListener, final boolean staticTitle) {

        new RxPermissions(activity)
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        final String finalText = TextUtils.isEmpty(text) ? " " : text;
                        final String finalTitle = TextUtils.isEmpty(title) ? "掌上有色" : title;
                        new ShareAction(activity).setDisplayList(displaylist)
                                .setShareboardclickCallback(new ShareBoardlistener() {
                                    @Override
                                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                        ShareAction shareAction = new ShareAction(activity);
                                        UMWeb umWeb = new UMWeb(url, finalTitle, finalText, umImage);
                                        if (staticTitle && (share_media == SHARE_MEDIA.SINA || share_media == SHARE_MEDIA.WEIXIN_CIRCLE)) {
                                            umWeb.setTitle(text);
                                        }
                                        shareAction.setPlatform(share_media)
                                                .withMedia(umWeb)
                                                .setCallback(umShareListener);
                                        shareAction.share();
                                    }
                                })
                                .open(shareBoardConfig);
                    } else if (!permission.shouldShowRequestPermissionRationale) {
                        PermissionUtils.showSettingDialog(activity, PermissionUtils.WRITE_EXTERNAL_STORAGE);
                    }
                });

    }

}
