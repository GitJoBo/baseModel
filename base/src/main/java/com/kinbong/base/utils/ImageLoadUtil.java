package com.kinbong.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.kinbong.base.BaseApp;
import com.kinbong.base.R;
import com.kinbong.model.util.MySP;
import com.kinbong.model.util.NetWorkUtil;
import com.kinbong.recycler.base.BaseViewHolder;

import java.io.File;


/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class ImageLoadUtil {
    public static void clearMemory() {
        Glide.get(BaseApp.sContext).clearMemory();
    }

    public static void clearDiskCache() {
        Glide.get(BaseApp.sContext).clearDiskCache();
    }

    public static void onLowMemory(Context context) {
        Glide.with(context).onLowMemory();
    }

    public static void displayImageList(BaseViewHolder holder, String url, @IdRes int idres) {
        displayImage(holder.getContext(), url, holder.getView(idres));
    }

    public static void displayImage(Context context, String url, ImageView imageView) {
        displayImage(context, url, imageView, R.mipmap.bg_load);
    }

    public static void displayImageNoCache(Context context, String url, ImageView imageView) {
        displayImageNoCache(context, url, imageView, R.mipmap.bg_load);
    }

    public static void displayImage(Context context, String url, ImageView imageView, @DrawableRes int imgId) {
        if (isMobLoadImage(url)) {
            imageView.setImageResource(imgId);
            return;
        }
        getDrawbleRequest(context, url, imgId).into(imageView);
    }
    public static void displayImage(Context context, String url, ImageView imageView, @DrawableRes int imgErrId, @DrawableRes int imgLoadId) {
        if (isMobLoadImage(url)) {
            imageView.setImageResource(imgErrId);
            return;
        }
        getDrawbleRequest(context, url, imgErrId,imgLoadId).into(imageView);
    }

    public static void displayImageNoCache(Context context, String url, ImageView imageView, @DrawableRes int imgId) {
        if (isMobLoadImage(url)) {
            imageView.setImageResource(imgId);
            return;
        }
        getDrawbleRequestNoCache(context, url, imgId).into(imageView);
    }

    private static boolean isMobLoadImage(String url) {
        boolean b = MySP.isMobileLoadImage();
        if (TextUtils.isEmpty(url) || (b && !(NetWorkUtil.getConnectedType() == NetWorkUtil.NETWORK_WIFI))) {

            return true;
        }
        return false;
    }

    public static void displayImage(RequestManager manager, String url, ImageView imageView) {
        manager.load(url).fitCenter().placeholder(R.mipmap.bg_load).error(R.mipmap.bg_load).into(imageView);
    }

    public static void displayImage(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    public static void displayImage(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).into(imageView);
    }

    public static void displayKeepImage(Context context, String url, ImageView imageView, @DrawableRes int imgId) {
        if (isMobLoadImage(url)) {
            imageView.setImageResource(imgId);
            return;
        }
        Glide.with(context).load(url)
                .asBitmap()//强制返回一个bitmap
                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                .skipMemoryCache(true)//跳过内存缓存
                .error(imgId)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getWidth();
                        int vh = Math.round(resource.getHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        params.height = vh;
                        params.width = vw;
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public static void displayCodeImg(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void clear(View view) {
        Glide.clear(view);
    }

    public static void displayHeadIcon(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).error(R.mipmap.bg_load).fitCenter().into(imageView);
    }

    public static DrawableRequestBuilder<String> getDrawbleRequest(Context context, String url) {
        return getDrawbleRequest(context, url, R.mipmap.bg_load);
    }

    public static DrawableRequestBuilder<String> getDrawbleRequest(Context context, String url, @DrawableRes int imgId) {
        return Glide.with(context).load(url).error(imgId);
    }
    public static DrawableRequestBuilder<String> getDrawbleRequest(Context context, String url, @DrawableRes int imgErrId, @DrawableRes int imgLoadId) {
        return Glide.with(context).load(url).error(imgErrId).placeholder(imgLoadId);
    }

    public static DrawableRequestBuilder<String> getDrawbleRequestNoCache(Context context, String url, @DrawableRes int imgId) {
        return Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                .skipMemoryCache(true)//跳过内存缓存
                .error(imgId);
    }

    public static void onDestroy(Context context) {
        Glide.with(context).onDestroy();
    }

    public static void onStop(Context context) {
        Glide.with(context).onStop();
    }

    public static void onStart(Context context) {
        Glide.with(context).onStart();
    }


    public static void displayRoundCornerImage(Context context, String url, ImageView iv_good) {

        Context c = WeakRefrenceUtil.getContext(context);
        if (c == null) return;

        if (isMobLoadImage(url)) {
            Glide.with(context).load(R.mipmap.bg_load).bitmapTransform(new RoundedCornersTransformation(c, 3, 0)).into(iv_good);
            return;
        }
        getDrawbleRequest(c, url).bitmapTransform(new RoundedCornersTransformation(c, 3, 0)).into(iv_good);
    }

    public static void displayCategoryImg(Context context, ImageView iv, String url, boolean isFocus) {
        Glide.with(context).load(url).crossFade(0).fitCenter().centerCrop().transform(new BitmapTransformation(context) {
            @Override
            protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                if (!isFocus)
                    return BitmapUtil.getOverlayImage(toTransform);
                return toTransform;
            }

            @Override
            public String getId() {
                return url + isFocus;
            }
        }).into(iv);
    }


}
