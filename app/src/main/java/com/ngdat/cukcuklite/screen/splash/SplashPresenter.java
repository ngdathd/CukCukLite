package com.ngdat.cukcuklite.screen.splash;

import android.content.Context;

import com.ngdat.cukcuklite.data.local.database.SQLiteDBManager;
import com.ngdat.cukcuklite.data.local.dish.DishDataSource;
import com.ngdat.cukcuklite.data.local.unit.UnitDataSource;

public class SplashPresenter implements ISplashContract.IPresenter {

    private Context mContext;
    private ISplashContract.IView mView;

    public SplashPresenter(Context context) {
        mContext = context;
    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 09/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(ISplashContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 15/04/2019
     */
    @Override
    public void onStart() {
        try {
            //khởi tạo cấu trúc dữ liệu cho app
            initDataCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 15/04/2019
     */
    @Override
    public void onStop() {

    }

    /**
     * Phương thức khởi tạo dữ liệu cache cho ứng dụng
     * Created at 13/04/2019
     */
    private void initDataCache() {
        SQLiteDBManager.getInstance(mContext);
        DishDataSource.getInstance().getAllDish();
        UnitDataSource.getInstance().getAllUnit();
    }
}
