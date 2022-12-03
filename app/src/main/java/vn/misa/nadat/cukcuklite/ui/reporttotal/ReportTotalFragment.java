package vn.misa.nadat.cukcuklite.ui.reporttotal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.ReportTotalAdapter;
import vn.misa.nadat.cukcuklite.items.ItemReportTime;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Màn hình báo cáo theo từng mục
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportTotalFragment extends Fragment {
    private ReportTotalAdapter mReportTotalAdapter;

    private ArrayList<ItemReportTime> mListItemReportTime;
    private String mTypeReport;

    private View view;
    private LineChart lineChart;
    private RecyclerView rvReport;
    private TextView tvYValDescription, tvXValDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report_total, container, false);

        initView();
        getData();
        init();

        return view;
    }

    /**
     * Lấy dữ liệu
     *
     * @created_by nadat on 24/04/2019
     */
    private void getData() {
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                mListItemReportTime = (ArrayList<ItemReportTime>) bundle.getSerializable(Constant.KEY_DATA_REPORT);
                mTypeReport = bundle.getString(Constant.KEY_TYPE_REPORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo giá trị ban đầu
     *
     * @created_by nadat on 24/04/2019
     */
    private void init() {
        try {
            mReportTotalAdapter = new ReportTotalAdapter(getContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
            rvReport.setLayoutManager(layoutManager);
            rvReport.setAdapter(mReportTotalAdapter);

            switch (mTypeReport) {
                case Constant.THISWEEK:
                    showDetailWeek(Constant.THISWEEK);
                    break;
                case Constant.LASTWEEK:
                    showDetailWeek(Constant.LASTWEEK);
                    break;
                case Constant.THISMONTH:
                    showDetailMonth(Constant.THISMONTH);
                    break;
                case Constant.LASTMONTH:
                    showDetailMonth(Constant.LASTMONTH);
                    break;
                case Constant.THISYEAR:
                    showDetailYear(Constant.THISYEAR);
                    break;
                case Constant.LASTYEAR:
                    showDetailYear(Constant.LASTYEAR);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ánh xạ view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initView() {
        try {
            lineChart = view.findViewById(R.id.lineChart);
            rvReport = view.findViewById(R.id.rvReport);
            tvYValDescription = view.findViewById(R.id.tvYValDescription);
            tvXValDescription = view.findViewById(R.id.tvXValDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo theo tuần
     *
     * @param typeReport kiểu báo cáo
     * @created_by nadat on 24/04/2019
     */
    public void showDetailWeek(String typeReport) {
        try {
            mReportTotalAdapter.setData(mListItemReportTime, typeReport);

            tvXValDescription.setText(getString(R.string.title_week_horizontal_report));
            tvYValDescription.setText(getString(R.string.title_week_vertical_report));

            showLineChart(7, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo theo tháng
     *
     * @param typeReport kiểu báo cáo
     * @created_by nadat on 24/04/2019
     */
    public void showDetailMonth(String typeReport) {
        try {
            mReportTotalAdapter.setData(mListItemReportTime, typeReport);

            tvYValDescription.setText(getString(R.string.title_month_vertical_report));
            tvXValDescription.setText(getString(R.string.title_month_horizontal_report));

            showLineChart(8, 29);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo theo năm
     *
     * @param typeReport kiểu báo cáo
     * @created_by nadat on 24/04/2019
     */
    public void showDetailYear(String typeReport) {
        try {
            mReportTotalAdapter.setData(mListItemReportTime, typeReport);

            tvYValDescription.setText(getString(R.string.title_year_vertical_report));
            tvXValDescription.setText(getString(R.string.title_year_horizontal_report));

            showLineChart(12, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo trên biểu đồ đường
     *
     * @param count tổng số muc theo chiều ngang
     * @param size  giá trị lớn nhất của mục theo chiều ngang
     * @created_by nadat on 24/04/2019
     */
    private void showLineChart(int count, final int size) {
        try {
            ArrayList<Entry> listEntry = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                Entry entry;

                if (i < mListItemReportTime.size()) {
                    long money = 0;
                    if (!TextUtils.isEmpty(mListItemReportTime.get(i).getMoney())) {
                        money = Long.parseLong(mListItemReportTime.get(i).getMoney());
                    }

                    if (count == 12 && size == 12) {
                        entry = new Entry(i + 1, (float) money / 1000000);
                    } else {
                        entry = new Entry(i + 1, (float) money / 1000);
                    }
                    listEntry.add(entry);
                }
            }
            LineDataSet dataSet = new LineDataSet(listEntry, null);
            dataSet.setValueTextSize(0f);
            dataSet.setCircleColor(Color.TRANSPARENT);
            dataSet.setCircleHoleColor(Color.GREEN);
            dataSet.setColor(Color.GREEN);
            LineData lineData = new LineData(dataSet);

            lineChart.setDrawGridBackground(false);
            lineChart.setDescription(null);
            lineChart.setTouchEnabled(false);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setAxisMinimum(1f);
            xAxis.setAxisMaximum(size);
            xAxis.setLabelCount(count, true);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    String title = "";

                    switch (size) {
                        case 7:
                            int day = (int) value + 1;
                            if (day != 8) {
                                title = "T" + day;
                            } else {
                                title = "CN";
                            }

                            break;
                        case 29:
                            title = (int) value + "";
                            break;
                        case 12:
                            title = (int) value + "";
                            break;
                    }

                    return title;
                }
            });

            YAxis axisLeft = lineChart.getAxisLeft();
            axisLeft.enableGridDashedLine(5.0f, 5.0f, 0.0f);
            axisLeft.setAxisLineColor(Color.TRANSPARENT);
            axisLeft.setAxisMinimum(0f);

            lineChart.getLegend().setEnabled(false);
            lineChart.getAxisRight().setEnabled(false);

            lineChart.setData(lineData);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
