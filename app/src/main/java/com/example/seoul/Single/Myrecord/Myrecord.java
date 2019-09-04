package com.example.seoul.Single.Myrecord;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class Myrecord implements Serializable {

    private int idx;
    private String userID;
    private String runTime;
    private String runDate;
    private String runVelocity;
    private String runDistance;
    private ArrayList<LatLng> cord;

    public Myrecord(int idx,String userID, String runTime,String runDistance,String runVelocity, String runDate,ArrayList<LatLng> cord)
    {
        this.idx=idx;
        this.userID=userID;
        this.runDate=runDate;
        this.runDistance=runDistance;
        this.runVelocity=runVelocity;
        this.runTime=runTime;
        this.cord=cord;

    }

    public ArrayList<LatLng> getCord() {
        return cord;
    }

    public int getIdx() {
        return idx;
    }

    public String getUserID() {
        return userID;
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


}
