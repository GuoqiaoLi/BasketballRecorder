package com.guoqiao.basketballrecorder.Beans;

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
    private ArrayList<SingleRecordBean> records;

    public RecordBean(){

    }

    public RecordBean(String player, String teamOneName, String teamTwoName, int teamOneScore, int teamTwoScore, ArrayList<SingleRecordBean> records) {
        this.player = player;
        this.teamOneName = teamOneName;
        this.teamTwoName = teamTwoName;
        this.teamOneScore = teamOneScore;
        this.teamTwoScore = teamTwoScore;
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

    public ArrayList<SingleRecordBean> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<SingleRecordBean> records) {
        this.records = records;
    }
}
