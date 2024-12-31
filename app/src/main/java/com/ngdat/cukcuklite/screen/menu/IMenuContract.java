package com.ngdat.cukcuklite.screen.menu;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Dish;

/**
 * MVP interface cho màn hình thực đơn
 * Created at 18/04/2019
 */
public interface IMenuContract {
    interface IView extends IBaseView {
        void showDish(List<Dish> dishes);
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
