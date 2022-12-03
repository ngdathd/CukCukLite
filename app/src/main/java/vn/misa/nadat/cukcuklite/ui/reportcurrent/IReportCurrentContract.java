package vn.misa.nadat.cukcuklite.ui.reportcurrent;

import java.util.List;

import vn.misa.nadat.cukcuklite.items.ItemReportCurrent;

/**
 * @created_by nadat on 24/04/2019
 */
public interface IReportCurrentContract {
    interface IView {
        void showItemReportCurrents(List<ItemReportCurrent> itemReportCurrents);

        void showNotificationError();
    }

    interface IPresenter {
        void getItemReportCurrents();
    }
}
