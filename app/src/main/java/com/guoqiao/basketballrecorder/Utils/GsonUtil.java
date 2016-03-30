package com.guoqiao.basketballrecorder.Utils;

import com.google.gson.Gson;
import com.guoqiao.basketballrecorder.Beans.RecordBean;

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

    public static RecordBean stringToRecordBean(String s){
        return GsonUtil.getGson().fromJson(s, RecordBean.class);
    }

    public static void setGson(Gson gson) {
        GsonUtil.gson = gson;
    }
}
