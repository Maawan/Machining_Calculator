package com.waqazystudios.machiningcalculator.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.waqazystudios.machiningcalculator.Activities.calculationActivity;
import com.waqazystudios.machiningcalculator.InteristialClick;
import com.waqazystudios.machiningcalculator.Models.Data;
import com.waqazystudios.machiningcalculator.R;
import java.util.ArrayList;

public class CustomAdapter2 extends ArrayAdapter<Data> {
    private Context ctx;
    private ArrayList<Data> list;
    private InteristialClick listener;
    public CustomAdapter2(@NonNull Context context, int resource, @NonNull ArrayList<Data> objects , InteristialClick click) {

        super(context, resource, objects);
        this.ctx = context;
        this.listener = click;
        list = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Data data = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.selector_layout,parent,false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(data.getTitle());
        CardView card = (CardView)convertView.findViewById(R.id.card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, calculationActivity.class).putExtra("Position",position +10));
                listener.clicked();
            }
        });

        return convertView;
    }


}


