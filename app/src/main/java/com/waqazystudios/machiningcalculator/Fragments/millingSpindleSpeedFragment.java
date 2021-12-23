package com.waqazystudios.machiningcalculator.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class millingSpindleSpeedFragment extends Fragment {
    private ArrayList<Integer> list;
    private TextView finalValue;
    private EditText cutterDiameterEditText, cuttingSpeedEditText,axialDepthOfCutEditText;
    private Spinner approachAngle;
    private AdView adView;
    private float cuttingSpeedValue = 0,cutterDiameterValue = 0, spindleSpeedValue = 0,axialDepthValue = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.milling_spindle_speed_fragment,container,false);
        adView = rootView.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        finalValue = rootView.findViewById(R.id.finalValue);
        cutterDiameterEditText = rootView.findViewById(R.id.cutterDiameterEditText);
        cuttingSpeedEditText = rootView.findViewById(R.id.cuttingSpeedEditText);
        axialDepthOfCutEditText = rootView.findViewById(R.id.axialDepthEditText);
        approachAngle = rootView.findViewById(R.id.approachAngleSpinner);
        list = new ArrayList<>();
        initialiseList();
        if(!isMetericSelected){
            TextView unitsTextView = rootView.findViewById(R.id.unitsTextView);
            unitsTextView.setText("rpm");
        }else {
            TextView unitsTextView = rootView.findViewById(R.id.unitsTextView);
            unitsTextView.setText("r/min");
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(),R.layout.support_simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        approachAngle.setAdapter(adapter);
        cuttingSpeedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    cuttingSpeedValue = Float.parseFloat(charSequence.toString());
                    calculateNDisplay();
                }else {
                    cuttingSpeedValue = 0;
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
                    calculateNDisplay();
                }else {
                    cutterDiameterValue = 0;
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
                    axialDepthValue = Float.parseFloat(charSequence.toString());
                    calculateNDisplay();
                }else {
                    axialDepthValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();


        return rootView;
    }
    public void loadData(){

        if(variableValues.get(Math.machinedDiameter) != null && variableValues.get(Math.cuttingSpeed) != null ){
            float tempMachinedDiameter = 0 , tempCuttingSpeed = 0;
            tempMachinedDiameter = variableValues.get(Math.machinedDiameter);
            tempCuttingSpeed = variableValues.get(Math.cuttingSpeed);
            cutterDiameterEditText.setText(String.valueOf(Math.getRoundOff(tempMachinedDiameter , 2)));
            cuttingSpeedEditText.setText(String.valueOf(Math.getRoundOff(tempCuttingSpeed ,2)));
        }
    }
    public void initialiseList(){
        list.add(0);
        list.add(15);
        list.add(20);
        list.add(25);
        list.add(30);
        list.add(45);
        list.add(48);
        list.add(60);
        list.add(65);
        list.add(71);
        list.add(75);
        list.add(80);

    }
    public void calculateNDisplay(){
        String m = "";
        if(cutterDiameterValue != 0) {
            if(isMetericSelected) {
                m = "r/min";
                spindleSpeedValue = (float) ((cuttingSpeedValue * 1000) / (3.14 * cutterDiameterValue));
            }else {
                m = "rpm";
                spindleSpeedValue = (float) ((cuttingSpeedValue * 12) / (3.14 * cutterDiameterValue));
            }
            int temp = (int) java.lang.Math.round(spindleSpeedValue);
            finalValue.setText(String.valueOf(temp));
            if(spindleSpeedValue > 0){
                ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData , DataBase_data.class);
                data.add(new DataBase_data("Spindle Speed", (float) spindleSpeedValue , m));
                Stash.put(Math.historyData , data);
            }
            saveData();
        }
    }
    public void saveData(){
        variableValues.put(Math.spindleSpeed , spindleSpeedValue);
        variableValues.put(Math.machinedDiameter , cutterDiameterValue);
        variableValues.put(Math.depthOfCut , axialDepthValue);
        variableValues.put(Math.cuttingSpeed , cuttingSpeedValue);

    }
}
