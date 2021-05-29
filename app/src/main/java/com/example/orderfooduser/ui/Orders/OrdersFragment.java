package com.example.orderfooduser.ui.Orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.orderfooduser.AdapterClass.OrderAdapter;
import com.example.orderfooduser.POJO.CommonModel;
import com.example.orderfooduser.POJO.Request;
import com.example.orderfooduser.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrdersFragment extends Fragment {

    private OrdersViewModel galleryViewModel;


    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    OrderAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference requests;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Orders");

        recyclerView = root.findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        loadOrder(CommonModel.currentUser.getUserMobile());

        return root;
    }

    private void loadOrder(String userMobile) {

        FirebaseRecyclerOptions<Request> options =
                new FirebaseRecyclerOptions.Builder<Request>()
                        .setQuery(requests, Request.class).build();
        adapter = new OrderAdapter(getActivity(), options);


    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}