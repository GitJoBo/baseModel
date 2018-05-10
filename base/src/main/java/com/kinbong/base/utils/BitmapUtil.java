package com.kinbong.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.kinbong.model.util.DevicesUtil;
import com.kinbong.model.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hankkin on 15/12/24.
 */
public class BitmapUtil {

    public static Bitmap getCompressedBitmap(Activity act, String filepath) {
        Bitmap tempBitmap = null;
        float width = act.getResources().getDisplayMetrics().widthPixels;
        float height = act.getResources().getDisplayMetrics().heightPixels;
        try {
            if (width > 640) {
                tempBitmap = getSuitableBitmap(act, Uri.fromFile(new java.io.File(filepath)), 640, (640 / width) * height);
            } else {
                tempBitmap = getSuitableBitmap(act, Uri.fromFile(new java.io.File(filepath)), (int) width, (int) height);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tempBitmap;
    }

    /**
     * 说明：请调用getSuitableBitmap()方法并传入图像路径，返回Bitmap
     * 修改宽高压缩比例
     */
    public static Bitmap getSuitableBitmap(Activity act, Uri uri, float ww, float hh)
            throws FileNotFoundException {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeStream(act.getContentResolver().openInputStream(uri), null, newOpts);

        newOpts.inJustDecodeBounds = false;
        float w = newOpts.outWidth;
        float h = newOpts.outHeight;

        float wwh = 640f;//
        float hhh = (wwh / w) * h;//
        int be = 1;
        if (w > h && w > wwh) {
            be = (int) (newOpts.outWidth / wwh);
        } else if (w < h && h > hhh) {
            be = (int) (newOpts.outHeight / hhh);
            be += 1;
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeStream(act.getContentResolver().openInputStream(uri), null, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 说明：请调用getSuitableBitmap()方法并传入图像路径，返回Bitmap
     * <p/>
     * 修改宽高压缩比例
     * by:Hankkin at:2015-2-14
     */
    public static Bitmap getSuitableBitmap2(Activity act, Uri uri, int ww, int hh)
            throws FileNotFoundException {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeStream(act.getContentResolver().openInputStream(uri), null, newOpts);

        newOpts.inJustDecodeBounds = false;
        float w = newOpts.outWidth;
        float h = newOpts.outHeight;

        float wwh = 640f;//
        float hhh = (wwh / w) * h;//
        int be = 1;
        if (w > h && w > wwh) {
            be = (int) (newOpts.outWidth / wwh);
        } else if (w < h && h > hhh) {
            be = (int) (newOpts.outHeight / hhh);
            be += 1;
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        newOpts.outHeight = hh;
        newOpts.outWidth = ww;

        bitmap = BitmapFactory.decodeStream(act.getContentResolver().openInputStream(uri), null, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 质量压缩
     * by:Hankkin at:2015-2-14
     *
     * @param image
     * @return
     */
    public static ByteArrayOutputStream compressImage(Bitmap image) {

        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        while (baos.size() / 1024 > 30 && options > 40) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        return baos;
    }

    /**
     * bitmap临时转为文件等待上传
     * by Hankkin at:2015-4-24
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String saveBitmap(Bitmap bitmap, String path) {

        String updatePath = Environment.getExternalStorageDirectory().getPath() + "/compustrading/tempUploadPic";
        File fileSD = new File(updatePath);
        if (!fileSD.exists()) {
            fileSD.mkdirs();
        }
        try {
            String filePath = updatePath + "/" + path;
            FileOutputStream out = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out);
            out.flush();
            out.close();
            return filePath.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
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
     * 旋转照片
     * by Hankkin at:2015年10月8日 11:17:04
     * @param img
     * @return
     */
    public static Bitmap toturn(Bitmap img){
        Matrix matrix = new Matrix();
        matrix.postRotate(+90); /*翻转90度*/
        int width = img.getWidth();
        int height =img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }

    /**
     * bitmap转byte数组
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 压缩图片（质量压缩）
     * @param bitmap
     */
    public static File compressImage2File(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".JPEG");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                LogUtil.e(e.getMessage());
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            LogUtil.e(e.getMessage());
            e.printStackTrace();
        }
//        recycleBitmap(bitmap);
        return file;
    }

    /**
     * 创建视频临时帧图片
     * by Hankkin at:2015年8月18日 11:21:03
     *
     * @param filePath
     * @return
     */
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        Activity a = WeakRefrenceUtil.getContext(activity);
        if(a==null){
            return null;
        }
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = DevicesUtil.getScreenWidth(activity);
        int height = DevicesUtil.getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        Activity a = WeakRefrenceUtil.getContext(activity);
        if(a==null){
            return null;
        }
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = DevicesUtil.getScreenWidth(activity);
        int height = DevicesUtil.getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static Bitmap getOverlayImage( Bitmap sourceBitmap) {
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();
        Bitmap resultBitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
//        paint.setColor(MyViewUtil.getColor(R.color.text));
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawRect(0, 0 , width,height, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 设置模式为: 遮罩, 是取交集
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);
        return resultBitmap;
    }
    /**
     * 图片叠加效果
     * @param text
     * @return
     */
    public static Bitmap getOverlayImage( Bitmap sourceBitmap, String text,int w,int h) {
        Bitmap tochange = tochange(sourceBitmap, w, h);
        // 获取原图
        Paint backPaint = new Paint();
        Rect textBound = new Rect();
        int width = tochange.getWidth();
        int height = tochange.getHeight();
        int size= w/ (text.length()+2);
//        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//               size, UIUtil.getRes().getDisplayMetrics());
        Bitmap resultBitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(tochange, 0, 0, null);
        backPaint.setColor(Color.WHITE);
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setTextSize(size);
        // 获得当前画笔绘制文本的宽和高
        backPaint.getTextBounds(text, 0, text.length(), textBound);
        // 添加遮罩效果
        Paint paint = new Paint();
        LinearGradient shader =new LinearGradient(0, height,0,
                height,0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // 设置模式为: 遮罩, 是取交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, 0 , width,height, paint);
        canvas.drawText(text,w / 2 - textBound.width() / 2,
                h / 2 + textBound.height() / 2, backPaint);
        sourceBitmap.recycle();
        return resultBitmap;
    }

    /**
     * 在指定的位图上添加图标
     * @param sourceBitmap 地图
     * @param markImg 图标
     * @param w
     * @param h
     * @return
     */
    public static Bitmap getOverlayImage(Bitmap sourceBitmap,Bitmap markImg,int w, int h) {
        Bitmap tochange = tochange(sourceBitmap, w, h);
        // 获取原图
        Paint backPaint = new Paint();
        int width = tochange.getWidth();
        int height = tochange.getHeight();
        backPaint.setColor(Color.WHITE);
        backPaint.setStyle(Paint.Style.FILL);
        Bitmap resultBitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(tochange, 0, 0, null);
        // 添加遮罩效果
        Paint paint = new Paint();
        LinearGradient shader =new LinearGradient(0, height,0,
                height,0x20000000,
                0x00000000, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(0, 0 , width,height, paint);
        canvas.drawBitmap(markImg, w/2-markImg.getWidth()/2, h/2-markImg.getHeight()/2, backPaint);
//        canvas.save(Canvas.ALL_SAVE_FLAG);
        sourceBitmap.recycle();
//        canvas.restore();
        return resultBitmap;
    }

    public static Bitmap tochange(Bitmap bigimage,int newWidth,int newHeight){
        // 获取这个图片的宽和高
        int width = bigimage.getWidth();
        int height = bigimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth)/width;
        float scaleHeight = ((float) newHeight)/height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bigimage, 0, 0, width, height,matrix, true);
        return bitmap;
    }

    public static StateListDrawable getSelector(Drawable normal, Drawable pressed){
        StateListDrawable bg=new StateListDrawable();
        bg.addState(new int[] { android.R.attr.state_focused }, pressed);
//        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        bg.addState(new int[] {android.R.attr.state_focused}, normal);
        return bg;
    }

    /**
     * 生成有倒影的图片
     * @param context
     * @param resId 图片资源ID
     * @param percent 倒影占原图的比例
     * @return
     */
    public static Bitmap getReverseBitmapById(Context context, int resId, float percent) {
        // get the source bitmap
        Bitmap srcBitmap=BitmapFactory.decodeResource(context.getResources(), resId);
        // get the tow third segment of the reverse bitmap
        Matrix matrix=new Matrix();
        matrix.setScale(1, -1);
        Bitmap rvsBitmap=Bitmap.createBitmap(srcBitmap, 0, (int) (srcBitmap.getHeight()*(1-percent)),
                srcBitmap.getWidth(), (int) (srcBitmap.getHeight()*percent), matrix, false);
        // combine the source bitmap and the reverse bitmap
        Bitmap comBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),
                srcBitmap.getHeight()+rvsBitmap.getHeight()+20, srcBitmap.getConfig());
        Canvas gCanvas=new Canvas(comBitmap);
        gCanvas.drawBitmap(srcBitmap, 0, 0, null);
        gCanvas.drawBitmap(rvsBitmap, 0, srcBitmap.getHeight()+20, null);
        Paint paint=new Paint();
        LinearGradient shader=new LinearGradient(0, srcBitmap.getHeight()+20, 0, comBitmap.getHeight(),
                Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        gCanvas.drawRect(0, srcBitmap.getHeight()+20, srcBitmap.getWidth(), comBitmap.getHeight(), paint);
        return comBitmap;
    }

}
