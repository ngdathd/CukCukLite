package com.ngdat.cukcuklite.screen.sale;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Order;

public interface ISaleContract {
    interface IView extends IBaseView {
        void showListOrder(List<Order> orders);
    }

    interface IPresenter extends IBasePresenter<IView> {

        void cancelOrder(String billId);
    }
}
