package com.example.seoul.Group.Grouplist;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GroupSurcrewlistRequest extends StringRequest {

    final static private String URL="http://39.115.93.52:8111/group/grouplist/groupsurlistsend.php";
    private Map<String,String> parameters;

    public GroupSurcrewlistRequest(String userID, String userRegion , Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userRegion",userRegion);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
