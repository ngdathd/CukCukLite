package com.ngdat.cukcuklite.screen.chooserestauranttype;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.cukcukenum.ResultEnum;
import com.ngdat.cukcuklite.data.local.dish.DishDataSource;
import com.ngdat.cukcuklite.data.local.unit.UnitDataSource;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.models.RestaurantType;
import com.ngdat.cukcuklite.data.models.Unit;
import com.ngdat.cukcuklite.data.service.FirebaseManager;
import com.ngdat.cukcuklite.data.service.IFirebaseRealTime;
import com.ngdat.cukcuklite.utils.CommonsUtils;

/**
 * Presenter cho màn hình chọn nhà hàng mặc định
 * Created at 19/04/2019
 */
public class ChooseRestaurantTypePresenter implements IChooseRestaurantTypeContract.IPresenter {

    private IChooseRestaurantTypeContract.IView mView;
    private Context mContext;
    private UnitDataSource mUnitDataSource;
    private DishDataSource mDishDataSource;
    private FirebaseManager mFirebaseManager;

    ChooseRestaurantTypePresenter(Context context) {
        mContext = context;
        mUnitDataSource = UnitDataSource.getInstance();
        mDishDataSource = DishDataSource.getInstance();
        mFirebaseManager = FirebaseManager.getInstance();
    }

    /**
     * Đặt view cho presenter
     * Created at 09/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IChooseRestaurantTypeContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 05/04/2019
     */
    @Override
    public void onStart() {
        mView.showLoading();
        //lấy danh sách loại quán ăn/nhà hàng và hiển thị ra màn hình
        try {
            List<RestaurantType> restaurantTypes = getListRestaurant();
            if (restaurantTypes != null) {
                mView.hideLoading();
                mView.showListRestaurantType(restaurantTypes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 05/04/2019
     */
    @Override
    public void onStop() {

    }

    /**
     * Phương thức thêm các đơn vị vào cơ sở dữ liệu
     * Created at 09/04/2019
     *
     * @param units - danh sách các đơn vị
     */
    @Override
    public void insertAllUnit(List<Unit> units) {
        try {
            if (units != null) {
                if (mUnitDataSource.deleteAllUnit()) {
                    int unitSize = units.size();
                    //mFirebaseManager.clearAllDataOfUser();
                    mFirebaseManager.addUnitsToFirebase(units, new IFirebaseRealTime.IFirebaseDataCallBack() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailed() {

                        }
                    });
                    for (int i = 0; i < unitSize; i++) {
                        //thêm vào sql local
                        if (!mUnitDataSource.addUnit(units.get(i))) {
                            mView.receiveMessage(R.string.something_went_wrong);
                            break;
                        }
                    }
                }
            } else {
                mView.receiveMessage(R.string.something_went_wrong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm các món ăn vào cơ sở dữ liệu
     * Created at 09/04/2019
     *
     * @param dishes - danh sách các đơn vị
     */
    @Override
    public void insertAllDish(List<Dish> dishes) {
        mView.showLoading();
        boolean insertSuccess = true;
        if (dishes != null) {
            //xóa các món ăn đã có trong cơ sở dữ liệu
            if (mDishDataSource.deleteAllDish()) {
                int size = dishes.size();
                if (size > 0) {
                    mFirebaseManager.addDishesToFirebase(dishes, new IFirebaseRealTime.IFirebaseDataCallBack() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailed() {

                        }
                    });
                    //Thêm các món ăn váo cơ sở dữ liệu
                    for (int i = 0; i < size; i++) {
                        ResultEnum result = mDishDataSource.addDishToDatabase(dishes.get(i));
                        if (ResultEnum.Failed == result
                                || ResultEnum.SomethingWentWrong == result) {
                            mView.receiveMessage(R.string.something_went_wrong);
                            insertSuccess = false;
                            break;
                        }
                    }
                }
            } else {
                mView.receiveMessage(R.string.something_went_wrong);
            }
        }
        mView.hideLoading();
        if (insertSuccess) {
            mView.showDishDefaultActivity();
        }
    }

    /**
     * Phương thức lấy danh sách nhà hàng/quán ăn mặc định cho ứng dụng
     * Created at 05/04/2019
     */
    private List<RestaurantType> getListRestaurant() {
        try {
            String jsonRestaurantType = CommonsUtils.loadJSONFromAsset(mContext, "jsons/restaurant_type.json");
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<RestaurantType>>() {
            }.getType();
            return gson.fromJson(jsonRestaurantType, collectionType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
