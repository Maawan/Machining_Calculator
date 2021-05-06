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
import com.waqazystudios.machiningcalculator.Models.DataBase_data;
import com.waqazystudios.machiningcalculator.R;
import com.waqazystudios.machiningcalculator.Utlis.Math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;
import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;

public class timeInCutFragment extends Fragment {
    private ExpandableRelativeLayout expandableRelativeLayout;
    private EditText startDiameterEditText, machinedDiameterEditText,cuttingSpeedEditText,spindleSpeedEditText,depthOfCutEditText,lengthOfCutEditText,feedPerRevolutionEditText;
    private ImageView imageView;
    private TextView finalValue;
    private float sec =0, startDiameterValue =0, machinedDiameterValue=0 , cuttingSpeedValue=0 , spindleSpeedValue =0, depthOfCutValue =0, lengthOfCutValue=0 , feedPerRevolutionValue=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_in_cut_fragment,container,false);
        expandableRelativeLayout = rootView.findViewById(R.id.expandableView);
        imageView = rootView.findViewById(R.id.dropDownImage);
        finalValue = rootView.findViewById(R.id.finalValue);

        startDiameterEditText = rootView.findViewById(R.id.startDiameterEditText);
        machinedDiameterEditText = rootView.findViewById(R.id.machinedDiameterEditText);
        cuttingSpeedEditText = rootView.findViewById(R.id.cuttingSpeedEditText);
        spindleSpeedEditText = rootView.findViewById(R.id.spindleSpeedEditText);
        depthOfCutEditText = rootView.findViewById(R.id.depthOfCutEditText);
        lengthOfCutEditText = rootView.findViewById(R.id.lengthOfCutEditText);
        feedPerRevolutionEditText = rootView.findViewById(R.id.feedPerRevolutionEditText);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableRelativeLayout.toggle();
            }
        });
        spindleSpeedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    spindleSpeedValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplaySeconds();
                     calculateCuttingSpeed();
                }else {
                    spindleSpeedValue = 0;
                    calculateCuttingSpeed();
                    calculateAndDisplaySeconds();
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        startDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    startDiameterValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplaySeconds();
                }else{
                    startDiameterValue = 0;
                    calculateAndDisplaySeconds();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        machinedDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    machinedDiameterValue = Float.parseFloat(charSequence.toString());
                    calculateCuttingSpeed();
                    calculateAndDisplaySeconds();
                }else{
                    machinedDiameterValue = 0 ;
                    calculateCuttingSpeed();
                    calculateAndDisplaySeconds();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        depthOfCutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 if(!charSequence.toString().equals("")){
                      depthOfCutValue = Float.parseFloat(charSequence.toString());
                     calculateAndDisplaySeconds();
                 }else {
                      depthOfCutValue = 0;
                     calculateAndDisplaySeconds();
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lengthOfCutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    lengthOfCutValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplaySeconds();
                }else {
                    lengthOfCutValue = 0;
                    calculateAndDisplaySeconds();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        feedPerRevolutionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    feedPerRevolutionValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplaySeconds();
                }else {
                    feedPerRevolutionValue = 0;
                    calculateAndDisplaySeconds();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();
        return rootView;
    }
    public void calculateCuttingSpeed(){
        if(isMetericSelected) {
            cuttingSpeedValue = (float) (machinedDiameterValue * (int) spindleSpeedValue * 3.14) / 1000;
        }else {
            cuttingSpeedValue = (float) (machinedDiameterValue * (int) spindleSpeedValue * 3.14) / 12;
        }
        cuttingSpeedEditText.setText(String.valueOf(cuttingSpeedValue));
    }
    public void calculateAndDisplaySeconds(){
        String m = "sec" ;
        if(isMetericSelected) {
            sec = lengthOfCutValue / (feedPerRevolutionValue * spindleSpeedValue);
        }else {
            sec = lengthOfCutValue / (feedPerRevolutionValue * spindleSpeedValue);
        }
        finalValue.setText(String.valueOf(sec));
        if(sec > 0) {
            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData, DataBase_data.class);
            data.add(new DataBase_data("Time in Cut ", (float) sec, m));
            Stash.put(Math.historyData, data);
        }

    }
    public void loadData(){
//        float tempDiameter = variableValues.get(Math.machinedDiameter) ;
//        float tempSpindle = variableValues.get(Math.spindleSpeed);
//        float tempFeedPerRevolution = variableValues.get(Math.feedPerRevolution);
//        float tempLengthOfCut = variableValues.get(Math.lengthOfCut);
//        float tempDepthOfCut = variableValues.get(Math.depthOfCut);
//        if(variableValues.get(Math.machinedDiameter) != null && variableValues.get(Math.spindleSpeed) != null){
//            machinedDiameterEditText.setText(String.valueOf(Math.getRoundOff(tempDiameter , 2)));
//            spindleSpeedEditText.setText(String.valueOf(Math.getRoundOff(tempSpindle , 2)));
//        }
//        if(variableValues.get(Math.feedPerRevolution) != null && variableValues.get(Math.lengthOfCut)!= null && variableValues.get(Math.depthOfCut) != null){
//            feedPerRevolutionEditText.setText(String.valueOf(Math.getRoundOff(tempFeedPerRevolution , 2)));
//            lengthOfCutEditText.setText(String.valueOf(Math.getRoundOff(tempLengthOfCut , 2)));
//            depthOfCutEditText.setText(String.valueOf(Math.getRoundOff(tempDepthOfCut , 2)));
//        }
        if(variableValues.get(Math.machinedDiameter) != null){
            float temp = variableValues.get(Math.machinedDiameter);
            machinedDiameterEditText.setText(String.valueOf(Math.getRoundOff(temp ,2)));
        }
        if(variableValues.get(Math.spindleSpeed) != null){
            float temp = variableValues.get(Math.spindleSpeed);
            spindleSpeedEditText.setText(String.valueOf(Math.getRoundOff(temp , 2)));
            spindleSpeedValue = temp;
            calculateCuttingSpeed();
        }
        if(variableValues.get(Math.depthOfCut) != null){
            float temp = variableValues.get(Math.depthOfCut);
            depthOfCutEditText.setText(String.valueOf(Math.getRoundOff(temp ,2)));
        }
        if(variableValues.get(Math.feedPerRevolution) != null){
            float temp = variableValues.get(Math.feedPerRevolution);
            feedPerRevolutionEditText.setText(String.valueOf(Math.getRoundOff(temp ,2)));
        }


    }
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
