package com.example.capstoneproject.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.capstoneproject.Bookings;
import com.example.capstoneproject.R;
import com.example.capstoneproject.ReservationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingListActivity extends AppCompatActivity implements BookingListAdapter.OnRowListener{
    RecyclerView reView;
    private ArrayList<Bookings> bookingsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        reView = findViewById(R.id.bookinsRecycler);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingsArrayList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Bookings result =  snapshot1.getValue(Bookings.class);
                    bookingsArrayList.add(result);
                    System.out.println("+++++++++++++++++++++++++++++"+bookingsArrayList);
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setAdapter(){
        BookingListAdapter bookingAdapter = new BookingListAdapter(getApplicationContext(),bookingsArrayList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reView.setLayoutManager(layoutManager);
        reView.setItemAnimator(new DefaultItemAnimator());
        reView.setAdapter(bookingAdapter);

        System.out.println("=============================recyclerview refreshed");
    }

    @Override
    public void onRowClick(int position) {
        System.out.println("=============================starting intent"+position);
        Intent intent = new Intent(this, BookingDetailsActivity.class);
        intent.putExtra("data", (bookingsArrayList.get(position)));
        startActivity(intent);
    }
}