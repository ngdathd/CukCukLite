package vn.misa.nadat.cukcuklite.items;

/**
 * Lưu thông tin chi tiết báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ItemReportDetail {
    private String NameDish;
    private String NumberDish;
    private String UnitDish;
    private int TotalMoney;

    public String getNameDish() {
        return NameDish;
    }

    public void setNameDish(String nameDish) {
        this.NameDish = nameDish;
    }

    public String getNumberDish() {
        return NumberDish;
    }

    public void setNumberDish(String numberDish) {
        this.NumberDish = numberDish;
    }

    public String getUnitDish() {
        return UnitDish;
    }

    public void setUnitDish(String unitDish) {
        this.UnitDish = unitDish;
    }

    public int getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.TotalMoney = totalMoney;
    }
}
