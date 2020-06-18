package com.rad5techhub.tranvertsapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Firebaseutil {
    public static FirebaseDatabase mFirebasedatabase;
    public static DatabaseReference mDatabaseref;
    public static Firebaseutil firebaseutil ;
    public  static ArrayList<TravertsDeal>adeals;


    private  Firebaseutil(){}


    public  static  void openFBRefrence(String ref){

        if (firebaseutil == null){
            firebaseutil = new Firebaseutil();
            mFirebasedatabase = FirebaseDatabase.getInstance();
            adeals = new ArrayList<TravertsDeal>();
        }

        mDatabaseref = mFirebasedatabase.getReference().child(ref);



    }




}
