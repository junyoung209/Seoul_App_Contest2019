package com.example.seoul;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL="http://39.115.93.52:8111/register/userregister.php";
    private Map<String,String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userRegion, String userEmail,String userPhone, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userGender",userGender);
        parameters.put("userRegion",userRegion);
        parameters.put("userEmail",userEmail);
        parameters.put("userPhone",userPhone);

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
