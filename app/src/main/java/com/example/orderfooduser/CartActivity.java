 package com.example.orderfooduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.orderfooduser.AdapterClass.CartAdapter;
import com.example.orderfooduser.Databse.Database;
import com.example.orderfooduser.POJO.CommonModel;
import com.example.orderfooduser.POJO.OrderModel;
import com.example.orderfooduser.POJO.Request;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView total_price;
    Button place_ordr;

    List<OrderModel> cart=new ArrayList<>();
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database= FirebaseDatabase.getInstance();
        requests=database.getReference("Orders");

        recyclerView= findViewById(R.id.recyc_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        total_price=findViewById(R.id.total_price);
        place_ordr=findViewById(R.id.place_ordr);

        
        loadListFood();


        place_ordr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus=DialogPlus.newDialog(CartActivity.this)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.pay_opt))
                        .setExpanded(true,600)
                        .create();

                View myview=dialogPlus.getHolderView();

                final TextView cash_btn= myview.findViewById(R.id.cash_btn);
                final TextView google_pay=myview.findViewById(R.id.google_pay);

                dialogPlus.show();

                cash_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Request request = new Request(

                                    CommonModel.currentUser.getUserMobile(),
                                    CommonModel.currentUser.getUserName(),
                                    total_price.getText().toString(),
                                    cash_btn.getText().toString(),
                                    cart
                            );
                            requests.child(String.valueOf(System.currentTimeMillis()))
                                    .setValue(request);
                            dialogPlus.dismiss();

                            new Database(getBaseContext()).cleanCart();
                            Toast.makeText(CartActivity.this, "Thank you ,Order placed", Toast.LENGTH_SHORT).show();
                        }catch (NullPointerException nl){
                            Toast.makeText(CartActivity.this,"Error"+nl, Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                google_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try{
                            Request request = new Request(

                                    CommonModel.currentUser.getUserMobile(),
                                    CommonModel.currentUser.getUserName(),
                                    total_price.getText().toString(),
                                    google_pay.getText().toString(),
                                    cart
                            );
                            requests.child(String.valueOf(System.currentTimeMillis()))
                                    .setValue(request);
                            dialogPlus.dismiss();

                            new Database(getBaseContext()).cleanCart();
                            Toast.makeText(CartActivity.this, "Thank you ,Order placed", Toast.LENGTH_SHORT).show();
                    }catch (NullPointerException nl){
                        Toast.makeText(CartActivity.this,"Error"+nl, Toast.LENGTH_SHORT).show();
                    }

                    }
                });




            }
        });
    }

    private void loadListFood() {
        cart=new Database(this).getCarts();
        adapter=new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        int total=0;
        for(OrderModel orderModel:cart){
            total+= (Integer.parseInt(orderModel.getPrice()))*(Integer.parseInt(orderModel.getQuantity()));

            NumberFormat indiaFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
           // Locale locale= new Locale("en","INDIA");
            //NumberFormat format= NumberFormat.getCurrencyInstance(locale);
            total_price.setText(indiaFormat.format(total));
        }
    }
}