package com.ngdat.cukcuklite.data.remote.service;

public interface EmailRegisterListener {
    void registerSuccess();

    void registerFailure(String message);
}