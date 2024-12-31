package com.ngdat.cukcuklite.data.models;

public class Order {

    private String BillId;
    private int BillNumber;
    private int TableNumber;
    private int NumberCustomer;
    private int TotalMoney;
    private String Content;
    private long DateCreated;

    public Order(Builder builder) {
        BillId = builder.BillId;
        BillNumber = builder.BillNumber;
        TableNumber = builder.TableNumber;
        NumberCustomer = builder.NumberCustomer;
        TotalMoney = builder.TotalMoney;
        Content = builder.Content;
        DateCreated = builder.DateCreated;
    }

    public long getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(long dateCreated) {
        DateCreated = dateCreated;
    }

    public String getBillId() {
        return BillId;
    }

    public void setBillId(String billId) {
        BillId = billId;
    }

    public int getBillNumber() {
        return BillNumber;
    }

    public void setBillNumber(int billNumber) {
        BillNumber = billNumber;
    }

    public int getTableNumber() {
        return TableNumber;
    }

    public void setTableNumber(int tableNumber) {
        TableNumber = tableNumber;
    }

    public int getNumberCustomer() {
        return NumberCustomer;
    }

    public void setNumberCustomer(int numberCustomer) {
        NumberCustomer = numberCustomer;
    }

    public int getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        TotalMoney = totalMoney;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public static class Builder {
        private String BillId;
        private int BillNumber;
        private int TableNumber;
        private int NumberCustomer;
        private int TotalMoney;
        private String Content;
        private long DateCreated;

        public Builder setDateCreated(long dateCreated) {
            DateCreated = dateCreated;
            return this;
        }

        public Builder setBillId(String billId) {
            BillId = billId;
            return this;
        }

        public Builder setBillNumber(int billNumber) {
            BillNumber = billNumber;
            return this;
        }

        public Builder setTableNumber(int tableNumber) {
            TableNumber = tableNumber;
            return this;
        }

        public Builder setNumberCustomer(int numberCustomer) {
            NumberCustomer = numberCustomer;
            return this;
        }

        public Builder setTotalMoney(int totalMoney) {
            TotalMoney = totalMoney;
            return this;
        }

        public Builder setContent(String content) {
            Content = content;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
