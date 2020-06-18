package com.rad5techhub.tranvertsapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class TravertsDeal implements Parcelable {
    public String title;
    public String price;
    public String distribution;
    public String imageurl;
    public String key;


    public TravertsDeal(){}

    public TravertsDeal( String title, String price, String distribution, String imageurl) {
        this.title = title;
        this.price = price;
        this.distribution = distribution;
        this.imageurl = imageurl;
    }


    protected TravertsDeal(Parcel in) {
        title = in.readString();
        price = in.readString();
        distribution = in.readString();
        imageurl = in.readString();
        key = in.readString();
    }

    public static final Creator<TravertsDeal> CREATOR = new Creator<TravertsDeal>() {
        @Override
        public TravertsDeal createFromParcel(Parcel in) {
            return new TravertsDeal(in);
        }

        @Override
        public TravertsDeal[] newArray(int size) {
            return new TravertsDeal[size];
        }
    };

    public String getTitle() {
        return title;
    }


    public String getPrice() {
        return price;
    }

    public String getDistribution() {
        return distribution;
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


    @Exclude
    public  String getKey(){
        return key;
    }
    @Exclude
    public void setKey(){
       this.key = key ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(distribution);
        parcel.writeString(imageurl);
        parcel.writeString(key);
    }
}
