package com.waqazystudios.machiningcalculator.Fragments;

import android.graphics.Typeface;
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

public class metalRemovalFragment extends Fragment {
    private EditText cuttingSpeedEditText, machinedDiameterEditText, spindleSpeedEditText, feedPerRevolutionEditText, depthofCutEditText;
    private ImageView dropDownImage;
    private TextView finalValue;
    private float cuttingSpeedValue, machinedDiameterValue, spindleSpeedValue, feedPerRevolutionValue, depthOfCutValue,q;
    private ExpandableRelativeLayout expandableRelativeLayout;
    public metalRemovalFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.metal_removal_fragment,container,false);
        cuttingSpeedEditText = rootView.findViewById(R.id.cuttingSpeedEditText);
        machinedDiameterEditText = rootView.findViewById(R.id.diameterEditText);
        spindleSpeedEditText = rootView.findViewById(R.id.spindleEditText);
        feedPerRevolutionEditText = rootView.findViewById(R.id.feedPerRevolutionEditText);
        depthofCutEditText = rootView.findViewById(R.id.depthOfCutEditText);
        finalValue = rootView.findViewById(R.id.finalValue);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/adventprobold.ttf");
        finalValue.setTypeface(custom_font);
        if(isMetericSelected){
                // HComments
        }else {
            TextView unitsValue = rootView.findViewById(R.id.unitsTextView);
            unitsValue.setText("inch3/min");
        }
        dropDownImage = rootView.findViewById(R.id.dropDownImage);
        expandableRelativeLayout = (ExpandableRelativeLayout)rootView.findViewById(R.id.expandableView);
        dropDownImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableRelativeLayout.toggle();
            }
        });
        machinedDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    machinedDiameterValue = Float.parseFloat(charSequence.toString());
                    calculateCuttingSpeed();
                    calculateQ();
                    Toast.makeText(getContext(), "If is callied on DM", Toast.LENGTH_SHORT).show();
                }
                else {
                    machinedDiameterValue = 0;
                    Toast.makeText(getContext(), "ELse id called on DM", Toast.LENGTH_SHORT).show();
                    calculateCuttingSpeed();
                    calculateQ();
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
                if(!charSequence.toString().equals("")) {
                    spindleSpeedValue = Float.parseFloat(charSequence.toString());
                    calculateCuttingSpeed();
                    calculateQ();
                    Toast.makeText(getContext(), "If is called on spnindle Speed ", Toast.LENGTH_SHORT).show();
                }else {
                    spindleSpeedValue = 0;
                    calculateCuttingSpeed();
                    calculateQ();
                    Toast.makeText(getContext(), "Else is called on spindle speed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        depthofCutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    depthOfCutValue = Float.parseFloat(charSequence.toString());
                    calculateQ();
                }else {
                    depthOfCutValue = 0;
                    calculateQ();
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
                if(!charSequence.toString().equals("")) {
                    feedPerRevolutionValue = Float.parseFloat(charSequence.toString());
                    calculateQ();
                }else {
                    feedPerRevolutionValue = 0;
                    calculateQ();
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
                    calculateQ();
                }else {
                    cuttingSpeedValue = 0;
                    calculateQ();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkForValues();











        return rootView;
    }
    private void calculateQ(){
        String m = "";
        if(isMetericSelected){
            q = cuttingSpeedValue * depthOfCutValue * feedPerRevolutionValue;
            m = "cm3/min";
        }else {
            q = cuttingSpeedValue * depthOfCutValue * feedPerRevolutionValue * 12;
            m = "inch3/min";
        }


        finalValue.setText(String.valueOf(Math.getRoundOff(q,2)));
        if(q > 0) {
            variableValues.put(Math.cuttingSpeed, cuttingSpeedValue);
            variableValues.put(Math.machinedDiameter, machinedDiameterValue);
            variableValues.put(Math.spindleSpeed, spindleSpeedValue);
            variableValues.put(Math.feedPerRevolution, feedPerRevolutionValue);
            variableValues.put(Math.depthOfCut, depthOfCutValue);
            variableValues.put(Math.metalRemovalRate, q);

            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData , DataBase_data.class);
            data.add(new DataBase_data("Metal Removal Rate", (float) q , m));
            Stash.put(Math.historyData , data);
        }
    }

    private void calculateCuttingSpeed(){
        if(isMetericSelected) {
            cuttingSpeedValue = (float) (machinedDiameterValue * 3.14 * (int) spindleSpeedValue) / 1000;

        }else {
            cuttingSpeedValue = (float) (machinedDiameterValue * 3.14 * (int) spindleSpeedValue) / 12;
        }
        cuttingSpeedEditText.setText(String.valueOf(Math.getRoundOff(cuttingSpeedValue, 2)));
    }
    public void checkForValues(){

        if(variableValues.get(Math.cuttingSpeed) != null && variableValues.get(Math.spindleSpeed) != null && variableValues.get(Math.machinedDiameter)!= null){
            float temp = (float) Math.getRoundOff(variableValues.get(Math.machinedDiameter),2);
            Toast.makeText(getContext(), "Cutting Speed Vc ->" + variableValues.get(Math.cuttingSpeed) + " Machined Diameter ->" + variableValues.get(Math.machinedDiameter) + "Spindle SPeed " + variableValues.get(Math.spindleSpeed), Toast.LENGTH_SHORT).show();
            spindleSpeedEditText.setText(String.valueOf(Math.getRoundOff(variableValues.get(Math.spindleSpeed),2)));
            Toast.makeText(getContext(), variableValues.get(Math.machinedDiameter)+ "", Toast.LENGTH_SHORT).show();
            machinedDiameterEditText.setText(String.valueOf(temp));

        }
    }
}
