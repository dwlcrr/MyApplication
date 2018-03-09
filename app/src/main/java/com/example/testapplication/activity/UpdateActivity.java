package com.example.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;
import com.loveplusplus.update.UpdateConstants;
import com.loveplusplus.update.updateApp.CheckUpdateTask;

/**
 * 更新软件
 * @author dongwanlin
 */
public class UpdateActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update:
                update();
                break;
        }
    }

    private void update() {

        new CheckUpdateTask(UpdateActivity.this, UpdateConstants.TYPE_DIALOG, false).execute();
    }
}