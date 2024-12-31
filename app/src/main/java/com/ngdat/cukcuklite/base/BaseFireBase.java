package com.ngdat.cukcuklite.base;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseFireBase {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    protected DatabaseReference getDatabaseReference() {
        if (null == mDatabase) {
            return FirebaseDatabase.getInstance().getReference();
        } else {
            return mDatabase;
        }
    }

    protected FirebaseAuth getFirebaseAuth() {
        if (null == mAuth) {
            return FirebaseAuth.getInstance();
        } else {
            return mAuth;
        }
    }
}