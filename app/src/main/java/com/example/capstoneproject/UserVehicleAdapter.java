package com.example.capstoneproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.admin.Vehicle;
import com.example.capstoneproject.UserVehicleAdapter;
import com.example.capstoneproject.admin.VehicleAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserVehicleAdapter extends RecyclerView.Adapter<UserVehicleAdapter.MyViewHolder>{
    private ArrayList<Vehicle> vehicleList;
    private UserVehicleAdapter.OnRowListener xOnRowListener;
    private Context context;

    public UserVehicleAdapter(Context context,ArrayList<Vehicle> vehicleList, UserVehicleAdapter.OnRowListener onRowListener){
        this.vehicleList = vehicleList;
        this.context = context;
        this.xOnRowListener = onRowListener;
    }
    @NonNull
    @Override
    public UserVehicleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_veh_list_row, parent, false);
        return new UserVehicleAdapter.MyViewHolder(itemView,xOnRowListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVehicleAdapter.MyViewHolder holder, int position) {
        String type = vehicleList.get(position).getType();
        String name = vehicleList.get(position).getName();
        Boolean auto = vehicleList.get(position).getAutomatic();
        Double price = vehicleList.get(position).getRent();
        String img = vehicleList.get(position).getImageUrl().toString();

        holder.txtType.setText(type);
        holder.txtName.setText(name);
        if(auto == true){
            holder.txtAuto.setText("Automatic");
        } else{
            holder.txtAuto.setText("Manual");
        }
        holder.txtPrice.setText(price.toString());
//        holder.imgCar.setImageURI(Uri.parse(img));
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+img);
        Picasso.with(context).load(img).fit().into(holder.imgCar);

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtType, txtName, txtAuto, txtPrice;
        private ImageView imgCar;
        UserVehicleAdapter.OnRowListener onRowListener;

        public MyViewHolder(View view, UserVehicleAdapter.OnRowListener onRowListener){
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
