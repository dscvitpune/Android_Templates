package com.example.bookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*This is the adapter for Main activity, which shows the bookings*/

public class BookingAdapter extends RecyclerView.Adapter<BookingViewHolder>{

    private Context mContext;
    private List<BookingClass> bookingList;

    public BookingAdapter(Context mContext, List<BookingClass> bookingList) {
        this.mContext = mContext;
        this.bookingList = bookingList;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item_layout, parent, false);

        return new BookingViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {

        long dateTime = bookingList.get(position).getDateTime();

        Date dates = new Date(dateTime);

        DateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy");
        DateFormat simpleTime = new SimpleDateFormat("HH:mm");

        String date = simpleDate.format(dates);
        String time = simpleTime.format(dates);

        holder.tvBookingDate.setText(date);
        holder.tvBookingTime.setText(time);
        holder.tvBookingName.setText(bookingList.get(position).getClientName());
        holder.tvBookingAddress.setText(bookingList.get(position).getClientAddress());

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}

class BookingViewHolder extends RecyclerView.ViewHolder{

    TextView tvBookingDate, tvBookingTime, tvBookingName, tvBookingAddress;
    CardView bookingCardView;

    public BookingViewHolder(View itemView) {
        super(itemView);

        tvBookingDate = itemView.findViewById(R.id.tvBookingDate);
        tvBookingTime = itemView.findViewById(R.id.tvBookingTime);
        tvBookingName = itemView.findViewById(R.id.tvBookingName);
        tvBookingAddress = itemView.findViewById(R.id.tvBookingAddress);

        bookingCardView = itemView.findViewById(R.id.bookingCardView);
    }
}
