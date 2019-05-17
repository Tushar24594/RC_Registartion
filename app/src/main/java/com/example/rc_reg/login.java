package com.example.rc_reg;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class login extends AppCompatActivity {
AutoCompleteTextView phone;
String User_phone;
Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone=(AutoCompleteTextView)findViewById(R.id.Mobile);
        login=(Button)findViewById(R.id.next);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fon.ttf");

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
                    Intent intent=new Intent(getApplicationContext(),start.class);
                    startActivity(intent);
                }
            }
        });
    }
}
