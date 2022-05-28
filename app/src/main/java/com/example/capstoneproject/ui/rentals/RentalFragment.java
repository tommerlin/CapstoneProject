package com.example.capstoneproject.ui.rentals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.Bookings;
import com.example.capstoneproject.UserVehicleAdapter;
import com.example.capstoneproject.admin.Vehicle;
import com.example.capstoneproject.databinding.FragmentRentalBinding;
import com.example.capstoneproject.ui.gallery.GalleryViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RentalFragment extends Fragment {
    RecyclerView recyclerView;
    RentalAdapter rentalAdapter;
    private FragmentRentalBinding binding;

    private ArrayList<Bookings> bookingDataList;

    FirebaseUser user;
    String uid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentRentalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerRenter;
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        initRecyclerView();
        loadList();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        rentalAdapter = new RentalAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rentalAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadList();
        rentalAdapter.notifyDataSetChanged();
    }

    private void loadList() {
        Query query = FirebaseDatabase.getInstance().getReference("Bookings").orderByChild("email").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(valueEventListener);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            bookingDataList = new ArrayList<>();
            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                Bookings result =  snapshot1.getValue(Bookings.class);
                bookingDataList.add(result);
                System.out.println("+++++++++++++++++++++++++++++"+bookingDataList);
            }
            rentalAdapter.setUserList(bookingDataList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
