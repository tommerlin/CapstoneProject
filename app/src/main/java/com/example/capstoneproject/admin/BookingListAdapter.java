package com.example.capstoneproject.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.Bookings;
import com.example.capstoneproject.R;

import java.util.ArrayList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.MyViewHolder>{
    private ArrayList<Bookings> bookingList;
    private BookingListAdapter.OnRowListener xOnRowListener;
    private Context context;

    public BookingListAdapter(Context context, ArrayList<Bookings> bookingList, OnRowListener bOnRowListener){
        this.bookingList = bookingList;
        this.context = context;
        this.xOnRowListener = bOnRowListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_row, parent, false);
        return new MyViewHolder(itemView,xOnRowListener);

    }

    @Override
    public void onBindViewHolder(@NonNull BookingListAdapter.MyViewHolder holder, int position) {
        String name = bookingList.get(position).getUserName();

        holder.name.setText(name);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        BookingListAdapter.OnRowListener onRowListener;

        public MyViewHolder(View view, BookingListAdapter.OnRowListener onRowListener){
            super(view);
            name = view.findViewById(R.id.user_name_);


            this.onRowListener = onRowListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println(getAdapterPosition());
            onRowListener.onRowClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public interface OnRowListener{
        void onRowClick(int position);
    }
}
