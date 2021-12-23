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
import android.widget.Toast;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;
import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;

public class cuttingSpeedFragment extends Fragment {
    private EditText diameter,spindleSpeed;
    private TextView finalValue;
    private CardView valueIndicator;
    private Boolean isMetric = true;
    float diameterValue = 0,spindleValue = 0;
    private AdView adView;
    public cuttingSpeedFragment(){}

    @Override
    public void onResume() {

        super.onResume();
        Stash.init(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cuttingspeed_fragment,container,false);
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
        if(isMetericSelected){

        }else {
            TextView units = rootView.findViewById(R.id.unitsTextView);
            units.setText("ft/min");
        }
        spindleSpeed = rootView.findViewById(R.id.spindleSpeed);
        finalValue = rootView.findViewById(R.id.finalValue);
        valueIndicator =rootView.findViewById(R.id.valueIndicator);
        HashMap<String,Integer> temp = new HashMap<>();
        temp.put("Sample Data",786);
        temp.put("Sample Data2",78692);
        Toast.makeText(getContext(), temp + " ", Toast.LENGTH_SHORT).show();
        temp.put("Sample Data",12345);
        Toast.makeText(getContext(), temp + " ", Toast.LENGTH_SHORT).show();
        if(temp.get("Ssample")!=null) {
            int temp2 = temp.get("Sample");
        }else {
            Toast.makeText(getContext(), "No Such value found", Toast.LENGTH_SHORT).show();
        }

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
                    calculateAndDisplay(diameterValue, spindleValue);
                }else {
                    diameterValue = 0;
                    calculateAndDisplay(diameterValue, spindleValue);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spindleSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    spindleValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplay(diameterValue, spindleValue);
                }else {
                    spindleValue = 0;
                    calculateAndDisplay(diameterValue, spindleValue);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return rootView;
    }
    public void calculateAndDisplay(float Dm,float n){
        double result;
        String m = " ";
        if(isMetericSelected) {
            result = (Dm * 3.14 * n) / 1000;
            m = "m/min";
            result = round(result, 2);
        }else {
            result = (Dm * 3.14 * n) / 12;
            m = "ft/inch";
            result = round(result, 2);
        }

        finalValue.setText(String.valueOf(result));
        variableValues.put(Math.machinedDiameter , diameterValue);
        variableValues.put(Math.spindleSpeed , spindleValue);
        variableValues.put(Math.cuttingSpeed , (float) result);

        if(result > 0){
            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData , DataBase_data.class);
            data.add(new DataBase_data("Cutting Speed", (float) result , m));
            Stash.put(Math.historyData , data);
        }


    }
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
