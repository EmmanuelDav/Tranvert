package com.rad5techhub.tranvertsapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.ViewHolder {


    TextView location, dicrib, price;
    ImageView pix;
    //ArrayList<TravertsDeal> deal = null;

    public DealAdapter(@NonNull View itemView) {
        super(itemView);
        location = itemView.findViewById(R.id.Title);
        dicrib = itemView.findViewById(R.id.discri);
        price = itemView.findViewById(R.id.price);
        pix = itemView.findViewById(R.id.post_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mclicklistiner.Onclickitem(view, getAdapterPosition());
            }


        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mclicklistiner.Onlongclickitem(view, getAdapterPosition());

                return true;
            }
        });

    }

    private DealAdapter.Clicklistiner mclicklistiner;


    public interface Clicklistiner {
        void Onclickitem(View v, int position);

        void Onlongclickitem(View v, int position);

    }


    public void setOnInterfaceClick(DealAdapter.Clicklistiner clicklistiner) {
        this.mclicklistiner = clicklistiner;

    }
}
















































/*  @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        Log.d("onclick ", String.valueOf(position));
        TravertsDeal selectdeal = deal.get(position);
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.putExtra("Data", selectdeal);
        view.getContext().startActivity(intent);

*/






