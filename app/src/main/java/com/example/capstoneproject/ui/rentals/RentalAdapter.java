package com.example.capstoneproject.ui.rentals;

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
import com.example.capstoneproject.UserVehicleAdapter;
import com.example.capstoneproject.admin.BookingListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Bookings> bookingList;

    public RentalAdapter(Context context){
        this.context = context;
    }

    public void setUserList(ArrayList<Bookings> bookingList){
        this.bookingList = bookingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RentalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rental_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentalAdapter.MyViewHolder holder, int position) {
        holder.txtViewName.setText(this.bookingList.get(position).getvName());
        System.out.println(bookingList.get(position).getvName());
    }

    @Override
    public int getItemCount() {
        return this.bookingList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewName;

        public MyViewHolder(View view){
            super(view);
            txtViewName = view.findViewById(R.id.renter_name);

        }

    }
}

