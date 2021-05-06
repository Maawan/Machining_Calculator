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

import java.util.ArrayList;

import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;
import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;

public class millingMetalRemovalRate extends Fragment {
    private ImageView dropDownImage;
    private TextView finalValue;
    private ExpandableRelativeLayout expandableRelativeLayout;
    private EditText feedSpeedEditText,spindleSpeedEditText,radialWidthOfCutEditText,feedperToothEditText,numberOfInsertsEditText,axialDepthOfCutEditText;
    private float feedSpeedValue = 0,spindleSpeedValue = 0,radialWithOfCutValue = 0,feedPerToothValue = 0,numberOfInsertsValue = 0,Q = 0,axialDepthOfCutValue = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.milling_metal_removal_rate,container,false);
        dropDownImage = rootView.findViewById(R.id.dropDownImage);
        feedSpeedEditText = rootView.findViewById(R.id.feedSpeedEditText);
        finalValue = rootView.findViewById(R.id.finalValue);
        if(!isMetericSelected){
            TextView unitsTextView = rootView.findViewById(R.id.unitsTextView);
            unitsTextView.setText("inch3 / min");
        }
        spindleSpeedEditText = rootView.findViewById(R.id.spindleSpeedEditText);
        axialDepthOfCutEditText = rootView.findViewById(R.id.axialDepthOfCutEditText);
        radialWidthOfCutEditText = rootView.findViewById(R.id.radialWidthOfCutEditText);
        feedperToothEditText = rootView.findViewById(R.id.feedPerToothEditText);
        numberOfInsertsEditText = rootView.findViewById(R.id.numberOfInsertsEditText);
        expandableRelativeLayout = rootView.findViewById(R.id.expandableView);
        dropDownImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableRelativeLayout.toggle();
            }
        });
        radialWidthOfCutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    radialWithOfCutValue = Float.parseFloat(charSequence.toString());
                    calculateAndDsiplayQ();
                }else {
                    radialWithOfCutValue = 0;
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
                    calculateFeedSpeedandDisplayIt();
                }else {
                    spindleSpeedValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        feedperToothEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    feedPerToothValue = Float.parseFloat(charSequence.toString());
                    calculateFeedSpeedandDisplayIt();
                }else {
                    feedPerToothValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        numberOfInsertsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    numberOfInsertsValue = Float.parseFloat(charSequence.toString());
                    calculateFeedSpeedandDisplayIt();
                }else {
                    numberOfInsertsValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        axialDepthOfCutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    axialDepthOfCutValue = Float.parseFloat(charSequence.toString());
                    calculateAndDsiplayQ();
                }else {
                    axialDepthOfCutValue = 0 ;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        feedSpeedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 if(!charSequence.toString().equals("")){
                     feedSpeedValue = Float.parseFloat(charSequence.toString());
                     calculateAndDsiplayQ();
                 }else {
                     feedSpeedValue = 0;
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();

        return rootView;
    }
    public void calculateFeedSpeedandDisplayIt(){
        feedSpeedValue = spindleSpeedValue * feedPerToothValue * numberOfInsertsValue ;
        feedSpeedEditText.setText(String.valueOf(feedSpeedValue));
    }
    public void calculateAndDsiplayQ(){
        String m = "";
        if(isMetericSelected) {
            m = "cm3/min";
            Q = (axialDepthOfCutValue * radialWithOfCutValue * feedSpeedValue) / 1000;
        }else {
            m = "inch3/min";
            Q = (axialDepthOfCutValue * radialWithOfCutValue * feedSpeedValue);
        }

        finalValue.setText(String.valueOf(Math.getRoundOff(Q,2)));
        if(Q > 0) {
            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData, DataBase_data.class);
            data.add(new DataBase_data("Metal Removal Rate", (float) Q, m));
            Stash.put(Math.historyData, data);
            saveData();
        }

    }
    private void loadData(){
        if(variableValues.get(Math.spindleSpeed) != null && variableValues.get(Math.feedPerTooth) != null && variableValues.get(Math.numberOfInserts) != null){
            float tempSpindleSpeed = variableValues.get(Math.spindleSpeed);
            float tempFeedPerTooth = variableValues.get(Math.feedPerTooth);
            float tempNumberOfInserts = variableValues.get(Math.numberOfInserts);
            spindleSpeedEditText.setText(String.valueOf(Math.getRoundOff(tempSpindleSpeed ,2)));
            feedperToothEditText.setText(String.valueOf(Math.getRoundOff(tempFeedPerTooth , 2)));
            numberOfInsertsEditText.setText(String.valueOf(Math.getRoundOff(tempNumberOfInserts ,2)));
        }
    }
    private void saveData(){
        variableValues.put(Math.feedSpeed , feedSpeedValue);
        variableValues.put(Math.spindleSpeed , spindleSpeedValue);
        variableValues.put(Math.feedPerTooth , feedPerToothValue);
        variableValues.put(Math.numberOfInserts , numberOfInsertsValue);
        variableValues.put(Math.depthOfCut , axialDepthOfCutValue);
    }
}
