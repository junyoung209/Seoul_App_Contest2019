package com.example.seoul.Group.Mycrewlist;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MycrewlistRequest extends StringRequest {

    final static private String URL="http://39.115.93.52:8111/group/mycrewlist/mycrewlistsend.php";
    private Map<String,String> parameters;

    public MycrewlistRequest(String userID, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}