package com.example.rc_reg;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class score extends AppCompatActivity {
    TextView name,voucher,email,phone,location,total,level_complete,parts_collected;
    String ipServer="192.168.0.144";
    private String TAG="--Score----";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        name=(TextView)findViewById(R.id.user_name);
        email=(TextView)findViewById(R.id.user_email);
        phone=(TextView)findViewById(R.id.Mobile);
        location=(TextView)findViewById(R.id.user_location);
        voucher=(TextView)findViewById(R.id.voucher_code);
        total=(TextView)findViewById(R.id.total);
        level_complete=(TextView)findViewById(R.id.level_complete);
        parts_collected=(TextView)findViewById(R.id.parts_collected);

//        FONTS
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fon.ttf");

        name.setTypeface(custom_font);
        email.setTypeface(custom_font);
        parts_collected.setTypeface(custom_font);
        phone.setTypeface(custom_font);
        level_complete.setTypeface(custom_font);
        location.setTypeface(custom_font);
        voucher.setTypeface(custom_font);
        total.setTypeface(custom_font);

        final RequestQueue queue = Volley.newRequestQueue(this);
        try{
            String url = "http://"+ipServer+":3000/api/consolidate_tabs";
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG,"Response"+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"VolleyError",error);
                }
            }){
                protected Map<String,String> getParams(){
                    Map<String, String> MyData=new HashMap<String, String>();
//                    MyData.put("email",User_Email);
//                    MyData.put("name",User_Name);
//                    MyData.put("phone",User_Phone);
//                    MyData.put("time",time);
//                    MyData.put("location",User_Location);
//                    MyData.put("voucher_code",voucher_coupon);
                    return MyData;
                }
            };
            queue.add(stringRequest);
        }catch (Exception ex){

        }
    }

}
