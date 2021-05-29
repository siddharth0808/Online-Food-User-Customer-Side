package com.example.orderfooduser.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.orderfooduser.MainActivity;
import com.example.orderfooduser.POJO.Model;
import com.example.orderfooduser.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import com.example.orderfooduser.Databse.Database;
import com.example.orderfooduser.POJO.OrderModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends FirebaseRecyclerAdapter<
        Model, Adapter.MenuViewholder> {


    Context context;
    public Adapter(
            MainActivity mainActivity, @NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
        this.context=mainActivity;
    }


    @Override
    protected void
    onBindViewHolder(@NonNull MenuViewholder holder,
                     int position, @NonNull final Model model)  {
         
        holder.nameItem.setText(model.getItemName());
        holder.priceItem.setText(model.getItemPrice());
        holder.descripItem.setText(model.getItemDescription());
        Picasso.get().load(model.getItemImgUri()).into(holder.imgItem);
        final String Quantity=holder.Elegan_btn.getNumber();
        final String ProductId= String.valueOf(FirebaseDatabase.getInstance().getReference().child("Food Items")
                .child(getRef(position).getKey()));
        holder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(context).addToCart(new OrderModel(ProductId, model.getItemName(), Quantity, model.getItemPrice()));
                Toast.makeText(context, model.getItemName()+" added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @NonNull
    @Override
    public MenuViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new Adapter.MenuViewholder(view);
    }

    public class MenuViewholder extends RecyclerView.ViewHolder {
        public TextView nameItem, priceItem, descripItem;
        CircleImageView imgItem;
        ImageView add_cart;
        ElegantNumberButton Elegan_btn;
        public MenuViewholder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.NameItem);
            priceItem = itemView.findViewById(R.id.PricItem);
            descripItem = itemView.findViewById(R.id.descripItem);
            imgItem = itemView.findViewById(R.id.ImgItem);
            add_cart=itemView.findViewById(R.id.add_cart);

            Elegan_btn=itemView.findViewById(R.id.Elegan_btn);

           /* Animation animation = AnimationUtils.loadAnimation(activity,android.R.anim.slide_in_left);
            */

            Animation animation= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
        }
    }
}