package com.example.rc_reg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class score extends AppCompatActivity {
    TextView name,voucher,email,levelText,partText,totalText,location,total,level_complete,parts_collected,logout;
    private String TAG="--Score----";
   private RequestQueue queue;
    RelativeLayout layout;
    ImageButton imageButton,sync;
    Boolean isConnected=true;
    private boolean monitoringConnectivity = false;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String nameKey = "nameKey";
    public static final String phoneKey = "phoneKey";
    public static final String emailKey = "emailKey";
    public static final String voucherKey = "voucherKey";
    public static final String cityKey = "cityKey";
    public static final String stateKey = "stateKey";
    public static final String playerScoreKey = "playerScoreKey";
    public static final String levelCompleteKey = "levelCompleteKey";
    public static final String TotalScore = "TotalScore";
    public static final String level1time = "level1time";
    public static final String level2time = "level2time";
    public static final String level3time = "level3time";
    public static final String level4time = "level4time";
    public static final String level5time = "level5time";
    String sharedPhone,sharedEmail,sharedN,sharedVoucher,sharedCity,sharedState,sharedLevelComplete,sharedScore,json_data;
    String getName,getPhone,getEmail,getVoucher,getLocation,getTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        imageButton=(ImageButton)findViewById(R.id.backbtn);
        sync=(ImageButton)findViewById(R.id.sync);
        name=(TextView)findViewById(R.id.user_name);
        email=(TextView)findViewById(R.id.user_email);
        levelText=(TextView)findViewById(R.id.levelText);
        partText=(TextView)findViewById(R.id.partText);
        totalText=(TextView)findViewById(R.id.totalText);
        location=(TextView)findViewById(R.id.user_location);
        voucher=(TextView)findViewById(R.id.voucher_code);
        total=(TextView)findViewById(R.id.total);
        logout=(TextView)findViewById(R.id.logout);
        level_complete=(TextView)findViewById(R.id.level_complete);
        parts_collected=(TextView)findViewById(R.id.parts_collected);
        layout=(RelativeLayout)findViewById(R.id.fourth);
        // shared getData
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        json_data = sharedpreferences.getString("JSON_DATA", "");



        try {
            JSONObject jsonObject=new JSONObject(json_data);
            sharedN = jsonObject.getString( "nameKey");
            sharedEmail = jsonObject.getString( "emailKey");
            sharedPhone = jsonObject.getString( "phoneKey");
            sharedVoucher = jsonObject.getString( "voucherKey");
            sharedCity = jsonObject.getString( "cityKey");
            sharedState = jsonObject.getString( "stateKey");
            sharedLevelComplete = jsonObject.getString( "lastLevel");
            sharedScore = jsonObject.getString( "TotalScore");
            email.setText(sharedEmail+" | "+sharedPhone);
            name.setText(sharedN);
            if(sharedVoucher.equals("null") || sharedVoucher.isEmpty() || sharedVoucher==null || sharedVoucher.equals("")){

                voucher.setText("VOUCHER : "+"UNLUCKY");

            }else if(!sharedVoucher.equals("")){
                voucher.setText("VOUCHER : "+sharedVoucher);

            }
//            JSONObject object = new JSONObject(json_data);
//            int TotalScoreShared=Integer.parseInt(object.getString("level1Score"))+Integer.parseInt(object.getString( "level2Score"))+Integer.parseInt(object.getString( "level3Score"))
//                    +Integer.parseInt(object.getString( "level4Score"))+Integer.parseInt(object.getString( "level5Score"));
//            object.put(TotalScore,TotalScoreShared);
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString("JSON_DATA", object.toString());
//            editor.commit();
            total.setText(sharedScore);
            if(jsonObject.get("level1Cleared").equals("complete")){
                level_complete.setText("1");
                parts_collected.setText("1");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete")){
                level_complete.setText("2");
                parts_collected.setText("2");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete") && jsonObject.get("level3Cleared").equals("complete")){
                level_complete.setText("3");
                parts_collected.setText("3");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete") && jsonObject.get("level3Cleared").equals("complete") && jsonObject.get("level4Cleared").equals("complete")){
                level_complete.setText("4");
                parts_collected.setText("4");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete") && jsonObject.get("level3Cleared").equals("complete") && jsonObject.get("level4Cleared").equals("complete") && jsonObject.get("level5Cleared").equals("complete")){
                level_complete.setText("5");
                parts_collected.setText("5");
            }

            location.setText(sharedCity+","+sharedState);
            Log.e(TAG,"sharedpreferences score: "+json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        FONTS
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fon.otf");
        name.setTypeface(custom_font);
        email.setTypeface(custom_font);
        parts_collected.setTypeface(custom_font);
        levelText.setTypeface(custom_font);
        totalText.setTypeface(custom_font);
        partText.setTypeface(custom_font);
        level_complete.setTypeface(custom_font);
        location.setTypeface(custom_font);
        voucher.setTypeface(custom_font);
        total.setTypeface(custom_font);
        logout.setTypeface(custom_font);


        queue = Volley.newRequestQueue(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),start.class);
                startActivity(intent);
                finish();
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectivity();
                if(isConnected){
                    try {
                        dataSync();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG,"DATA SYNCED CALLING....");
                }else {
                    Log.e(TAG,"Please connect internet first CALLING....");
                    Toast.makeText(getApplicationContext(), "Please connect internet first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),bottle.class);
                startActivity(intent);
            }
        });
    }

    public void dataSync() throws JSONException, UnsupportedEncodingException {
//        try{
            Log.e(TAG,"Try Score DataSynced");
//            json_data=sharedpreferences.getString("JSON_DATA", "");
            JSONObject jsonSync=new JSONObject(json_data);
            String url = "https://royalstag-server.herokuapp.com/api/scores/update?where=";
            String queryString = "{\"phone\":\""+jsonSync.getString(phoneKey)+"\"}";
            String search= URLEncoder.encode(queryString,"UTF-8");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url+search, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG,"Score Response"+response);
                    if(response==null){
                        Log.e(TAG,"Response Score is empty :"+response);
                    }else {
//                    JSONDATA
                        Log.e(TAG,"Response Score is not empty :"+response);
                        Toast.makeText(getApplicationContext(), "Data Synched", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"VolleyError",error);
                    NetworkResponse networkResponse=error.networkResponse;
                    if(networkResponse!=null && networkResponse.statusCode==422){

                        Log.e(TAG,"Sync Score error code: "+networkResponse.statusCode);
                    }
//                Toast.makeText(MainActivity.this, "Cannot post response", Toast.LENGTH_SHORT).show();

                }
            }){
                protected Map<String,String> getParams(){
                    Map<String, String> MyData=new HashMap<String, String>();
                    try {

//                        json_data=sharedpreferences.getString("JSON_DATA", "");
                        JSONObject jsonSync=new JSONObject(json_data);
                        int TotalScoreShared=Integer.parseInt(jsonSync.getString("level1Score"))+Integer.parseInt(jsonSync.getString( "level2Score"))+Integer.parseInt(jsonSync.getString( "level3Score"))
                                +Integer.parseInt(jsonSync.getString( "level4Score"))+Integer.parseInt(jsonSync.getString( "level5Score"));
                        Log.e(TAG,"TOTAL"+TotalScoreShared);

                        MyData.put("total_score", String.valueOf(TotalScoreShared));
                        MyData.put("level_1_score",jsonSync.getString( "level1Score"));
                        MyData.put("level_1_status",jsonSync.getString( "level1Cleared"));
                        MyData.put("level_2_score",jsonSync.getString( "level2Score"));
                        MyData.put("level_2_status",jsonSync.getString( "level2Cleared"));
                        MyData.put("level_3_score",jsonSync.getString( "level3Score"));
                        MyData.put("level_3_status",jsonSync.getString( "level3Cleared"));
                        MyData.put("level_4_score",jsonSync.getString( "level4Score"));
                        MyData.put("level_4_status",jsonSync.getString( "level4Cleared"));
                        MyData.put("level_5_score",jsonSync.getString( "level5Score"));
                        MyData.put("level_5_status",jsonSync.getString( "level5Cleared"));
                        MyData.put("level_1_time",jsonSync.getString(level1time));
                        MyData.put("level_2_time",jsonSync.getString(level2time));
                        MyData.put("level_3_time",jsonSync.getString(level3time));
                        MyData.put("level_4_time",jsonSync.getString(level4time));
                        MyData.put("level_5_time",jsonSync.getString(level5time));
                        MyData.put("level_completed",jsonSync.getString( "lastLevel"));
                        Log.e(TAG,"SCORE SCORE SYNCED");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    return MyData;
                }
            };
            queue.add(stringRequest);
            Log.e(TAG,"DataSync Score request");


    }


    @Override
    protected void onResume() {
        super.onResume();
        checkConnectivity();
    }

    @Override
    protected void onPause() {
        // if network is being moniterd then we will unregister the network callback
        if (monitoringConnectivity) {
            final ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(connectivityCallback);
            monitoringConnectivity = false;
        }
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkConnectivity();
    }

    private ConnectivityManager.NetworkCallback connectivityCallback
            = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            isConnected = true;
            Log.e(TAG, "INTERNET CONNECTED");
            Toast.makeText(getApplicationContext(), "INTERNET CONNECTED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLost(Network network) {
            isConnected = false;
            Log.e(TAG, "INTERNET LOST");
            Toast.makeText(getApplicationContext(), "INTERNET LOST", Toast.LENGTH_SHORT).show();
        }
    };


    private void checkConnectivity() {
        // here we are getting the connectivity service from connectivity manager
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);

        // Getting network Info
        // give Network Access Permission in Manifest
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        // isConnected is a boolean variable
        // here we check if network is connected or is getting connected
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (!isConnected) {
            // SHOW ANY ACTION YOU WANT TO SHOW
            // WHEN WE ARE NOT CONNECTED TO INTERNET/NETWORK
            Log.e(TAG, " NO NETWORK!");
//            Toast.makeText(this, " NO NETWORK!", Toast.LENGTH_SHORT).show();
// if Network is not connected we will register a network callback to  monitor network
            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback);
            monitoringConnectivity = true;
        }

    }
}
