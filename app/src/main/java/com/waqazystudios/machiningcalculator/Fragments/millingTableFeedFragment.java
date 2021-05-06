package com.waqazystudios.machiningcalculator.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class millingTableFeedFragment extends Fragment {
    private ArrayList<String> list;
    private TextView finalValue;
    private ArrayList<Integer> list2;
    private Spinner approachAngleSpinner;
    private ImageView dropDownImage;
    private Spinner cutterPositionSpinner;
    private float axialDepthOfCutValue = 0;
    private ExpandableRelativeLayout expandableRelativeLayout;
    private EditText spindleSpeedEditText,axialDepthEditText ,cutterDiameterEditText,cuttingSpeedEditText,feedPerToothEditText,numberOfInsertsEditText;
    private float spindleSpeedValue = 0 , cutterDiameterValue  = 0 , cuttingSpeedValue = 0, feedPerToothValue = 0 , numberOfInsertsValue = 0,tableFeedValue = 0;

    @Override
    public void onResume() {
        super.onResume();
        feedPerToothEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Toast.makeText(getContext(), charSequence, Toast.LENGTH_SHORT).show();
                if(!charSequence.toString().equals("")) {
                    feedPerToothValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayTableFeed();

                }else {
                    feedPerToothValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.milling_table_feed_fragment,container,false);
        expandableRelativeLayout = rootView.findViewById(R.id.expandableView);
        cutterPositionSpinner = rootView.findViewById(R.id.cutterPositionSpinner);
        approachAngleSpinner = rootView.findViewById(R.id.approachAngleSpinner);
        list = new ArrayList<String>();
        list.add("Centered WorkPiece");
        list.add("Side Milling");
        initialiseList2();
        if(!isMetericSelected){
            TextView unitsTextView = rootView.findViewById(R.id.unitsTextView);
            unitsTextView.setText("inch / min");
        }
        finalValue = rootView.findViewById(R.id.finalValue);
        spindleSpeedEditText = rootView.findViewById(R.id.spindleSpeedEditText);
        cutterDiameterEditText = rootView.findViewById(R.id.cutterDiameterEditText);
        cuttingSpeedEditText = rootView.findViewById(R.id.cuttingSpeedEditText);
        feedPerToothEditText = rootView.findViewById(R.id.feedPerToothEditText);
        axialDepthEditText = rootView.findViewById(R.id.axialDepthEditText);
        numberOfInsertsEditText = rootView.findViewById(R.id.numberOfInsertsEditText);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(getContext(),R.layout.support_simple_spinner_dropdown_item,list2);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        approachAngleSpinner.setAdapter(adapter1);
        cutterPositionSpinner.setAdapter(adapter);
        dropDownImage = rootView.findViewById(R.id.dropDownImage);

        dropDownImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                expandableRelativeLayout.toggle();
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
                    displayNcalculateSpindleSPeed();
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
                    displayNcalculateSpindleSPeed();
                }else{
                    cuttingSpeedValue = 0;
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
                    calculateAndDisplayTableFeed();
                }else {
                    spindleSpeedValue = 0;
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
                    calculateAndDisplayTableFeed();
                }else {
                    numberOfInsertsValue = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();
        return rootView;
    }
    public void initialiseList2(){
        list2 = new ArrayList<Integer>();
        list2.add(0);
        list2.add(15);
        list2.add(20);
        list2.add(25);
        list2.add(30);
        list2.add(45);
        list2.add(48);
        list2.add(60);
        list2.add(65);
        list2.add(71);
        list2.add(75);
        list2.add(80);

    }
    public void displayNcalculateSpindleSPeed(){
        if(cutterDiameterValue != 0 && cuttingSpeedValue !=0) {
            spindleSpeedValue = (int) ((cuttingSpeedValue * 1000) / (3.14 * cutterDiameterValue));
            spindleSpeedEditText.setText(String.valueOf(spindleSpeedValue));
        }

    }
    public void calculateAndDisplayTableFeed(){
        String m = "";

        if(spindleSpeedValue != 0 && feedPerToothValue != 0 && numberOfInsertsValue != 0) {
            tableFeedValue = spindleSpeedValue * feedPerToothValue * numberOfInsertsValue;
            finalValue.setText(String.valueOf(tableFeedValue));
            saveData();
            if(tableFeedValue > 0){
                ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData , DataBase_data.class);
                if(isMetericSelected){
                    m = "mm/min";

                }else {
                    m = "inch/min";

                }
                data.add(new DataBase_data("Table Feed", (float) tableFeedValue , m));
                Stash.put(Math.historyData , data);
            }

        }


    }
    public void saveData(){
        variableValues.put(Math.spindleSpeed , spindleSpeedValue);
        variableValues.put(Math.machinedDiameter , cutterDiameterValue);
        variableValues.put(Math.cuttingSpeed , cuttingSpeedValue);
        variableValues.put(Math.depthOfCut , axialDepthOfCutValue);
        variableValues.put(Math.feedPerTooth , feedPerToothValue);
        variableValues.put(Math.numberOfInserts , numberOfInsertsValue);

    }
    public void loadData(){

        if(variableValues.get(Math.machinedDiameter) != null && variableValues.get(Math.cuttingSpeed) != null && variableValues.get(Math.depthOfCut) != null){
            float tempMachinedDiameter = variableValues.get(Math.machinedDiameter);
            float tempCuttingSpeed = variableValues.get(Math.cuttingSpeed);
            float tempDepthOfCut = variableValues.get(Math.depthOfCut);
            cutterDiameterEditText.setText(String.valueOf(Math.getRoundOff(tempMachinedDiameter , 2)));
            cuttingSpeedEditText.setText(String.valueOf(Math.getRoundOff(tempCuttingSpeed , 2)));
            axialDepthEditText.setText(String.valueOf(Math.getRoundOff(tempDepthOfCut , 2)));
        }
    }
}
