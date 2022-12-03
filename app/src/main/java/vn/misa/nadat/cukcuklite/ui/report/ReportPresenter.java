package vn.misa.nadat.cukcuklite.ui.report;

import java.util.List;

import vn.misa.nadat.cukcuklite.database.DBItemReportManager;
import vn.misa.nadat.cukcuklite.items.ItemReportTime;

/**
 * Presenter của ReportFragment
 *
 * @created_by nadat on 24/04/2019
 */
class ReportPresenter implements IReportContact.IPresenter {
    private IReportContact.IView mIView;

    /**
     * Khởi tạo ReportPresenter
     *
     * @param iView: interface IReportContact.IView
     * @created_by nadat on 24/04/2019
     */
    ReportPresenter(IReportContact.IView iView) {
        mIView = iView;
    }

    /**
     * Lấy ra báo cáo trong tuần
     *
     * @param typeReport: loại tuần
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void getDetailWeek(String typeReport) {
        List<ItemReportTime> itemReportTimes = DBItemReportManager.getInstance().getDetailWeek(typeReport);
        if (itemReportTimes == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            mIView.showDetailWeek(itemReportTimes);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Lấy ra báo cáo trong tháng.
     *
     * @param typeReport: loại tháng
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void getDetailMonth(String typeReport) {
        List<ItemReportTime> listItemReportTime = DBItemReportManager.getInstance().getDetailMonth(typeReport);
        if (listItemReportTime == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            mIView.showDetailMonth(listItemReportTime);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }

    /**
     * Lấy ra báo cáo trong năm
     *
     * @param typeReport: loại năm
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void getDetailYear(String typeReport) {
        List<ItemReportTime> listItemReportTime = DBItemReportManager.getInstance().getDetailYear(typeReport);
        if (listItemReportTime == null) {
            mIView.showNotificationError();
            return;
        }
        try {
            mIView.showDetailYear(listItemReportTime);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }
}
