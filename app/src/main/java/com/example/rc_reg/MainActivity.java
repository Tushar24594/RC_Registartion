package com.example.rc_reg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
//import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.util.Config.LOGD;
import static com.google.android.gms.common.api.GoogleApiClient.*;
public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    EditText Name;
    private View view;
    public int counter;
    AutoCompleteTextView Phone;
    EditText state,city;
    AutoCompleteTextView Email;
    ProgressBar spinner;
    FileWriter fileWriter=null;
    BufferedWriter bufferedWriter;
    ImageButton getLocation;
    Button Submit,sendOtp,ResendOtp,VerifyOtp;
    String User_Name, User_Phone, User_Location,User_Email,PlayerScore,user_state,user_city,response_phone;
    String voucher_coupon="123456";
    String playerScore="50";
    String levelComplete="2";
    String sharedData;
    String time;
    TextView LogIn,Loc_text,useLoc,already,gift,card;
    public static final String TAG = "----------------------";
    boolean connected = false;
    File filename;
    String lat,lng;
    Double la;
    Double ln;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    LocationManager locationManager;
    private  RequestQueue queue;
    String packageName="com.example.rc_reg";
    String UserData;
    // to check if we are connected to Network
    boolean isConnected = true;
//    SharedPreferences
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
    JSONObject jsonObj;
    // to check if we are monitoring Network
    private boolean monitoringConnectivity = false;
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (!(permissionCheck == PackageManager.PERMISSION_GRANTED)) {
//            Toast.makeText(this, "Nahi hai", Toast.LENGTH_SHORT).show();
            // User may have declined earlier, ask Android if we should show him a reason
            ActivityCompat.requestPermissions(MainActivity.this,  new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
        } else {

//            Toast.makeText(this, " hai", Toast.LENGTH_SHORT).show();
        }

       queue = Volley.newRequestQueue(this);
        LogIn=(TextView)findViewById(R.id.login);
        Name = (EditText) findViewById(R.id.Name);
        Phone = (AutoCompleteTextView) findViewById(R.id.Mobile);
        Email = (AutoCompleteTextView) findViewById(R.id.Email);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        Loc_text=(TextView)findViewById(R.id.Loc_text);
        useLoc=(TextView)findViewById(R.id.useLoc);
        already=(TextView)findViewById(R.id.already);
        gift=(TextView)findViewById(R.id.gift);
        card=(TextView)findViewById(R.id.card);
        spinner=(ProgressBar)findViewById(R.id.spin);
        //shared preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedData=sharedpreferences.getString("JSON_DATA", "");
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation = (ImageButton) findViewById(R.id.getLocation);

        Submit = (Button) findViewById(R.id.Submit);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fon.otf");

        LogIn.setTypeface(custom_font);
        Name.setTypeface(custom_font);
        Phone.setTypeface(custom_font);
        Email.setTypeface(custom_font);
        city.setTypeface(custom_font);
        state.setTypeface(custom_font);
        Loc_text.setTypeface(custom_font);
        useLoc.setTypeface(custom_font);
        already.setTypeface(custom_font);
        gift.setTypeface(custom_font);
        card.setTypeface(custom_font);
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                settingRequest();

            }
        });
            if(isConnected){
                dataSync();
                Log.e(TAG,"DATA SYNCED CALLING....");
            }else {
                Log.e(TAG,"Please connect internet first CALLING....");
            }
    LogIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(MainActivity.this,login.class);
            startActivity(intent);
        }
    });


