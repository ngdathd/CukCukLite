package com.ngdat.cukcuklite.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/**
 * Lớp món ăn
 * Created at 27/03/2019
 */
public class Dish implements Parcelable {

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };
    private String DishId;
    private String DishName;
    private int Price;
    private String UnitId;
    private String ColorCode;
    private String IconPath;
    private boolean IsSale;
    private boolean State;

    public Dish() {

    }

    protected Dish(Parcel in) {
        DishId = in.readString();
        DishName = in.readString();
        Price = in.readInt();
        UnitId = in.readString();
        ColorCode = in.readString();
        IconPath = in.readString();
        IsSale = in.readByte() != 0;
        State = in.readByte() != 0;
    }

    public Dish(Builder builder) {
        DishId = builder.DishId;
        DishName = builder.DishName;
        Price = builder.Price;
        UnitId = builder.UnitId;
        ColorCode = builder.ColorCode;
        IconPath = builder.IconPath;
        IsSale = builder.IsSale;
        State = builder.State;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    @NonNull
    @Override
    public String toString() {
        return getDishId() + " " + getDishName() + " " + getPrice() + " " + getColorCode() + " " + getIconPath() + " " + isSale();
    }

    public String getDishId() {
        return DishId;
    }

    public void setDishId(String dishId) {
        DishId = dishId;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public String getColorCode() {
        return ColorCode;
    }

    public void setColorCode(String colorCode) {
        ColorCode = colorCode;
    }

    public String getIconPath() {
        return IconPath;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }

    public boolean isSale() {
        return IsSale;
    }

    public void setSale(boolean sale) {
        IsSale = sale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DishId);
        dest.writeString(DishName);
        dest.writeInt(Price);
        dest.writeString(UnitId);
        dest.writeString(ColorCode);
        dest.writeString(IconPath);
        dest.writeByte((byte) (IsSale ? 1 : 0));
        dest.writeByte((byte) (State ? 1 : 0));
    }

    public static class Builder {
        private String DishId;
        private String DishName;
        private int Price;
        private String UnitId;
        private String ColorCode;
        private String IconPath;
        private boolean IsSale;
        private boolean State;

        public Builder setState(boolean state) {
            State = state;
            return this;
        }

        public Builder setDishId(String dishId) {
            DishId = dishId;
            return this;
        }

        public Builder setDishName(String dishName) {
            DishName = dishName;
            return this;
        }

        public Builder setPrice(int price) {
            Price = price;
            return this;
        }

        public Builder setUnitId(String unitId) {
            UnitId = unitId;
            return this;
        }

        public Builder setColorCode(String colorCode) {
            ColorCode = colorCode;
            return this;
        }

        public Builder setIconPath(String iconPath) {
            IconPath = iconPath;
            return this;
        }

        public Builder setSale(boolean sale) {
            IsSale = sale;
            return this;
        }

        public Dish build() {
            return new Dish(this);
        }

    }
}
