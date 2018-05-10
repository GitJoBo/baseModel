package com.kinbong.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static android.R.attr.src;

/**
 * Created by Administrator on 2017/1/12.
 */

public class GsonUtil {

    private static Gson mGson;

    public final static Gson getGson(){
        if(mGson==null){
            synchronized (GsonUtil.class){
                if(mGson==null)
                mGson = new GsonBuilder().
                        registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                            @Override
                            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                                if (src == src.longValue())
                                    return new JsonPrimitive(src.longValue());
                                return new JsonPrimitive(src);
                            }
                        })
                        .disableHtmlEscaping()//解决Gson把实体类转成json特殊符号会出现乱码
                        .create();
            }

        }

        return mGson;
    }
    public final static String getJson(Object o){
        return getGson().toJson(o);
    }
    public final static  <T> T  getJson(Object o, Class<T> classOfT){
        return getGson().fromJson(getGson().toJson(o), classOfT);
    }
    public final static  <T> T  fromJson(String json, Class<T> classOfT){
        return getGson().fromJson(json, classOfT);
    }

    public final static  <T> List<T> getJsonList(Object o, Class<T> classOfT){
        List list = getGson().fromJson(getGson().toJson(o), List.class);
        List<T> ts = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ts.add(getJson(list.get(i), classOfT));
        }
        return ts;
    }
}
