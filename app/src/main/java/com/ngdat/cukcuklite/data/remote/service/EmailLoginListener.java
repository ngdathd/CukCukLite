package com.ngdat.cukcuklite.data.remote.service;

public interface EmailLoginListener {
    void loginSuccess();

    void loginFailure(String message);
}