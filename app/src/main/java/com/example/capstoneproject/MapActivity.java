package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.example.capstoneproject.admin.LocationClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap gMap;
    private LocationClass location;
    public Location currentLocation;
    private static final int REQUEST_CODE = 101;
    private ArrayList<LocationClass> locationArrayList;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationClass selectedLocationData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        System.out.println("______________________________fetchCurrent");
        fetchCurrentLocation();
//
//        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            getCurrentLocation();
//        } else {
//            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//        }

    }

    private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location_) {
                if(location_ != null){
                    currentLocation = location_;
                    System.out.println("______________________________"+currentLocation);
                    mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapActivity.this);
                }
            }
        });

    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        selectedLocationData = locationArrayList.get((Integer) marker.getTag());
        goToReservationPage();
        return false;
    }

    private void goToReservationPage() {
        Intent intent = new Intent(this, UserVehicleListActivity.class);
        intent.putExtra("location", selectedLocationData);
        startActivity(intent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions options = new MarkerOptions().position(latLng).title("Im here!!");
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(1500) // radius in meters
                .strokeColor(Color.WHITE)
                .fillColor(Color.parseColor("#2196F3")) //The stroke (border) is blue
                .strokeWidth(4);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        gMap.addCircle(circleOptions);

        firebaseData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("---------------------------------------onActivityResult!");
//        updateMap();
//        selectedLocationData = null;
    }

    private void updateMap() {
//        gMap.clear();
        int index = 0;
        System.out.println("############################reached updateMap");
        for (LocationClass locData : locationArrayList) {
            System.out.println("______________________________"+locData.getLocLat());
            System.out.println("------------------------------"+locData.getLocLong());
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(locData.getLocLat(), locData.getLocLong()));
            markerOptions.title(locData.getLocName());
            Marker marker = gMap.addMarker(markerOptions);
            marker.setTag(index++);
            marker.showInfoWindow();
        }
        gMap.setOnMarkerClickListener(this);
    }

    private void firebaseData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Locations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationArrayList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    LocationClass result =  snapshot1.getValue(LocationClass.class);
                    locationArrayList.add(result);
                    System.out.println("+++++++++++++++++++++++++++++"+locationArrayList);
                }
                updateMap();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}