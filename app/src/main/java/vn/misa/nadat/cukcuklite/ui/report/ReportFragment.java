package vn.misa.nadat.cukcuklite.ui.report;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.dialogs.ReportSelectTimeDialog;
import vn.misa.nadat.cukcuklite.dialogs.ReportTypeDialog;
import vn.misa.nadat.cukcuklite.items.ItemReportTime;
import vn.misa.nadat.cukcuklite.ui.reportcurrent.ReportCurrentFragment;
import vn.misa.nadat.cukcuklite.ui.reportdetail.ReportDetailFragment;
import vn.misa.nadat.cukcuklite.ui.reporttotal.ReportTotalFragment;
import vn.misa.nadat.cukcuklite.ui.revenue.ReportRevenueActivity;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.DateUtils;

/**
 * Màn hình báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportFragment extends Fragment implements IReportContact.IView, View.OnClickListener {
    private IReportContact.IPresenter mIPresenter;

    private ReportCurrentFragment mReportCurrentFragment;
    private FragmentTransaction mFragmentTransaction;

    private View view;
    private TextView tvTimeValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        initView();
        initEvent();
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
            tvTimeValue = view.findViewById(R.id.tvTimeValue);
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
            view.findViewById(R.id.lnTime).setOnClickListener(this);
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
            mIPresenter = new ReportPresenter(this);
            tvTimeValue.setText(Constant.RECENT);

            if (getFragmentManager() != null) {
                mFragmentTransaction = getFragmentManager().beginTransaction();
            }

            mReportCurrentFragment = ReportCurrentFragment.getInstance(new ReportCurrentFragment.CallBack() {
                @Override
                public void onDetailYesterday() {
                    try {
                        ItemReportTime itemReportTime = new ItemReportTime();
                        itemReportTime.setTitle(getString(R.string.yesterday));
                        itemReportTime.setTime(DateUtils.getInstance().getYesterday()[0]);

                        Intent intent = new Intent(getContext(), ReportRevenueActivity.class);
                        intent.putExtra(Constant.KEY_DATA_REPORT, itemReportTime);
                        intent.putExtra(Constant.KEY_TYPE_REPORT, Constant.YESTERDAY);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDetailToday() {
                    try {
                        ItemReportTime itemReportTime = new ItemReportTime();
                        itemReportTime.setTitle(getString(R.string.today));
                        itemReportTime.setTime(DateUtils.getInstance().getToday()[0]);

                        Intent intent = new Intent(getContext(), ReportRevenueActivity.class);
                        intent.putExtra(Constant.KEY_DATA_REPORT, itemReportTime);
                        intent.putExtra(Constant.KEY_TYPE_REPORT, Constant.TODAY);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDetailWeek() {
                    try {
                        mIPresenter.getDetailWeek(Constant.THISWEEK);
                        tvTimeValue.setText(Constant.THISWEEK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDetailMonth() {
                    try {
                        mIPresenter.getDetailMonth(Constant.THISMONTH);
                        tvTimeValue.setText(Constant.THISMONTH);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDetailYear() {
                    try {
                        mIPresenter.getDetailYear(Constant.THISYEAR);
                        tvTimeValue.setText(Constant.THISYEAR);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            mFragmentTransaction.replace(R.id.rlContent, mReportCurrentFragment);
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Xử lý sự kiện khi view được click
     *
     * @param v view được click
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void onClick(View v) {
        try {
            if (getContext() == null) {
                return;
            }
            ReportTypeDialog reportTypeDialog = new ReportTypeDialog(getContext(),
                    tvTimeValue.getText().toString(), new ReportTypeDialog.CallBack() {
                @Override
                public void onClick(String typeReport) {
                    switch (typeReport) {
                        case Constant.RECENT: {
                            tvTimeValue.setText(typeReport);

                            if (getFragmentManager() != null) {
                                mFragmentTransaction = getFragmentManager().beginTransaction();
                            }

                            mFragmentTransaction.replace(R.id.rlContent, mReportCurrentFragment);
                            mFragmentTransaction.commit();
                            break;
                        }
                        case Constant.THISWEEK: {
                            tvTimeValue.setText(typeReport);

                            mIPresenter.getDetailWeek(Constant.THISWEEK);
                            break;
                        }
                        case Constant.LASTWEEK: {
                            tvTimeValue.setText(typeReport);

                            mIPresenter.getDetailWeek(Constant.LASTWEEK);
                            break;
                        }
                        case Constant.THISMONTH: {
                            tvTimeValue.setText(typeReport);

                            mIPresenter.getDetailMonth(Constant.THISMONTH);
                            break;
                        }
                        case Constant.LASTMONTH: {
                            tvTimeValue.setText(typeReport);

                            mIPresenter.getDetailMonth(Constant.LASTMONTH);
                            break;
                        }
                        case Constant.THISYEAR: {
                            tvTimeValue.setText(typeReport);

                            mIPresenter.getDetailYear(Constant.THISYEAR);
                            break;
                        }
                        case Constant.LASTYEAR: {
                            tvTimeValue.setText(typeReport);

                            mIPresenter.getDetailYear(Constant.LASTYEAR);
                            break;
                        }
                        default: {
                            handleOtherReport();
                            break;
                        }
                    }
                }
            });
            reportTypeDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị chi tiết báo cáo theo khoảng thời gian
     *
     * @created_by nadat on 24/04/2019
     */
    private void handleOtherReport() {
        try {
            ReportSelectTimeDialog reportSelectTimeDialog = new ReportSelectTimeDialog(getContext(),
                    new ReportSelectTimeDialog.CallBack() {
                        @Override
                        public void onClick(String[] time) {
                            try {
                                if (time != null) {
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date startDate = DateUtils.getInstance().getDate(time[0]);
                                    Date endDate = DateUtils.getInstance().getDate(time[1]);

                                    tvTimeValue.setText(String.format(getString(R.string.title_other_report),
                                            dayFormat.format(startDate), dayFormat.format(endDate)));

                                    if (getFragmentManager() != null) {
                                        mFragmentTransaction = getFragmentManager().beginTransaction();
                                    }

                                    ReportDetailFragment reportDetailFragment = new ReportDetailFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(Constant.KEY_TIME, time);
                                    reportDetailFragment.setArguments(bundle);

                                    mFragmentTransaction.replace(R.id.rlContent, reportDetailFragment);
                                    mFragmentTransaction.commit();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            reportSelectTimeDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lý hiển thị báo cáo
     *
     * @param itemReportTimes danh sách báo cáo
     * @param typeReport      kiểu báo cáo
     * @created_by nadat on 24/04/2019
     */
    private void handleShow(List<ItemReportTime> itemReportTimes, String typeReport) {
        try {
            if (itemReportTimes != null && !TextUtils.isEmpty(typeReport)) {
                if (getFragmentManager() != null) {
                    mFragmentTransaction = getFragmentManager().beginTransaction();
                }

                ReportTotalFragment reportTotalFragment = new ReportTotalFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_DATA_REPORT, (ArrayList<ItemReportTime>) itemReportTimes);
                bundle.putString(Constant.KEY_TYPE_REPORT, typeReport);
                reportTotalFragment.setArguments(bundle);

                mFragmentTransaction.replace(R.id.rlContent, reportTotalFragment);
                mFragmentTransaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo theo tuần
     *
     * @param itemReportTimes danh sách báo cáo
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void showDetailWeek(List<ItemReportTime> itemReportTimes) {
        try {
            if (itemReportTimes != null) {
                handleShow(itemReportTimes, Constant.THISWEEK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo theo tháng
     *
     * @param itemReportTimes danh sách báo cáo
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void showDetailMonth(List<ItemReportTime> itemReportTimes) {
        try {
            if (itemReportTimes != null) {
                handleShow(itemReportTimes, Constant.THISMONTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị báo cáo theo năm
     *
     * @param itemReportTimes danh sách báo cáo
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void showDetailYear(List<ItemReportTime> itemReportTimes) {
        try {
            if (itemReportTimes != null) {
                handleShow(itemReportTimes, Constant.THISYEAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thông báo đã có lỗi
     *
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void showNotificationError() {
        try {
            Toast.makeText(getContext(), "Đã có lỗi mời bạn thử lại!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
