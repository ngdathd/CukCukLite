package com.ngdat.cukcuklite.screen.reportdetail;

import java.util.Date;
import java.util.List;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;
import com.ngdat.cukcuklite.data.models.ReportDetail;

interface IReportDetailContract {

  interface IView extends IBaseView {

    void onLoadDataDone(List<ReportDetail> reportDetails);
  }

  interface IPresenter extends IBasePresenter<IView> {

    void loadData(Date[] dates);
  }
}
