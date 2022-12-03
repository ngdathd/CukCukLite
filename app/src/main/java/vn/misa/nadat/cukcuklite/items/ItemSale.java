package vn.misa.nadat.cukcuklite.items;

import android.text.SpannableStringBuilder;

import java.io.Serializable;

/**
 * Đối tượng ItemSale ở mục Bán hàng.
 *
 * @created_by nadat on 20/04/2019
 */
public class ItemSale implements Serializable {
    private int IdSale;
    private String NumberOfTable;
    private String NumberOfPerson;
    private int PaymentStatus;
    private String ItemSaleColor;
    private String TotalMoney;
    private SpannableStringBuilder Content;

    public ItemSale() {

    }

    public int getIdSale() {
        return IdSale;
    }

    public void setSaleId(int idSale) {
        IdSale = idSale;
    }

    public String getNumberOfTable() {
        return NumberOfTable;
    }

    public void setNumberOfTable(String numberOfTable) {
        NumberOfTable = numberOfTable;
    }

    public String getNumberOfPerson() {
        return NumberOfPerson;
    }

    public void setNumberOfPerson(String numberOfPerson) {
        NumberOfPerson = numberOfPerson;
    }

    public int getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getItemSaleColor() {
        return ItemSaleColor;
    }

    public void setItemSaleColor(String itemSaleColor) {
        ItemSaleColor = itemSaleColor;
    }

    public String getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        TotalMoney = totalMoney;
    }

    public SpannableStringBuilder getContent() {
        return Content;
    }

    public void setContent(SpannableStringBuilder content) {
        Content = content;
    }
}
