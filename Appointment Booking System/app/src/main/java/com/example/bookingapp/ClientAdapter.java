package com.example.bookingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;

/*This is the adapter for client recyclerView*/

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder>{

    private Context mContext;
    private List<ClientClass> clientList;

    public ClientAdapter(Context mContext, List<ClientClass> clientList) {
        this.mContext = mContext;
        this.clientList = clientList;
    }

    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);

        return new ClientViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, final int position) {

        holder.tvClientName.setText(clientList.get(position).getClientName());
        holder.tvClientAddress.setText(clientList.get(position).getClientAddress());
        holder.tvClientEmail.setText(clientList.get(position).getClientEmail());

        holder.clientCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Client selected, now create booking

                Intent intent = new Intent(mContext, CreateBookingActivity.class);
                intent.putExtra("clientName", clientList.get(position).getClientName());
                intent.putExtra("clientAddress", clientList.get(position).getClientAddress());
                intent.putExtra("clientEmail", clientList.get(position).getClientEmail());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }
}

class ClientViewHolder extends RecyclerView.ViewHolder{

    TextView tvClientName, tvClientAddress, tvClientEmail;
    CardView clientCardView;

    public ClientViewHolder(View itemView) {
        super(itemView);
        tvClientName = itemView.findViewById(R.id.tvClientName);
        tvClientAddress = itemView.findViewById(R.id.tvClientAddress);
        tvClientEmail = itemView.findViewById(R.id.tvClientEmail);

        clientCardView = itemView.findViewById(R.id.clientCardView);

    }
}