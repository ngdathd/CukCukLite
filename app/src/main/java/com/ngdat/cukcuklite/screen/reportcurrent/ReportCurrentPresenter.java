package com.ngdat.cukcuklite.screen.reportcurrent;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import com.ngdat.cukcuklite.data.local.report.IReportDataSource;
import com.ngdat.cukcuklite.data.local.report.ReportDataSource;
import com.ngdat.cukcuklite.data.models.ReportCurrent;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo
 * Created at 18/04/2019
 */
public class ReportCurrentPresenter implements IReportCurrentContract.IPresenter {

    private static final String TAG = ReportCurrentPresenter.class.getName();
    IReportDataSource mDataReportSource;
    private IReportCurrentContract.IView mView;

    public ReportCurrentPresenter() {
        mDataReportSource = new ReportDataSource();
    }

    /**
     * Trả về danh sách ReportCurrent
     * <p>
     * Created at 18/04/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getListReportCurrent() {
        try {
            mView.showLoading();
            new AsyncTask<Void, Void, List<ReportCurrent>>() {
                @Override
                protected List<ReportCurrent> doInBackground(Void... voids) {
                    List<ReportCurrent> reportCurrentList = mDataReportSource.getOverviewReport();
                    if (reportCurrentList != null && reportCurrentList.size() > 0) {
                        return reportCurrentList;
                    } else {
                        return new ArrayList<>();
                    }
                }

                @Override
                protected void onPostExecute(List<ReportCurrent> reportCurrents) {
                    super.onPostExecute(reportCurrents);
                    mView.onLoadReportCurrentDone(reportCurrents);
                    mView.hideLoading();
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 18/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IReportCurrentContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created at 18/04/2019
     */
    @Override
    public void onStart() {

    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created at 18/04/2019
     */
    @Override
    public void onStop() {

    }
}
