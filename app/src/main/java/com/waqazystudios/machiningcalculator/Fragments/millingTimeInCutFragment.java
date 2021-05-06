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

import static com.waqazystudios.machiningcalculator.MainActivity.variableValues;

public class millingTimeInCutFragment extends Fragment {
    private ImageView dropDownImage;
    private TextView finalValue;
    private ExpandableRelativeLayout expandableRelativeLayout;
    private EditText feedPerToothEditText,spindleSpeedEditText,numberOfInsertsEditText,tableFeedEditText,lengthOfCutEditText;
    private float lengthOfCutValue = 0 , tableFeedValue = 0 , feedPerToothValue = 0 , spindleSpeedValue = 0 , numberOfInsertsValue = 0,timeInCut = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.miiling_time_in_cut_fragment,container,false);
        dropDownImage = rootView.findViewById(R.id.dropDownImage);
        finalValue = rootView.findViewById(R.id.finalValue);
        expandableRelativeLayout = rootView.findViewById(R.id.expandableView1);
        dropDownImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                expandableRelativeLayout.toggle();

            }
        });
        feedPerToothEditText = rootView.findViewById(R.id.feedPerToothEditText);
        lengthOfCutEditText = rootView.findViewById(R.id.lengthOfCutEditText);
        spindleSpeedEditText = rootView.findViewById(R.id.spindleSpeedEditText);
        numberOfInsertsEditText = rootView.findViewById(R.id.numberOfInsertsEditText);
        tableFeedEditText = rootView.findViewById(R.id.tableFeedEditText);

        lengthOfCutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    lengthOfCutValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayTc();
                }else {
                    lengthOfCutValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        feedPerToothEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    feedPerToothValue = Float.parseFloat(charSequence.toString());
                    calculateAndDisplayTableFeed();
                    calculateAndDisplayTc();
                }else {
                    feedPerToothValue = 0;
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
                    calculateAndDisplayTc();
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
                    calculateAndDisplayTc();
                }else {
                    numberOfInsertsValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                    calculateAndDisplayTc();
                }else {
                    tableFeedValue = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();
        return rootView;
    }
    public void calculateAndDisplayTableFeed(){
        tableFeedValue = feedPerToothValue * spindleSpeedValue * numberOfInsertsValue;
        Toast.makeText(getContext(), feedPerToothValue +"  " + spindleSpeedValue + " " + numberOfInsertsValue, Toast.LENGTH_SHORT).show();
        tableFeedEditText.setText(String.valueOf(tableFeedValue));

    }
    public void calculateAndDisplayTc(){
        String m = "sec";
        if(lengthOfCutValue != 0 && tableFeedValue != 0) {
            timeInCut = lengthOfCutValue / tableFeedValue;
            finalValue.setText(String.valueOf(timeInCut));
            if(timeInCut > 0){
                ArrayList<DataBase_data> data = Stash.getArrayList(Math.historyData , DataBase_data.class);
                data.add(new DataBase_data("Time in Cut", (float) timeInCut , m));
                Stash.put(Math.historyData , data);
            }
            saveData();
        }
    }
    private void loadData(){
        if(variableValues.get(Math.feedPerTooth) != null && variableValues.get(Math.spindleSpeed) != null && variableValues.get(Math.numberOfInserts) != null){
            float tempFeedPerTooth = variableValues.get(Math.feedPerTooth);
            float tempSpindleSpeed = variableValues.get(Math.spindleSpeed);
            float tempNumberOfInserts = variableValues.get(Math.numberOfInserts);
            float tempTableFeed = variableValues.get(Math.feedSpeed);
            feedPerToothEditText.setText(String.valueOf(Math.getRoundOff(tempFeedPerTooth , 2)));
            spindleSpeedEditText.setText(String.valueOf(Math.getRoundOff(tempSpindleSpeed , 2)));
            numberOfInsertsEditText.setText(String.valueOf(Math.getRoundOff(tempNumberOfInserts , 2)));
            tableFeedEditText.setText(String.valueOf(Math.getRoundOff(tempTableFeed , 2)));
        }
    }
    private void saveData(){
        variableValues.put(Math.lengthOfCut , lengthOfCutValue);
        variableValues.put(Math.feedSpeed , tableFeedValue);
        variableValues.put(Math.feedPerTooth , feedPerToothValue);
        variableValues.put(Math.spindleSpeed , spindleSpeedValue);
        variableValues.put(Math.numberOfInserts , numberOfInsertsValue);
        variableValues.put(Math.timeInCut , timeInCut);
    }
}
