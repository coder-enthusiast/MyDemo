package com.jqk.mydemo.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    private int id;

    public Data() {
    }

    public Data(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    private Data(Parcel in) {
        id = in.readInt();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
