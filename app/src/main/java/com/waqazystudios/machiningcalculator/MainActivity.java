package com.waqazystudios.machiningcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.waqazystudios.machiningcalculator.Activities.AboutTheApp;
import com.waqazystudios.machiningcalculator.Activities.HistoryActivity;
import com.waqazystudios.machiningcalculator.Activities.PrivacyPolicy;
import com.waqazystudios.machiningcalculator.Activities.Settings;
import com.waqazystudios.machiningcalculator.Activities.equationSelector;
import com.waqazystudios.machiningcalculator.Activities.millingCalculationActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private CardView turningCard,millingCard;
    private TextView turningTextView, maillingTextView, calculationHistoryTextView;
    public static HashMap<String , Float> variableValues;
    private CardView historyCard;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view,menu);


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(variableValues != null){
            variableValues.clear();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
               startActivity(new Intent(MainActivity.this, Settings.class));
               break;
            case R.id.aboutTheApp:
                startActivity(new Intent(MainActivity.this, AboutTheApp.class));
                break;
            case R.id.shareApp:
                Toast.makeText(this, "Sharing option will only work... When it will be uploaded to Store", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rateUs:
                Toast.makeText(this, "App is not live in PLay Store", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contactUs:
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@waqazystudios.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Enter your Subject");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(),"Gmail App is not installed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.privacyPolicy:
                startActivity(new Intent(this, PrivacyPolicy.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variableValues = new HashMap<>();
        variableValues.clear();
        Stash.init(this);
        historyCard = findViewById(R.id.turningCard3);





        millingCard = findViewById(R.id.turningCard);
        turningCard = findViewById(R.id.turningCard2);
        turningTextView = findViewById(R.id.turningTextView);
        maillingTextView = findViewById(R.id.millingTextView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/adventprobold.ttf");
        //turningTextView.setTypeface(custom_font);
        //maillingTextView.setTypeface(custom_font);

        calculationHistoryTextView = findViewById(R.id.calculationHistoryTextView);
        //calculationHistoryTextView.setTypeface(custom_font);
        turningCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, equationSelector.class).putExtra("Identifier","Turning"));
            }
        });

        millingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, millingCalculationActivity.class).putExtra("Identifier","Milling"));
            }
        });
        historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
    }
}