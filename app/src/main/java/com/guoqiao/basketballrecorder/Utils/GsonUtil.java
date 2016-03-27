package com.guoqiao.basketballrecorder.Utils;

import com.google.gson.Gson;

/**
 * Created by Guoqiao on 3/26/16.
 */
public class GsonUtil {
    public static Gson gson;

    public static Gson getGson() {
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }

    public static void setGson(Gson gson) {
        GsonUtil.gson = gson;
    }
}
