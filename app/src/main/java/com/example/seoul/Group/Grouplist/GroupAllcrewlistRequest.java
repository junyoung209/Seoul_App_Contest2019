package com.example.seoul.Group.Grouplist;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


public class GroupAllcrewlistRequest extends StringRequest {

    final static private String URL="http://39.115.93.52:8111/group/grouplist/groupalllistsend.php";


    public GroupAllcrewlistRequest(Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
    }

}
