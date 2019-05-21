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
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Url;

public class login extends AppCompatActivity {
AutoCompleteTextView phone;
String User_phone;
TextView play,england;
Button login;
ProgressBar progressBar;
    JSONObject jsonObj;
    RequestQueue queue;
    Boolean isConnected=true;
    private boolean monitoringConnectivity = false;
    public int counter;
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
    public static final String level1Cleared = "level1Cleared";
    public static final String level2Cleared = "level2Cleared";
    public static final String level3Cleared = "level3Cleared";
    public static final String level4Cleared = "level4Cleared";
    public static final String level5Cleared = "level5Cleared";
    public static final String level1Score = "level1Score";
    public static final String level2Score = "level2Score";
    public static final String level3Score = "level3Score";
    public static final String level4Score = "level4Score";
    public static final String level5Score = "level5Score";
    public static final String level1time = "level1time";
    public static final String level2time = "level2time";
    public static final String level3time = "level3time";
    public static final String level4time = "level4time";
    public static final String level5time = "level5time";
    public static final String TotalScore = "TotalScore";
    public static final String dataSync = "dataSync";
    public static final String lastLevel = "lastLevel";
    public static final String TAG = "----Login---------";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        phone=(AutoCompleteTextView)findViewById(R.id.Mobile);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        login=(Button)findViewById(R.id.next);
        play=(TextView)findViewById(R.id.play);
        england=(TextView)findViewById(R.id.england);
        play.setText("PLAY & WIN CRICKET WORLD CUP 2019");
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fon.otf");
        queue = Volley.newRequestQueue(this);
        phone.setTypeface(custom_font);
        login.setTypeface(custom_font);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User_phone=phone.getText().toString().trim();
                if(User_phone.isEmpty() || User_phone.length()<10){
                    phone.setError("Please Enter your Number");
                    phone.requestFocus();
                    return;
                }
                if(User_phone!=null && !User_phone.isEmpty()){
                    checkConnectivity();
                    if(isConnected){
                        getUserData();
                        progressBar.setVisibility(View.VISIBLE);
                    }else{
                        Log.e(TAG,"Please connect internet first CALLING....");
                        Toast.makeText(getApplicationContext(), "Please connect internet first", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    public void getUserData()  {
        try{
            String url = "https://royalstag-server.herokuapp.com/api/registers?filter=";
            String queryString = "{\"phone\":\""+User_phone+"\"}";
            String search= URLEncoder.encode(queryString,"UTF-8");

            Log.e(TAG,"url encoder: "+url+search);

            StringRequest stringRequest =new StringRequest(Request.Method.GET, url+search, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG,"GET RESPONSE: "+response);
                    if(response==null || response.equals("{}") || response.trim().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please Registered First", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject data=jsonObject.getJSONObject("data");
                            JSONObject score=jsonObject.getJSONObject("score");
//                            Log.e(TAG,"email"+jsonObject.getString("email"));
//                            Log.e(TAG,"name"+jsonObject.getString("name"));
//                            Log.e(TAG,"phone"+jsonObject.getString("phone"));

                            jsonObj = new JSONObject();
                            jsonObj.put(nameKey, data.getString("name")); // Set the first name/pair
                            jsonObj.put(emailKey,data.getString("email"));
                            jsonObj.put(phoneKey,data.getString("phone"));
                            jsonObj.put(voucherKey,data.getString("voucher_code"));
                            jsonObj.put(cityKey, data.getString("city"));
                            jsonObj.put(stateKey,data.getString("state"));
                            jsonObj.put(level1Cleared,score.getString("level_1_status"));
                            jsonObj.put(level2Cleared,score.getString("level_2_status"));
                            jsonObj.put(level3Cleared,score.getString("level_3_status"));
                            jsonObj.put(level4Cleared,score.getString("level_4_status"));
                            jsonObj.put(level5Cleared,score.getString("level_5_status"));
                            jsonObj.put(level1Score,score.getString("level_1_score"));
                            jsonObj.put(level2Score,score.getString("level_2_score"));
                            jsonObj.put(level3Score,score.getString("level_3_score"));
                            jsonObj.put(level4Score,score.getString("level_4_score"));
                            jsonObj.put(level5Score,score.getString("level_5_score"));
                            jsonObj.put(level1time,score.getString("level_1_time"));
                            jsonObj.put(level2time,score.getString("level_2_time"));
                            jsonObj.put(level3time,score.getString("level_3_time"));
                            jsonObj.put(level4time,score.getString("level_4_time"));
                            jsonObj.put(level5time,score.getString("level_5_time"));
                            jsonObj.put(TotalScore,score.getString("total_score"));
                            jsonObj.put(dataSync,"false");
                            jsonObj.put(lastLevel,score.getString("level_completed"));
                            Log.e(TAG,"JSON OBJECT login:"+jsonObj.toString());

                            new CountDownTimer(300, 1000){
                                public void onTick(long millisUntilFinished){
                                    counter++;
                                }
                                public  void onFinish(){
                                    progressBar.setVisibility(View.GONE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("JSON_DATA", jsonObj.toString());
                                    editor.apply();
                                    Log.e(TAG,"Shared Data login"+jsonObj.toString());
                                    Intent intent=new Intent(getApplicationContext(),start.class);
                                    startActivity(intent);
//                                    finish();
                                }
                            }.start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"GET ERROR RESPONSE: "+error);
                }
                //             @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> param = new HashMap<String, String>();

                    return param;
                }
                //             @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            });
            queue.add(stringRequest);
        }
        catch (Exception ex){
            Log.e(TAG,"GET REQUEST ERROR"+ex);
        }
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
//            Toast.makeText(MainActivity.this, "INTERNET CONNECTED", Toast.LENGTH_SHORT).show();
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
