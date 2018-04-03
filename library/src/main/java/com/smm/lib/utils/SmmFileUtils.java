package com.smm.lib.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by guizhen on 2017/4/25.
 */

public class SmmFileUtils {
    private static final String ROOT_FILEDIR_NAME = "smm";
    public static final String AUDIO_DIR = "audio";

    private static File rootFileDir;


    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootFileDir = new File(Environment.getExternalStorageDirectory(), ROOT_FILEDIR_NAME);
        } else {
            rootFileDir = new File(Environment.getDataDirectory(), ROOT_FILEDIR_NAME);
        }
        if (!rootFileDir.exists()) rootFileDir.mkdirs();
    }

    private static File getFileDir(String childDirName) {
        File fileDir = new File(rootFileDir, childDirName);
        if (!fileDir.exists()) fileDir.mkdirs();
        return fileDir;
    }

    public static File getAudioDir() {
        return getFileDir(AUDIO_DIR);
    }


}
