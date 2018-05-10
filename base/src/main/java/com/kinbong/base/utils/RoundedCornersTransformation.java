package com.kinbong.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.kinbong.model.util.DevicesUtil;


/**
 * Created by Administrator on 2016/10/11.
 */

public class RoundedCornersTransformation implements Transformation<Bitmap> {

    private  BitmapPool mBitmapPool;
    private final int mRadius;
    private int mMargin;

    public RoundedCornersTransformation(Context context,int radius,int margin){
        mBitmapPool=Glide.get(context).getBitmapPool();
        mRadius = (int) (radius* DevicesUtil.getScreenDensity());
        mMargin = (int) (margin* DevicesUtil.getScreenDensity());
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        BlurMaskFilter bf = new BlurMaskFilter(20,BlurMaskFilter.Blur.INNER);
//        paint.setColor(Color.GRAY);
//        paint.setMaskFilter(bf);
        float right = width - mMargin;
        float bottom = height - mMargin;
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
        source.recycle();
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "RoundedTransformation(radius=" + mRadius + ", margin=" + mMargin + ")";
    }
}
