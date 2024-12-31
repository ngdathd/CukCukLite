package com.ngdat.cukcuklite.screen.reportdetail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.models.ReportDetail;
import com.ngdat.cukcuklite.utils.AppConstants;


/**
 * Màn báo cáo chi tiết
 * Created at 18/04/2019
 */
public class ReportDetailFragment extends Fragment implements IReportDetailContract.IView {
    private static final String TAG = ReportDetailFragment.class.getName();
    private IReportDetailContract.IPresenter mPresenter;
    private Date[] dates;
    private ReportDetailAdapter mAdapter;
    private PieChart mPieChart;
    private int[] colors;


    /**
     * Mục đích method: Khởi tạo ReportDetailFragment;
     * Created at 18/04/2019
     *
     * @param dates: Mảng Date chứa ngày bắt đầu và ngày kết thúc
     * @return reportTotalFragment
     */
    public static ReportDetailFragment newInstance(Date[] dates) {
        ReportDetailFragment reportTotalFragment = new ReportDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.EXTRA_DATES, dates);
        reportTotalFragment.setArguments(args);
        return reportTotalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_detail, container, false);
        initView(view);
        return view;
    }

    /**
     * Chỉnh thông số cho PieChart
     * Created at 18/04/2019
     */
    private void setupPieChart(List<ReportDetail> reportDetails) {
        try {
            long amount = 0;
            mPieChart.setUsePercentValues(true);
            mPieChart.setDescription(null);
            float margin = 2;
            mPieChart.setExtraOffsets(margin, margin, margin, margin);
            mPieChart.setHoleRadius(65.0f);
            mPieChart.setDrawCenterText(true);
            mPieChart.setRotationAngle(0.0f);
            mPieChart.setRotationEnabled(false);
            mPieChart.setHighlightPerTapEnabled(false);

            List<PieEntry> entries = new ArrayList<>();
            for (ReportDetail reportDetail : reportDetails) {
                amount += (reportDetail.getAmount());
                PieEntry pieEntry = new PieEntry((float) reportDetail.getAmount());
                entries.add(pieEntry);
            }
            PieDataSet pieDataSet = new PieDataSet(entries, null);
            pieDataSet.setColors(getResources().getIntArray(R.array.color_report));
            pieDataSet.setValueTextSize(12);
            pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData data = new PieData(pieDataSet);
            data.setValueFormatter(new PercentFormatter(mPieChart));

            mPieChart.setData(data);

            String textCenter = getString(R.string.totalRevenue);
            String textTotalRevenue = NumberFormat.getNumberInstance(Locale.US).format(amount);
            SpannableString spannableString = new SpannableString(textCenter + "\n" + textTotalRevenue);
            spannableString
                    .setSpan(new RelativeSizeSpan(1.8f), textCenter.length() + 1, spannableString.length(),
                            0);
            spannableString
                    .setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)),
                            textCenter.length() + 1, spannableString.length(), 0);

            spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, textCenter.length(), 0);
            spannableString
                    .setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlackLight)), 0,
                            textCenter.length(), 0);

            mPieChart.setCenterText(spannableString);
            mPieChart.animateY(1400);
            mPieChart.getLegend().setEnabled(false);
            mPieChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 18/04/2019
     */
    private void initView(View view) {
        try {
            Bundle args = getArguments();
            if (args != null) {
                dates = (Date[]) args.getSerializable(AppConstants.EXTRA_DATES);
            }
            mPieChart = view.findViewById(R.id.pieChart);
            mPresenter = new ReportDetailPresenter();
            mPresenter.setView(this);
            mPresenter.loadData(dates);
            mAdapter = new ReportDetailAdapter(getContext(), new ArrayList<ReportDetail>());
            RecyclerView rvReport = view.findViewById(R.id.rvReport);
            rvReport.setAdapter(mAdapter);
            rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * gán data và adapter và cặp nhật lại PieChart khi load dữ liêu thành công
     * Created at 18/04/2019
     *
     * @param reportDetails: Danh sách ReportDetail
     */
    @Override
    public void onLoadDataDone(List<ReportDetail> reportDetails) {
        try {
            if (reportDetails != null) {
                List<ReportDetail> reportDetailsValid = getValidReportDetail(reportDetails);
                if (reportDetailsValid != null) {
                    mAdapter.setData(reportDetailsValid);
                    setupPieChart(reportDetailsValid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy danh sách hợp lệ. Đối với các món ăn được bán
     * nhỏ hơn 3% tổng tiền đã bán đươc thì sẽ gom lại thành 1 loại.
     * Created at 23/04/2019
     *
     * @param reportDetails - danh sách món ăn hợp lệ
     */
    private List<ReportDetail> getValidReportDetail(List<ReportDetail> reportDetails) {
        try {
            long amount = 0;
            for (ReportDetail reportDetail : reportDetails) {
                amount += (reportDetail.getAmount());
            }
            long threePercent = 3 * (amount / 100);
            int size = reportDetails.size();
            List<ReportDetail> reportDetailsValid = new ArrayList<>();
            List<ReportDetail> reportDetailsInvalid = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                long amountItem = reportDetails.get(i).getAmount();
                if (amountItem < threePercent) {
                    reportDetailsInvalid.add(reportDetails.get(i));
                } else {
                    reportDetailsValid.add(reportDetails.get(i));
                }
            }
            ReportDetail reportDetail = new ReportDetail();
            int sizeInvalid = reportDetailsInvalid.size();
            String name = AppConstants.DIFFERENT;
            long amountDiff = 0;
            int quantity = 0;
            String unit = AppConstants.UNIT;
            if (sizeInvalid > 1) {
                for (ReportDetail r : reportDetailsInvalid) {
                    amountDiff += r.getAmount();
                    quantity += r.getQuantity();
                }
                reportDetail.setName(name);
                reportDetail.setAmount(amountDiff);
                reportDetail.setQuantity(quantity);
                reportDetail.setUnit(unit);
                reportDetailsValid.add(reportDetail);
            }
            return reportDetailsValid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created at 18/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 18/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created at 18/04/2019
     */
    @Override
    public void hideLoading() {

    }
}
