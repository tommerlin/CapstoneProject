package com.example.capstoneproject.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneproject.Bookings;
import com.example.capstoneproject.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class BookingDetailsActivity extends AppCompatActivity {
    TextView location, type, name, auto, pickup, return_, user, email, phone, rental, tax, total;
    ImageView veh;
    Button c_pickup, c_return;
    Bookings theBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            theBooking = (Bookings) bundle.get("data");
            Intent intent = getIntent();
        }

        location = findViewById(R.id.location);
        type = findViewById(R.id.type);
        name = findViewById(R.id.vehname);
        auto = findViewById(R.id.auto);
        pickup = findViewById(R.id.pickup);
        return_ = findViewById(R.id.returnd);
        user = findViewById(R.id.user);
        email = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);
        veh = findViewById(R.id.vehicle);
        rental = findViewById(R.id.rental);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        c_pickup = findViewById(R.id.pickBtn);
        c_return = findViewById(R.id.returnBtn);

        fillLabels();

        c_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        c_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void fillLabels() {
        location.setText(theBooking.getTheLocation());
        pickup.setText(DateFormat.getDateTimeInstance().format(theBooking.getPickup()));
        return_.setText(DateFormat.getDateTimeInstance().format(theBooking.getReturnd()));
        type.setText(theBooking.getvType());
        name.setText(theBooking.getvName());
        auto.setText(theBooking.getvAuto());
        user.setText(theBooking.getUserName());
        email.setText(theBooking.getEmail());
        phone.setText(Long.toString(theBooking.getPhoneNum()));
        rental.setText(Double.toString(theBooking.getRental()));
        tax.setText(Double.toString(theBooking.getTax()));
        total.setText(Double.toString(theBooking.getTotal()));
        Picasso.with(this).load(theBooking.getImg()).fit().into(veh);

    }
}