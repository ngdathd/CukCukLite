package vn.misa.nadat.cukcuklite.dialogs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.utils.DateUtils;

/**
 * Màn hình chọn thời gian báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportSelectTimeDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mDialog;
    private CallBack mCallBack;

    private SimpleDateFormat mDateFormat, mDayFormat;
    private String[] time;

    private View view;
    private LinearLayout llStartDate, llEndDate;
    private Button btnAccept, btnCancel;
    private TextView tvStartDate, tvEndDate;

    /**
     * Khởi tạo ReportSelectTimeDialog
     *
     * @param context  context của lớp gọi đến ReportSelectTimeDialog
     * @param callBack interface trong lớp ReportSelectTimeDialog
     * @created_by nadat on 24/04/2019
     */
    @SuppressLint("SimpleDateFormat")
    public ReportSelectTimeDialog(Context context, CallBack callBack) {
        super(context);
        try {
            mCallBack = callBack;

            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mDayFormat = new SimpleDateFormat("dd/MM/yyy");

            time = new String[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị dialog
     *
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void show() {
        try {
            initView();
            initEvent();
            init();

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(view);
            mDialog = builder.create();
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ánh xạ view
     *
     * @created_by nadat on 24/04/2019
     */
    @SuppressLint("InflateParams")
    private void initView() {
        try {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.dialog_select_time_report, null, false);

            llStartDate = view.findViewById(R.id.llStartDate);
            llEndDate = view.findViewById(R.id.llEndDate);
            btnCancel = view.findViewById(R.id.btnCancelDialog);
            btnAccept = view.findViewById(R.id.btnAcceptDialog);
            tvStartDate = view.findViewById(R.id.tvStartDate);
            tvEndDate = view.findViewById(R.id.tvEndDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gán sự kiện item click cho view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initEvent() {
        try {
            llStartDate.setOnClickListener(this);
            llEndDate.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnAccept.setOnClickListener(this);
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
            String starDate = mDayFormat.format(DateUtils.getInstance().getStartMonth());
            String endDate = mDayFormat.format(DateUtils.getInstance().getEndMonth());

            tvStartDate.setText(starDate);
            tvEndDate.setText(endDate);

            time[0] = mDateFormat.format(DateUtils.getInstance().getStartMonth());
            time[1] = mDateFormat.format(DateUtils.getInstance().getEndMonth());
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
            switch (v.getId()) {
                case R.id.llStartDate:
                    handleSelectDay(0);
                    break;
                case R.id.llEndDate:
                    handleSelectDay(1);
                    break;
                case R.id.btnCancelDialog:
                    mDialog.dismiss();
                    break;
                case R.id.btnAcceptDialog:
                    mCallBack.onClick(time);
                    mDialog.dismiss();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lý chọn thời gian trong lịch
     *
     * @param typeDay kiểu ngày đầu tháng hoặc cuối tháng
     * @created_by nadat on 24/04/2019
     */
    private void handleSelectDay(final int typeDay) {
        try {
            int year = DateUtils.getInstance().getYear(time[typeDay]);
            int month = DateUtils.getInstance().getMonth(time[typeDay]);
            int dayOfMonth = DateUtils.getInstance().getDayOfMonth(time[typeDay]);

            CalendarDialog calendarDialog = new CalendarDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    if (typeDay == 0) {
                        time[0] = DateUtils.getInstance().setDate(time[typeDay], i, i1, i2);
                        tvStartDate.setText(mDayFormat.format(DateUtils.getInstance().getDate(time[0])));
                    } else if (typeDay == 1) {
                        time[1] = DateUtils.getInstance().setDate(time[typeDay], i, i1, i2);
                        tvEndDate.setText(mDayFormat.format(DateUtils.getInstance().getDate(time[1])));
                    }
                }
            }, year, month, dayOfMonth);

            if (typeDay == 0) {
                calendarDialog.setPermanentTitle(getContext().getString(R.string.start_date));
            } else if (typeDay == 1) {
                calendarDialog.setPermanentTitle(getContext().getString(R.string.end_date));
            }
            calendarDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallBack {
        void onClick(String[] time);
    }
}
