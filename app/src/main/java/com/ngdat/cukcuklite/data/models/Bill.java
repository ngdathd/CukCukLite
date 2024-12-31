package com.ngdat.cukcuklite.data.models;

import java.util.Calendar;
import java.util.UUID;

import com.ngdat.cukcuklite.data.cukcukenum.BillSateEnum;

/**
 * Lớp hóa đơn
 * Created at 11/04/2019
 */
public class Bill {
    private String BillId;
    private int BillNumber;
    private long DateCreated;
    private BillSateEnum State;
    private int TableNumber;
    private int NumberCustomer;
    private int TotalMoney;
    private int CustomerPay;

    public Bill() {
        BillId = UUID.randomUUID().toString();
        DateCreated = Calendar.getInstance().getTimeInMillis();
    }

    public Bill(Builder builder) {
        BillId = builder.BillId;
        BillNumber = builder.BillNumber;
        DateCreated = builder.DateCreated;
        State = builder.State;
        TableNumber = builder.TableNumber;
        NumberCustomer = builder.NumberCustomer;
        TotalMoney = builder.TotalMoney;
        CustomerPay = builder.CustomerPay;
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

    public Long getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(long dateCreated) {
        DateCreated = dateCreated;
    }

    public BillSateEnum getState() {
        return State;
    }

    public void setState(BillSateEnum state) {
        State = state;
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

    public int getCustomerPay() {
        return CustomerPay;
    }

    public void setCustomerPay(int customerPay) {
        CustomerPay = customerPay;
    }

    public static class Builder {
        private String BillId;
        private int BillNumber;
        private long DateCreated;
        private BillSateEnum State;
        private int TableNumber;
        private int NumberCustomer;
        private int TotalMoney;
        private int CustomerPay;

        public Builder setBillId(String billId) {
            BillId = billId;
            return this;
        }

        public Builder setBillNumber(int billNumber) {
            BillNumber = billNumber;
            return this;
        }

        public Builder setDateCreated(long dateCreated) {
            DateCreated = dateCreated;
            return this;
        }

        public Builder setState(BillSateEnum state) {
            State = state;
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

        public Builder setCustomerPay(int customerPay) {
            CustomerPay = customerPay;
            return this;
        }

        public Bill build() {
            return new Bill(this);
        }
    }
}
