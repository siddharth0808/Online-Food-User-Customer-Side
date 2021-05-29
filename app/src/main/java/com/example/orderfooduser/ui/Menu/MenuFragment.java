package com.example.orderfooduser.ui.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.orderfooduser.AdapterClass.Adapter;
import com.example.orderfooduser.MainActivity;
import com.example.orderfooduser.POJO.Model;
import com.example.orderfooduser.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuFragment extends Fragment {

    private MenuViewModel homeViewModel;

    FirebaseDatabase database;
    DatabaseReference reference;

    RecyclerView RV;
    RecyclerView.LayoutManager layoutManager;

    Adapter adapter;

    SwipeRefreshLayout swipe;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);


        RV= root.findViewById(R.id.RV);

        swipe=root.findViewById(R.id.swippe);

        database= FirebaseDatabase.getInstance();
        reference=database.getReference("Food Items");

        RV= root.findViewById(R.id.RV);
        RV.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        RV.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Model> options
                = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference, Model.class)
                .build();

        adapter = new Adapter((MainActivity) getActivity(),options);
        // Connecting Adapter class with the Recycler view*/

        return root;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });
        RV.setAdapter(adapter);
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }




}