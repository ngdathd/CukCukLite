package vn.misa.nadat.cukcuklite.ui.reportdetail;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.ReportDetailAdapter;
import vn.misa.nadat.cukcuklite.items.ItemReportDetail;
import vn.misa.nadat.cukcuklite.items.ItemReportTime;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.DateUtils;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Màn hình chi tiết báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportDetailFragment extends Fragment implements IReportDetailContact.IView {
    private ReportDetailAdapter mAdapter;
    private String[] mTime;
    private ItemReportTime mReport;
    private String typeReport;

    private View view;
    private RecyclerView rvReport;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report_detail, container, false);

        initView();
        getData();
        init();

        return view;
    }

    /**
     * Ánh xạ view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initView() {
        try {
            pieChart = view.findViewById(R.id.pieChart);
            rvReport = view.findViewById(R.id.rvReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                mTime = (String[]) bundle.getSerializable(Constant.KEY_TIME);
                mReport = (ItemReportTime) bundle.getSerializable(Constant.KEY_DATA_REPORT);
                typeReport = bundle.getString(Constant.KEY_TYPE_REPORT);
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
    @SuppressLint("SimpleDateFormat")
    private void init() {
        try {
            IReportDetailContact.IPresenter IPresenter = new ReportDetailPresenter(this);
            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

            mAdapter = new ReportDetailAdapter(getContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvReport.setLayoutManager(layoutManager);
            rvReport.setAdapter(mAdapter);

            if (mReport != null) {
                Date date = DateUtils.getInstance().getDate(mReport.getTime());

                if (typeReport.equals(Constant.THISYEAR) || typeReport.equals(Constant.LASTYEAR)) {
                    IPresenter.getItemReportDetails(monthFormat.format(date));
                } else {
                    IPresenter.getItemReportDetails(dayFormat.format(date));
                }
            } else if (mTime != null) {
                Date startDate = DateUtils.getInstance().getDate(mTime[0]);
                Date endDate = DateUtils.getInstance().getDate(mTime[1]);
                IPresenter.getItemReportDetails(dayFormat.format(startDate), dayFormat.format(endDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Hiển thị danh sách chi tiết báo cáo
     *
     * @param itemReportDetails: danh sách chi tiết báo cáo
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void showItemReportDetails(List<ItemReportDetail> itemReportDetails) {
        try {
            if (itemReportDetails != null) {
                mAdapter.setDate(itemReportDetails);
                showPieChart(itemReportDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo trên biểu đồ
     *
     * @param itemReportDetails: danh sách chi tiết báo cáo
     * @created_by nadat on 24/04/2019
     */
    private void showPieChart(List<ItemReportDetail> itemReportDetails) {
        try {
            if (itemReportDetails != null) {
                Collections.sort(itemReportDetails, new Comparator<ItemReportDetail>() {
                    @Override
                    public int compare(ItemReportDetail report1, ItemReportDetail report2) {
                        return report2.getTotalMoney() > report1.getTotalMoney() ? 1 : -1;
                    }
                });

                String[] strColors = getResources().getStringArray(R.array.color_detail_report);
                int[] intColors = new int[strColors.length];

                for (int i = 0; i < strColors.length; i++) {
                    intColors[i] = Color.parseColor(strColors[i]);
                }

                pieChart.setUsePercentValues(true);
                pieChart.setDescription(null);

                float margin = getResources().getDimension(R.dimen.padding_circle_img);
                pieChart.setExtraOffsets(margin, margin, margin, margin);
                pieChart.setHoleRadius(75.0f);
                pieChart.setDrawCenterText(true);
                pieChart.setRotationAngle(0.0f);
                pieChart.setRotationEnabled(false);
                pieChart.setHighlightPerTapEnabled(false);

                List<PieEntry> listPieEntry = new ArrayList<>();
                float totalRevenue = 0;
                float totalRemain = 0;
                for (int i = 0; i < itemReportDetails.size(); i++) {
                    ItemReportDetail detailReport = itemReportDetails.get(i);
                    Float totalMoney = (float) detailReport.getTotalMoney();

                    if (i < 6) {
                        PieEntry pieEntry = new PieEntry(totalMoney);
                        listPieEntry.add(pieEntry);
                    } else {
                        totalRemain += totalMoney;

                        if (i == itemReportDetails.size() - 1) {
                            PieEntry pieEntry = new PieEntry(totalRemain);
                            listPieEntry.add(pieEntry);
                        }
                    }

                    totalRevenue += totalMoney;
                }

                PieDataSet pieDataSet = new PieDataSet(listPieEntry, null);
                pieDataSet.setColors(intColors);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

                PieData data = new PieData(pieDataSet);
                data.setValueFormatter(new PercentFormatter(pieChart));

                pieChart.setData(data);

                String textCenter = getString(R.string.totalRevenue);
                String textTotalRevenue = PriceUtils.formatPrice(String.valueOf((int) totalRevenue));
                SpannableString spannableString = new SpannableString(textCenter + "\n" + textTotalRevenue);
                spannableString.setSpan(new RelativeSizeSpan(1.6f), textCenter.length() + 1, spannableString.length(), 0);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_black)), textCenter.length() + 1, spannableString.length(), 0);

                spannableString.setSpan(new RelativeSizeSpan(1.2f), 0, textCenter.length(), 0);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_gray)), 0, textCenter.length(), 0);

                pieChart.setCenterText(spannableString);
                pieChart.animateY(1400);
                pieChart.getLegend().setEnabled(false);
                pieChart.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
