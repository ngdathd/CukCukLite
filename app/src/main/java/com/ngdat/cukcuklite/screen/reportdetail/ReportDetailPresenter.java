package com.ngdat.cukcuklite.screen.reportdetail;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.ngdat.cukcuklite.data.local.dish.DishDataSource;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.models.ReportDetail;
import com.ngdat.cukcuklite.data.models.Unit;
import com.ngdat.cukcuklite.data.local.report.IReportDataSource;
import com.ngdat.cukcuklite.data.local.report.ReportDataSource;
import com.ngdat.cukcuklite.data.local.unit.UnitDataSource;
import com.ngdat.cukcuklite.utils.DateUtil;

public class ReportDetailPresenter implements IReportDetailContract.IPresenter {

    private static final String TAG = "ReportDetailPresenter";
    private IReportDetailContract.IView mView;
    private IReportDataSource mReportDataSource;
    private DishDataSource mDishDataSource;
    private UnitDataSource mUnitDataSource;

    public ReportDetailPresenter() {
        mReportDataSource = new ReportDataSource();
        mUnitDataSource = UnitDataSource.getInstance();
        mDishDataSource = DishDataSource.getInstance();
    }

    /**
     * Load dữ liệu từ 2 mốc thời gian
     * Created at 18/04/2019
     *
     * @param dates: mảng 2 mốc thời gian
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadData(final Date[] dates) {
        try {
            mView.showLoading();
            new AsyncTask<Void, Void, List<ReportDetail>>() {
                @Override
                protected List<ReportDetail> doInBackground(Void... voids) {
                    List<ReportDetail> reportDetails = mReportDataSource.getReportDetailByDate(DateUtil.getDateFormat(dates[0]),DateUtil.getDateFormat(dates[1]));
                    if (reportDetails != null) {
                        int size = reportDetails.size();
                        List<Dish> dishes = mDishDataSource.getAllDish();
                        List<Unit> units = mUnitDataSource.getAllUnit();
                        int dishSize;
                        int unitSize;
                        if (dishes != null && units != null) {
                            dishSize = dishes.size();
                            unitSize = units.size();
                            if (dishSize > 0 && unitSize > 0) {
                                for (int i = 0; i < size; i++) {
                                    String dishId = reportDetails.get(i).getDishId();
                                    for (int j = 0; j < dishSize; j++) {
                                        if (dishes.get(j).getDishId().equals(dishId)) {
                                            reportDetails.get(i).setName(dishes.get(j).getDishName());
                                            for (int k = 0; k < unitSize; k++) {
                                                if (units.get(k).getUnitId().equals(dishes.get(j).getUnitId())) {
                                                    reportDetails.get(i).setUnit(units.get(k).getUnitName());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        return makeListBeautiful(reportDetails);
                    } else {
                        return new ArrayList<>();
                    }
                }

                @Override
                protected void onPostExecute(List<ReportDetail> reportDetails) {
                    super.onPostExecute(reportDetails);
                    mView.onLoadDataDone(reportDetails);
                    mView.hideLoading();
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Sắp xếp lại list
     * Created at 18/04/2019
     *
     * @param reportDetails: mảng truyền vào
     */
    private List<ReportDetail> makeListBeautiful(final List<ReportDetail> reportDetails) {
        Collections.sort(reportDetails, new Comparator<ReportDetail>() {
            @Override
            public int compare(ReportDetail o1, ReportDetail o2) {
                return (int) (o2.getAmount() - o1.getAmount());
            }
        });
        return reportDetails;
    }

    /**
     * Phương thức đặt view cho presenter
     * Created at 18/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IReportDetailContract.IView view) {
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
