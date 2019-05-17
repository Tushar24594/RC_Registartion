package com.example.rc_reg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class start extends AppCompatActivity {
TextView coupon;
Button starGameBtn;
    public static final String TAG = "-----Start---------";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String nameKey = "nameKey";
    public static final String phoneKey = "phoneKey";
    public static final String emailKey = "emailKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String language = sharedpreferences.getString(phoneKey, "");
        Log.e(TAG,"Shared Pref: "+language);
        coupon=(TextView)findViewById(R.id.coupon);
        starGameBtn=(Button)findViewById(R.id.startGame);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fon.ttf");

        coupon.setTypeface(custom_font);
        starGameBtn.setTypeface(custom_font);
        coupon.setText(language);

        starGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),score.class);
                startActivity(intent);
                Toast.makeText(start.this, "Score Screen", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
