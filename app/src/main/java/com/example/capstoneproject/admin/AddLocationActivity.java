package com.example.capstoneproject.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddLocationActivity extends AppCompatActivity {
    EditText txtName, txtPostal, txtLatitude, txtLongitude, txtPhone;
    Button addLocation;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = findViewById(R.id.txtLocaName);
        txtPostal = findViewById(R.id.txtPostal);
        txtLatitude = findViewById(R.id.txtLat);
        txtLongitude = findViewById(R.id.txtLong);
        txtPhone = findViewById(R.id.txtPhone);

        addLocation = findViewById(R.id.btnAddLoc);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });
    }

    private void addLocation() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Locations");

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^addLoc");

        String name = txtName.getText().toString().trim();
        String postal = txtPostal.getText().toString().trim();
        float lat = Float.valueOf(txtLatitude.getText().toString().trim());
        float longi = Float.valueOf(txtLongitude.getText().toString().trim());
        long phone = Long.valueOf(txtPhone.getText().toString().trim());

        LocationClass loc = new LocationClass(name,postal,lat,longi,phone);

        reference.push().setValue(loc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^success");
                            Toast.makeText(AddLocationActivity.this,"Location has been added successfully!",Toast.LENGTH_LONG).show();
                            txtName.setText("");
                            txtPostal.setText("");
                            txtLatitude.setText("");
                            txtLongitude.setText("");
                            txtPhone.setText("");
                        }
                        else{
                            Toast.makeText(AddLocationActivity.this,"Failed to add Location! Try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}