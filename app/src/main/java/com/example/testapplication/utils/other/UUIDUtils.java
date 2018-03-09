package com.example.testapplication.utils.other;

import android.content.Context;
import android.provider.Settings;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by guizhen on 2017/11/20.
 */

public class UUIDUtils {

    public static UUID generateUUID(Context context) {

        synchronized (UUIDUtils.class) {
            UUID uuid;
            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    uuid = UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return uuid;
        }
    }
}
