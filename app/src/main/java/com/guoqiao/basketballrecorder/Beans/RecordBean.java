package com.guoqiao.basketballrecorder.Beans;

import com.guoqiao.basketballrecorder.Utils.GsonUtil;

import java.util.ArrayList;

/**
 * Created by Guoqiao on 3/25/16.
 */
public class RecordBean {
    private String player;
    private String teamOneName;
    private String teamTwoName;
    private int teamOneScore;
    private int teamTwoScore;
    private int threePointScored;
    private int threePointMissed;
    private int twoPointScored;
    private int twoPointMissed;
    private int freeThrowScored;
    private int freeThrowMissed;
    private int rebound;
    private int steal;
    private int assist;
    private ArrayList<SingleRecordBean> records;

    public RecordBean(){

    }

    public RecordBean(String player, String teamOneName, String teamTwoName, int teamOneScore, int teamTwoScore,
                      int threePointScored, int threePointMissed, int twoPointScored, int twoPointMissed, int freeThrowScored, int freeThrowMissed,
                      int rebound, int steal, int assist, ArrayList<SingleRecordBean> records) {
        this.player = player;
        this.teamOneName = teamOneName;
        this.teamTwoName = teamTwoName;
        this.teamOneScore = teamOneScore;
        this.teamTwoScore = teamTwoScore;
        this.threePointScored = threePointScored;
        this.threePointMissed = threePointMissed;
        this.twoPointScored = twoPointScored;
        this.twoPointMissed = twoPointMissed;
        this.freeThrowScored = freeThrowScored;
        this.freeThrowMissed = freeThrowMissed;
        this.rebound = rebound;
        this.steal = steal;
        this.assist = assist;
        this.records = records;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getTeamOneName() {
        return teamOneName;
    }

    public void setTeamOneName(String teamOneName) {
        this.teamOneName = teamOneName;
    }

    public String getTeamTwoName() {
        return teamTwoName;
    }

    public void setTeamTwoName(String teamTwoName) {
        this.teamTwoName = teamTwoName;
    }

    public int getTeamOneScore() {
        return teamOneScore;
    }

    public void setTeamOneScore(int teamOneScore) {
        this.teamOneScore = teamOneScore;
    }

    public int getTeamTwoScore() {
        return teamTwoScore;
    }

    public void setTeamTwoScore(int teamTwoScore) {
        this.teamTwoScore = teamTwoScore;
    }

    public int getThreePointScored() {
        return threePointScored;
    }

    public void setThreePointScored(int threePointScored) {
        this.threePointScored = threePointScored;
    }

    public int getThreePointMissed() {
        return threePointMissed;
    }

    public void setThreePointMissed(int threePointMissed) {
        this.threePointMissed = threePointMissed;
    }

    public int getTwoPointScored() {
        return twoPointScored;
    }

    public void setTwoPointScored(int twoPointScored) {
        this.twoPointScored = twoPointScored;
    }

    public int getTwoPointMissed() {
        return twoPointMissed;
    }

    public void setTwoPointMissed(int twoPointMissed) {
        this.twoPointMissed = twoPointMissed;
    }

    public int getFreeThrowScored() {
        return freeThrowScored;
    }

    public void setFreeThrowScored(int freeThrowScored) {
        this.freeThrowScored = freeThrowScored;
    }

    public int getFreeThrowMissed() {
        return freeThrowMissed;
    }

    public void setFreeThrowMissed(int freeThrowMissed) {
        this.freeThrowMissed = freeThrowMissed;
    }

    public int getRebound() {
        return rebound;
    }

    public void setRebound(int rebound) {
        this.rebound = rebound;
    }

    public int getSteal() {
        return steal;
    }

    public void setSteal(int steal) {
        this.steal = steal;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public ArrayList<SingleRecordBean> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<SingleRecordBean> records) {
        this.records = records;
    }

    public String toString(){
        return GsonUtil.getGson().toJson(this);
    }

}
