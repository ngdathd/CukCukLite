package vn.misa.nadat.cukcuklite.items;

import java.io.Serializable;

/**
 * @created_by nadat on 24/04/2019
 */
public class ItemReportTime implements Serializable {
    private String Title;
    private String Time;
    private String Money;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }
}
