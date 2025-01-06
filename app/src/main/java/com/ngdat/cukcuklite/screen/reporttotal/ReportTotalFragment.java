package com.ngdat.cukcuklite.screen.reporttotal;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.cukcukenum.ReportTotalEnum;
import com.ngdat.cukcuklite.data.models.ParamReport;
import com.ngdat.cukcuklite.data.models.ReportTotal;
import com.ngdat.cukcuklite.screen.reportdetail.ReportDetailActivity;

/**
 * Mục đích Class :Màn hình Báo cáo gần đây
 * Created at 18/04/2019
 */
public class ReportTotalFragment extends Fragment implements IReportTotalContract.IView,
        ReportTotalAdapter.OnClickItemTotalReport {

    public static final String EXTRA_PARAM_REPORT = "EXTRA_PARAM_REPORT";
    private static final String TAG = "ReportTotalFragment";
    private IReportTotalContract.IPresenter mPresenter;
    private ReportTotalAdapter mAdapter;
    private ParamReport paramReport;
    private LineChart mLineChart;
    private TextView tvXValDescription;


    /**
     * Mục đích method: Khởi tạo ReportTotalFragment;
     * Created at 18/04/2019
     *
     * @param paramReport: ParamReport
     * @return reportTotalFragment
     */
    public static ReportTotalFragment newInstance(ParamReport paramReport) {
        ReportTotalFragment reportTotalFragment = new ReportTotalFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PARAM_REPORT, paramReport);
        reportTotalFragment.setArguments(args);
        return reportTotalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_total, container, false);
        Bundle args = getArguments();
        paramReport = (ParamReport) args.getSerializable(EXTRA_PARAM_REPORT);
        initViews(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadData(paramReport);
    }

    /**
     * Chỉnh thông số cho LineChart
     * Created at 18/04/2019
     */
    private void setupLineChart(List<ReportTotal> reportTotals) {
        try {
            mLineChart = getView().findViewById(R.id.lineChart);
            ReportTotalEnum type = reportTotals.get(0).getType();
            ArrayList<Entry> list = new ArrayList<>();
            for (int i = 0; i < reportTotals.size(); i++) {
                Entry entry = new Entry(i + 1, (float) reportTotals.get(i).getAmount() / 1000);
                list.add(entry);
            }
            LineDataSet dataSet = new LineDataSet(list, null);
            dataSet.setValueTextSize(0f);
            dataSet.setCircleColor(Color.TRANSPARENT);
            dataSet.setCircleHoleColor(getResources().getColor(R.color.color_line_chart));
            dataSet.setCircleHoleRadius(2.5f);
            dataSet.setColor(getResources().getColor(R.color.color_line_chart));
            LineData lineData = new LineData(dataSet);
            mLineChart.setDrawGridBackground(false);
            mLineChart.setDescription(null);
            mLineChart.setTouchEnabled(false);
            XAxis xAxis = mLineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            switch (type) {
                case WEEK:
                    tvXValDescription.setText(getString(R.string.day_of_week));
                    xAxis.setAxisMinimum(1f);
                    xAxis.setAxisMaximum(7f);
                    xAxis.setLabelCount(7, true);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            if (value == 7) {
                                return "CN";
                            }
                            return "T" + ((int) value + 1);
                        }
                    });
                    break;
                case MONTH:
                    tvXValDescription.setText(getString(R.string.day));
                    xAxis.setAxisMinimum(1f);
                    xAxis.setAxisMaximum(31f);
                    xAxis.setLabelCount(11, true);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            return String.valueOf((int) value);
                        }
                    });
                    break;
                case YEAR:
                    tvXValDescription.setText(getString(R.string.month));
                    xAxis.setAxisMinimum(1f);
                    xAxis.setAxisMaximum(12f);
                    xAxis.setLabelCount(12, true);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            return String.valueOf((int) value);
                        }
                    });
                    break;
            }

            YAxis axisLeft = mLineChart.getAxisLeft();
            axisLeft.enableGridDashedLine(5.0f, 5.0f, 0.0f);
            axisLeft.setAxisLineColor(Color.TRANSPARENT);
            axisLeft.setAxisMinimum(0f);
            mLineChart.getLegend().setEnabled(false);
            mLineChart.getAxisRight().setEnabled(false);
            mLineChart.setData(lineData);
            mLineChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 18/04/2019
     */
    private void initViews(View v) {
        try {
            mPresenter = new ReportTotalPresenter(this, getContext());
            mAdapter = new ReportTotalAdapter(getContext(), new ArrayList<ReportTotal>());
            mAdapter.setOnClickItemTotalReport(this);
            RecyclerView rvReport = v.findViewById(R.id.rvReport);
            tvXValDescription = v.findViewById(R.id.tvXValDescription);
            rvReport.setAdapter(mAdapter);
            rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add data và adapter và cặp nhật lại LineChart khi load dữ liêu thành công
     * Created at 18/04/2019
     *
     * @param reportTotals: Danh sách ReportTotal
     */
    @Override
    public void onLoadDataDone(List<ReportTotal> reportTotals) {
        try {
            if (reportTotals != null) {
                setupLineChart(reportTotals);
                mAdapter.setData(reportTotals);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Xử lý sự kiện
     * Created at 18/04/2019
     */
    @Override
    public void onClickItem(ReportTotal reportTotal) {
        startActivity(ReportDetailActivity.getIntent(getContext(), reportTotal));
    }
}
