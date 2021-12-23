package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.waqazystudios.machiningcalculator.Adapters.CustomAdapter;
import com.waqazystudios.machiningcalculator.InteristialClick;
import com.waqazystudios.machiningcalculator.Models.Data;
import com.waqazystudios.machiningcalculator.R;
import com.waqazystudios.machiningcalculator.Utlis.*;
import com.waqazystudios.machiningcalculator.Utlis.Math;

import java.util.ArrayList;

public class equationSelector extends AppCompatActivity implements InteristialClick {
    private ListView listView;
    private String title;
    private AdView adView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_selector);
        listView = findViewById(R.id.listView);
        adView = findViewById(R.id.adView);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        adView.loadAd(new AdRequest.Builder().build());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("Identifier");
        }
        setTitle(title);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        ArrayList<Data> list = new ArrayList<>();
        if(title.equals("Turning")) {
            list.add(new Data("Cutting Speed"));
            list.add(new Data("Spindle Speed"));
            list.add(new Data("Metal removal Rate"));
            list.add(new Data("Power Requirement"));
            list.add(new Data("Time in cut"));
        }
        else if(title.equals("Milling")){
            list.add(new Data("Cutting Speed"));
            list.add(new Data("Spindle Speed"));
            list.add(new Data("Table Feed (vf)"));
            list.add(new Data("Metal Removal Rate (Q)"));
            list.add(new Data("Time in Cut (Tc)"));
            list.add(new Data("Power Requirement (Pc)"));
            list.add(new Data("Torque (Mc)"));

        }

        CustomAdapter adapter = new CustomAdapter(this,R.layout.selector_layout,list , this);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void clicked() {
        if(interstitialAd.isLoaded() && Math.adCount % 2 == 0){
            interstitialAd.show();
            //Toast.makeText(equationSelector.this, "Clicked", Toast.LENGTH_SHORT).show();
        }else{
            Math.adCount++;
        }
    }
}