package com.example.capstoneproject.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.capstoneproject.LoginActivity;
import com.example.capstoneproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHomeActivity extends AppCompatActivity {

    FirebaseAuth uAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        uAuth = FirebaseAuth.getInstance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_home, menu);

        // first parameter is the file for icon and second one is menu
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_location:
                Intent intent1 = new Intent(this, AddLocationActivity.class);
                startActivity(intent1);
                return true;
            case R.id.add_category:
                Intent intent2 = new Intent(this, AddCategoryActivity.class);
                startActivity(intent2);
                return true;
            case R.id.add_vehicle:
                Intent intent3 = new Intent(this, AddVehicleActivity.class);
                startActivity(intent3);
                return true;
            case R.id.view_vehicles:
                Intent intent4 = new Intent(this, VehicleListActivity.class);
                startActivity(intent4);
                return true;
            case R.id.bookings:
                Intent intent5 = new Intent(this, BookingListActivity.class);
                startActivity(intent5);
                return true;
            case R.id.logout:
                uAuth.signOut();
                Intent intent6 = new Intent(this, LoginActivity.class);
                startActivity(intent6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}