package com.example.seoul.Single.Myrecord;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyrecordDeleteRequset extends StringRequest {

    final static private String URL = "http://39.115.93.52:8111/runresult/datadelete.php";
    private Map<String, String> parameters;

    public MyrecordDeleteRequset(String idx,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);//해당 URL에 POST방식으로 파마미터들을 전송함
        parameters = new HashMap<>();
        parameters.put("idx", idx);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}