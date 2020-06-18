package com.rad5techhub.tranvertsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ListActivity extends AppCompatActivity {

    ArrayList<TravertsDeal> deals;
    ProgressBar mprogressbar;
    private DatabaseReference mdatabseRef;
    RecyclerView mRecyclerview;
    FirebaseRecyclerOptions<TravertsDeal> options;
    FirebaseRecyclerAdapter<TravertsDeal, DealAdapter> Adapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRecyclerview = findViewById(R.id.Recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mdatabseRef = FirebaseDatabase.getInstance().getReference().child("Travel deal");
        deals = new ArrayList<>();

        options = new FirebaseRecyclerOptions
                .Builder<TravertsDeal>()
                .setQuery(mdatabseRef, TravertsDeal.class)
                .build();

        Adapter = new FirebaseRecyclerAdapter<TravertsDeal, DealAdapter>(options) {

            @NonNull
            @Override
            public DealAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mainView = LayoutInflater.from(ListActivity.this).inflate(R.layout.rv_row, parent, false);
                return new DealAdapter(mainView);

            }

            @Override
            protected void onBindViewHolder(@NonNull final DealAdapter dealAdapter, final int position, @NonNull final TravertsDeal travertsDeal) {
                dealAdapter.location.setText(travertsDeal.getTitle());
                dealAdapter.price.setText(travertsDeal.getPrice());
                dealAdapter.dicrib.setText(travertsDeal.getDistribution());
                Glide.with(ListActivity.this).load(travertsDeal.getImageurl()).into(dealAdapter.pix);
                dealAdapter.setOnInterfaceClick(new DealAdapter.Clicklistiner() {
                    @Override
                    public void Onclickitem(View v, int position) {

                        Intent intent = new Intent(v.getContext(), editActivity.class);
                        intent.putExtra("title", travertsDeal.getPrice());
                        intent.putExtra("pix", travertsDeal.getPrice());
                        intent.putExtra("description", travertsDeal.getDistribution());

                        v.getContext().startActivity(intent);

                    }

                    @Override
                    public void Onlongclickitem(View v, int position) {
                        Toast.makeText(ListActivity.this, "not clicking ", Toast.LENGTH_SHORT).show();
                        String currenttitle = travertsDeal.getTitle();
                        String currentImage = travertsDeal.getImageurl();
                        showdeletedialog(currenttitle, currentImage);
                        Log.d("eroor", "showdialog" + travertsDeal.getImageurl());

                    }
                });


            }
        };


        mRecyclerview.setAdapter(Adapter);

    }

    private void showdeletedialog(final String currenttitle, final String currentImage) {

        final AlertDialog.Builder bulid = new AlertDialog.Builder(this);
        bulid.setTitle("Delete");
        bulid.setMessage("Are you sure you want to delete this post ");
        bulid.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query query = mdatabseRef.orderByChild("title").equalTo(currenttitle);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.getRef().removeValue();

                            Toast.makeText(ListActivity.this, "Post deleted", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ListActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
                StorageReference pictures = getInstance().getReferenceFromUrl(currentImage);
                pictures.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ListActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                bulid.create().show();

            }
        });
        bulid.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int data = item.getItemId();
        switch (data) {
            case (R.id.add):
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}



