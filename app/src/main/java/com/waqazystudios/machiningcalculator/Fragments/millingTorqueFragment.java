package com.waqazystudios.machiningcalculator.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.waqazystudios.machiningcalculator.Models.DataBase_data;
import com.waqazystudios.machiningcalculator.R;
import com.waqazystudios.machiningcalculator.Utlis.Math;

import java.util.ArrayList;

import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;
import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;

public class millingTorqueFragment extends Fragment {
    private ImageView dropDown, dropDown1;
    private TextView finalValue;
    private AdView adView;
    private EditText powerRequirementEditText , metalRemovalRateEditText , specificCuttingForceEditText , spindleSpeedEditText , cutterDiameterEditText , cuttingSpeedEditText;
    private ExpandableRelativeLayout layout1, layout2;
    private float Mc = 0,Pc = 0 , metalRemovalRateValue = 0 , specificCuttingValue = 0, spindleSpeedValue = 0 , cutterDiameterValue = 0 , cuttingSpeedValue = 0 ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.milling_torque_fragment,container,false);
        adView = rootView.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        dropDown = rootView.findViewById(R.id.dropDownImage);
        dropDown1 = rootView.findViewById(R.id.dropDownImage1);
        layout1 = rootView.findViewById(R.id.expandableView);
        layout2 = rootView.findViewById(R.id.expandableView1);
        finalValue = rootView.findViewById(R.id.finalValue);
        if(!isMetericSelected){
            TextView unitsTextView = rootView.findViewById(R.id.unitsTextView);
            unitsTextView.setText("Ibf ft");
        }
        powerRequirementEditText = rootView.findViewById(R.id.powerRequirementEditText);
        metalRemovalRateEditText = rootView.findViewById(R.id.metalRemovalEditText);
        specificCuttingForceEditText = rootView.findViewById(R.id.specificCuttingForceEditText);
        spindleSpeedEditText = rootView.findViewById(R.id.spindleSpeedEditText);
        cutterDiameterEditText = rootView.findViewById(R.id.cutterDiameterEditText);
        cuttingSpeedEditText = rootView.findViewById(R.id.cuttingSpeedEditText);

        metalRemovalRateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    metalRemovalRateValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayPc();
                    calculateAndDisplayMc();
                }else {
                    metalRemovalRateValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        specificCuttingForceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    specificCuttingValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayPc();
                    calculateAndDisplayMc();
                }else {
                    specificCuttingValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cutterDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(!charSequence.toString().equals("")){
                   cutterDiameterValue = Float.parseFloat(charSequence.toString());
                   calculateAndDisplaySpindleSpeed();
                   calculateAndDisplayMc();
               }else {
                   cutterDiameterValue = 0;
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cuttingSpeedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(!charSequence.toString().equals("")){
                   cuttingSpeedValue = Float.parseFloat(charSequence.toString());
                   calculateAndDisplaySpindleSpeed();
                   calculateAndDisplayMc();
               }else {
                   cuttingSpeedValue = 0;
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.toggle();
            }
        });
        dropDown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout2.toggle();
            }
        });
        powerRequirementEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    Pc = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayMc();
                }else {
                    Pc = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spindleSpeedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 if(!charSequence.toString().equals("")){
                     spindleSpeedValue = Float.parseFloat(charSequence.toString());
                     calculateAndDisplayMc();
                 }else {
                     spindleSpeedValue = 0;
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();
        return rootView;
    }
    public void calculateAndDisplayPc(){
        if(isMetericSelected) {
            Pc = (metalRemovalRateValue * specificCuttingValue) / 60000;
        }else {
            Pc = (metalRemovalRateValue * specificCuttingValue) / 396000;
        }
        powerRequirementEditText.setText(String.valueOf(Pc));
    }
    public void calculateAndDisplaySpindleSpeed(){
        if(cutterDiameterValue != 0) {
            if(isMetericSelected) {
                spindleSpeedValue = (float) ((cuttingSpeedValue * 1000) / (3.14 * cutterDiameterValue));
            }else {
                spindleSpeedValue = (float) ((cuttingSpeedValue * 12) / (3.14 * cutterDiameterValue));
            }
            spindleSpeedValue = (float) Math.getRoundOff(spindleSpeedValue, 2);
            spindleSpeedEditText.setText(String.valueOf(spindleSpeedValue));
        }
    }
    public void calculateAndDisplayMc(){
        String m = "";
          if(Pc != 0 && spindleSpeedValue != 0) {
              if(isMetericSelected) {
                  Mc = (float) Math.getRoundOff((float) ((Pc * 30000) / (3.14 * spindleSpeedValue)), 2);
                  m = "Nm";
              }else {
                  m = "Ibf ft";
                  Mc = (float) Math.getRoundOff((float) ((Pc * 16501) / (3.14 * spindleSpeedValue)), 2);
              }
              finalValue.setText(String.valueOf(Mc));
              if(Mc > 0) {
                  ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData, DataBase_data.class);
                  data.add(new DataBase_data("Torque", (float) Mc, m));
                  Stash.put(Math.historyData, data);
              }
          }
    }
    public void loadData(){
        if(variableValues.get(Math.metalRemovalRate) != null){
            float value = variableValues.get(Math.metalRemovalRate);
            metalRemovalRateEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }
        if(variableValues.get(Math.specificCuttingForce) != null){
            float value = variableValues.get(Math.specificCuttingForce);
            metalRemovalRateEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }
        if(variableValues.get(Math.machinedDiameter) != null){
            float value = variableValues.get(Math.machinedDiameter);
            metalRemovalRateEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }
        if(variableValues.get(Math.cuttingSpeed) != null){
            float value = variableValues.get(Math.cuttingSpeed);
            metalRemovalRateEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }
        if(variableValues.get(Math.depthOfCut) != null){
            float value = variableValues.get(Math.depthOfCut);
            metalRemovalRateEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }

    }

}
