package com.ngdat.cukcuklite.data.service;

public interface EmailRegisterListener {
    void registerSuccess();

    void registerFailure(String message);
}