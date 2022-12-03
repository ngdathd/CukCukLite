package vn.misa.nadat.cukcuklite.ui.reportdetail;

import android.text.TextUtils;

import java.util.List;

import vn.misa.nadat.cukcuklite.database.DBItemReportManager;
import vn.misa.nadat.cukcuklite.items.ItemReportDetail;

/**
 * Presenter cho màn hình chi tiết báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
class ReportDetailPresenter implements IReportDetailContact.IPresenter {
    private IReportDetailContact.IView mIView;

    /**
     * Khởi tạo ReportDetailPresenter
     *
     * @param iView: interface IReportDetailContact.IView
     * @created_by nadat on 24/04/2019
     */
    ReportDetailPresenter(IReportDetailContact.IView iView) {
        try {
            mIView = iView;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy danh sách chi tiết báo cáo
     *
     * @param day ngày lấy
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void getItemReportDetails(String day) {
        try {
            if (!TextUtils.isEmpty(day)) {
                List<ItemReportDetail> listDetailReport = DBItemReportManager.getInstance().queryListDetailReport(day);
                mIView.showItemReportDetails(listDetailReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy danh sách chi tiết báo cáo
     *
     * @param startDate ngày bắt đầu lấy
     * @param endDate   ngày kết thúc lấy
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void getItemReportDetails(String startDate, String endDate) {
        try {
            if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
                List<ItemReportDetail> listDetailReport = DBItemReportManager.getInstance().queryListDetailReport(startDate, endDate);
                mIView.showItemReportDetails(listDetailReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
