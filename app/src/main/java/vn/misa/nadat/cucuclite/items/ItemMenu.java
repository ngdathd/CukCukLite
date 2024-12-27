package vn.misa.nadat.cucuclite.items;

import android.support.annotation.DrawableRes;

public class ItemMenu {
    @DrawableRes
    private int mDrawable;

    private String mTitle;

    public ItemMenu(int drawable, String title) {
        mDrawable = drawable;
        mTitle = title;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public void setDrawable(int drawable) {
        mDrawable = drawable;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
