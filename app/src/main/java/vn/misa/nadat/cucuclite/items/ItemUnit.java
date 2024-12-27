package vn.misa.nadat.cucuclite.items;

public class ItemUnit {
    private String mUnit;
    private boolean mIsCheck;

    public ItemUnit() {
    }

    public ItemUnit(String unit) {
        mUnit = unit;
    }

    public ItemUnit(String unit, boolean isCheck) {
        mUnit = unit;
        mIsCheck = isCheck;
    }

    public boolean isCheck() {
        return mIsCheck;
    }

    public ItemUnit setCheck(boolean check) {
        mIsCheck = check;
        return this;
    }

    public String getUnit() {
        return mUnit;
    }

    public ItemUnit setUnit(String unit) {
        mUnit = unit;
        return this;
    }
}
