package com.ngdat.cukcuklite.screen.reporttotal;

import java.util.List;

import com.ngdat.cukcuklite.data.models.ParamReport;
import com.ngdat.cukcuklite.data.models.ReportTotal;

/**
 * MVP interface cho màn hình báo cáo tổng quan
 * Created at 18/04/2019
 */
interface IReportTotalContract {

    interface IView {

        void onLoadDataDone(List<ReportTotal> reportTotals);
    }

    interface IPresenter {

        void loadData(ParamReport paramReport);
    }
}
