package com.ngdat.cukcuklite.screen.adddish;

import com.ngdat.cukcuklite.CukCukLiteApplication;
import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.dish.DishDataSource;
import com.ngdat.cukcuklite.data.local.unit.UnitDataSource;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.models.Unit;
import com.ngdat.cukcuklite.data.service.FirebaseManager;
import com.ngdat.cukcuklite.data.service.IFirebaseRealTime;
import com.ngdat.cukcuklite.utils.CommonsUtils;

/**
 * Presenter màn hình thêm món ăn
 * Created at 19/04/2019
 */
public class AddDishPresenter implements IAddDishContract.IPresenter {
    private static final String TAG = "AddDishPresenter";
    private IAddDishContract.IView mView;
    private DishDataSource mDishDataSource;
    private UnitDataSource mUnitDataSource;
    private FirebaseManager mFirebaseManager;

    /**
     * Phương thức khởi tạo AddDishPresenter
     * Created at 27/03/2019
     */
    AddDishPresenter() {
        mDishDataSource = DishDataSource.getInstance();
        mUnitDataSource = UnitDataSource.getInstance();
        mFirebaseManager = FirebaseManager.getInstance();
    }

    /**
     * Phương thức thêm mới món ăn
     * Created at 27/03/2019
     *
     * @param dish - món ăn
     */
    @Override
    public void addDish(Dish dish) {
        try {
            if (dish.getDishName().equals("")) {
                mView.dishNameEmpty();
                return;
            }
            if (!dish.getDishId().isEmpty() && dish.getDishId() != null) {
                switch (mDishDataSource.addDishToDatabase(dish)) {
                    case Exists:
                        mView.addDishFailed(R.string.dish_name_is_exists);
                        break;
                    case Success:
                        mView.addDishSuccess();
                        if (CommonsUtils.isNetworkAvailable(CukCukLiteApplication.getInstance())) {
                            mFirebaseManager.setDishToFirebase(dish, new IFirebaseRealTime.IFirebaseDataCallBack() {
                                @Override
                                public void onSuccess() {
                                    //
                                }

                                @Override
                                public void onFailed() {
                                    //
                                }
                            });
                        } else {
                            //lưu dữ liệu vào bảng dữ liệu lưu trữ
                        }
                        break;
                    case SomethingWentWrong:
                        mView.addDishFailed(R.string.something_went_wrong);
                        break;
                }
            } else {
                mView.addDishFailed(R.string.something_went_wrong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức chỉnh sửa món ăn
     * Created at 27/03/2019
     *
     * @param dish - món ăn
     */
    @Override
    public void updateDish(Dish dish) {
        try {
            if (dish.getDishName().equals("")) {
                mView.dishNameEmpty();
                return;
            }
            if (!dish.getDishId().isEmpty() && dish.getDishId() != null) {
                switch (mDishDataSource.updateDishToDatabase(dish)) {
                    case Exists:
                        mView.receiveMessage(R.string.dish_name_is_exists);
                        break;
                    case Success:
                        mView.upDateDishSuccess();
                        if (CommonsUtils.isNetworkAvailable(CukCukLiteApplication.getInstance())) {
                            mFirebaseManager.setDishToFirebase(dish, new IFirebaseRealTime.IFirebaseDataCallBack() {
                                @Override
                                public void onSuccess() {
                                    //
                                }

                                @Override
                                public void onFailed() {
                                    //
                                }
                            });
                        } else {
                            //lưu dữ liệu vào bảng dữ liệu lưu trữ
                        }
                        break;
                    case SomethingWentWrong:
                        mView.receiveMessage(R.string.something_went_wrong);
                        break;
                }
            } else {
                mView.addDishFailed(R.string.something_went_wrong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xóa món ăn
     * Created at 27/03/2019
     *
     * @param dishId - id của món ăn
     */
    @Override
    public void deleteDish(String dishId) {
        if (mDishDataSource.deleteDishById(dishId)) {
            if (CommonsUtils.isNetworkAvailable(CukCukLiteApplication.getInstance())) {
                mFirebaseManager.removeDishAtFirebase(dishId, new IFirebaseRealTime.IFirebaseDataCallBack() {
                    @Override
                    public void onSuccess() {
                        //
                    }

                    @Override
                    public void onFailed() {
                        //
                    }
                });
            } else {
                //lưu dữ liệu vào bảng dữ liệu lưu trữ
            }
//            mFireStoreManager.removeDocument("users/" + FirebaseAuth.getInstance().getUid()
//                    + "/dish", dishId, new IFirebaseResponse.IComplete() {
//                @Override
//                public void onSuccess(String documentId) {
//
//                }
//
//                @Override
//                public void onFailed() {
//
//                }
//            });
            mView.deleteDishSuccess();
        } else {
            mView.receiveMessage(R.string.dish_is_using);
        }
    }

    /**
     * Phương thức lấy tên đơn vị qua Id
     * Created at 09/04/2019
     *
     * @param unitId - id của đơn vị
     * @return - tên đơn vị
     */
    @Override
    public String getUnitName(String unitId) {
        String unitName = "";
        try {
            if (unitId != null) {
                unitName = mUnitDataSource.getUnitNameById(unitId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitName;
    }


    /**
     * Phương thức đặt view cho presenter
     * Created at 09/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IAddDishContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 09/04/2019
     */
    @Override
    public void onStart() {
        //gán đơn vị mặc định cho món ăn khi vào tính năng thêm mới
        try {
            Unit unit = mUnitDataSource.getAllUnit().get(0);
            mView.setUnit(unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 09/04/2019
     */
    @Override
    public void onStop() {

    }
}
