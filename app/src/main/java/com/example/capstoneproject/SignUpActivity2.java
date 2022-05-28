package com.example.capstoneproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class SignUpActivity2 extends AppCompatActivity {
    EditText userEmail, userPhNumber, userPassword, userCpass;
    Button signUp;
    ArrayList<UserBasicDetails> selectedData = new ArrayList<UserBasicDetails>();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        mAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.textView_email);
        userPhNumber = findViewById(R.id.textView_phNum);
        userPassword = findViewById(R.id.textView_password);
        userCpass = findViewById(R.id.textView_cPass);
        signUp = findViewById(R.id.join);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedData = (ArrayList<UserBasicDetails>) bundle.get("selectedData");
            Intent intent = getIntent();
//            System.out.println("****************************"+selectedData.get(0).getFirstName());
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToDb();
            }
        });
    }

    private void addUserToDb() {
        String fname = selectedData.get(0).getFirstName();
        String lname = selectedData.get(0).getLastName();
        String licenceNum = selectedData.get(0).getLicenceNumber();
        String issuedBy = selectedData.get(0).getLicenceIssuedBy();
        Date issueDate = selectedData.get(0).getLicenceIssueDate();
        Date expiryDate = selectedData.get(0).getLicenceExpiryDate();
        Date birth = selectedData.get(0).getDateOfBirth();


        String email = userEmail.getText().toString().trim();
        long phone = Long.valueOf(userPhNumber.getText().toString());
        String cPass = userCpass.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(email.isEmpty()){
            userEmail.setError("Email is required!");
            userEmail.requestFocus();
            return;
        }
        if(cPass.isEmpty()){
            userCpass.setError("Confirm  password!");
            userCpass.requestFocus();
            return;
        }
        if(password.isEmpty()){
            userPassword.setError("Password is required!");
            userPassword.requestFocus();
            return;
        }
        if (!(password.equals(cPass))){
            userCpass.setError("Password doesn't match!");
            userCpass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please provide valid email!");
            userEmail.requestFocus();
            return;
        }
        if(password.length()< 6){
            userPassword.setError("Password must contain at least 8 characters!");
            userPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fname,lname,licenceNum,issuedBy,email,issueDate,expiryDate,birth,phone);

//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user_ = mAuth.getCurrentUser();
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            Toast.makeText(SignUpActivity2.this, "Registered successfully! Please check your email for verification!", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(SignUpActivity2.this, LoginActivity.class);
                                                            startActivity(intent);
                                                        }
                                                        else{
                                                            Toast.makeText(SignUpActivity2.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                                        }
                                                    }
                                                });

                                    }
                                    else{
                                        Toast.makeText(SignUpActivity2.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(SignUpActivity2.this,"Failed to register!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}