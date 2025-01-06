package com.ngdat.cukcuklite.screen.dishorder;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.ngdat.cukcuklite.data.local.bill.BillDataSource;
import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;

/**
 * Presenter cho màn hình thêm/sửa order
 * Created at 18/04/2019
 */
public class DishOrderPresenter implements DishOrderContract.IPresenter {

    private static final String TAG = "DishOrderPresenter";
    private DishOrderContract.IView mView;
    private BillDataSource mBillDataSource;
    private String mBillId;

    /**
     * Phương thức khởi tạo presenter
     * Created at 16/04/2019
     *
     * @param billId - bill id có thể null khi cập nhật order
     */
    DishOrderPresenter(@Nullable String billId) {
        mBillDataSource = BillDataSource.getInstance();
        mBillDataSource.getAllOrder();
        mBillId = billId;
    }

    /**
     * Phương thức lưu trữ hóa đơn và danh sách hóa đơn chi tiết
     * Created at 12/04/2019
     *
     * @param bill        - hóa đơn
     * @param billDetails - danh sách hóa đơn chi tiết
     * @param isPayNow    - kiểm tra xem sau khi thêm có thanh toán luôn không
     */
    @Override
    public void saveOrder(Bill bill, List<BillDetail> billDetails, boolean isPayNow) {
        try {
            mView.showLoading();
            List<BillDetail> validBillDetailList = getValidBillDetailList(billDetails);
            if (validBillDetailList != null) {
                if (mBillDataSource.addBill(bill, validBillDetailList)) {
                    if (isPayNow) {
                        //nếu thanh toán luôn thì thanh toán
                        mView.pay(bill.getBillId());
                    } else {
                        mView.saveOrderSuccess();
                    }
                } else {
                    mView.saveOrderFailed();
                }
            }
            mView.hideLoading();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mView.saveOrderFailed();
    }

    /**
     * Phương thức gán danh sách món ăn order từ hóa đơn id
     * Created at 16/04/2019
     */
    @Override
    public void setListDishOrder(String billId) {
        try {
            mView.showLoading();
            if (billId != null) {
                List<BillDetail> billDetails = mBillDataSource.initBillDetailList(billId);
                if (billDetails != null) {
                    mView.showListDishOrder(billDetails);
                }
            }
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy hóa đơn thông qua hóa đơn id
     * Created at 16/04/2019
     *
     * @param billId - hóa đơn id
     */
    @Override
    public void getBillById(String billId) {
        try {
            Bill bill = mBillDataSource.getBillById(billId);
            if (bill != null) {
                mView.setBill(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật hóa đơn và danh sách hóa đơn chi tiết
     * Created at 12/04/2019
     *
     * @param bill        - hóa đơn
     * @param billDetails - danh sách hóa đơn chi tiết
     */
    @Override
    public void updateOrder(Bill bill, List<BillDetail> billDetails, boolean isPayNow) {
        try {
            mView.showLoading();
            List<BillDetail> validBillDetailList = getValidBillDetailList(billDetails);
            if (validBillDetailList != null) {
                if (mBillDataSource.updateBill(bill, validBillDetailList)) {
                    if (isPayNow) {
                        mView.pay(bill.getBillId());
                    } else {
                        mView.saveOrderSuccess();
                    }
                } else {
                    mView.saveOrderFailed();
                }
            }
            mView.hideLoading();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mView.saveOrderFailed();
    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 12/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(DishOrderContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 12/04/2019
     */
    @Override
    public void onStart() {
        try {
            mView.showLoading();
            if (mBillId != null) {
                List<BillDetail> billDetails = mBillDataSource.initNewBillDetailList(mBillId);
                if (billDetails != null) {
                    mView.showListDishOrder(billDetails);
                }
            }
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 12/04/2019
     */
    @Override
    public void onStop() {

    }

    private List<BillDetail> getValidBillDetailList(List<BillDetail> billDetails) {
        try {
            if (billDetails != null) {
                int size = billDetails.size();
                List<BillDetail> validBillDetailList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    //nếu như danh sách món ăn trong hóa đơn chi tiết có số lượng lớn hơn 0
                    //thì sẽ thêm vào danh sách món ăn trong hóa đơn chi tiết mới để lưu trữ
                    if (billDetails.get(i).getQuantity() > 0) {
                        validBillDetailList.add(billDetails.get(i));
                    }
                }
                return validBillDetailList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
