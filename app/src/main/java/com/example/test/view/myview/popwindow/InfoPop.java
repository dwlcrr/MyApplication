package com.example.test.view.myview.popwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.example.test.R;
import java.util.List;

/**
 * 资讯选择品目
 * Created by dongwanlin on 2017/1/17.
 */

public class InfoPop extends BasePopupWindow {

    private View conentView;
    private Context mContext;
    private List<String> type_list;

    public InfoPop(final Context context, List<String> type_list) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.item_album_folder_list, null);
        mContext = context;
        this.type_list = type_list;
        initView();
    }

    @Override
    public void initView() {
        setSize(conentView);
    }

}