Submit.setOnClickListener(new View.OnClickListener() {


    @Override
    public void onClick(View view) {
        checkConnectivity();
        if(isConnected){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        time =  mdformat.format(calendar.getTime());
        User_Name = Name.getText().toString().trim();
        User_Phone = Phone.getText().toString().trim();
        User_Email = Email.getText().toString().trim();
        User_Location = city.getText().toString().trim()+", "+state.getText().toString().trim();
        user_state=state.getText().toString().trim();
        user_city=city.getText().toString().trim();
        if(User_Name.isEmpty()){
            Name.setError("Please Enter Your Name");
            Name.requestFocus();
            return;

        }if(User_Phone.isEmpty() && User_Phone.length()<10){
            Phone.setError("Please Enter your Number");
            Phone.requestFocus();
            return;
        }
        if(User_Email.isEmpty()){
            Email.setError("Please Enter Email");
            Email.requestFocus();
            return;
        }
        if(user_city.isEmpty()){
            city.setError("Please Enter city");
            city.requestFocus();
            return;
        }
        if(user_state.isEmpty()){
            state.setError("Please Enter State");
            state.requestFocus();
            return;
        }
        if(User_Name!=null && User_Phone!=null && User_Location!=null && !User_Name.isEmpty() && !User_Phone.isEmpty() && !User_Location.isEmpty() && User_Email!=null && !User_Email.isEmpty()){
            registerUser();
            spinner.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity.this, "Name:"+User_Name+" Phone:"+User_Phone+" Email:"+User_Email+" Location:"+User_Location, Toast.LENGTH_SHORT).show();
        }

        }else {
            Toast.makeText(MainActivity.this, "Please connect internet first", Toast.LENGTH_SHORT).show();
        }
    }


    });

}
public void registerUser(){

    try{
        String url = "https://royalstag-server.herokuapp.com/api/registers";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"Response"+response);
                if(response==null){
//                    Toast.makeText(MainActivity.this, "Please try again later...", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(0000, 1000){
                        public void onTick(long millisUntilFinished){
//                            textView.setText(String.valueOf(counter));
                            counter++;
                        }
                        public  void onFinish(){
//                            textView.setText("FINISH!!");
//                            Toast.makeText(MainActivity.this, "Please Wait...", Toast.LENGTH_SHORT).show();

                        }
                    }.start();
                }else {
//                    JSONDATA
                    try{
//                        spinner.setVisibility(View.GONE);
                        JSONObject jsonObject=new JSONObject(response);
                        jsonObj = new JSONObject();
                        jsonObj.put(nameKey, User_Name); // Set the first name/pair
                        jsonObj.put(emailKey, User_Email);
                        jsonObj.put(phoneKey,User_Phone);
                        jsonObj.put(voucherKey,jsonObject.getString("voucher_code"));
                        jsonObj.put(cityKey, user_city);
                        jsonObj.put(stateKey,user_state);
                        jsonObj.put(level1Cleared,"Incomplete");
                        jsonObj.put(level2Cleared,"Incomplete");
                        jsonObj.put(level3Cleared,"Incomplete");
                        jsonObj.put(level4Cleared,"Incomplete");
                        jsonObj.put(level5Cleared,"Incomplete");
                        jsonObj.put(level1Score,"0");
                        jsonObj.put(level2Score,"0");
                        jsonObj.put(level3Score,"0");
                        jsonObj.put(level4Score,"0");
                        jsonObj.put(level5Score,"0");
                        jsonObj.put(level1time,"0");
                        jsonObj.put(level2time,"0");
                        jsonObj.put(level3time,"0");
                        jsonObj.put(level4time,"0");
                        jsonObj.put(level5time,"0");
                        jsonObj.put(TotalScore,"0");
                        jsonObj.put(dataSync,"false");
                        jsonObj.put(lastLevel,"0");
                        Log.e(TAG,"JSON OBJECT :"+jsonObj.toString());
                        new CountDownTimer(1500, 1000){
                            public void onTick(long millisUntilFinished){
                                counter++;
                            }
                            public  void onFinish(){
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("JSON_DATA", jsonObj.toString());
                                editor.commit();
                                Log.e(TAG,"Shared Data"+jsonObj.toString());
                                Intent intent=new Intent(getApplicationContext(),start.class);
                                startActivity(intent);
                                spinner.setVisibility(View.GONE);
                                finish();
                            }
                        }.start();

                        // Here we convert Java Object to JSON

                    }catch (Exception e){
                        Log.e(TAG,"Error in json object"+e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"VolleyError",error);
                NetworkResponse networkResponse=error.networkResponse;
                if(networkResponse!=null && networkResponse.statusCode==422){
                    getUserData();
                    Toast.makeText(MainActivity.this, "You Are Already registered", Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"error code: "+networkResponse.statusCode);
                }
//                Toast.makeText(MainActivity.this, "Cannot post response", Toast.LENGTH_SHORT).show();
                new CountDownTimer(2000, 1000){
                    public void onTick(long millisUntilFinished){
//                            textView.setText(String.valueOf(counter));
                        counter++;
                    }
                    public  void onFinish(){
//                            textView.setText("FINISH!!");
//                        Toast.makeText(MainActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
//                        spinner.setVisibility(View.GONE);
                    }
                }.start();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> MyData=new HashMap<String, String>();
                MyData.put("email",User_Email);
                MyData.put("name",User_Name);
                MyData.put("phone",User_Phone);
                MyData.put("city",user_city);
                MyData.put("state",user_state);
                return MyData;
            }
        };
        queue.add(stringRequest);
        Log.e(TAG,"In Try request");

    }
    catch (Exception e){
        Log.e(TAG,"Error in post request"+e);
//        Toast.makeText(MainActivity.this, "Error in post request"+e, Toast.LENGTH_SHORT).show();
    }
}
public void getUserData()  {
     try{
         String url = "https://royalstag-server.herokuapp.com/api/registers?filter=";
         String queryString = "{\"phone\":\""+User_Phone+"\"}";
         String search= URLEncoder.encode(queryString,"UTF-8");
         Log.e(TAG,"url encoder: "+url+search);

         StringRequest stringRequest =new StringRequest(Request.Method.GET, url+search, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                Log.e(TAG,"GET RESPONSE: "+response);

                 try {
                     JSONObject jsonObject=new JSONObject(response);
                     JSONObject data=jsonObject.getJSONObject("data");
                     JSONObject score=jsonObject.getJSONObject("score");


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
                     Log.e(TAG,"JSON OBJECT :"+jsonObj.toString());
                     new CountDownTimer(3000, 1000){
                         public void onTick(long millisUntilFinished){
                             counter++;
                         }
                         public  void onFinish(){
                             spinner.setVisibility(View.GONE);
                             SharedPreferences.Editor editor = sharedpreferences.edit();
                             editor.putString("JSON_DATA", jsonObj.toString());
                             editor.apply();
                             Log.e(TAG,"Shared Data login"+jsonObj.toString());
                             Intent intent=new Intent(getApplicationContext(),start.class);
                             startActivity(intent);
                                    finish();
                         }
                     }.start();
                 } catch (JSONException e) {
                     e.printStackTrace();
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

public void dataSync(){
    try{
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedData=sharedpreferences.getString("JSON_DATA", "");
        JSONObject jsonSync=new JSONObject(sharedData);
        String url = "https://royalstag-server.herokuapp.com/api/scores/update?where=";
        String queryString = "{\"phone\":\""+jsonSync.getString(phoneKey)+"\"}";
        String search= URLEncoder.encode(queryString,"UTF-8");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url+search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"Response"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"VolleyError",error);
                NetworkResponse networkResponse=error.networkResponse;
                if(networkResponse!=null && networkResponse.statusCode==422){
                    Log.e(TAG,"error code: "+networkResponse.statusCode);
                }
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> MyData=new HashMap<String, String>();
                try {
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    sharedData=sharedpreferences.getString("JSON_DATA", "");
                    JSONObject jsonSync=new JSONObject(sharedData);
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
                    Log.e(TAG,"MAIN SCORE SYNCED");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return MyData;
            }
        };
        queue.add(stringRequest);
        Log.e(TAG,"DataSync request");
    }
    catch (Exception e){
        Log.e(TAG,"Error in post sync request"+e);
    }
}


    private ConnectivityManager.NetworkCallback connectivityCallback
            = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            isConnected = true;
            Log.e(TAG, "INTERNET CONNECTED");
            Toast.makeText(MainActivity.this, "INTERNET CONNECTED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLost(Network network) {
            isConnected = false;
            Log.e(TAG, "INTERNET LOST");
            Toast.makeText(MainActivity.this, "INTERNET LOST", Toast.LENGTH_SHORT).show();
        }
    };


    // Method to check network connectivity in Main Activity
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

    @Override
    protected void onResume() {
        super.onResume();
        checkConnectivity();
        int permissionLocation = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

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
//        dataSync();
        super.onPause();

    }


    @Override
    public void onLocationChanged(android.location.Location location) {
        mLastLocation = location;
//        spinner.setVisibility(View.INVISIBLE);
       lat =String.valueOf(mLastLocation.getLatitude());
       lng= String.valueOf(mLastLocation.getLongitude());
       Log.e(TAG,":"+lat+" "+TAG+":"+lng);
       settingRequest();
//       Toast.makeText(this, "Latitude: " + lat+"Longitude: " + lng, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    private void settingRequest() {
//        spinner.setVisibility(View.VISIBLE);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    @Override
    public void onConnectionSuspended(int i) {
//        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Connection Suspended!");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
//                        Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            /*Getting the location after aquiring location service*/
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
//                spinner.setVisibility(View.INVISIBLE);
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                la= Double.valueOf(String.valueOf(mLastLocation.getLatitude()));
                ln= Double.valueOf(String.valueOf(mLastLocation.getLongitude()));
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(la, ln, 1);
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String Geo_city = addresses.get(0).getLocality();
                    String Geo_state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    city.setText( Geo_city);
                    state.setText( Geo_state);
                    spinner.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
            }
        }
    }


}





