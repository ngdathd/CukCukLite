package vn.misa.nadat.cukcuklite.ui.revenue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.items.ItemReportTime;
import vn.misa.nadat.cukcuklite.ui.reportdetail.ReportDetailFragment;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.DateUtils;

/**
 * Màn hình nội chi tiết báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportRevenueActivity extends AppCompatActivity implements View.OnClickListener {
    private ItemReportTime mItemReportTime;
    private String typeReport;

    private TextView tvTitleReport;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        initView();
        initEvent();
        getData();
        init();
    }

    /**
     * Ánh xạ view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initView() {
        try {
            tvTitleReport = findViewById(R.id.tvTitleReport);
            ivBack = findViewById(R.id.ivBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gán sự kiện cho view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initEvent() {
        try {
            ivBack.setOnClickListener(this);
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
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mItemReportTime = (ItemReportTime) bundle.getSerializable(Constant.KEY_DATA_REPORT);
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
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
            String time = mItemReportTime.getTime();
            String title;

            switch (typeReport) {
                case Constant.THISYEAR:
                case Constant.LASTYEAR:
                    Date startDate = DateUtils.getInstance().getStartMonth(time);
                    Date endDate = DateUtils.getInstance().getEndMonth(time);

                    title = String.format(getString(R.string.title_detail_report_month),
                            mItemReportTime.getTitle(), dayFormat.format(startDate), dayFormat.format(endDate));

                    break;
                case Constant.THISWEEK:
                case Constant.LASTWEEK:
                case Constant.TODAY:
                case Constant.YESTERDAY: {
                    Date date = DateUtils.getInstance().getDate(time);

                    title = String.format(getString(R.string.title_detail_report_day_of_week),
                            mItemReportTime.getTitle(), dayFormat.format(date));

                    break;
                }
                default: {
                    Date date = DateUtils.getInstance().getDate(time);

                    title = dayFormat.format(date);

                    break;
                }
            }

            tvTitleReport.setText(title);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ReportDetailFragment reportDetailFragment = new ReportDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.KEY_DATA_REPORT, mItemReportTime);
            bundle.putString(Constant.KEY_TYPE_REPORT, typeReport);
            reportDetailFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.rlContent, reportDetailFragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Bắt sự kiện click vào view.
     *
     * @param view: view được click
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void onClick(View view) {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
