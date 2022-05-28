package com.example.capstoneproject.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.capstoneproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VehicleListActivity extends AppCompatActivity implements VehicleAdapter.OnRowListener{
    private RecyclerView recyclerVehicleList;
    private VehicleAdapter vehicleAdapter;
    private Vehicle vehicle;
    private ArrayList<Vehicle> vehicleArrayList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        recyclerVehicleList = findViewById(R.id.recyclerVehicle);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vehicles");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleArrayList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Vehicle result =  snapshot1.getValue(Vehicle.class);
                    vehicleArrayList.add(result);
                    System.out.println("+++++++++++++++++++++++++++++"+vehicleArrayList);
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void setAdapter(){
        VehicleAdapter vehicleAdapter = new VehicleAdapter(getApplicationContext(),vehicleArrayList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerVehicleList.setLayoutManager(layoutManager);
        recyclerVehicleList.setItemAnimator(new DefaultItemAnimator());
        recyclerVehicleList.setAdapter(vehicleAdapter);

        System.out.println("=============================recyclerview refreshed");
    }

    @Override
    public void onRowClick(int position) {
        Toast.makeText(VehicleListActivity.this,"Click working!!!",Toast.LENGTH_LONG).show();
    }
}