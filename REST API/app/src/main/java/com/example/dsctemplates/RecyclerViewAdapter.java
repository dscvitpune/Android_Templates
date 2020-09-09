package com.example.dsctemplates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Record> recordList;

    public RecyclerViewAdapter(Context context, List<Record> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.record, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.timestamp.setText(record.getTimestamp());
        holder.arrival_date.setText(record.getArrival_date());
        holder.market.setText(record.getMarket());
        holder.district.setText(record.getDistrict());
        holder.state.setText(record.getState());
        holder.variety.setText(record.getVariety());
        holder.max_price.setText(record.getMax_price());
        holder.min_price.setText(record.getMin_price());
        holder.modal_price.setText(record.getModal_price());
        holder.commodity.setText(record.getCommodity());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView timestamp, state, district, market, variety, commodity, arrival_date, min_price, max_price, modal_price;


        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.timestamp2);
            state = itemView.findViewById(R.id.state2);
            district = itemView.findViewById(R.id.district2);
            market = itemView.findViewById(R.id.market2);
            variety = itemView.findViewById(R.id.variety2);
            commodity = itemView.findViewById(R.id.commodity2);
            arrival_date = itemView.findViewById(R.id.arrival_date2);
            min_price = itemView.findViewById(R.id.min_price2);
            max_price = itemView.findViewById(R.id.max_price2);
            modal_price = itemView.findViewById(R.id.modal_price2);
        }
    }
}
