package com.ngdat.cukcuklite.data.service;

public interface EmailLoginListener {
    void loginSuccess();

    void loginFailure(String message);
}