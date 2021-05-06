package com.waqazystudios.machiningcalculator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waqazystudios.machiningcalculator.Models.DataBase_data;
import com.waqazystudios.machiningcalculator.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder1> {
    public View view;
    private Context mContext;
    private ArrayList<DataBase_data> list;
    private onDelete onDeleteListener;

    public interface onDelete{
        public void Delete(int position);
    }

    public HistoryAdapter(Context context , ArrayList<DataBase_data> list){
        this.list = list;
        this.mContext = context;
    }

    public class ViewHolder1 extends  RecyclerView.ViewHolder{
        TextView valueTextView , finalValueTextView , valueUnitsTextView;
        ImageButton deleteBtn;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            valueTextView = itemView.findViewById(R.id.valueNameTextView);
            finalValueTextView = itemView.findViewById(R.id.valueTextView);
            valueUnitsTextView = itemView.findViewById(R.id.valueUnitsTextView);
            deleteBtn = itemView.findViewById(R.id.deletebtn);

        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder1 holder) {
        super.onViewAttachedToWindow(holder);
        onDeleteListener = (HistoryAdapter.onDelete)mContext;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.history_container,parent,false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder1 holder, int position) {
        DataBase_data data =list.get(position);
        holder.valueTextView.setText(data.getTitle());
        holder.finalValueTextView.setText(String.valueOf(data.getValue()));
        holder.valueUnitsTextView.setText(data.getUnits());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onDeleteListener.Delete(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}