package com.ngdat.cukcuklite.data.models;

/**
 * Đối tượng báo cáo chi tiết
 * Created at 17/04/2019
 */
public class ReportDetail {

  private String mName;
  private long mAmount;
  private int mQuantity;
  private String mUnit;
  private String dateCreated;
  private String dishId;

  public ReportDetail() {
    mAmount = 0;
    this.dateCreated = "";
  }

  public ReportDetail(Builder builder) {
    mName = builder.mName;
    mAmount = builder.mAmount;
    mQuantity = builder.mQuantity;
    mUnit = builder.mUnit;
    dateCreated = builder.dateCreated;
    dishId = builder.dishId;

  }

  public String getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getDishId() {
    return dishId;
  }

  public void setDishId(String dishId) {
    this.dishId = dishId;
  }


  public long getAmount() {
    return mAmount;
  }

  public void setAmount(long amount) {
    mAmount = amount;
  }

  public int getQuantity() {
    return mQuantity;
  }

  public void setQuantity(int quantity) {
    mQuantity = quantity;
  }

  public String getUnit() {
    return mUnit;
  }

  public void setUnit(String unit) {
    mUnit = unit;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public void addAmount(long amount) {
    mAmount += amount;
  }

  public void addQuantity(int quantity) {
    mQuantity += quantity;
  }

  public static class Builder {

    private String mName;
    private long mAmount;
    private int mQuantity;
    private String mUnit;
    private String dateCreated;
    private String dishId;

    public Builder setDateCreated(String dateCreated) {
      this.dateCreated = dateCreated;
      return this;
    }

    public Builder setDishId(String dishId) {
      this.dishId = dishId;
      return this;
    }

    public Builder setName(String name) {
      mName = name;
      return this;
    }

    public Builder setAmount(long amount) {
      mAmount = amount;
      return this;
    }

    public Builder setQuantity(int quantity) {
      mQuantity = quantity;
      return this;
    }

    public Builder setUnit(String unit) {
      mUnit = unit;
      return this;
    }

    public ReportDetail build() {
      return new ReportDetail(this);
    }
  }
}
