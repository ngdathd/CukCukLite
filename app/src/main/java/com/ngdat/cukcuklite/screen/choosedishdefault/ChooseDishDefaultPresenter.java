package com.ngdat.cukcuklite.screen.choosedishdefault;

import com.ngdat.cukcuklite.data.local.dish.DishDataSource;

/**
 * Presenter xử lý logic cho màn hình lựa chọn món ăn mặc định
 * Created at 18/04/2019
 */
public class ChooseDishDefaultPresenter implements IChooseDishDefaultContract.IPresenter {

    private IChooseDishDefaultContract.IView mView;
    private DishDataSource mDishDataSource;

    /**
     * Phương thức khởi tạo lớp
     * Created at 18/04/2019
     */
    ChooseDishDefaultPresenter() {
        mDishDataSource = DishDataSource.getInstance();
    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 09/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IChooseDishDefaultContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 09/04/2019
     */
    @Override
    public void onStart() {
        //lấy danh sách các món ăn và hiển thị ra màn hình
        try {
            mView.showDish(mDishDataSource.getAllDish());
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
