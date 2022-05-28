package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.SearchView;
import android.widget.Toast;

import com.example.capstoneproject.admin.LocationClass;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SearchLocationActivity extends AppCompatActivity implements LocationSearchAdapter.OnLocationClickListener{

    RecyclerView recyclerView;
    LocationSearchAdapter locationSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.searchRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Location");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String query) {
        FirebaseRecyclerOptions<LocationClass> options = new FirebaseRecyclerOptions.Builder<LocationClass>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Locations").orderByChild("locName").startAt(query).endAt(query+"\uf8ff"), LocationClass.class)
                .build();
        locationSearchAdapter = new LocationSearchAdapter(options,this);
        locationSearchAdapter.startListening();
        recyclerView.setAdapter(locationSearchAdapter);
    }

    @Override
    public void onLocationClick(int position) {
        Intent intent = new Intent(this, UserVehicleListActivity.class);
        intent.putExtra("location", (locationSearchAdapter.getItem(position)));
        startActivity(intent);
    }
}