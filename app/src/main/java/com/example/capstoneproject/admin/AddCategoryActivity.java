package com.example.capstoneproject.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneproject.LoginActivity;
import com.example.capstoneproject.R;
import com.example.capstoneproject.SignUpActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class AddCategoryActivity extends AppCompatActivity {
    EditText category_name;
    Button add_category;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference1;
    Random rand = new Random(); //instance of random class
    int upperbound = 20;
    //generate random values from 0-24
    String int_random = String.valueOf(rand.nextInt(upperbound));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category_name = findViewById(R.id.txtCategory);
        add_category = findViewById(R.id.addCat);

        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });
    }

    private void addCategory() {
        reference1 = firebaseDatabase.getInstance().getReference("Categories");
//        reference1 = firebaseDatabase.getReference("Categories");
        String cat = category_name.getText().toString().trim();
        HashMap<String, String> category_map = new HashMap<>();

        category_map.put("category", cat);

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&"+category_map);

        System.out.println("entered addCategory!!!!!!!!!!!!!!");




//        firebaseDatabase.child("0001").setValue(category_map)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(AddCategoryActivity.this,"entered onComplete!!!!!!!!!!!",Toast.LENGTH_LONG).show();
//
//                if(task.isSuccessful()){
//                    Toast.makeText(AddCategoryActivity.this,"Category has been added successfully!",Toast.LENGTH_LONG).show();
//                    category_name.setText("");
//                }
//                else{
//                    Toast.makeText(AddCategoryActivity.this,"Failed to add Category! Try again!",Toast.LENGTH_LONG).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(AddCategoryActivity.this,"Failed to add Category! Try again!",Toast.LENGTH_LONG).show();
//
//            }
//        });
        reference1.push().setValue(category_map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(AddCategoryActivity.this,"Category has been added successfully!",Toast.LENGTH_LONG).show();
                    category_name.setText("");
                }
                else{
                    Toast.makeText(AddCategoryActivity.this,"Failed to add Category! Try again!",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCategoryActivity.this,"Failed to add Category! Try again!",Toast.LENGTH_LONG).show();
            }
        });
    }
}