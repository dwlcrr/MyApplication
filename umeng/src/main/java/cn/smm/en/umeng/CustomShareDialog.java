package cn.smm.en.umeng;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guizhen on 16/9/13.
 */
public class CustomShareDialog extends Dialog implements View.OnClickListener {
    public List<Pair<Integer, SHARE_MEDIA>> shares;

    private View rootView;
    private onShareSelected onShareSelectListener;

    public CustomShareDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.custom_share_dialog, null);
        Window window = getWindow();
        WindowManager.LayoutParams lParams = window.getAttributes();
        WindowManager systemService = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(metric);
        lParams.width = metric.widthPixels;
        lParams.height = dip2px(context, 160);
        window.setAttributes(lParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        setContentView(rootView);
        window.setGravity(Gravity.BOTTOM);
        initData();
    }

    private void initData() {
        shares = new ArrayList<>();
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_facebook, SHARE_MEDIA.FACEBOOK));
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_fbmessage, SHARE_MEDIA.FACEBOOK_MESSAGER));
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_twitter, SHARE_MEDIA.TWITTER));
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_whatsapp, SHARE_MEDIA.WHATSAPP));
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_Linkedin, SHARE_MEDIA.LINKEDIN));
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_googleplus, SHARE_MEDIA.GOOGLEPLUS));
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_email, SHARE_MEDIA.EMAIL));
        shares.add(Pair.create(R.id.umeng_socialize_shareboard_item_more, SHARE_MEDIA.MORE));

        for (Pair<Integer, SHARE_MEDIA> pair : shares) {
            rootView.findViewById(pair.first).setOnClickListener(this);
        }
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        if (onShareSelectListener == null) return;
        int vId = v.getId();
        for (Pair<Integer, SHARE_MEDIA> pair : shares) {
            if (vId == pair.first) {
                onShareSelectListener.onShare(pair.second);
                break;
            }
        }
    }

    public void setListener(onShareSelected listener) {
        this.onShareSelectListener = listener;
    }

    public static interface onShareSelected {
        void onShare(SHARE_MEDIA share_media);
    }

}
