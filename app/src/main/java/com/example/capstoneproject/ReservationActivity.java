package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.capstoneproject.admin.LocationClass;
import com.example.capstoneproject.admin.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReservationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView locName, locPostal, locaPhone;
    TextView pickupDay, returnDay;
    Vehicle theVehicle;
    Date pickedPickup, pickedReturn;
    String[] ageGroup = { "17", "21", "25+"};
    Button contin;

    FirebaseUser user;
    String uid;

    String email = null ;
    String userName = null;
    long phNo = 0;


    private ArrayList<LocationClass> locationArrayList;
    final Calendar myCalendar = Calendar.getInstance();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();


        locName = findViewById(R.id.txtPlaceName);
        locPostal = findViewById(R.id.txtPlacePostal);
        locaPhone = findViewById(R.id.txtPlaceNumber);
        pickupDay = findViewById(R.id.txtPickup);
        returnDay = findViewById(R.id.txtReturn);
        contin = findViewById(R.id.btnReserContinue);

        Spinner spin = (Spinner) findViewById(R.id.spinnerAge);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ageGroup);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        //date picker
        DatePickerDialog.OnDateSetListener pickup_date, return_date;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            theVehicle = (Vehicle) bundle.get("data");
            Intent intent = getIntent();
        }

        //firebase
        firebaseData();

        //datepicker listener
        pickup_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                pickedPickup = myCalendar.getTime();
                updatePickupLabel();
            }
        };
        return_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                pickedReturn = myCalendar.getTime();
                updateReturnLabel();
            }
        };

        //calendar
        pickupDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReservationActivity.this, pickup_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        returnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReservationActivity.this, return_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToReviewList();
            }
        });

    }

    private void goToReviewList() {

        String location = theVehicle.getLocation();
        String type = theVehicle.getType();
        String vehicle = theVehicle.getName();
        String auto;
        if(theVehicle.getAutomatic()){
            auto = "Automatic";
        } else{
            auto = "Manual";
        }
        double rental = theVehicle.getRent();
        double tax = theVehicle.getTax();
        String img = theVehicle.getImageUrl();
        long diffInMillies = Math.abs(pickedReturn.getTime() - pickedPickup.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);


        double total = (rental + tax) * (diff);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+total);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+pickedPickup);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child(uid).child("firstName").getValue(String.class);
                email = snapshot.child(uid).child("email").getValue(String.class);
                phNo = snapshot.child(uid).child("phNumber").getValue(long.class);

                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+userName + email);
                Bookings bookingDetails = new Bookings(location,type,vehicle,auto,rental,tax,total,userName, email, phNo, pickedPickup, pickedReturn, img);
                Intent intent = new Intent(ReservationActivity.this, UserReviewActivity.class);
                intent.putExtra("bookingData", bookingDetails);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void updateReturnLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        returnDay.setText(sdf.format(myCalendar.getTime()));
    }

    private void updatePickupLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        pickupDay.setText(sdf.format(myCalendar.getTime()));
    }

    private void firebaseData() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Locations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationArrayList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    LocationClass result =  snapshot1.getValue(LocationClass.class);
                    locationArrayList.add(result);
                    System.out.println("+++++++++++++++++++++++++++++"+locationArrayList);
                }
                locName.setText(theVehicle.getLocation());
                locPostal.setText("N5X 4L5");
                locaPhone.setText("+1 3726-384-234");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}