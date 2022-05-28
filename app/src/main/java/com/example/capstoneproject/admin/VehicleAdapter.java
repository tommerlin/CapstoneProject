package com.example.capstoneproject.admin;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.admin.Vehicle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {
    private ArrayList<Vehicle> vehicleList;
    private OnRowListener xOnRowListener;
    private Context context;

    public VehicleAdapter(Context context,ArrayList<Vehicle> vehicleList, OnRowListener onRowListener){
        this.vehicleList = vehicleList;
        this.context = context;
        this.xOnRowListener = onRowListener;
    }
    @NonNull
    @Override
    public VehicleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(itemView,xOnRowListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleAdapter.MyViewHolder holder, int position) {
        String type = vehicleList.get(position).getType();
        String name = vehicleList.get(position).getName();
        Boolean auto = vehicleList.get(position).getAutomatic();
        Double price = vehicleList.get(position).getRent();
        String img = vehicleList.get(position).getImageUrl();

        holder.txtType.setText(type);
        holder.txtName.setText(name);
        if(auto == true ){
            holder.txtAuto.setText("Automatic");
        } else{
            holder.txtAuto.setText("Manual");
        }

        holder.txtPrice.setText(price.toString());
        Picasso.with(context).load(img).fit().into(holder.imgCar);

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtType, txtName, txtAuto, txtPrice;
        private ImageView imgCar;
        OnRowListener onRowListener;

        public MyViewHolder(View view, OnRowListener onRowListener){
            super(view);
            txtName = view.findViewById(R.id.rTxtName);
            txtType  = view.findViewById(R.id.rTxtType);
            txtAuto  = view.findViewById(R.id.rTxtAuto);
            txtPrice  = view.findViewById(R.id.rtxtPrice);
            imgCar = view.findViewById(R.id.rImgCar);


            this.onRowListener = onRowListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println(getAdapterPosition());
            onRowListener.onRowClick(getAdapterPosition());
        }
    }

    public interface OnRowListener{
        void onRowClick(int position);
    }


}

