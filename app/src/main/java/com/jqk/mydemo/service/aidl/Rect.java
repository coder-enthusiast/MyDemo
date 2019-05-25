package com.jqk.mydemo.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public final class Rect implements Parcelable {
    public int left;
    public int top;
    public int right;
    public int bottom;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(left);
        parcel.writeInt(top);
        parcel.writeInt(right);
        parcel.writeInt(bottom);
    }

    public static final Parcelable.Creator<Rect> CREATOR = new
            Parcelable.Creator<Rect>() {
                public Rect createFromParcel(Parcel in) {
                    return new Rect(in);
                }

                public Rect[] newArray(int size) {
                    return new Rect[size];
                }
            };

    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    private Rect(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        left = in.readInt();
        top = in.readInt();
        right = in.readInt();
        bottom = in.readInt();
    }

    @Override
    public String toString() {
        return "Rect{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }
}
