package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.waqazystudios.machiningcalculator.Adapters.CustomAdapter;
import com.waqazystudios.machiningcalculator.Models.Data;
import com.waqazystudios.machiningcalculator.R;

import java.util.ArrayList;

public class equationSelector extends AppCompatActivity {
    private ListView listView;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_selector);
        listView = findViewById(R.id.listView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("Identifier");
        }
        setTitle(title);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        ArrayList<Data> list = new ArrayList<>();
        if(title.equals("Turning")) {
            list.add(new Data("Cutting Speed"));
            list.add(new Data("Spindle Speed"));
            list.add(new Data("Metal removal Rate"));
            list.add(new Data("Power Requirement"));
            list.add(new Data("Time in cut"));
        }
        else if(title.equals("Milling")){
            list.add(new Data("Cutting Speed"));
            list.add(new Data("Spindle Speed"));
            list.add(new Data("Table Feed (vf)"));
            list.add(new Data("Metal Removal Rate (Q)"));
            list.add(new Data("Time in Cut (Tc)"));
            list.add(new Data("Power Requirement (Pc)"));
            list.add(new Data("Torque (Mc)"));

        }

        CustomAdapter adapter = new CustomAdapter(this,R.layout.selector_layout,list);
        listView.setAdapter(adapter);

    }
}