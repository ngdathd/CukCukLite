package vn.misa.nadat.cukcuklite.ui.reportdetail;

import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemReportDetail;

/**
 * @created_by nadat on 24/04/2019
 */
public interface IReportDetailContact {
    interface IView {
        void showItemReportDetails(List<ItemReportDetail> itemReportDetails);
    }

    interface IPresenter {
        void getItemReportDetails(String day);

        void getItemReportDetails(String startDate, String endDate);
    }
}
