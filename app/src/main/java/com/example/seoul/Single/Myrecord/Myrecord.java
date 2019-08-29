package com.example.seoul.Single.Myrecord;

public class Myrecord {

    private int idx;
    private String runTime;
    private String date;

    public Myrecord(int idx, String runTime, String date)
    {
        this.idx=idx;
        this.date=date;
        this.runTime=runTime;
    }

    public int getIdx() {
        return idx;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getDate() {
        return date;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
