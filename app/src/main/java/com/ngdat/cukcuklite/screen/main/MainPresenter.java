package com.ngdat.cukcuklite.screen.main;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class MainPresenter implements IMainContract.IPresenter {

    private IMainContract.IView mView;
    private Context mContext;

    public MainPresenter(Context context) {
        mContext = context;
    }

    /**
     * Xóa toàn bộ dữ liệu app
     * Created at 25/04/2019
     */
    @Override
    public void clearData() {
        try {
            FirebaseAuth.getInstance().signOut();
//            SharedPrefersManager.getInstance(mContext).setIsLoginSuccess(false);
//            SharedPrefersManager.getInstance(mContext).setAlreadyHasData(false);
//            SQLiteDBManager.getInstance().clearDatabase();
//            UnitDataSource.getInstance().removeAllCache();
//            DishDataSource.getInstance().removeAllCache();
//            BillDataSource.getInstance().removeAllCache();
            mView.goToLoginScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 25/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IMainContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 25/04/2019
     */
    @Override
    public void onStart() {

    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 25/04/2019
     */
    @Override
    public void onStop() {

    }
}
