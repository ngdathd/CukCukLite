package com.ngdat.cukcuklite.screen.report;

import java.util.ArrayList;
import java.util.List;

import com.ngdat.cukcuklite.data.cukcukenum.ParamReportEnum;
import com.ngdat.cukcuklite.data.models.ParamReport;

/**
 * Presenter cho màn hình báo cáo
 * Created at 18/04/2019
 */
public class ReportPresenter implements IReportContract.IPresenter {

    private IReportContract.IView mView;

    /**
     * Phương thức đặt view cho presenter
     * Created at 18/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IReportContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 18/04/2019
     */
    @Override
    public void onStart() {
        try {
            if (mView != null) {
                //gán danh sách mốc thời gian mặc định
                mView.setParamReport(getListParam());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 18/04/2019
     */
    @Override
    public void onStop() {

    }


    /**
     * Mục đích method: Lấy danh sách mốc thời gian mặc định
     * Created at 18/04/2019
     */
    private List<ParamReport> getListParam() {
        List<ParamReport> paramReports = new ArrayList<>();
        paramReports.add(new ParamReport(ParamReportEnum.CURRENT));
        paramReports.add(new ParamReport(ParamReportEnum.THIS_WEEK));
        paramReports.add(new ParamReport(ParamReportEnum.LAST_WEEK));
        paramReports.add(new ParamReport(ParamReportEnum.THIS_MONTH));
        paramReports.add(new ParamReport(ParamReportEnum.LAST_MONTH));
        paramReports.add(new ParamReport(ParamReportEnum.THIS_YEAR));
        paramReports.add(new ParamReport(ParamReportEnum.LAST_YEAR));
        paramReports.add(new ParamReport(ParamReportEnum.OTHER));
        paramReports.get(0).setSelected(true);
        return paramReports;
    }
}
