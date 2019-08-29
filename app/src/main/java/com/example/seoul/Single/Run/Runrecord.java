package com.example.seoul.Single.Run;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Runrecord {

    String userID;
    int idx;
    ArrayList<LatLng> cord;

    public Runrecord(String userID, int idx, ArrayList<LatLng> cord)
    {
//        cord=new ArrayList<>();
        this.userID=userID;
        this.idx=idx;
        this.cord=cord;
    }


    public String getUserID() {
        return userID;
    }

    public int getIdx() {
        return idx;
    }

    public ArrayList<LatLng> getCord() {
        return cord;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setCord(ArrayList<LatLng> cord) {
        this.cord = cord;
    }

    public JSONArray recordToJson()  //위치 좌표 json타입으로 변환
    {
        JSONArray jsonArray = new JSONArray();
        try {

            for(int i=0;i<cord.size();i++)
            {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("userID",userID);
                jsonObject.put("idx",idx);
                jsonObject.put("lat",cord.get(i).latitude);
                jsonObject.put("log",cord.get(i).longitude);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


}
