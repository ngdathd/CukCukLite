package com.ngdat.cukcuklite.screen.report;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.ParamReport;

/**
 * MVP interface cho màn hình báo cáo
 * Created at 18/04/2019
 */
public interface IReportContract {
    interface IView extends IBaseView {
        void setParamReport(List<ParamReport> paramReports);
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
