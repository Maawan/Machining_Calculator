package com.waqazystudios.machiningcalculator.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;
import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;

public class powerRequirementFragment extends Fragment {
    private ImageView imageToggle;
    private TextView finalValue;
    private ArrayList<Integer> leadAngle,rakeAngle;
    private Spinner leadAngleSpinner,rakeAngleSpinner;
    private ExpandableRelativeLayout expandableRelativeLayout;
    private EditText metalRemovalEditText, cuttingSpeedEditText, feedPerRevolutionEditText,depthOfCutEditText,specificCuttingForceEditText;
    private float Pc = 0,metalRemovalValue = 0, cuttingSpeedValue = 0, feedPerRevolutionValue = 0, depthOfCutValue = 0,specificCuttingValue = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.power_requirement_fragment,container,false);
        expandableRelativeLayout = rootView.findViewById(R.id.expandableView);
        imageToggle = rootView.findViewById(R.id.dropDownImage);
        finalValue = rootView.findViewById(R.id.finalValue);
        if(isMetericSelected){

        }else {
            TextView units = rootView.findViewById(R.id.unitsTextView);
            units.setText("HP");
        }
        leadAngleSpinner = rootView.findViewById(R.id.leadAngleSpinner);
        rakeAngleSpinner = rootView.findViewById(R.id.rakeAngleSpinner);
        metalRemovalEditText = rootView.findViewById(R.id.metalRemovalEditText);
        cuttingSpeedEditText = rootView.findViewById(R.id.cuttingSpeedEditText);
        feedPerRevolutionEditText = rootView.findViewById(R.id.feedPerRevolutionEditText);
        depthOfCutEditText = rootView.findViewById(R.id.depthOfCutEditText);
        specificCuttingForceEditText = rootView.findViewById(R.id.specificCuttingForceEditText);
        initialiseRakeList();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(),R.layout.support_simple_spinner_dropdown_item,leadAngle);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(getContext(),R.layout.support_simple_spinner_dropdown_item,rakeAngle);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        leadAngleSpinner.setAdapter(adapter);
        rakeAngleSpinner.setAdapter(adapter2);
        rakeAngleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        leadAngleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), leadAngle.get(i) + " is Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        imageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableRelativeLayout.toggle();
            }
        });
        metalRemovalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")) {
                    metalRemovalValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplaykW();
                }else {
                    metalRemovalValue = 0;
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
                    calculateAndDisplayQ();
                }else {
                    cuttingSpeedValue = 0;
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
                   calculateAndDisplayQ();
               }else {
                   feedPerRevolutionValue = 0;
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
                    calculateAndDisplayQ();

                }else {
                    depthOfCutValue = 0;

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
                    calculateAndDisplaykW();
                }else {
                    specificCuttingValue = 0 ;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkForValues();


        return rootView;
    }
    public void checkForValues(){
        if(variableValues.get(Math.cuttingSpeed) != null && variableValues.get(Math.feedPerRevolution) != null && variableValues.get(Math.depthOfCut) !=null){
            float tempCuttingSpeed = variableValues.get(Math.cuttingSpeed);
            float tempfeedPerRevolution = variableValues.get(Math.feedPerRevolution);
            float tempDepth = variableValues.get(Math.depthOfCut);
            cuttingSpeedEditText.setText(String.valueOf(Math.getRoundOff(tempCuttingSpeed,2)));
            feedPerRevolutionEditText.setText(String.valueOf(Math.getRoundOff(tempfeedPerRevolution,2)));
            depthOfCutEditText.setText(String.valueOf(Math.getRoundOff(tempDepth,2)));
        }

    }
    public void calculateAndDisplayQ(){
        if(isMetericSelected) {
            metalRemovalValue = (cuttingSpeedValue * feedPerRevolutionValue * depthOfCutValue);
        }else {
            metalRemovalValue = (cuttingSpeedValue * feedPerRevolutionValue * depthOfCutValue) * 12 ;
        }
        metalRemovalEditText.setText(String.valueOf(metalRemovalValue));
    }
    private void initialiseRakeList(){
        rakeAngle = new ArrayList<>();
        for(int i = -5 ; i <= 30 ; i++){
            rakeAngle.add(i);
        }
        leadAngle = new ArrayList<>();
        leadAngle.add(0);
        leadAngle.add(45);
        leadAngle.add(30);
        leadAngle.add(27);
        leadAngle.add(25);
        leadAngle.add(23);
        leadAngle.add(20);
        leadAngle.add(17);
        leadAngle.add(15);
        leadAngle.add(10);
        leadAngle.add(0);
        leadAngle.add(-1);
        leadAngle.add(-2);
        leadAngle.add(-3);
        leadAngle.add(-5);
        leadAngle.add(-8);
        leadAngle.add(-17);
        leadAngle.add(-20);
        leadAngle.add(-25);
        leadAngle.add(-27);
        leadAngle.add(-30);
        leadAngle.add(-45);
        leadAngle.add(-50);
    }
    public void calculateAndDisplaykW(){
        String m = "";
        if (isMetericSelected) {
            Pc = (metalRemovalValue * specificCuttingValue) / 60000;
            m = "cm3/min";
        }else {
            Pc = (metalRemovalValue * specificCuttingValue) / 33000;
            m = "inch3/min";
        }
        finalValue.setText(String.valueOf(round(Pc , 3)) + " kW");
        if(Pc > 0) {
            ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData, DataBase_data.class);
            data.add(new DataBase_data("Power Requirement", (float) Pc, m));
            Stash.put(Math.historyData, data);
            saveData();
        }


    }
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void saveData(){
        variableValues.put(Math.specificCuttingForce,specificCuttingValue);
        variableValues.put(Math.metalRemovalRate,metalRemovalValue);
        variableValues.put(Math.cuttingSpeed,cuttingSpeedValue);
        variableValues.put(Math.feedPerRevolution , feedPerRevolutionValue);
        variableValues.put(Math.depthOfCut , depthOfCutValue);
    }
}
