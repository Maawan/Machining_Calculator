package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.waqazystudios.machiningcalculator.R;

public class Settings extends AppCompatActivity {
    private RadioButton metic,inch,enteringAngle,leadAngle;
    public static Boolean isMetericSelected = true;
    private Boolean isEnteringAngleSelected = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        isMetericSelected = findUnitFromSharefPrefs();
        findUnitFromSharefPrefs();
        metic = findViewById(R.id.metricRadioButton);
        inch = findViewById(R.id.inchRadioButton);
        enteringAngle = findViewById(R.id.enteringRadioButton);
        leadAngle = findViewById(R.id.leadRadioButton);
        if(isMetericSelected){
            metic.setChecked(true);
            inch.setChecked(false);
        }else{
            metic.setChecked(false);
            inch.setChecked(true);
        }
        metic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    inch.setChecked(false);
                    isMetericSelected = true;
                    setMeticAsDefault(true);
                }else {
                    inch.setChecked(true);
                    isMetericSelected = false;
                }
            }
        });
        inch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    metic.setChecked(false);
                    setMeticAsDefault(false);
                    isMetericSelected = false;
                }else {
                    metic.setChecked(true);
                    isMetericSelected = true;
                }
            }
        });
        if(isEnteringAngleSelected){
            enteringAngle.setChecked(true);
            leadAngle.setChecked(false);
        }else {
            enteringAngle.setChecked(false);
            leadAngle.setChecked(true);
        }
        enteringAngle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    leadAngle.setChecked(false);

                    isEnteringAngleSelected = true;
                }else{
                    leadAngle.setChecked(true);

                    isEnteringAngleSelected = false;
                }
            }
        });
        leadAngle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    enteringAngle.setChecked(false);
                }else {
                    enteringAngle.setChecked(true);
                }
            }
        });

    }
    private Boolean findUnitFromSharefPrefs(){
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Boolean temp = sh.getBoolean("unit",false);
        return temp;

    }
    public void setMeticAsDefault(Boolean temp){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("unit", temp);
        myEdit.apply();
    }
}