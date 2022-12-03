package vn.misa.nadat.cukcuklite.ui.report;

import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemReportTime;

/**
 * @created_by nadat on 24/04/2019
 */
public interface IReportContact {
    interface IView {
        void showDetailWeek(List<ItemReportTime> listReport);

        void showDetailMonth(List<ItemReportTime> listReport);

        void showDetailYear(List<ItemReportTime> listReport);

        void showNotificationError();
    }

    interface IPresenter {
        void getDetailWeek(String typeReport);

        void getDetailMonth(String typeReport);

        void getDetailYear(String typeReport);
    }
}
