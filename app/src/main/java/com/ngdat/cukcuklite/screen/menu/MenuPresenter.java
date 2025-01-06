package com.ngdat.cukcuklite.screen.menu;

import com.ngdat.cukcuklite.data.local.dish.DishDataSource;
import com.ngdat.cukcuklite.data.models.Dish;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Presenter cho màn hình menu
 */
public class MenuPresenter implements IMenuContract.IPresenter {
    private IMenuContract.IView mView;
    private DishDataSource mDishDataSource;

    /**
     * Phương thức khởi tạo presenter
     * Created at 27/03/2019
     */
    MenuPresenter() {
        mDishDataSource = DishDataSource.getInstance();
    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 10/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IMenuContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 10/04/2019
     */
    @Override
    public void onStart() {
        //lấy danh sách các món ăn và hiển thị ra màn hình
        try {
            mView.showLoading();
            final List<Dish> dishes = mDishDataSource.getAllDish();
            if (dishes != null) {
                Collections.sort(dishes, new Comparator<Dish>() {
                    @Override
                    public int compare(Dish o1, Dish o2) {
                        return o1.getDishName().compareToIgnoreCase(o2.getDishName());
                    }
                });
                mView.showDish(dishes);
            }
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 10/04/2019
     */
    @Override
    public void onStop() {

    }
}
