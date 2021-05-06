package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.waqazystudios.machiningcalculator.R;

public class AboutTheApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_app);
        setTitle("About The App");
        CardView shareApp , rateUs , mail;
        shareApp = findViewById(R.id.shareApp);
        rateUs = findViewById(R.id.shareApp);
        mail = findViewById(R.id.gmail);
        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutTheApp.this, "App is not Available on PLay Store", Toast.LENGTH_SHORT).show();
            }
        });
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutTheApp.this, "App is not Available on PLay Store", Toast.LENGTH_SHORT).show();
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@waqazystudios.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Enter your Subject");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(),"Gmail App is not installed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}