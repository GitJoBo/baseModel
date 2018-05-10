package com.kinbong.base.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.kinbong.base.BaseApp;
import com.kinbong.model.util.CacheUtil;
import com.kinbong.model.util.MySP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static com.kinbong.base.utils.UIUtils.getFileProvider;



/**
 * Created by Administrator on 2016/11/25.
 */

public class PhotoUtil {
    public final static  int REQUEST_PHOTO = 1005;
    public final static  int REQUEST_CAMERA= 1004;
    public final static  int REQUEST_PHOTO_CROP = 1006;
    public final static void photo(Fragment fragment) {
        Intent i = getPhotoIntent();
        fragment.startActivityForResult(i, REQUEST_PHOTO);
    }

    @NonNull
    private static Intent getPhotoIntent() {
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    public final static File camera(Fragment fragment) {
        Intent intent = getCameraIntent();
        File imgFile = getImgFile();
        Uri fileUri =getOutputMediaFileUri(imgFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        fragment.startActivityForResult(intent,REQUEST_PHOTO);
        return imgFile;
    }
    public final static Uri camera(Activity activity) {
        Intent intent = getCameraIntent();
        Uri fileUri = getOutputMediaFileUri(getImgFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        activity.startActivityForResult(intent,REQUEST_PHOTO);
        return fileUri;
    }


    public static Uri getOutputMediaFileUri() {
        File mediaFile = getImgFile();
        if (mediaFile == null) return null;
//        if(mediaFile.exists())mediaFile.delete();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri imageUri = FileProvider.getUriForFile(BaseApp.sContext,UIUtils.getFileProvider(), mediaFile);
            //通过FileProvider创建一个content类型的Uri
            return imageUri;
        }

        return Uri.fromFile(mediaFile);
    }

    @Nullable
    public static File getImgFile() {
        File mediaStorageDir = new File(
                CacheUtil.getSaveDirPath());
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        long timeMillis = System.currentTimeMillis();
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +timeMillis+ CacheUtil.HEAD_IMG);
//        if(mediaFile.exists())mediaFile.delete();
        return mediaFile;
    }
    @NonNull
    private final static Intent getCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //添加这一句表示对目标应用临时授权该Uri所代表的文件

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return intent;
    }

    public final static Uri onActivityResult(Fragment fragment,int requestCode, int resultCode, Intent data, File file){

        if (requestCode == REQUEST_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                Uri fileUri = null;
                if (data != null) {
                    fileUri = data.getData();
                }

                Uri fileCropUri = Uri.fromFile(getImgFile());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (file == null && fileUri != null) {
                        cropImageUri(fragment, fileUri, fileCropUri, 640, 640, REQUEST_PHOTO_CROP);
                    } else if (file != null) {
                        Uri imageUri = FileProvider.getUriForFile(BaseApp.sContext, UIUtils.getFileProvider(), file);

                        cropImageUri(fragment, imageUri, fileCropUri, 640, 640, REQUEST_PHOTO_CROP);
                    }

                } else {
                    cropImageUri(fragment, fileUri, fileCropUri, 640, 640, REQUEST_PHOTO_CROP);
                }
                return fileCropUri;

            }

        }
        return null;
    }
    public static void cropImageUri(Fragment fragment, Uri uri, Uri outputUri, int outputX, int outputY, int requestCode) {
        try {
            Intent intent = getCropImageIntent(uri, outputUri, outputX, outputY);
            fragment.startActivityForResult(intent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void cropImageUri(Activity activity, Uri uri, Uri outputUri, int outputX, int outputY, int requestCode) {
        try {
            Intent intent = getCropImageIntent(uri, outputUri, outputX, outputY);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {

        }
    }

    @NonNull
    private static Intent getCropImageIntent(Uri uri, Uri outputUri, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * 根据file拿uri
     * @param mediaFile
     * @return
     */
    public static Uri getOutputMediaFileUri(File mediaFile) {
        if (mediaFile == null) return null;
//        if(mediaFile.exists())mediaFile.delete();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            Uri imageUri = FileProvider.getUriForFile(BaseApp.sContext,UIUtils.getFileProvider(), mediaFile);
            //通过FileProvider创建一个content类型的Uri
            return imageUri;
        }
        return Uri.fromFile(mediaFile);
    }

    /**
     * 根据uri拿file
     * @param imageUri
     * @return
     * @throws Exception
     */
    public static File getFile(Uri imageUri) throws Exception {
        File file =null;
        OutputStream os = null;

        InputStream ins = BaseApp.sContext.getContentResolver().openInputStream(imageUri);
        file =getImgFile();
        os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();

        return file;
    }

}

