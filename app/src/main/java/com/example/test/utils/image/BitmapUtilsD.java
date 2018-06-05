package com.example.test.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import com.example.test.base.MyApplication;
import com.smm.lib.utils.base.Logger;
import com.smm.lib.utils.base.StrUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static android.graphics.BitmapFactory.decodeFile;

/**
 * Created by zhangdi on 16/7/15.
 * 图片处理工具类
 */
public class BitmapUtilsD {
    public static Bitmap comp(String path) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        int reqHeight = 800;
        int reqWidth = 480;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        bitmap = decodeFile(path, options);
        if (bitmap == null) {
            return null;
        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int option = 100;
        // while (baos.toByteArray().length / 1024 > 100) { //
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        // baos.reset();// 重置baos即清空baos
        // bitmap.compress(Bitmap.CompressFormat.JPEG, option, baos);//
        // 这里压缩options%，把压缩后的数据存放到baos中
        // option -= 10;// 每次都减少10
        // }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap2 = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int compress = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, compress, baos);
        if (baos.toByteArray().length / 1024 > 300) {
            compress = compress - 5;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, compress, baos);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 通过 resId 获取 {@link Bitmap.Config#RGB_565} 的bitmap
     *
     * @param context
     * @param id
     * @return
     */
    public static Bitmap getBitmapFromResouse(Context context, int id) {
        InputStream is = context.getResources().openRawResource(id);
        BitmapFactory.Options bf = new BitmapFactory.Options();
        bf.inJustDecodeBounds = false;
        bf.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bi = BitmapFactory.decodeStream(is, null, bf);
        return compressBitmap(bi);
    }

    public static Bitmap compressFromResurece(Resources resources, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, id, options);
        int w = options.outWidth;
        int h = options.outHeight;
        float hh = 1280;
        float ww = 720f;
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight / hh);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = be;
        return BitmapFactory.decodeResource(resources, id, options);
    }

    /**
     * 通过 resId 获取 {@link Bitmap.Config#ARGB_8888} 的bitmap
     *
     * @param context
     * @param id
     * @return
     */
    public static Bitmap getBitmapFromResousetwo(Context context, int id) {
        InputStream is = context.getResources().openRawResource(id);
        BitmapFactory.Options bf = new BitmapFactory.Options();
        bf.inJustDecodeBounds = false;
        bf.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bf.inPreferQualityOverSpeed = true;
        Bitmap bi = BitmapFactory.decodeStream(is, null, bf);
        return compressBitmap(bi);
    }

    /**
     * drawable 转换成 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    public static String compressChatImg(String path) {
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        if (StrUtil.isEmpty(suffix)) return path;

        Bitmap outBitmap = compressImgTo1000(path);

        File nf = new File(MyApplication.ins().getExternalCacheDir() + "/img/uploadcache");
        if (!nf.exists()) {
            nf.mkdirs();
        }
        //在根目录下面的smm/photo文件夹下 创建文件
        String fileName = System.currentTimeMillis() + "." + suffix;
        File f = new File(nf, fileName);

        FileOutputStream out = null;
        Bitmap.CompressFormat compressFormat = getCompressFormat(suffix);

        try {
            //打开输出流 将图片数据填入文件中
            out = new FileOutputStream(f);
            if (outBitmap != null) {
                outBitmap.compress(compressFormat, 90, out);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (f.exists() && StrUtil.isNotEmpty(f.getPath()))
            path = f.getPath();
        Logger.info("compressChatImg===", "path:" + path);
        return path;
    }

    private static Bitmap.CompressFormat getCompressFormat(String suffix) {
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        if (suffix.equalsIgnoreCase("png")) {
            compressFormat = Bitmap.CompressFormat.PNG;
        } else if (suffix.equalsIgnoreCase("webp")) {
            compressFormat = Bitmap.CompressFormat.WEBP;
        } else {
            compressFormat = Bitmap.CompressFormat.JPEG;
        }
        return compressFormat;
    }

    public static Bitmap compressImgTo1000(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        decodeFile(path, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        final int reqHeight = 1000;
        final int reqWidth = 1000;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int degree = getBitmapDegree(path);
        if (degree != 0) {
            bitmap = rotateBitmapByDegree(bitmap, degree);
        }
        return bitmap;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

}
