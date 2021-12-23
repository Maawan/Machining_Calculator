package com.waqazystudios.machiningcalculator.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.waqazystudios.machiningcalculator.Models.DataBase_data;
import com.waqazystudios.machiningcalculator.R;
import com.waqazystudios.machiningcalculator.Utlis.Math;

import java.util.ArrayList;

import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;
import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;

public class spindleSpeedFragment extends Fragment {
    private EditText diameter,cuttingSpeed;
    private TextView finalValue;
    private CardView valueIndicator;
    private AdView adView;
    float diameterValue = 0,cuttingSpeedValue = 0;
    public spindleSpeedFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.spindle_speed_fragment,container,false);
        diameter = rootView.findViewById(R.id.diameter);
        adView = rootView.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        cuttingSpeed = rootView.findViewById(R.id.cuttingSpeed);
        finalValue = rootView.findViewById(R.id.finalValue);
        valueIndicator =rootView.findViewById(R.id.valueIndicator);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/adventprobold.ttf");
        finalValue.setTypeface(custom_font);
        diameter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    diameterValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplay(diameterValue, cuttingSpeedValue);
                }else {
                    diameterValue = 0;
                    calculateAndDisplay(diameterValue, cuttingSpeedValue);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cuttingSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    cuttingSpeedValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplay(diameterValue, cuttingSpeedValue);
                }else {
                    cuttingSpeedValue = 0;
                    calculateAndDisplay(diameterValue, cuttingSpeedValue);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkForDatabase();

        return rootView;
    }
    public void calculateAndDisplay(float Dm,float n){
        double result;
        String m = "";
        if(isMetericSelected) {
            m = "r/min";
            result = (n * 1000) / (3.14 * Dm);
        }else {
            m = "rpm";
            result = (n * 12)/(3.14 * Dm);
        }


        finalValue.setText(String.valueOf((int) result));
        variableValues.put(Math.machinedDiameter,Dm);
        variableValues.put(Math.cuttingSpeed,n);
        variableValues.put(Math.spindleSpeed,(float)result);
        if(result > 0){
            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData , DataBase_data.class);
            data.add(new DataBase_data("Spindle Speed", (float) result , m));
            Stash.put(Math.historyData , data);
        }

    }

    public void checkForDatabase(){
//        Toast.makeText(getContext(), "DM - > " + "VC ->"  + variableValues.get + "N ->" + variableValues.get(Math.spindleSpeed), Toast.LENGTH_SHORT).show();
        if(variableValues.get(Math.machinedDiameter) != null){
            diameter.setText(String.valueOf(variableValues.get(Math.machinedDiameter)));
            diameterValue = variableValues.get(Math.machinedDiameter);
        }
    }
}
