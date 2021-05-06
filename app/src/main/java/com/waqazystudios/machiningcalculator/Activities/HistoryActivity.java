package com.waqazystudios.machiningcalculator.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.waqazystudios.machiningcalculator.Adapters.HistoryAdapter;
import com.waqazystudios.machiningcalculator.Models.DataBase_data;
import com.waqazystudios.machiningcalculator.R;
import com.waqazystudios.machiningcalculator.Utlis.Math;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.onDelete {
    private RecyclerView listView;
    private RelativeLayout noHistoryLayout;
    private HistoryAdapter adapter;
    private ArrayList<DataBase_data> temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        noHistoryLayout = findViewById(R.id.noHistoryLayout);
        setTitle("Your Calculations History");
        listView = findViewById(R.id.recyclr);
        Stash.init(this);
        temp = Stash.getArrayList(Math.historyData , DataBase_data.class);
        adapter = new HistoryAdapter(this, temp);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        if(temp.size() == 0){
            noHistoryLayout.setVisibility(View.VISIBLE);
        }else {
            noHistoryLayout.setVisibility(View.GONE);
        }

    }
    @Override
    public void Delete(int position) {
        try {
            if(position < temp.size()) {
                temp.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
                Stash.put(Math.historyData, temp);
                Toast.makeText(this, "History Deleted Successfully", Toast.LENGTH_SHORT).show();
                if (temp.size() == 0) {
                    noHistoryLayout.setVisibility(View.VISIBLE);
                } else {
                    noHistoryLayout.setVisibility(View.GONE);
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(this, "Wait...", Toast.LENGTH_SHORT).show();
        }
    }
}