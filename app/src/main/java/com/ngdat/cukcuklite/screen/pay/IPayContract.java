package com.ngdat.cukcuklite.screen.pay;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;

public interface IPayContract {
    interface IView extends IBaseView {

        void paySuccess();

        void setBill(Bill bill, List<BillDetail> billDetailList, int billNumber);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void pay(Bill bill);
    }
}
