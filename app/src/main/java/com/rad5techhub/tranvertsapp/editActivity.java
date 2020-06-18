package com.rad5techhub.tranvertsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class editActivity extends AppCompatActivity {
    TextView location, dicrib, price;
    ImageView pix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        location = findViewById(R.id.myTitle);
        dicrib = findViewById(R.id.mydiscri);
        price = findViewById(R.id.myprice);
        pix = findViewById(R.id.mypost_image);


        Intent intent = getIntent();
        String songName = intent.getStringExtra("title");
        String songArtist = intent.getStringExtra("pix");
        String songData = intent.getStringExtra("description");
        location.setText(songName);
        price.setText(songData);
        dicrib.setText(songArtist);


    }
}
