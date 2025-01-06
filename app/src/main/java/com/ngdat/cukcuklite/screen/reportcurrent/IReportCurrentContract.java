package com.ngdat.cukcuklite.screen.reportcurrent;

import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.ReportCurrent;

/**
 * Contract  trong mô hình MVP cho màn hình Báo cáo Gần đây
 * Created at 18/04/2019
 */
interface IReportCurrentContract {

    interface IView extends IBaseView {

        void onLoadReportCurrentDone(List<ReportCurrent> reportCurrents);
    }

    interface IPresenter extends IBasePresenter<IView> {

        void getListReportCurrent();
    }
}
