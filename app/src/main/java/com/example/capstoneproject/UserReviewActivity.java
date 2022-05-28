package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;

public class UserReviewActivity extends AppCompatActivity {
    TextView location, pickupDay, returnDay, type, vehName, auto, rental, tax, total;
    ImageView imgCar;
    Button bookNow;
    Bookings details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            details = (Bookings) bundle.get("bookingData");
            Intent intent = getIntent();
            System.out.println(details.getReturnd());
        }

        location = findViewById(R.id.location);
        pickupDay = findViewById(R.id.pickup);
        returnDay = findViewById(R.id.returnd);
        type = findViewById(R.id.type);
        vehName = findViewById(R.id.vehname);
        auto = findViewById(R.id.auto);
        rental = findViewById(R.id.rental);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        imgCar = findViewById(R.id.vehicle);
        bookNow = findViewById(R.id.pickBtn);
        
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book();
            }
        });

        System.out.println("%%%%%%%%%%%%%%%%%"+details.getPickup());

        location.setText(details.getTheLocation());
        pickupDay.setText(DateFormat.getDateTimeInstance().format(details.getPickup()));
        returnDay.setText(DateFormat.getDateTimeInstance().format(details.getReturnd()));
        type.setText(details.getvType());
        vehName.setText(details.getvName());
        auto.setText(details.getvAuto());
        rental.setText(Double.toString(details.getRental()));
        tax.setText(Double.toString(details.getTax()));
        total.setText(Double.toString(details.getTotal()));
        Picasso.with(this).load(details.getImg()).fit().into(imgCar);
    }

    private void book() {
        DatabaseReference reference;

        reference = FirebaseDatabase.getInstance().getReference("Bookings");
        reference.push().setValue(details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UserReviewActivity.this,"Booking has been done.",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(UserReviewActivity.this,"Failed to book the vehicle! Try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserReviewActivity.this,"Failed to book the vehicle! Try again!",Toast.LENGTH_LONG).show();
            }
        });

    }
}