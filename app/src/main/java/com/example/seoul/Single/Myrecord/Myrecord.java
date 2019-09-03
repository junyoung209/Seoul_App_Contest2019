package com.example.seoul.Single.Myrecord;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Myrecord {

    private int idx;
    private String runTime;
    private String runDate;
    private String runVelocity;
    private String runDistance;
    private ArrayList<LatLng> cord=new ArrayList<>();

    public Myrecord(int idx, String runTime,String runDistance,String runVelocity, String runDate)
    {
        this.idx=idx;
        this.runDate=runDate;
        this.runDistance=runDistance;
        this.runVelocity=runVelocity;
        this.runTime=runTime;

    }

    public int getIdx() {
        return idx;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getRunVelocity() {
        return runVelocity;
    }

    public String getRunDistance() {
        return runDistance;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public void setDate(String date) {
        this.runDate = date;
    }
}
