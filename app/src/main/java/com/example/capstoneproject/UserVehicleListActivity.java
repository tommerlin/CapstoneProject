package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capstoneproject.admin.LocationClass;
import com.example.capstoneproject.admin.Vehicle;
import com.example.capstoneproject.UserVehicleAdapter;
import com.example.capstoneproject.admin.VehicleAdapter;
import com.example.capstoneproject.admin.VehicleListActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserVehicleListActivity extends AppCompatActivity implements UserVehicleAdapter.OnRowListener{

    private RecyclerView recyclerVehicle;
    private UserVehicleAdapter userVehicleAdapter;
    private Vehicle vehicle;
    private ArrayList<Vehicle> vehicleArrayList;
    LocationClass theLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vehicle_list);

        recyclerVehicle = findViewById(R.id.recyclerUser);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            theLocation = (LocationClass) bundle.get("location");
            Intent intent = getIntent();
        }
        System.out.println("+++++++++++++++++++++++++++++"+theLocation.getLocName());

        Query query = FirebaseDatabase.getInstance().getReference("Vehicles").orderByChild("location").equalTo(theLocation.getLocName());
        query.addListenerForSingleValueEvent(valueEventListener);
    }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleArrayList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Vehicle result =  snapshot1.getValue(Vehicle.class);
                    vehicleArrayList.add(result);
                    System.out.println("+++++++++++++++++++++++++++++"+vehicleArrayList);
                }
                setAdapterUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


    private void setAdapterUser() {
        UserVehicleAdapter userVehicleAdapter = new UserVehicleAdapter(getApplicationContext(),vehicleArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerVehicle.setLayoutManager(layoutManager);
        recyclerVehicle.setItemAnimator(new DefaultItemAnimator());
        recyclerVehicle.setAdapter(userVehicleAdapter);

        System.out.println("=============================recyclerview refreshed"+vehicleArrayList);
    }

    @Override
    public void onRowClick(int position) {
//        Toast.makeText(UserVehicleListActivity.this,"Click working!!!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ReservationActivity.class);
        intent.putExtra("data", (vehicleArrayList.get(position)));
        startActivity(intent);

    }
}