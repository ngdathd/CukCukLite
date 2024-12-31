package com.ngdat.cukcuklite.screen.dishorder;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;

/**
 * MVP interface cho màn hình thêm/sửa order
 * Created at 18/04/2019
 */
public interface DishOrderContract {
    interface IView extends IBaseView {

        void showListDishOrder(List<BillDetail> billDetails);

        void saveOrderSuccess();

        void saveOrderFailed();

        void setBill(Bill bill);

        void pay(String billId);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void saveOrder(Bill bill, List<BillDetail> billDetails, boolean isPayNow);

        void setListDishOrder(String billId);

        void getBillById(String billId);

        void updateOrder(Bill bill, List<BillDetail> billDetails, boolean isPayNow);
    }
}
