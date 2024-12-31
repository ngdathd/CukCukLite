package com.ngdat.cukcuklite.data.remote.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Lớp chứa các phương thức thao tác với cơ sở dữ liệu Firebase cloud fire store
 * Created at 04/04/2019
 */
public class FireStoreManager {
    private static final String TAG = "FireStoreManager";
    private static FireStoreManager sInstance;
    private FirebaseFirestore mFirebaseFireStore;

    /**
     * Phương thức khởi tạo class FireStoreManager
     * Created at 04/04/2019
     */
    private FireStoreManager() {
        mFirebaseFireStore = FirebaseFirestore.getInstance();
    }

    /**
     * Phương thức khởi tạo đảm bảo duy nhất 1 đối tượng FireStoreManager tồn tại và sử dụng cho
     * toàn bộ ứng dụng
     * Created at 04/04/2019
     *
     * @return - đối tượng FireStoreManager
     */
    public static FireStoreManager getInstance() {
        if (sInstance == null) {
            synchronized (FireStoreManager.class) {
                if (sInstance == null) {
                    sInstance = new FireStoreManager();
                }
            }
        }
        return sInstance;
    }


    /**
     * Phương thức thêm dữ liệu lên cloud fireStore
     * Created at 24/04/2019
     *
     * @param collectionPath - đường đẫn tới nơi bản ghi sẽ lưu
     * @param documentId     - id của bản ghi
     * @param object         - bản ghi dạng object
     * @param listener       - calllback khi thêm dữ liệu
     */
    public void addDocument(String collectionPath, @Nullable final String documentId, Object object, final IFirebaseResponse.IComplete listener) {
        try {
            if (documentId != null) {
                mFirebaseFireStore.collection(collectionPath).document(documentId).set(object)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    listener.onSuccess(documentId);
                                } else {
                                    listener.onFailed();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onFailed();
                                e.printStackTrace();

                            }
                        });
                return;
            }
            listener.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa bản ghi trên firebase
     * Created at 24/04/2019
     *
     * @param collectionPath - Đường dẫn đến đối tượng muốn xóa
     */
    public void removeDocument(final String collectionPath, final String documentId, final IFirebaseResponse.IComplete listener) {
        try {
            mFirebaseFireStore.collection(collectionPath).document(documentId).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listener.onSuccess(documentId);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailed();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật dữ liệu đến tài liệu
     * Created at 24/04/2019
     *
     * @param object         - Thông tin cần cập nhật
     * @param CollectionPath - Đường dẫn đến đối tượng cần cập nhật
     */
    public void updateDocument(final String CollectionPath, final String documentId, Object object, final IFirebaseResponse.IComplete listener) {
        try {
            mFirebaseFireStore.document(CollectionPath).set(object)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listener.onSuccess(documentId);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailed();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy dữ liệu bản ghi từ firebase
     * Created at 24/04/2019
     *
     * @param documentPath - Đường dẫn đến dữ liệu muốn lấy
     */
    public void getDocument(String documentPath, final IFirebaseResponse.IGetDocument listener) {
        try {
            mFirebaseFireStore.document(documentPath).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                listener.onSuccess(documentSnapshot);
                            } else {
                                listener.onFailed();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailed();
                            Log.d(TAG, "onFailure: get document");
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy dữ liệu bản ghi từ firebase
     * Created at 24/04/2019
     *
     * @param collectionPath - Đường dẫn đến dữ liệu muốn lấy
     */
    public void getCollection(String collectionPath, final IFirebaseResponse.IGetCollection listener) {
        try {
            mFirebaseFireStore.collection(collectionPath).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                listener.onFailed();
                            } else {
                                listener.onSuccess(queryDocumentSnapshots.getDocuments());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailed();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
