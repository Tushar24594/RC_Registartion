package in.tagglabs.rs_reg;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.rs_reg.R;

public class terms extends AppCompatActivity {
TextView one,two,three,four,five,six,seven,eight,nine,ten,eleven,tweleve,thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,ninteen,twenty,twentyone,twentytwo,twentythree,twentyfour,
    twentyfive,twentysix,twentyseven,twentyeight,twentynine,thirty,thirtyone,thirtytwo,thirtythree,thirtyfour,thirtyfive,thirtysix,thirtyseven,thirtyeight,thirtynine,fourty,fourtyone,fourtytwo,fourtythree,
    fourtyfour,fourtyfive,fourtysix,fourtyseven,fourtyeight,fourtynine,fifty,fiftyone,fiftytwo,fiftythree;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        one=(TextView)findViewById(R.id.one);
        two=(TextView)findViewById(R.id.two);
        three=(TextView)findViewById(R.id.three);
        four=(TextView)findViewById(R.id.four);
        five=(TextView)findViewById(R.id.five);
        six=(TextView)findViewById(R.id.six);
        seven=(TextView)findViewById(R.id.seven);
        eight=(TextView)findViewById(R.id.eight);
        nine=(TextView)findViewById(R.id.nine);
        ten=(TextView)findViewById(R.id.ten);
        eleven=(TextView)findViewById(R.id.eleven);
        tweleve=(TextView)findViewById(R.id.tweleve);
        thirteen=(TextView)findViewById(R.id.thirteen);
        fourteen=(TextView)findViewById(R.id.fourteen);
        fifteen=(TextView)findViewById(R.id.fifteen);
        sixteen=(TextView)findViewById(R.id.sixteen);
        seventeen=(TextView)findViewById(R.id.seventeen);
        eighteen=(TextView)findViewById(R.id.eighteen);
        ninteen=(TextView)findViewById(R.id.nineteen);
        twenty=(TextView)findViewById(R.id.twenty);
        twentyone=(TextView)findViewById(R.id.twentyone);
        twentytwo=(TextView)findViewById(R.id.twentytwo);
        twentythree=(TextView)findViewById(R.id.twentythree);
        twentyfour=(TextView)findViewById(R.id.twentyfour);
        twentyfive=(TextView)findViewById(R.id.twentyfive);
        twentysix=(TextView)findViewById(R.id.twentysix);
        twentyseven=(TextView)findViewById(R.id.twentyseven);
        twentyeight=(TextView)findViewById(R.id.twentyeight);
        twentynine=(TextView)findViewById(R.id.twentynine);
        thirty=(TextView)findViewById(R.id.thirty);
        thirtyone=(TextView)findViewById(R.id.thirtyone);
        thirtytwo=(TextView)findViewById(R.id.thirtytwo);
        thirtythree=(TextView)findViewById(R.id.thirtythree);
        thirtyfour=(TextView)findViewById(R.id.thirtyfour);
        thirtyfive=(TextView)findViewById(R.id.thirtyfive);
        thirtysix=(TextView)findViewById(R.id.thirtysix);
        thirtyseven=(TextView)findViewById(R.id.thirtyseven);
        thirtyeight=(TextView)findViewById(R.id.thirtyeight);
        thirtynine=(TextView)findViewById(R.id.thirtynine);
        fourty=(TextView)findViewById(R.id.fourty);
        fourtyone=(TextView)findViewById(R.id.fourtyone);
        fourtytwo=(TextView)findViewById(R.id.fourtytwo);
        fourtythree=(TextView)findViewById(R.id.fourtythree);
        fourtyfour=(TextView)findViewById(R.id.fourtyfour);
        fourtyfive=(TextView)findViewById(R.id.fourtyfive);
        fourtysix=(TextView)findViewById(R.id.fourtysix);
        fourtyseven=(TextView)findViewById(R.id.fourtyseven);
        fourtyeight=(TextView)findViewById(R.id.fourtyeight);
        fourtynine=(TextView)findViewById(R.id.fourtynine);
        fifty=(TextView)findViewById(R.id.fifty);
        fiftyone=(TextView)findViewById(R.id.fiftyone);
        fiftytwo=(TextView)findViewById(R.id.fiftytwo);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        one.setTypeface(custom_font);
        two.setTypeface(custom_font);
        three.setTypeface(custom_font);
        four.setTypeface(custom_font);
        five.setTypeface(custom_font);
        six.setTypeface(custom_font);
        seven.setTypeface(custom_font);
        eight.setTypeface(custom_font);
        nine.setTypeface(custom_font);
        ten.setTypeface(custom_font);
        eleven.setTypeface(custom_font);
        tweleve.setTypeface(custom_font);
        thirteen.setTypeface(custom_font);
        fourteen.setTypeface(custom_font);
        fifteen.setTypeface(custom_font);
        sixteen.setTypeface(custom_font);
        seventeen.setTypeface(custom_font);
        eighteen.setTypeface(custom_font);
        ninteen.setTypeface(custom_font);
        twenty.setTypeface(custom_font);
        twentyone.setTypeface(custom_font);
        twentytwo.setTypeface(custom_font);
        twentythree.setTypeface(custom_font);
        twentyfour.setTypeface(custom_font);
        twentyfive.setTypeface(custom_font);
        twentysix.setTypeface(custom_font);
        twentyseven.setTypeface(custom_font);
        twentyeight.setTypeface(custom_font);
        twentynine.setTypeface(custom_font);
        thirty.setTypeface(custom_font);
        thirtyone.setTypeface(custom_font);
        thirtytwo.setTypeface(custom_font);
        thirtythree.setTypeface(custom_font);
        thirtyfour.setTypeface(custom_font);
        thirtyfive.setTypeface(custom_font);
        thirtysix.setTypeface(custom_font);
        thirtyseven.setTypeface(custom_font);
        thirtyeight.setTypeface(custom_font);
        thirtynine.setTypeface(custom_font);
        fourty.setTypeface(custom_font);
        fourtyone.setTypeface(custom_font);
        fourtytwo.setTypeface(custom_font);
        fourtythree.setTypeface(custom_font);
        fourtyfour.setTypeface(custom_font);
        fourtyfive.setTypeface(custom_font);
        fourtysix.setTypeface(custom_font);
        fourtyseven.setTypeface(custom_font);
        fourtyeight.setTypeface(custom_font);
        fourtynine.setTypeface(custom_font);
        fifty.setTypeface(custom_font);
        fiftyone.setTypeface(custom_font);
        fiftytwo.setTypeface(custom_font);
        imageView=(ImageView)findViewById(R.id.goback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
