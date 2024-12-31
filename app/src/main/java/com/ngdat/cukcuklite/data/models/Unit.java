package com.ngdat.cukcuklite.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Lớp Đơn vị cho món ăn
 * Created at 27/03/2019
 */
public class Unit implements Parcelable {

    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };
    private String UnitId;
    private String UnitName;


    public Unit(String unitId, String unitName) {
        UnitId = unitId;
        UnitName = unitName;
    }

    protected Unit(Parcel in) {
        UnitId = in.readString();
        UnitName = in.readString();
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UnitId);
        dest.writeString(UnitName);
    }
}
