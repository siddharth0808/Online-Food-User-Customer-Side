package com.example.orderfooduser.AdapterClass;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfooduser.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.orderfooduser.POJO.OrderModel;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView Pname,price,Pquant;


    public void setTxt_cart_name(TextView Pname)
    {
        this.Pname=Pname;
    }

    public CartViewHolder(View itemView){
        super(itemView);
        Pname=itemView.findViewById(R.id.Pname);
        price=itemView.findViewById(R.id.price);
        Pquant=itemView.findViewById(R.id.Pquant);
    }

    @Override
    public void onClick(View v) {

    }
}
public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<OrderModel> listData=new ArrayList<>();
    private Context context;

    public CartAdapter(List<OrderModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.Pname.setText(listData.get(position).getProductName());
        holder.Pquant.setText(listData.get(position).getQuantity());
        //Locale locale= new Locale("en","INDIA");
        NumberFormat indiaFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
       // NumberFormat format= NumberFormat.getCurrencyInstance(locale);
        int price= (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.price.setText(indiaFormat.format(price));


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
