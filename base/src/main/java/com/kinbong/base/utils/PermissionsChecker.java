package com.kinbong.base.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.kinbong.base.BaseApp;


/**
 * 检查权限的工具类
 * <p/>
 * Created by wangchenlong on 16/1/26.
 */
public class PermissionsChecker {
   public static final String[] PERMISSIONS = new String[]{
//           Manifest.permission.CALL_PHONE,
           Manifest.permission.READ_EXTERNAL_STORAGE,
           Manifest.permission.WRITE_EXTERNAL_STORAGE,
//           Manifest.permission.ACCESS_FINE_LOCATION,
//           Manifest.permission.ACCESS_COARSE_LOCATION
    };
//

//    public static final String[] PERMISSIONS = new String[]{
//           Manifest.permission_group.MICROPHONE,
//            Manifest.permission_group.STORAGE,
//            Manifest.permission_group.PHONE,
//            Manifest.permission_group.LOCATION
//    };

    // 判断权限集合
    public static boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    public static boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(BaseApp.sContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }




}
