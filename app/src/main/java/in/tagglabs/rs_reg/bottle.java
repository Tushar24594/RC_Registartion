package in.tagglabs.rs_reg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

//import com.example.rs_reg.R;

import org.json.JSONException;
import org.json.JSONObject;

public class bottle extends AppCompatActivity {
    ImageView back;
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String levelCompleteKey = "levelCompleteKey";
    String level,json_data;
    JSONObject jsonObject;
    int on=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottle);
        back=(ImageView)findViewById(R.id.back1);
        imageView=(ImageView)findViewById(R.id.b0);
        imageView1=(ImageView)findViewById(R.id.b1);
        imageView2=(ImageView)findViewById(R.id.b2);
        imageView3=(ImageView)findViewById(R.id.b3);
        imageView4=(ImageView)findViewById(R.id.b4);
        imageView5=(ImageView)findViewById(R.id.b5);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        json_data = sharedpreferences.getString("JSON_DATA", "");
        try {
           jsonObject =new JSONObject(json_data);
            level=jsonObject.getString("lastLevel");
            if(jsonObject.getString("level1Cleared").equals("complete")){
                imageView1.setVisibility(View.VISIBLE);
                Log.e("show---bottle","1");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete")){
                imageView2.setVisibility(View.VISIBLE);
                Log.e("show---bottles","2");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete") && jsonObject.get("level3Cleared").equals("complete")){
                imageView3.setVisibility(View.VISIBLE);
                Log.e("show---bottles","3");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete") && jsonObject.get("level3Cleared").equals("complete") && jsonObject.get("level4Cleared").equals("complete")){
                imageView4.setVisibility(View.VISIBLE);
                Log.e("show---bottles","4");
            }
            if(jsonObject.get("level1Cleared").equals("complete") &&jsonObject.get("level2Cleared").equals("complete") && jsonObject.get("level3Cleared").equals("complete") && jsonObject.get("level4Cleared").equals("complete") && jsonObject.get("level5Cleared").equals("complete")){
                imageView5.setVisibility(View.VISIBLE);
                Log.e("show---bottles","5");
            }
            {
                imageView.setVisibility(View.VISIBLE);
                Log.e("show---bottles","0");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),score.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
