package com.ngdat.cukcuklite.data.remote.firebase.firebaserealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.listeners.IDataCallBack;
import com.ngdat.cukcuklite.data.cukcukenum.BillSateEnum;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.models.Unit;
import com.ngdat.cukcuklite.data.remote.firebase.firestore.FireStoreManager;
import com.ngdat.cukcuklite.utils.AppConstants;

public class FirebaseManager {
    public static final String USERS = "users";
    public static final String UNITS = "units";
    public static final String DISHES = "dishes";
    public static final String BILLS = "bills";
    public static final String BILL_DETAILS = "billDetails";

    private static FirebaseManager sInstance;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private String uuid;

    private FirebaseManager() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        uuid = mFirebaseAuth.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseManager getInstance() {
        if (sInstance == null) {
            synchronized (FireStoreManager.class) {
                if (sInstance == null) {
                    sInstance = new FirebaseManager();
                }
            }
        }
        return sInstance;
    }

    public void userHasData(final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            uuid = FirebaseAuth.getInstance().getUid();
            if (uuid != null) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(USERS).child(uuid);
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            callBack.onSuccess();
                        } else {
                            callBack.onFailed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Phương thức thêm danh sách món ăn lên server firebase
     * Created at 25/04/2019
     *
     * @param dishes   - danh sách món ăn
     * @param callBack - callback lắng nghe sự kiện thành công - thất bại
     */
    public void addDishesToFirebase(List<Dish> dishes, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (dishes != null && dishes.size() > 0) {
                DatabaseReference reference = mDatabaseReference.child(USERS).child(uuid).child(DISHES);
                int dishSize = dishes.size();
                for (int i = 0; i < dishSize; i++) {
                    reference.child(dishes.get(i).getDishId()).setValue(dishes.get(i),
                            new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        callBack.onSuccess();
                                    } else {
                                        callBack.onFailed();
                                    }
                                }
                            });
                }
                return;
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm/sửa 1 món ăn trên server firebase
     * Created at 25/04/2019
     *
     * @param dish     - món ăn
     * @param callBack - callback lắng nghe sự kiện thêm/sửa thành công - thất bại
     */
    public void setDishToFirebase(Dish dish, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (dish != null) {
                mDatabaseReference.child(USERS).child(uuid).child(DISHES).child(dish.getDishId()).setValue(dish,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    callBack.onSuccess();
                                } else {
                                    callBack.onFailed();
                                }
                            }
                        });
                return;
            }
            callBack.onFailed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xóa món ăn thông qua id của món ăn trên server firebase
     * Created at 25/04/2019
     *
     * @param dishId   - id của món ăn
     * @param callBack - callback lắng nghe sự kiện xóa món ăn thành công - thất bại
     */
    public void removeDishAtFirebase(String dishId, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (dishId != null) {
                mDatabaseReference.child(USERS).child(uuid).child(DISHES).child(dishId).setValue(null, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            callBack.onSuccess();
                        } else {
                            callBack.onFailed();
                        }
                    }
                });
                return;
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm danh sách đơn vị lên server firebase
     * Created at 25/04/2019
     *
     * @param units    - danh sách đơn vị
     * @param callBack - callback lắng nghe sự kiện thêm thành công - thất bại
     */
    public void addUnitsToFirebase(List<Unit> units, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (units != null && units.size() > 0) {
                DatabaseReference reference = mDatabaseReference.child(USERS).child(uuid).child(UNITS);
                int size = units.size();
                for (int i = 0; i < size; i++) {
                    reference.child(units.get(i).getUnitId()).setValue(units.get(i),
                            new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        callBack.onSuccess();
                                    } else {
                                        callBack.onFailed();
                                    }
                                }
                            });
                }
                return;
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm/cập nhật đơn vị lên server firebase
     * Created at 25/04/2019
     *
     * @param unit     - đơn vị
     * @param callBack - callback lắng nghe sụ kiện thêm/cập nhật đơn vị thành công  - thất bại
     */
    public void setUnitToFirebase(Unit unit, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (unit != null) {
                mDatabaseReference.child(USERS).child(uuid).child(UNITS).child(unit.getUnitId()).setValue(unit,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    callBack.onSuccess();
                                } else {
                                    callBack.onFailed();
                                }
                            }
                        });
                return;
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xóa món ăn thông qua id của đơn vị trên server firebase
     * Created at 25/04/2019
     *
     * @param unitId   - id của đơn vị
     * @param callBack - callback lắng nghe sự kiện xóa đơn vị thành công - thất bại
     */
    private void removeUnitAtFirebase(String unitId, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (unitId != null) {
                mDatabaseReference.child(USERS).child(uuid).child(UNITS).child(unitId).setValue(null, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            callBack.onSuccess();
                        } else {
                            callBack.onFailed();
                        }
                    }
                });
                return;
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm hóa đơn thông qua id của đơn vị trên server firebase
     * Created at 25/04/2019
     *
     * @param bill     - hóa đơn
     * @param callBack - callback lắng nghe sự kiện thêm hóa đơn thành công - thất bại
     */
    public void addBillToFirebase(Bill bill, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (bill != null) {
                mDatabaseReference.child(USERS).child(uuid).child(BILLS)
                        .child(bill.getBillId()).setValue(bill,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    callBack.onSuccess();
                                } else {
                                    callBack.onFailed();
                                }
                            }
                        });
                return;
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xóa hóa đơn và danh sách chi tiết của hoa đơn
     * Created at 25/04/2019
     *
     * @param billId - id của hóa đơn
     */
    public void removeBillAtFirebase(String billId, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (billId != null) {
                mDatabaseReference.child(USERS).child(uuid).child(BILLS).child(billId).setValue(null);
                mDatabaseReference.child(USERS).child(uuid).child(BILL_DETAILS).child(billId).setValue(null, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            callBack.onSuccess();
                        } else {
                            callBack.onFailed();
                        }
                    }
                });
                return;
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm/sửa danh sách hóa đơn chi tiết cho hóa đơn lên firebase
     * Created at 25/04/2019
     *
     * @param callBack - callback lắng nghe sự kiện thêm/sửa danh sách hóa đơn chi tiết thành công - thất bại
     */
    public void setBillDetailsToFirebase(List<BillDetail> billDetails, final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            if (billDetails != null && billDetails.size() > 0) {
                DatabaseReference reference = mDatabaseReference.child(USERS).child(uuid).child(BILL_DETAILS);
                String billId = billDetails.get(0).getBillId();
                reference.child(billId).setValue(billDetails,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    callBack.onSuccess();
                                } else {
                                    callBack.onFailed();
                                }
                            }
                        });
            }
            callBack.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Phương thức xóa dữ liệu của user
     * Created at 25/04/2019
     *
     * @param callBack - callback lắng nghe sự kiện xóa  dữ liệu user thành công - thất bại
     */
    public void clearAllDataOfUser(final IFirebaseRealTime.IFirebaseDataCallBack callBack) {
        try {
            mDatabaseReference.child(USERS).child(uuid).setValue(null, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        callBack.onSuccess();
                    } else {
                        callBack.onFailed();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức lấy danh sách  món ăn từ firebase
     * Created at 25/04/2019
     *
     * @param callback - callback lắng nghe xử lý kết quả lấy danh sách món ăn thành công/thất bại
     */
    public void getAllDishFromFirebase(final IDataCallBack<List<Dish>> callback) {
        final List<Dish> dishes = new ArrayList<>();
        try {
            mDatabaseReference.child(USERS).child(uuid).child(DISHES).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        Dish dish = new Dish.Builder()
                                .setDishId(String.valueOf(childDataSnapshot.child("dishId").getValue()))
                                .setDishName(String.valueOf(childDataSnapshot.child("dishName").getValue()))
                                .setPrice((int) (long) childDataSnapshot.child("price").getValue())
                                .setUnitId(String.valueOf(childDataSnapshot.child("unitId").getValue()))
                                .setColorCode(String.valueOf(childDataSnapshot.child("colorCode").getValue()))
                                .setIconPath(String.valueOf(childDataSnapshot.child("iconPath").getValue()))
                                .setSale((boolean) childDataSnapshot.child("state").getValue())
                                .setState((boolean) childDataSnapshot.child("sale").getValue())
                                .build();
                        dishes.add(dish);
                    }
                    callback.onDataSuccess(dishes);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onDataFailed(R.string.something_went_wrong);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.onDataFailed(R.string.something_went_wrong);
    }


    /**
     * Phương thức lấy danh sách đơn vị từ firebase
     * Created at 25/04/2019
     *
     * @param callback - callback lắng nghe xử lý kết quả lấy danh sách đơn vị thành công/thất bại
     */
    public void getAllUnitFromFirebase(final IDataCallBack<List<Unit>> callback) {
        final List<Unit> units = new ArrayList<>();
        try {
            mDatabaseReference.child(USERS).child(uuid).child(UNITS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        Unit unit = new Unit(String.valueOf(childDataSnapshot.child("unitId").getValue()),
                                String.valueOf(childDataSnapshot.child("unitName").getValue()));
                        units.add(unit);
                    }
                    callback.onDataSuccess(units);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onDataFailed(R.string.something_went_wrong);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.onDataFailed(R.string.something_went_wrong);
    }

    /**
     * Phương thức lấy danh sách hóa đơn từ firebase
     * Created at 25/04/2019
     *
     * @param callback - callback lắng nghe xử lý kết quả lấy danh sách hóa đơn thành công/thất bại
     */
    public void getAllBillFromFirebase(final IDataCallBack<List<Bill>> callback) {
        final List<Bill> bills = new ArrayList<>();
        try {
            mDatabaseReference.child(USERS).child(uuid).child(BILLS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        Bill bill = new Bill.Builder()
                                .setBillId(String.valueOf(childDataSnapshot.child("billId").getValue()))
                                .setDateCreated((long) childDataSnapshot.child("dateCreated").getValue())
                                .setTotalMoney((int) (long) childDataSnapshot.child("totalMoney").getValue())
                                .setCustomerPay((int) (long) childDataSnapshot.child(IDBUtils.ITableBillUtils.COLUMN_CUSTOMER_PAY).getValue())
                                .setTableNumber((int) (long) childDataSnapshot.child(IDBUtils.ITableBillUtils.COLUMN_TABLE_NUMBER).getValue())
                                .setNumberCustomer((int) (long) childDataSnapshot.child(IDBUtils.ITableBillUtils.COLUMN_NUMBER_CUSTOMER).getValue())
                                .build();
                        switch ((int) childDataSnapshot.child(IDBUtils.ITableBillUtils.COLUMN_STATE).getValue()) {
                            case AppConstants
                                    .UN_PAID:
                                bill.setState(BillSateEnum.UNPAID);
                                break;
                            case AppConstants
                                    .PAID:
                                bill.setState(BillSateEnum.PAID);
                                break;
                            case AppConstants
                                    .CANCEL:
                                bill.setState(BillSateEnum.CANCEL);
                                break;
                        }
                        bills.add(bill);
                    }
                    callback.onDataSuccess(bills);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onDataFailed(R.string.something_went_wrong);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.onDataFailed(R.string.something_went_wrong);
    }

    /**
     * Phương thức lấy danh sách hóa đơn chi tiết từ firebase
     * Created at 25/04/2019
     *
     * @param callback - callback lắng nghe xử lý kết quả lấy danh sách hóa đơn chi tiết thành công/thất bại
     */
    public void getAllBillDetailFromFirebase(final IDataCallBack<List<BillDetail>> callback) {
        final List<BillDetail> billDetails = new ArrayList<>();
        try {
            mDatabaseReference.child(USERS).child(uuid).child(BILL_DETAILS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        BillDetail billDetail = new BillDetail.Builder()
                                .setBillDetailId(String.valueOf(childDataSnapshot.child(IDBUtils.ITableBillDetailUtils.COLUMN_BILL_DETAIL_ID).getValue()))
                                .setBillId(String.valueOf(childDataSnapshot.child(IDBUtils.ITableBillDetailUtils.COLUMN_BILL_ID).getValue()))
                                .setDishId(String.valueOf(childDataSnapshot.child(IDBUtils.ITableBillDetailUtils.COLUMN_DISH_ID).getValue()))
                                .setQuantity((int) (long) childDataSnapshot.child(IDBUtils.ITableBillDetailUtils.COLUMN_QUANTITY).getValue())
                                .setTotalMoney((int) (long) childDataSnapshot.child(IDBUtils.ITableBillDetailUtils.COLUMN_TOTAL_MONEY).getValue())
                                .build();
                        billDetails.add(billDetail);
                    }
                    callback.onDataSuccess(billDetails);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onDataFailed(R.string.something_went_wrong);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.onDataFailed(R.string.something_went_wrong);
    }

}

