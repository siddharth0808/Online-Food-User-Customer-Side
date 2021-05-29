package com.example.orderfooduser.AdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfooduser.POJO.Request;
import com.example.orderfooduser.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OrderAdapter extends FirebaseRecyclerAdapter<Request,OrderAdapter.OrderViewHolder> {

    FragmentActivity context;
    public OrderAdapter(FragmentActivity ordersFragment, @NonNull FirebaseRecyclerOptions options) {
        super(options);
        this.context=ordersFragment;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {
        holder.txtOrderId.setText(getRef(position).getKey());
        holder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
        holder.txtOrderPhone.setText(model.getPhone());
        holder.txtOrderPrice.setText(model.getTotal());
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_layout, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    private String convertCodeToStatus(String status) {
        if(status.equals("0")){
            return "Placed";
        }else if(status.equals("1")){
            return "Preparing";
        }else{
            return "Shipped";
        }

    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderPrice;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.order_id);
            txtOrderStatus = itemView.findViewById(R.id.order_status);
            txtOrderPhone = itemView.findViewById(R.id.order_name);
            txtOrderPrice = itemView.findViewById(R.id.order_price);


        }
    }
}
