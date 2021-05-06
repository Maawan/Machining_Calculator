package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.waqazystudios.machiningcalculator.Adapters.CustomAdapter2;
import com.waqazystudios.machiningcalculator.Models.Data;
import com.waqazystudios.machiningcalculator.R;

import java.util.ArrayList;

public class millingCalculationActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milling_calculation);
        listView = findViewById(R.id.listView);
        listView.setDivider(null);
        setTitle("Milling");
        ArrayList<Data> list = new ArrayList<>();
        list.add(new Data("Cutting Speed"));
        list.add(new Data("Spindle Speed"));
        list.add(new Data("Table Feed (vf)"));
        list.add(new Data("Metal Removal Rate (Q)"));
        list.add(new Data("Time in Cut (Tc)"));
        list.add(new Data("Power Requirement (Pc)"));
        list.add(new Data("Torque (Mc)"));
        CustomAdapter2 adapter = new CustomAdapter2(this,R.layout.selector_layout,list);
        listView.setAdapter(adapter);
    }
}