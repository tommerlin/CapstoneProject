package com.example.capstoneproject.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstoneproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddVehicleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner category_list_sp, locationList;
    EditText veh_name, veh_rent, veh_tax, veh_no_people, veh_no_luggage, veh_count;
    CheckBox auto;
    ImageView veh_img;
    RadioButton standard, intermediate, compact, radioButton;
    Button add_vehicle;
    RadioGroup radioGroup;
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    String selectedCategory = null;
    String selectedLocation = null;
    boolean auto_check = false;
    int SELECT_PICTURE = 200;
    Uri selectedImageUri;
    String type = null;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category_list_sp = findViewById(R.id.spCategory);
        veh_name = findViewById(R.id.txtVName);
        veh_rent = findViewById(R.id.txtRent);
        veh_tax = findViewById(R.id.txtTax);
        veh_no_people = findViewById(R.id.txtPeople);
        veh_no_luggage = findViewById(R.id.txtLuggage);
        veh_count = findViewById(R.id.txtNoVeh);

        locationList = findViewById(R.id.spinnerLocation);

        auto = findViewById(R.id.checkAuto);

        standard = findViewById(R.id.rdbStandard);
        intermediate = findViewById(R.id.rdbInter);
        compact = findViewById(R.id.rdbCompact);

        veh_img = findViewById(R.id.imgCar);

        add_vehicle = findViewById(R.id.btnAddVehicle);

        category_list_sp.setOnItemSelectedListener(this);
        locationList.setOnItemSelectedListener(this);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Categories");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    categories.add(snapshot1.child("category").getValue().toString());
                }
                setAdapterCategory();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Locations");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    locations.add(snapshot1.child("locName").getValue().toString());
                }
                setAdapterLocations();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicle();
            }
        });
        veh_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

    private void setAdapterLocations() {
        ArrayAdapter<String> locListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, locations);
        locListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationList.setAdapter(locListAdapter);
    }

    private void imageChooser() {
        // create an instance of the intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();

                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    veh_img.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void setAdapterCategory() {
        ArrayAdapter<String> cateListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        cateListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_list_sp.setAdapter(cateListAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.spCategory)
        {
            selectedCategory = parent.getItemAtPosition(position).toString();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+selectedCategory);
        }
        else if(spin2.getId() == R.id.spinnerLocation)
        {
            selectedLocation = parent.getItemAtPosition(position).toString();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+selectedLocation);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void addVehicle() {
        String name = veh_name.getText().toString().trim();

        double rent = Double.valueOf(veh_rent.getText().toString().trim());
        double tax = Double.valueOf(veh_tax.getText().toString().trim());
        int people = Integer.valueOf(veh_no_people.getText().toString().trim());
        int luggage = Integer.valueOf(veh_no_luggage.getText().toString().trim());
        int count = Integer.valueOf(veh_count.getText().toString().trim());


        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(AddVehicleActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else{
            type = radioButton.getText().toString();
        }

        if(auto.isChecked()){
            auto_check = true;
        }
        else{
            auto_check = false;
        }

//        if(selectedImageUri != null){
//            DatabaseReference databaseReference = reference.child(System.currentTimeMillis()
//            + "." + getFileExtension(selectedImageUri));
//        } else{
//            Toast.makeText(AddVehicleActivity.this,"No image selected", Toast.LENGTH_SHORT).show();
//        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("ImageUrl");
        reference = firebaseDatabase.getReference("Vehicles");
        System.out.println("____________________________"+selectedLocation);

        StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(selectedImageUri));
        fileReference.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Vehicle vehicle = new Vehicle(auto_check,selectedCategory,count, String.valueOf(uri), luggage,name, people,rent, tax, type, selectedLocation );
                                reference.push().setValue(vehicle)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(AddVehicleActivity.this,"Vehicle has been added successfully!",Toast.LENGTH_LONG).show();
                                                    veh_name.setText("");
                                                    veh_rent.setText("");
                                                    veh_tax.setText("");
                                                    veh_no_people.setText("");
                                                    veh_no_luggage.setText("");
                                                    veh_count.setText("");
                                                }
                                                else{
                                                    Toast.makeText(AddVehicleActivity.this,"Failed to add Vehicle! Try again!",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        });

                    }
                });




    }
}