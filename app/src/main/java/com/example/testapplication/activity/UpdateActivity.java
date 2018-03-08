package com.example.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;
import com.example.testapplication.net.callback.JsonCallback;
import com.example.testapplication.utils.Constant;
import com.loveplusplus.update.updateApp.CheckUpdateTask;
import com.loveplusplus.update.UpdateConstants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

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
        OkGo.<JSONObject>get(Constant.UPDATE_URL)
                .execute(new JsonCallback<JSONObject>() {
                    @Override
                    public void onSuccess(Response<JSONObject> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onError(Response<JSONObject> response) {
                        response.getException().printStackTrace();
                    }
                });
        new CheckUpdateTask(UpdateActivity.this, UpdateConstants.TYPE_DIALOG, false).execute();
    }
}