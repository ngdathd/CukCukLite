package vn.misa.nadat.cukcuklite.ui.reportcurrent;

import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cukcuklite.CukCukLiteApp;
import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.database.DBItemReportManager;
import vn.misa.nadat.cukcuklite.items.ItemReportCurrent;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.DateUtils;

class ReportCurrentPresenter implements IReportCurrentContract.IPresenter {
    private IReportCurrentContract.IView mIView;

    ReportCurrentPresenter(IReportCurrentContract.IView IView) {
        mIView = IView;
    }

    @Override
    public void getItemReportCurrents() {
        try {
            List<ItemReportCurrent> itemReportCurrents = new ArrayList<>();
            String[] yesterday = DateUtils.getInstance().getYesterday();
            String[] today = DateUtils.getInstance().getToday();
            String[] thisWeek = DateUtils.getInstance().getThisWeek();
            String[] thisMonth = DateUtils.getInstance().getThisMonth();
            String[] thisYear = DateUtils.getInstance().getThisYear();
            String[] color = CukCukLiteApp.getInstance().getResources().getStringArray(R.array.color_report);

            itemReportCurrents.add(new ItemReportCurrent(R.drawable.ic_calendar_yesterday, color[0], Constant.YESTERDAY, DBItemReportManager.getInstance().getItemReportsRevenue(yesterday[0], yesterday[1])));
            itemReportCurrents.add(new ItemReportCurrent(R.drawable.ic_calendar_today, color[1], Constant.TODAY, DBItemReportManager.getInstance().getItemReportsRevenue(today[0], today[1])));
            itemReportCurrents.add(new ItemReportCurrent(R.drawable.ic_calendar_week, color[2], Constant.THISWEEK, DBItemReportManager.getInstance().getItemReportsRevenue(thisWeek[0], thisWeek[1])));
            itemReportCurrents.add(new ItemReportCurrent(R.drawable.ic_calendar_month, color[3], Constant.THISMONTH, DBItemReportManager.getInstance().getItemReportsRevenue(thisMonth[0], thisMonth[1])));
            itemReportCurrents.add(new ItemReportCurrent(R.drawable.ic_calendar_year, color[4], Constant.THISYEAR, DBItemReportManager.getInstance().getItemReportsRevenue(thisYear[0], thisYear[1])));

            mIView.showItemReportCurrents(itemReportCurrents);
        } catch (Exception e) {
            e.printStackTrace();
            mIView.showNotificationError();
        }
    }
}
