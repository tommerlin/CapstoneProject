package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    EditText userFname, userLname, userLicenceBy, userLicenceIssueDate, userLicenceExpiryDate, userLicenceNumber, userDob;
    Button next;

    final Calendar myCalendar = Calendar.getInstance();
    Date pickedIssueDate, pickedExpiryDate, pickedDob;
    ArrayList<UserBasicDetails> userBasicDetails = new ArrayList<UserBasicDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userFname = findViewById(R.id.textView_fname);
        userLname = findViewById(R.id.textView_lname);
        userLicenceBy = findViewById(R.id.textView_licenceby);
        userLicenceIssueDate = findViewById(R.id.textView_issueDate);
        userLicenceExpiryDate = findViewById(R.id.textView_expiryDate);
        userLicenceNumber = findViewById(R.id.textView_licenceNum);
        userDob = findViewById(R.id.textView_dob);

        next = findViewById(R.id.signin_next);

        //date picker
        DatePickerDialog.OnDateSetListener issue_date, expiry_date, birth_date;

        //issue date
        issue_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                pickedIssueDate = myCalendar.getTime();
                updateIssueDateLabel();
            }

        };
        //expiry date
        expiry_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                pickedExpiryDate = myCalendar.getTime();
                updateExpiryDateLabel();
            }

        };
        //birth date
        birth_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                pickedDob = myCalendar.getTime();
                updateBirthDateLabel();
            }

        };



        //calendar
        userLicenceIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpActivity.this, issue_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        userLicenceExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpActivity.this, expiry_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        userDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpActivity.this, birth_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBasicDetails.add(new UserBasicDetails(userFname.getText().toString(),userLname.getText().toString(),userLicenceBy.getText().toString(),userLicenceNumber.getText().toString(),pickedIssueDate,pickedExpiryDate,pickedDob));
                nextPage(userBasicDetails);
                userBasicDetails.removeAll(userBasicDetails);
            }
        });
    }

    private void nextPage(ArrayList<UserBasicDetails> userBasicDetails) {
        Intent intent = new Intent(SignUpActivity.this, SignUpActivity2.class);
        intent.putExtra("selectedData", userBasicDetails);
        startActivityForResult(intent,0);
    }


    private void updateIssueDateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        userLicenceIssueDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateExpiryDateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        userLicenceExpiryDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateBirthDateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        userDob.setText(sdf.format(myCalendar.getTime()));
    }

}