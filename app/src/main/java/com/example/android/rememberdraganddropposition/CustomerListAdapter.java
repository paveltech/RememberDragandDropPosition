package com.example.android.rememberdraganddropposition;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.interfaceitems.ItemTouchHelperAdapter;
import com.interfaceitems.ItemTouchHelperViewHolder;
import com.interfaceitems.OnCustomerListChangedListener;
import com.interfaceitems.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by android on 5/28/2017.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ItemiewHolder> implements ItemTouchHelperAdapter{


    public List<Customer> customers;
    public Context context;
    public OnStartDragListener onStartDragListener;
    public OnCustomerListChangedListener onCustomerListChangedListener;




    public CustomerListAdapter(List<Customer> customers , Context context , OnStartDragListener onStartDragListener , OnCustomerListChangedListener onCustomerListChangedListener){
        this.customers = customers;
        this.context = context;
        this.onStartDragListener = onStartDragListener;
        this.onCustomerListChangedListener = onCustomerListChangedListener;

    }

    @Override
    public ItemiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_customer_list , parent, false);
        ItemiewHolder itemiewHolder = new ItemiewHolder(view);
        return itemiewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemiewHolder holder, int position) {

        final Customer customer = customers.get(position);

        holder.cuctomerName.setText(customer.getName());
        holder.customerEmail.setText(customer.getEmailAddress());



        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    onStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override

    public int getItemCount() {
        return customers.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(customers , fromPosition , toPosition);
        onCustomerListChangedListener.onNoteListChanged(customers);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onitemDismiss(int position) {

    }

    public static class ItemiewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{


        public final TextView cuctomerName , customerEmail;
        public final ImageView handleView , profileImageView;

        public ItemiewHolder(View itemView) {
            super(itemView);
            cuctomerName = (TextView)itemView.findViewById(R.id.text_view_customer_name);
            customerEmail = (TextView)itemView.findViewById(R.id.text_view_customer_email);
            handleView = (ImageView)itemView.findViewById(R.id.handle);
            profileImageView = (ImageView)itemView.findViewById(R.id.image_view_customer_head_shot);


        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.BLUE);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

}
