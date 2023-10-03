package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class DataModel implements Parcelable {
    public String groupName;
    public String groupDescription;
    public String groupDate;
    public String userId;

    public DataModel() {
        // Default constructor required by Firebase
    }

    // Parcelable implementation methods
    protected DataModel(Parcel in) {
        groupName = in.readString();
        groupDescription = in.readString();
        groupDate = in.readString();
        userId = in.readString();
    }

    public static final Parcelable.Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeString(groupDescription);
        dest.writeString(groupDate);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public String toString() {
        return "GroupName: " + groupName + "\nGroupDescription: " + groupDescription + "\nCreatedDate: " + groupDate;
    }
}



