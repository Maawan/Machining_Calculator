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
import com.waqazystudios.machiningcalculator.Models.DataBase_data;
import com.waqazystudios.machiningcalculator.R;
import com.waqazystudios.machiningcalculator.Utlis.Math;

import java.util.ArrayList;

import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;
import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;

public class millingCuttingSpeedFragment extends Fragment {
    private ArrayList<Integer> list;
    private TextView finalValue;
    private EditText cutterDiameterEditText, spindleSpeedEditText,axialDepthOfCutEditText;
    private Spinner approachAngle;
    private float cuttingSpeedValue = 0,cutterDiameterValue = 0, spindleSpeedValue = 0;//axialDepthValue = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.milling_cutting_speed_fragment,container,false);
        finalValue = rootView.findViewById(R.id.finalValue);
        cutterDiameterEditText = rootView.findViewById(R.id.cutterDiameterEditText);
        spindleSpeedEditText = rootView.findViewById(R.id.spindleSpeedEditText);
//        axialDepthOfCutEditText = rootView.findViewById(R.id.axialDepthEditText);
        approachAngle = rootView.findViewById(R.id.approachAngleSpinner);
        list = new ArrayList<>();
        initialiseList();
        if(!isMetericSelected){
            TextView unitsTextView = rootView.findViewById(R.id.unitsTextView);
            unitsTextView.setText("ft / min");
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(),R.layout.support_simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        approachAngle.setAdapter(adapter);
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
        spindleSpeedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    spindleSpeedValue = Float.parseFloat(charSequence.toString());
                    calculateNDisplay();
                }else {
                    spindleSpeedValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return rootView;
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
        if(!isMetericSelected){
            cuttingSpeedValue = (float) (3.14 * cutterDiameterValue * spindleSpeedValue) / 12;
            m = "ft/inch";
        }else {
            cuttingSpeedValue = (float) (3.14 * cutterDiameterValue * spindleSpeedValue) / 1000;
            m = "m/min";
        }
        finalValue.setText(String.valueOf(Math.getRoundOff(cuttingSpeedValue,2)));
        if(cuttingSpeedValue > 0) {
            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData, DataBase_data.class);
            data.add(new DataBase_data("Cutting Speed", (float) cuttingSpeedValue, m));
            Stash.put(Math.historyData, data);
        }
        saveData();

    }
    public void saveData(){
        variableValues.put(Math.machinedDiameter,cutterDiameterValue);
        variableValues.put(Math.spindleSpeed , spindleSpeedValue);
        variableValues.put(Math.cuttingSpeed,cuttingSpeedValue);

    }
}
