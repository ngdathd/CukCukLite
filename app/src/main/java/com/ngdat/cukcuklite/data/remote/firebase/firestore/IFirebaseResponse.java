package com.ngdat.cukcuklite.data.remote.firebase.firestore;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

/**
 * Interface callBack dữ liệu thao tác trên file base
 * Created at 24/04/2019
 */
public interface IFirebaseResponse {

    // .. Interface callback cho thao tác Thêm dữ liệu
    interface IComplete {
        void onSuccess(String documentId);

        void onFailed();
    }

    // .. Interface callback cho thao tác Lấy dữ liệu
    interface IGetDocument {
        void onSuccess(DocumentSnapshot documentSnapshot);

        void onFailed();
    }

    // .. Interface callback cho thao tác Lấy collection
    interface IGetCollection {
        void onSuccess(List<DocumentSnapshot> listDocumentSnapshot);

        void onFailed();
    }
}
