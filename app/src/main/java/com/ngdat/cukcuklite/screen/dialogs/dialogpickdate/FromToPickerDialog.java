package com.ngdat.cukcuklite.screen.dialogs.dialogpickdate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.utils.AppConstants;

/**
 * Dialog chọn từ ngày - đến ngày
 * Created at 18/04/2019
 */
public class FromToPickerDialog extends DialogFragment implements View.OnClickListener {

    private TextView tvFromDateValue, tvToDateValue;
    private Date fromDate, toDate;
    private DateFormat dateFormat;
    private OnClickAcceptPickDate mOnClickAcceptPickDate;

    /**
     * Mục đích method: Set callback cho Dialog
     * <p>
     * Created at 18/04/2019
     */
    public void setOnClickAcceptPickDate(OnClickAcceptPickDate onClickAcceptPickDate) {
        mOnClickAcceptPickDate = onClickAcceptPickDate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pick_date, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initView(view);
        initListener(view);
        initDefDate();
        return view;
    }

    /**
     * Mục đích method: Thay đổi kích cỡ dialog
     * <p>
     * Created at 18/04/2019
     */
    @Override
    public void onResume() {
        super.onResume();
        try {
            Window window = getDialog().getWindow();
            int width = getResources().getDimensionPixelSize(R.dimen.color_dialog_width);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            assert window != null;
            window.setLayout(width, height);
            window.setGravity(Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Khởi tạo giá trị mặc định
     * <p>
     * Created at 18/04/2019
     */
    private void initDefDate() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            fromDate = calendar.getTime();
            tvFromDateValue.setText(formatDate(fromDate));
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            toDate = calendar.getTime();
            tvToDateValue.setText(formatDate(toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Format date về string
     * Created at 18/04/2019
     *
     * @param date: ngày truyên vào
     * @return : dạng string format
     */
    private String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Mục đích method: Bắt sự kiện
     * <p>
     * Created at 18/04/2019
     */
    private void initListener(View view) {
        view.findViewById(R.id.lnFromDate).setOnClickListener(this);
        view.findViewById(R.id.lnToDate).setOnClickListener(this);
        view.findViewById(R.id.btnAcceptDialog).setOnClickListener(this);
        view.findViewById(R.id.btnCancelDialog).setOnClickListener(this);
    }

    /**
     * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
     * <p>
     * Created at 18/04/2019
     */
    @SuppressLint("SimpleDateFormat")
    private void initView(View view) {
        try {
            tvToDateValue = view.findViewById(R.id.tvToDateValue);
            tvFromDateValue = view.findViewById(R.id.tvFromDateValue);
            dateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 18/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        final Calendar calendar = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.lnFromDate:
                try {
                    calendar.setTime(dateFormat.parse(tvFromDateValue.getText().toString()));
                    DatePickerDialog dialogFromDate = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    calendar.set(year, month, dayOfMonth);
                                    fromDate = calendar.getTime();
                                    tvFromDateValue.setText(formatDate(fromDate));
                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dialogFromDate.show();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                break;
            case R.id.lnToDate:
                try {
                    calendar.setTime(dateFormat.parse(tvToDateValue.getText().toString()));
                    DatePickerDialog dialogToDate = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    calendar.set(year, month, dayOfMonth);
                                    toDate = calendar.getTime();
                                    tvToDateValue.setText(formatDate(toDate));
                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dialogToDate.show();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                break;
            case R.id.btnAcceptDialog:
                try {
                    if (fromDate.compareTo(toDate) < 0) {
                        mOnClickAcceptPickDate.onPickDate(fromDate, toDate);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.msg_dialog_pick_date),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case R.id.btnCancelDialog:
                dismiss();
                break;
        }
    }

    /**
     * Mục đích method: interface bắt sự kiện
     * Created at 18/04/2019
     */
    public interface OnClickAcceptPickDate {

        void onPickDate(Date fromDate, Date toDate);
    }

}
