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
import android.widget.Toast;

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

public class millingPowerRequirementFragment extends Fragment {
    private ImageView dropDownImage;
    private TextView finalValue;
    private ExpandableRelativeLayout expandableRelativeLayout;
    private EditText metalRemovalRateEditText , tableFeedEditText, axialDepthEditText,radialWidthEditText,specificCuttingForceEditText;
    private float metalRemovalRateValue = 0 , tableFeedValue = 0 , axialDepthValue = 0 , radialWidthValue = 0 , specificCuttingValue = 0 , Pc = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.milling_power_req_fragment,container,false);
        metalRemovalRateEditText = rootView.findViewById(R.id.metalRemovalEditText);
        tableFeedEditText = rootView.findViewById(R.id.tableFeedEditText);
        finalValue = rootView.findViewById(R.id.finalValue);
        if(!isMetericSelected){
            TextView unitsTextView = rootView.findViewById(R.id.unitsTextView);
            unitsTextView.setText("HP");
        }
        axialDepthEditText = rootView.findViewById(R.id.axialDepthEditText);
        radialWidthEditText = rootView.findViewById(R.id.radialWidthOfCutEditText);
        specificCuttingForceEditText = rootView.findViewById(R.id.specificCuttingForceEditText);
        dropDownImage = rootView.findViewById(R.id.dropDownImage);
        expandableRelativeLayout = rootView.findViewById(R.id.expandableView);
        dropDownImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableRelativeLayout.toggle();
            }
        });
        tableFeedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    tableFeedValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayQ();
                    calculateAndDisplayPc();
                }else {
                    tableFeedValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        axialDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    axialDepthValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayQ();
                    calculateAndDisplayPc();
                }else {
                    axialDepthValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        radialWidthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    radialWidthValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayPc();
                    calculateAndDisplayQ();
                }else {
                    radialWidthValue = 0;
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

                 }else {
                     specificCuttingValue = 0;
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        metalRemovalRateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    metalRemovalRateValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayPc();
                }else {
                    metalRemovalRateValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();
        return rootView;
    }
    public void calculateAndDisplayQ(){
        if(isMetericSelected) {
            metalRemovalRateValue = (axialDepthValue * radialWidthValue * tableFeedValue) / 1000;
        }else {
            metalRemovalRateValue = (axialDepthValue * radialWidthValue * tableFeedValue);
        }
        metalRemovalRateEditText.setText(String.valueOf(metalRemovalRateValue));

    }
    public void calculateAndDisplayPc(){
        String m ="";
        if(isMetericSelected){
            m = "kW";
            Pc = ( metalRemovalRateValue * specificCuttingValue ) / 60000;
        }else {
            m = "HP";
            Pc = ( metalRemovalRateValue * specificCuttingValue ) / 396000;
        }

        finalValue.setText(String.valueOf(Math.getRoundOff(Pc,2)));
        if(Pc > 0){
            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData , DataBase_data.class);
            data.add(new DataBase_data("Power Requirement", (float) Pc , m));
            Stash.put(Math.historyData , data);
        }
        saveData();


    }
    private void loadData(){
        Toast.makeText(getContext(), variableValues.get(Math.feedSpeed) + " " + variableValues.get(Math.depthOfCut) + variableValues.get(Math.radialWidthOfCut), Toast.LENGTH_SHORT).show();
        if(variableValues.get(Math.feedSpeed) != null){
            float value = variableValues.get(Math.feedSpeed);
            tableFeedEditText.setText(String.valueOf(Math.getRoundOff(value ,2 )));
        }
        if(variableValues.get(Math.depthOfCut) != null){
            float value = variableValues.get(Math.depthOfCut);
            axialDepthEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }
        if(variableValues.get(Math.radialWidthOfCut) != null){
            float value = variableValues.get(Math.radialWidthOfCut);
            radialWidthEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }
        if(variableValues.get(Math.metalRemovalRate) != null){
            float value = variableValues.get(Math.metalRemovalRate);
            metalRemovalRateEditText.setText(String.valueOf(Math.getRoundOff(value , 2)));
        }
    }
    public void saveData(){
        variableValues.put(Math.metalRemovalRate , metalRemovalRateValue);
        variableValues.put(Math.feedSpeed , tableFeedValue);
        variableValues.put(Math.depthOfCut , axialDepthValue);
        variableValues.put(Math.radialWidthOfCut , radialWidthValue);
        variableValues.put(Math.specificCuttingForce , specificCuttingValue);
        variableValues.put(Math.powerRequirement , Pc);
    }

}
