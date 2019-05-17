package com.example.rc_reg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.util.Config.LOGD;
import static com.google.android.gms.common.api.GoogleApiClient.*;
public class MainActivity extends AppCompatActivity implements
       ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    EditText Name;
    AutoCompleteTextView Phone;
    EditText state,city;
    AutoCompleteTextView Email;
    ImageButton getLocation;
    Button Submit,sendOtp,ResendOtp,VerifyOtp;
    String User_Name, User_Phone, User_Location,User_Email,PlayerScore;
    String voucher_coupon="123456";
    String ipServer="192.168.0.144";
    String time;
    TextView LogIn,Loc_text,useLoc,already;
    public static final String TAG = "----------------------";
    boolean connected = false;
    String lat,lng;
    Double la;
    Double ln;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    LocationManager locationManager;
    // to check if we are connected to Network
    boolean isConnected = true;
//    SharedPreferences
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String nameKey = "nameKey";
    public static final String phoneKey = "phoneKey";
    public static final String emailKey = "emailKey";
    // to check if we are monitoring Network
    private boolean monitoringConnectivity = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        final RequestQueue queue = Volley.newRequestQueue(this);
        LogIn=(TextView)findViewById(R.id.login);
        Name = (EditText) findViewById(R.id.Name);
        Phone = (AutoCompleteTextView) findViewById(R.id.Mobile);
        Email = (AutoCompleteTextView) findViewById(R.id.Email);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        Loc_text=(TextView)findViewById(R.id.Loc_text);
        useLoc=(TextView)findViewById(R.id.useLoc);
        already=(TextView)findViewById(R.id.useLoc);

        //shared preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation = (ImageButton) findViewById(R.id.getLocation);

        Submit = (Button) findViewById(R.id.Submit);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fon.ttf");

        LogIn.setTypeface(custom_font);
        Name.setTypeface(custom_font);
        Phone.setTypeface(custom_font);
        Email.setTypeface(custom_font);
        city.setTypeface(custom_font);
        state.setTypeface(custom_font);
        Loc_text.setTypeface(custom_font);
        useLoc.setTypeface(custom_font);
        already.setTypeface(custom_font);
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
            public void onClick(View view) {

                settingRequest();
            }
        });

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
        String user_state=state.getText().toString().trim();
        String user_city=city.getText().toString().trim();
        if(User_Name.isEmpty()){
            Name.setError("Please Enter Your Name");
            Name.requestFocus();
//            Toast.makeText(MainActivity.this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
            return;

        }if(User_Phone.isEmpty() || User_Phone.length()<10){
            Phone.setError("Please Enter your Number");
            Phone.requestFocus();
//            Toast.makeText(MainActivity.this, "Please Enter your Mobile Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(User_Email.isEmpty()){
            Email.setError("Please Enter Email");
            Email.requestFocus();
//            Toast.makeText(MainActivity.this, "Please select your address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(user_city.isEmpty()){
            city.setError("Please Enter city");
            city.requestFocus();
//            Toast.makeText(MainActivity.this, "Please select your address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(user_state.isEmpty()){
            state.setError("Please Enter State");
            state.requestFocus();
//            Toast.makeText(MainActivity.this, "Please select your address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(User_Name!=null && User_Phone!=null && User_Location!=null && !User_Name.isEmpty() && !User_Phone.isEmpty() && !User_Location.isEmpty() && User_Email!=null && !User_Email.isEmpty()){

//            sharedpreferences
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(nameKey, User_Name);
            editor.putString(phoneKey, User_Phone);
            editor.putString(emailKey, User_Email);
            editor.commit();

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
                        MyData.put("email",User_Email);
                        MyData.put("name",User_Name);
                        MyData.put("phone",User_Phone);
                        MyData.put("time",time);
                        MyData.put("location",User_Location);
                        MyData.put("voucher_code",voucher_coupon);
                        return MyData;
                    }
                };
                queue.add(stringRequest);
                Log.e(TAG,"In Try request");
                Intent intent=new Intent(getApplicationContext(),start.class);
                startActivity(intent);
            }
            catch (Exception e){
                Log.e(TAG,"Error in post request"+e);
                Toast.makeText(MainActivity.this, "Error in post request"+e, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "Name:"+User_Name+" Phone:"+User_Phone+" Email:"+User_Email+" Location:"+User_Location, Toast.LENGTH_SHORT).show();
        }


        //check internet connection
        }else {
            Toast.makeText(MainActivity.this, "Please connect internet first", Toast.LENGTH_SHORT).show();
        }
    }


    });

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
    public void onLocationChanged(android.location.Location location) {
        mLastLocation = location;
//        _progressBar.setVisibility(View.INVISIBLE);
       lat =String.valueOf(mLastLocation.getLatitude());
       lng= String.valueOf(mLastLocation.getLongitude());
       Log.e(TAG,":"+lat+" "+TAG+":"+lng);
       Toast.makeText(this, "Latitude: " + lat+"Longitude: " + lng, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    private void settingRequest() {
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
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
//                _progressBar.setVisibility(View.INVISIBLE);
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





