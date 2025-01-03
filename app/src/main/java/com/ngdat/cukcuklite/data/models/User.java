package com.ngdat.cukcuklite.data.models;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private HashMap<String, Unit> units;
    private HashMap<String, Dish> dishes;
    private HashMap<String, Bill> bills;
    private HashMap<String, BillDetail> billDetails;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, Unit> getUnits() {
        return units;
    }

    public void setUnits(HashMap<String, Unit> units) {
        this.units = units;
    }

    public HashMap<String, Dish> getDishes() {
        return dishes;
    }

    public void setDishes(HashMap<String, Dish> dishes) {
        this.dishes = dishes;
    }

    public HashMap<String, Bill> getBills() {
        return bills;
    }

    public void setBills(HashMap<String, Bill> bills) {
        this.bills = bills;
    }

    public HashMap<String, BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(HashMap<String, BillDetail> billDetails) {
        this.billDetails = billDetails;
    }
}
