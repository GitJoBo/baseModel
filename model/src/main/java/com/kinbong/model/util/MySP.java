package com.kinbong.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kinbong.model.ModelApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Set;

public class MySP {
    public static final String CLIENT_MEMBER_ID = "CLIENT_MEMBER_ID";
    public static final String MEMBER_ID = "member_id";
    private static final String PREFERENCE_NAME = "config";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";

    public static final String TOKEN = "token";
    public static String TOKENID = "";
    public static String USER_IMGURL = "";
    public static final String LOGIN_NAME = "name";
    public static final String LOGIN_IMGURL = "imgUrl";
    public static final String STORE_ID = "store_id";
    public static final String IS_MOBILE = "IS_MOBILE";
    public static final String USER = "USER";

    public static final int LOAD_GO = 0;

    private static SharedPreferences mSP = null;
    public static final String MY_KEY = "MY_KEY";
    public static final int LOAD_SUCCESS_CODE = 200;
    public static final int TOKEN_EMPTY = 900;
    public static final String PACKAGE = "com.kinbong.jbcclient";
    public static final int LOAD_INIT_CODE = 1;
    public static final String ANDROID = "ANDROID";
    public static final String DEVICE = "device";
    public static final int TIME = 1;
    public static final int TIME_LONG = 1000;


    public static final String imgId = "imgId";
    public static final String isEnable = "isEnable";
    public static final String orgId = "orgId";
    public static final String phoneNumber = "phoneNumber";
    public static final String realName = "realName";
    public static final String sex = "sex";
    public static final String sort = "sort";
    public static final String type = "type";
    public static final String updateTime = "updateTime";
    public static final String updateUserId = "updateUserId";
    public static final String userId = "userId";
    public static final String userName = "userName";

    public static String XMLX = "";

    public static boolean saveData(String key, Object value) {
        if (key == null || value == null) {
            return false;
        }
        SharedPreferences.Editor editor = createSp().edit();

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set) value);
        } else {
            editor.putString(key, value.toString());

        }
        return editor.commit();
    }

    public static String getStringData(String key) {

        return createSp().getString(key, "");

    }

    public static boolean getBooleanData(String key) {

        return createSp().getBoolean(key, false);

    }

    public static int getIntData(String key) {
        int value = 0;
        if (key == null) {
            return value;
        }
        value = createSp().getInt(key, 0);
        return value;

    }

    public static long getLongData(String key) {
        long value = -1;
        if (key == null) {
            return value;
        }
        value = createSp().getLong(key, 0);
        return value;

    }

    public static String getID() {
        return createSp().getString(USER_ID, "");
    }

    public static String getMemberId() {
        return createSp().getString(MEMBER_ID, "");
    }

    public static String getStoreID() {
        return createSp().getString(STORE_ID, "");
    }

    public static String getLoginName() {

        return createSp().getString(LOGIN_NAME, "");
    }

    public static String getUserName() {
        return createSp().getString(USER_NAME, "");
    }

    public static final String getClientMemberId() {
        return createSp().getString(CLIENT_MEMBER_ID, "");
    }

    public static String getToken() {
        if (TextUtils.isEmpty(TOKENID)) {
            TOKENID = createSp().getString(TOKEN, "");
        }
        return TOKENID;
    }
    public static String getImgUrl() {
        if (TextUtils.isEmpty(MySP.USER_IMGURL)) {
            USER_IMGURL = createSp().getString(LOGIN_IMGURL, "");
        }
        return USER_IMGURL;
    }


    public static boolean deleteKey(String key) {
        if (key == null) {
            return false;
        }
        SharedPreferences.Editor editor = createSp().edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean deleteKeys(String... keys) {
        if (keys == null) {
            return false;
        }

        SharedPreferences.Editor editor = createSp().edit();
        for (int i = 0; i < keys.length; i++) {
            editor.remove(keys[i]);
        }
        return editor.commit();
    }

    public static SharedPreferences createSp() {
        if (mSP == null) {
            synchronized (MySP.class) {
                if (mSP == null) {
                    mSP = ModelApp.sContext.getSharedPreferences(PREFERENCE_NAME,
                            Context.MODE_PRIVATE);
                }
            }
        }
        return mSP;
    }

    public static boolean isMobileLoadImage() {
        return createSp().getBoolean(IS_MOBILE, false);
    }

    public static void clear() {
        createSp().edit().clear().commit();
    }

    public static void remove(String key) {
//       JPushUtil.clearAlias();
        createSp().edit().remove(key).commit();
    }

    public static void clearToken() {
//        createSp().edit().putString(TOKEN,"").commit();
        try {
            createSp().edit().remove(TOKEN).commit();
        } catch (Exception e) {

        }
    }

    public static final boolean clearLoginInfo() {
//        JPushUtil.setAliasAndTags("");
        return createSp().edit().remove(TOKEN).remove(MEMBER_ID).remove(STORE_ID).commit();
    }

    public static String getMyKey() {
        return createSp().getString(MY_KEY, "");
    }


    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    public static final HashMap<String, String> getDefaultParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MySP.TOKEN, MySP.getToken());
        map.put(MySP.MEMBER_ID, MySP.getMemberId());
        map.put(MySP.DEVICE, MySP.ANDROID);
        return map;
    }
//    public static final HashMap<String, String> getDefaultUser() {
//        HashMap<String, String> map = new HashMap<>();
//        map.put(MySP.userId, MySP.getStringData(MySP.userId));
//        map.put(MySP.orgId, MySP.getStringData(MySP.orgId));
//        return map;
//    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
//    public void setObject(String key, Object object) {
//        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
//
//        //创建字节输出流
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //创建字节对象输出流
//        ObjectOutputStream out = null;
//        try {
//            //然后通过将字对象进行64转码，写入key值为key的sp中
//            out = new ObjectOutputStream(baos);
//            out.writeObject(object);
//            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString(key, objectVal);
//            editor.commit();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (baos != null) {
//                    baos.close();
//                }
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public <T> T getObject(String key, Class<T> clazz) {
//        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
//        if (sp.contains(key)) {
//            String objectVal = sp.getString(key, null);
//            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
//            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
//            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
//            ObjectInputStream ois = null;
//            try {
//                ois = new ObjectInputStream(bais);
//                T t = (T) ois.readObject();
//                return t;
//            } catch (StreamCorruptedException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bais != null) {
//                        bais.close();
//                    }
//                    if (ois != null) {
//                        ois.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
}
