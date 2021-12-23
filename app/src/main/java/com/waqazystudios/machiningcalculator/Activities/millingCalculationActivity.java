package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.waqazystudios.machiningcalculator.Adapters.CustomAdapter2;
import com.waqazystudios.machiningcalculator.InteristialClick;
import com.waqazystudios.machiningcalculator.Models.Data;
import com.waqazystudios.machiningcalculator.R;
import com.waqazystudios.machiningcalculator.Utlis.*;
import com.waqazystudios.machiningcalculator.Utlis.Math;


import java.util.ArrayList;

public class millingCalculationActivity extends AppCompatActivity implements InteristialClick {
    private ListView listView;
    private AdView adView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milling_calculation);
        listView = findViewById(R.id.listView);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        listView.setDivider(null);
        adView = findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        setTitle("Milling");
        ArrayList<Data> list = new ArrayList<>();
        list.add(new Data("Cutting Speed"));
        list.add(new Data("Spindle Speed"));
        list.add(new Data("Table Feed (vf)"));
        list.add(new Data("Metal Removal Rate (Q)"));
        list.add(new Data("Time in Cut (Tc)"));
        list.add(new Data("Power Requirement (Pc)"));
        list.add(new Data("Torque (Mc)"));
        CustomAdapter2 adapter = new CustomAdapter2(this,R.layout.selector_layout,list , this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void clicked() {
        if(interstitialAd.isLoaded() && Math.adCount %2 == 0){
            interstitialAd.show();
        }else{
            Math.adCount++;
        }
    }
}