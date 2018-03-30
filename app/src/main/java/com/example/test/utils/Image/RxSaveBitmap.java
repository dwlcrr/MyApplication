package com.example.test.utils.Image;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import com.example.test.utils.other.Md5Utils;
import java.io.File;
import java.io.FileOutputStream;
import rx.Observable;
import rx.Subscriber;

/**
 * 异步保存图片
 * Created by guizhen on 16/9/8.
 */
public class RxSaveBitmap implements Observable.OnSubscribe<File> {

    private String path;
    private String fileName;
    private Bitmap bitmap;

    public RxSaveBitmap(Bitmap bitmap, String url) {
        this(Environment.getExternalStorageDirectory() + "/metal/photo", parseName(url), bitmap);
    }

    public RxSaveBitmap(String path, String fileName, Bitmap bitmap) {
        this.path = path;
        this.fileName = fileName;
        this.bitmap = bitmap;
    }

    private static String parseName(String url) {
        int indexWenhao = url.indexOf("?");
        if (indexWenhao > 0) {
            url = url.substring(0, url.indexOf("?"));
        }
        return Md5Utils.str2md5(url) + url.substring(url.lastIndexOf("."));
    }

    @Override
    public void call(Subscriber<? super File> subscriber) {
        //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的smm文件夹
        File nf = new File(path);
        if (!nf.exists()) {
            nf.mkdirs();
        }
        //在根目录下面的smm/photo文件夹下 创建文件
        File f = new File(nf, fileName);
        Log.e("savabitmap", "filename" + fileName);

        FileOutputStream out = null;
        try {
            //打开输出流 将图片数据填入文件中
            out = new FileOutputStream(f);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            }
            out.flush();
            out.close();
            subscriber.onNext(f);
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }
}
