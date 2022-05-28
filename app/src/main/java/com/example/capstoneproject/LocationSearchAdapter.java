package com.example.capstoneproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.admin.LocationClass;
import com.example.capstoneproject.admin.VehicleAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class LocationSearchAdapter extends FirebaseRecyclerAdapter<LocationClass, LocationSearchAdapter.myViewHolder > {
    private OnLocationClickListener mOnLocationClickListener;

    public LocationSearchAdapter(@NonNull FirebaseRecyclerOptions<LocationClass> options, OnLocationClickListener onLocationClickListener) {
        super(options);
        this.mOnLocationClickListener = onLocationClickListener;
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull LocationClass locationClass) {
        myViewHolder.placeName.setText(locationClass.getLocName());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_single_row,parent,false);

        return new myViewHolder(view, mOnLocationClickListener);
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView placeName;
        OnLocationClickListener onLocationClickListener;

        public myViewHolder(@NonNull View itemView, OnLocationClickListener onLocationClickListener) {
            super(itemView);
            placeName = (TextView) itemView.findViewById(R.id.txtPlace);
            this.onLocationClickListener = onLocationClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onLocationClickListener.onLocationClick(getAdapterPosition() );
        }
    }
    public interface OnLocationClickListener{
        void onLocationClick(int position);
    }
}
