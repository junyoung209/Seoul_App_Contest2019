package com.example.seoul.Single.Run;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Runrecord implements Serializable {

    String userID;
    String runTime;
    String runDate;
    String runDistance;
    String runVelocity;



    public Runrecord(String userID, String runTime,String runDistance,String runVelocity,String runDate)
    {

        this.userID=userID;
        this.runTime=runTime;
        this.runDistance=runDistance;
        this.runVelocity=runVelocity;
        this.runDate=runDate;
    }

    public Runrecord() {

    }

    public String getUserID() {
        return userID;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getRunDate() {
        return runDate;
    }

    public String getRunDistance() {
        return runDistance;
    }

    public String getRunVelocity() {
        return runVelocity;
    }
    //    public JSONArray recordToJson()  //위치 좌표 json타입으로 변환
//    {
//        JSONArray jsonArray = new JSONArray();
//        try {
//
//            for(int i=0;i<cord.size();i++)
//            {
//                JSONObject jsonObject=new JSONObject();
//                jsonObject.put("userID",userID);
//                jsonObject.put("idx",idx);
//                jsonObject.put("lat",cord.get(i).latitude);
//                jsonObject.put("log",cord.get(i).longitude);
//                jsonArray.put(jsonObject);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonArray;
//    }



}
