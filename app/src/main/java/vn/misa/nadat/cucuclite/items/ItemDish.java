package vn.misa.nadat.cucuclite.items;

public class ItemDish {
    private String mImgIcon;
    private int mImgColor;
    private String mName;
    private String mPrice;
    private String mUnit;
    private boolean mIsSale;

    public ItemDish() {
    }

    public ItemDish(Builder builder) {
        mImgIcon = builder.mImgIcon;
        mImgColor = builder.mImgColor;
        mName = builder.mName;
        mPrice = builder.mPrice;
        mUnit = builder.mUnit;
        mIsSale = builder.mIsSale;
    }

    public String getUnit() {
        return mUnit;
    }

    public ItemDish setUnit(String unit) {
        mUnit = unit;
        return this;
    }

    public String getImgIcon() {
        return mImgIcon;
    }

    public void setImgIcon(String imgIcon) {
        mImgIcon = imgIcon;
    }

    public int getImgColor() {
        return mImgColor;
    }

    public void setImgColor(int imgColor) {
        mImgColor = imgColor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public boolean isSale() {
        return mIsSale;
    }

    public void setSale(boolean sale) {
        mIsSale = sale;
    }

    public static class Builder {
        private String mImgIcon;
        private int mImgColor;
        private String mName;
        private String mPrice;
        private String mUnit;
        private boolean mIsSale;

        public Builder setImgIcon(String imgIcon) {
            mImgIcon = imgIcon;
            return this;
        }

        public Builder setImgColor(int imgColor) {
            mImgColor = imgColor;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setPrice(String price) {
            mPrice = price;
            return this;
        }

        public Builder setSale(boolean sale) {
            mIsSale = sale;
            return this;
        }

        public Builder setUnit(String unit) {
            mUnit = unit;
            return this;
        }

        public ItemDish build() {
            return new ItemDish(this);
        }
    }
}
