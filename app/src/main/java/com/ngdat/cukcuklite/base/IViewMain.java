package com.ngdat.cukcuklite.base;

import androidx.annotation.StringRes;

public interface IViewMain {
    int getLayout();

    void inflateComponents();

    void findViewByIds();

    void initComponents();

    void setEvents();

    void showProgressDialog(String message);

    void hideProgressDialog();

    void showMessage(String message);

    void showMessage(@StringRes int message);

    void onBackRoot();
}