package cn.smm.en.umeng;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.DeviceConfig;

/**
 * Created by guizhen on 2017/5/25.
 */

public class ShareUtils {
    private static final SHARE_MEDIA[] shares = {
            SHARE_MEDIA.FACEBOOK,
            SHARE_MEDIA.FACEBOOK_MESSAGER,
            SHARE_MEDIA.TWITTER,
            SHARE_MEDIA.WHATSAPP,
            SHARE_MEDIA.LINKEDIN,
            SHARE_MEDIA.GOOGLEPLUS,
            SHARE_MEDIA.EMAIL,
            SHARE_MEDIA.MORE};

    private static ShareBoardConfig shareBoardConfig;

    static {
        shareBoardConfig = new ShareBoardConfig();
        shareBoardConfig.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM)
                .setCancelButtonVisibility(false)
                .setTitleVisibility(false)
                .setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE)
                .setIndicatorVisibility(false);
    }

    /**
     * @param context  上下文 activity
     * @param title    标题
     * @param desc     描述
     * @param webUrl   分享链接
     * @param content  分享内容
     * @param umImage  分享缩略图
     * @param listener 分享回调
     */
    public static void shareWeb(final Activity context, final String title, final String desc, final String webUrl, String content, final UMImage umImage, final UMShareListener listener) {
        final CustomShareDialog customShareDialog = new CustomShareDialog(context);
        customShareDialog.setListener(new CustomShareDialog.onShareSelected() {
            @Override
            public void onShare(SHARE_MEDIA share_media) {
                customShareDialog.dismiss();
                if (share_media == SHARE_MEDIA.WHATSAPP
                        || share_media == SHARE_MEDIA.LINKEDIN
                        || share_media == SHARE_MEDIA.GOOGLEPLUS) {
                    boolean isInstall;
                    if (share_media == SHARE_MEDIA.WHATSAPP) {
                        isInstall = DeviceConfig.isAppInstalled("com.whatsapp", context);
                    } else {
                        isInstall = UMShareAPI.get(context).isInstall(context, share_media);
                    }

                    if (!isInstall) {
                        Toast.makeText(context, "Please install " + share_media, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                ShareAction shareAction = new ShareAction(context);
                if (share_media == SHARE_MEDIA.FACEBOOK
                        || share_media == SHARE_MEDIA.FACEBOOK_MESSAGER
                        || share_media == SHARE_MEDIA.LINKEDIN) {
                    shareAction.withMedia(new UMWeb(webUrl, title, desc, umImage));
                } else {
                    shareAction.withText(share_media == SHARE_MEDIA.EMAIL ? (title + "<br/>" + webUrl) : (title + "\r\n" + webUrl));
                }
                shareAction.setPlatform(share_media).setCallback(listener).share();
            }
        });
        customShareDialog.show();
        /*new ShareAction(context).setDisplayList(shares)
                .withText(content)
                .withMedia(new UMWeb(webUrl, title, desc, umImage))
                .setCallback(listener)
                .open(shareBoardConfig);*/
    }

    public static void shareWeb(Activity context, String title, String desc, String webUrl, String imgUrl, UMShareListener listener) {
        //title + "\n" + webUrl
        shareWeb(context, title, desc, webUrl,
                title + "\n" + webUrl
                , new UMImage(context, imgUrl), listener);
    }
}
