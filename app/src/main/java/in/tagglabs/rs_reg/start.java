package in.tagglabs.rs_reg;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.rs_reg.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class start extends AppCompatActivity {
TextView coupon,tap;
Button starGameBtn;
ImageButton imageButton;
LinearLayout first;
    private boolean monitoringConnectivity = false;
    boolean doubleBackToExitPressedOnce = false;
    boolean isConnected = true;
    String sharedData;
    ImageView rotate;
    public int counter;
    public static final String TAG = "-----Start---------";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String json_data = "JSON_DATA";
    public static final String phoneKey = "phoneKey";
    public static final String emailKey = "emailKey";
    public static final String voucherKey = "voucherKey";
    public static final String level1time = "level1time";
    public static final String level2time = "level2time";
    public static final String level3time = "level3time";
    public static final String level4time = "level4time";
    public static final String level5time = "level5time";
    public static final String TotalScore = "TotalScore";
    String phone,name,email,voucher,jsonData;
    TextView congrats,text2,text3,redeem;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        imageButton=(ImageButton)findViewById(R.id.profile);
        queue = Volley.newRequestQueue(this);
        coupon=(TextView)findViewById(R.id.coupon);
        congrats=(TextView)findViewById(R.id.congrats);
        text2=(TextView)findViewById(R.id.text2);
        tap=(TextView)findViewById(R.id.tap);
        text3=(TextView)findViewById(R.id.text3);
        first=(LinearLayout)findViewById(R.id.first);
        redeem=(TextView)findViewById(R.id.redeem);
        starGameBtn=(Button)findViewById(R.id.startGame);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        rotate=(ImageView)findViewById(R.id.rotate);
        coupon.setTypeface(custom_font);
        congrats.setTypeface(custom_font);
        text3.setTypeface(custom_font);
        text2.setTypeface(custom_font);
        tap.setTypeface(custom_font);
        redeem.setTypeface(custom_font);
        starGameBtn.setTypeface(custom_font);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rotate.animate().rotation(180).start();
                final ObjectAnimator oa1=ObjectAnimator.ofFloat(rotate,"scaleX",1f,0f);
                final ObjectAnimator oa2=ObjectAnimator.ofFloat(rotate,"scaleX",0f,1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rotate.setVisibility(INVISIBLE);
                        first.setVisibility(VISIBLE);
                        oa2.start();
                    }
                });oa1.start();
            }
        });
        if(isConnected){
            dataSync();
            Log.e(TAG,"START DATA SYNCED CALLING....");
        }else {
            Log.e(TAG," START Please connect internet first CALLING....");
        }
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        jsonData = sharedpreferences.getString(json_data, "");
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            voucher = jsonObject.getString( "voucherKey");
//            int TotalScoreShared=Integer.parseInt(jsonObject.getString("level1Score"))+Integer.parseInt(jsonObject.getString( "level2Score"))+Integer.parseInt(jsonObject.getString( "level3Score"))
//                    +Integer.parseInt(jsonObject.getString( "level4Score"))+Integer.parseInt(jsonObject.getString( "level5Score"));
//            jsonObject.put(TotalScore,TotalScoreShared);
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString("JSON_DATA", jsonObject.toString());
//            editor.commit();
//            Log.e(TAG,"Score Upated"+TotalScoreShared);
            if(voucher.equals("null") || voucher.isEmpty() || voucher==null || voucher.equals("")){
                coupon.setText("UNLUCKY");
            }else{
                coupon.setText(voucher);
            }
            Log.e(TAG,"Shared Pref: "+jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),score.class);
                startActivity(intent);
                finish();
            }
        });
        starGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),score.class);
                startActivity(intent);
                finish();
            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),redeem.class);
                startActivity(intent);
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ObjectAnimator oa1=ObjectAnimator.ofFloat(rotate,"scaleX",1f,0f);
                final ObjectAnimator oa2=ObjectAnimator.ofFloat(rotate,"scaleX",0f,1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rotate.setVisibility(VISIBLE);
                        first.setVisibility(INVISIBLE);
                        oa2.start();
                    }
                });oa1.start();
            }
        });
    }


    private ConnectivityManager.NetworkCallback connectivityCallback
            = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            isConnected = true;
            Log.e(TAG, "INTERNET CONNECTED");

        }

        @Override
        public void onLost(Network network) {
            isConnected = false;
            Log.e(TAG, "INTERNET LOST");

        }
    };
    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

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
                        jsonSync.put(TotalScore,TotalScoreShared);
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
                        MyData.put("level_completed",jsonSync.getString( "lastLevel"));
                        MyData.put("level_1_time",jsonSync.getString(level1time));
                        MyData.put("level_2_time",jsonSync.getString(level2time));
                        MyData.put("level_3_time",jsonSync.getString(level3time));
                        MyData.put("level_4_time",jsonSync.getString(level4time));
                        MyData.put("level_5_time",jsonSync.getString(level5time));
                        Log.e(TAG,"MAIN SCORE SYNCED");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("JSON_DATA", jsonSync.toString());
                        editor.commit();
                        Log.e(TAG,"Score is"+TotalScoreShared);
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

    @Override
    protected void onResume() {
        super.onResume();
        checkConnectivity();
        dataSync();
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

}
