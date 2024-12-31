package com.ngdat.cukcuklite.screen.sale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.bill.BillDataSource;

public class SalePresenter implements ISaleContract.IPresenter {

    private ISaleContract.IView mView;
    private BillDataSource mBillDataSource;

    public SalePresenter() {
        mBillDataSource = BillDataSource.getInstance();
    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 15/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(ISaleContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 15/04/2019
     */
    @Override
    public void onStart() {
        try {
            mView.showLoading();
            mView.showListOrder(mBillDataSource.getAllOrder());
            mView.hideLoading();
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
     * Phương thức hủy order
     * Created at 15/04/2019
     *
     * @param billId - hóa đơn id
     */
    @Override
    public void cancelOrder(String billId) {
        try {
            if (mBillDataSource.cancelOrder(billId)) {
                onStart();
            } else {
                mView.receiveMessage(R.string.something_went_wrong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
