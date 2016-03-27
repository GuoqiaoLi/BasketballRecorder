package com.guoqiao.basketballrecorder.Beans;

import com.google.gson.Gson;
import com.guoqiao.basketballrecorder.Utils.GsonUtil;

/**
 * Created by Guoqiao on 3/26/16.
 */
public class SingleRecordBean {
    private float x;
    private float y;
    private boolean scored;
    private int tag;
    private String description;

    public SingleRecordBean(){

    }

    public SingleRecordBean(float x, float y, boolean scored, int tag, String description) {
        this.x = x;
        this.y = y;
        this.scored = scored;
        this.tag = tag;
        this.description = description;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return GsonUtil.getGson().toJson(this);
    }

    public SingleRecordBean stringToSingleRecordBean(String s){
        return GsonUtil.getGson().fromJson(s, SingleRecordBean.class);
    }
}
