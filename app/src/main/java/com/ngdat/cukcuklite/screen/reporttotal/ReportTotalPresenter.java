package com.ngdat.cukcuklite.screen.reporttotal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ngdat.cukcuklite.data.models.ParamReport;
import com.ngdat.cukcuklite.data.models.ReportDetail;
import com.ngdat.cukcuklite.data.models.ReportTotal;
import com.ngdat.cukcuklite.data.local.report.ReportDataSource;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo Số lượng
 * Created at 18/04/2019
 */
public class ReportTotalPresenter implements IReportTotalContract.IPresenter {

    private static final String TAG = ReportTotalPresenter.class.getName();

    private IReportTotalContract.IView mView;
    private Context mContext;
    private ReportDataSource mReportDataSource;

    public ReportTotalPresenter(IReportTotalContract.IView view, Context context) {
        mView = view;
        mContext = context;
        mReportDataSource = new ReportDataSource();
    }

    /**
     * Mục đích method: Tính tổng số tiền từ danh sách hóa đơn
     * Created at 18/04/2019
     *
     * @param reportDetails :danh sách báo cáo chi tiết
     * @return amount: tổng số tiền
     */
    private long getAmount(List<ReportDetail> reportDetails) {
        try {
            if (reportDetails != null) {
                long amount = 0;
                for (ReportDetail reportDetail : reportDetails) {
                    amount += reportDetail.getAmount();
                }
                return amount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Mục đích method: Lấy ra danh sách ReportTotal từ ParamReport
     * Created at 18/04/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadData(final ParamReport paramReport) {
        try {
            new AsyncTask<Void, Void, List<ReportTotal>>() {
                @Override
                protected List<ReportTotal> doInBackground(Void... voids) {
                    try {
                        Calendar calendar = Calendar.getInstance();
                        List<ReportTotal> reportTotals = new ArrayList<>();

                        switch (paramReport.getParamType()) {
                            case LAST_WEEK:
                                Log.d(TAG, "doInBackground: ");
                                reportTotals = mReportDataSource.getReportLastWeek();
                                break;
                            case THIS_WEEK:
                                reportTotals = mReportDataSource.getReportThisWeek();
                                break;
                            case LAST_MONTH:
                                reportTotals = mReportDataSource.getReportLastMonth();
                                break;
                            case THIS_MONTH:
                                reportTotals = mReportDataSource.getReportThisMonth();
                                break;
                            case LAST_YEAR:
                                reportTotals = mReportDataSource.getReportLastYear();
                                break;
                            case THIS_YEAR:
                                reportTotals = mReportDataSource.getReportThisYear();
                                break;
                        }
                        return reportTotals;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new ArrayList<>();
                }

                @Override
                protected void onPostExecute(List<ReportTotal> reportTotals) {
                    super.onPostExecute(reportTotals);
                    mView.onLoadDataDone(reportTotals);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
