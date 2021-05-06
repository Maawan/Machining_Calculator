package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.waqazystudios.machiningcalculator.Fragments.cuttingSpeedFragment;
import com.waqazystudios.machiningcalculator.Fragments.metalRemovalFragment;
import com.waqazystudios.machiningcalculator.Fragments.millingCuttingSpeedFragment;
import com.waqazystudios.machiningcalculator.Fragments.millingMetalRemovalRate;
import com.waqazystudios.machiningcalculator.Fragments.millingPowerRequirementFragment;
import com.waqazystudios.machiningcalculator.Fragments.millingSpindleSpeedFragment;
import com.waqazystudios.machiningcalculator.Fragments.millingTableFeedFragment;
import com.waqazystudios.machiningcalculator.Fragments.millingTimeInCutFragment;
import com.waqazystudios.machiningcalculator.Fragments.millingTorqueFragment;
import com.waqazystudios.machiningcalculator.Fragments.powerRequirementFragment;
import com.waqazystudios.machiningcalculator.Fragments.spindleSpeedFragment;
import com.waqazystudios.machiningcalculator.Fragments.timeInCutFragment;
import com.waqazystudios.machiningcalculator.R;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import static com.waqazystudios.machiningcalculator.Activities.Settings.isMetericSelected;

public class calculationActivity extends AppCompatActivity {
    private cuttingSpeedFragment fragment;
    private spindleSpeedFragment spindleSpeedFrag;
    metalRemovalFragment metalRemovalFragment1;
    powerRequirementFragment fm;
    int position = 0;
    boolean isFirstTime = false;

    @Override
    protected void onStop() {
//        if(position == 0) {
//            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        }else if(position ==1){
//            getSupportFragmentManager().beginTransaction().remove(spindleSpeedFrag).commit();
//        }else if(position == 2){
//            getSupportFragmentManager().beginTransaction().remove(metalRemovalFragment1).commit();
//        }else if(position == 4){
//            getSupportFragmentManager().beginTransaction().remove(fm).commit();
//        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        fragment = new cuttingSpeedFragment();
        setTitle("Turning");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("Position");
        }
        isFirstTime = getFirstTimeVariableFromSharedPrefs();
        if(isFirstTime) {
            //showDialog();
            isFirstTime = false;
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("isFirstTime", isFirstTime);
            myEdit.apply();
            setMeticAsDefault(true);
        }
        displayFragments();






    }
    public void showDialog(){
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Units ?")
                .setMessage("Please choose Unit before continuing !")
                .setCancelable(false)
                .setPositiveButton("Metric", R.drawable.ic_baseline_radio_button_unchecked_24, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(getApplicationContext(), "Great ! Now Metric (Meter) is selected as your Default Unit \n You can change this from Settings Anytime", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        setMeticAsDefault(true);
                    }
                })
                .setNegativeButton("Inch", R.drawable.ic_baseline_radio_button_unchecked_24, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(getApplicationContext(), "Wow ! Now Inch is selected as your Default Unit \n You can change this from Settings Anytime", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        setMeticAsDefault(false);
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();
    }
    public void setMeticAsDefault(Boolean temp){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("unit", temp);
        myEdit.apply();
        isMetericSelected = temp;
        displayFragments();
    }
    public boolean getFirstTimeVariableFromSharedPrefs(){
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        boolean temp = sh.getBoolean("isFirstTime",true);
        return temp;
    }
    public void displayFragments(){
        switch (position){
            case 0 :
                getSupportFragmentManager().beginTransaction().remove(new cuttingSpeedFragment()).add(R.id.frameContainer,fragment).commit();
                setTitle("Cutting Speed");
                break;
            case 1 :
                spindleSpeedFrag = new spindleSpeedFragment();
                getSupportFragmentManager().beginTransaction().remove(new spindleSpeedFragment()).add(R.id.frameContainer,spindleSpeedFrag).commit();
                setTitle("Spindle Speed");
                break;
            case 2 :
                metalRemovalFragment1 = new metalRemovalFragment();
                getSupportFragmentManager().beginTransaction().remove(new metalRemovalFragment()).add(R.id.frameContainer,metalRemovalFragment1).commit();
                setTitle("Metal Removal Rate");
                break;
            case 3 :
                fm = new powerRequirementFragment();
                getSupportFragmentManager().beginTransaction().remove(new powerRequirementFragment()).add(R.id.frameContainer,fm).commit();
                setTitle("Power Requirement");
                break;
            case 4:
                timeInCutFragment timeInCutFragment1 = new timeInCutFragment();
                getSupportFragmentManager().beginTransaction().remove(new timeInCutFragment()).add(R.id.frameContainer,timeInCutFragment1).commit();
                setTitle("Time in Cut (Tc)");
                break;
            case 10:
                millingCuttingSpeedFragment MFrag = new millingCuttingSpeedFragment();
                getSupportFragmentManager().beginTransaction().remove(new millingCuttingSpeedFragment()).add(R.id.frameContainer,MFrag).commit();
                setTitle("Cutting Speed");
                break;
            case 11 :
                getSupportFragmentManager().beginTransaction().remove(new millingSpindleSpeedFragment()).add(R.id.frameContainer,new millingSpindleSpeedFragment()).commit();
                setTitle("Spindle Speed");
                break;
            case 12:
                getSupportFragmentManager().beginTransaction().remove(new millingTableFeedFragment()).add(R.id.frameContainer,new millingTableFeedFragment()).commit();
                setTitle("Table Feed");
                break;
            case 13:
                getSupportFragmentManager().beginTransaction().remove(new millingMetalRemovalRate()).add(R.id.frameContainer,new millingMetalRemovalRate()).commit();
                setTitle("Metal Removal Rate (Q)");
                break;
            case 14:
                getSupportFragmentManager().beginTransaction().remove(new millingTimeInCutFragment()).add(R.id.frameContainer,new millingTimeInCutFragment()).commit();
                setTitle("Time in Cut (Tc)");
                break;
            case 15:
                getSupportFragmentManager().beginTransaction().remove(new millingPowerRequirementFragment()).add(R.id.frameContainer,new millingPowerRequirementFragment()).commit();
                setTitle("Power Requirement (Pc)");
                break;
            case 16:
                getSupportFragmentManager().beginTransaction().remove(new millingTorqueFragment()).add(R.id.frameContainer,new millingTorqueFragment()).commit();
                setTitle("Torque (Mc)");
                break;
        }
    }
}