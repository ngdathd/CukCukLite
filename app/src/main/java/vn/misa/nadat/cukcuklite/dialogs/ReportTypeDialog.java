package vn.misa.nadat.cukcuklite.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.ReportTypeAdapter;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Màn hình các kiểu báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportTypeDialog extends AlertDialog {
    private CallBack mCallBack;
    private String mTypeReport;

    private AlertDialog mDialog;

    private View view;
    private RecyclerView rvTypeReport;

    /**
     * Khởi tạo ReportTypeDialog
     *
     * @param context    context của lớp gọi đến ReportTypeDialog
     * @param typeReport kiểu báo cáo hiện tại
     * @param callBack   interface trong lớp ReportTypeDialog
     * @created_by nadat on 24/04/2019
     */
    public ReportTypeDialog(@NonNull Context context, String typeReport, CallBack callBack) {
        super(context);
        try {
            mTypeReport = typeReport;
            mCallBack = callBack;
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
            init();

            Builder builder = new Builder(getContext());
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
            view = inflater.inflate(R.layout.dialog_type_report, null, false);

            rvTypeReport = view.findViewById(R.id.rvTypeReport);
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
            ReportTypeAdapter reportTypeAdapter = new ReportTypeAdapter(getContext(), new ReportTypeAdapter.CallBack() {
                @Override
                public void onClick(String typeReport) {
                    mCallBack.onClick(typeReport);
                    mDialog.dismiss();
                }
            });

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvTypeReport.setLayoutManager(layoutManager);
            rvTypeReport.setAdapter(reportTypeAdapter);

            ArrayList<String> listTypeReport = new ArrayList<>();
            listTypeReport.add(Constant.RECENT);
            listTypeReport.add(Constant.THISWEEK);
            listTypeReport.add(Constant.LASTWEEK);
            listTypeReport.add(Constant.THISMONTH);
            listTypeReport.add(Constant.LASTMONTH);
            listTypeReport.add(Constant.THISYEAR);
            listTypeReport.add(Constant.LASTYEAR);
            listTypeReport.add(Constant.ORTHER);

            reportTypeAdapter.setDate(listTypeReport, mTypeReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallBack {
        void onClick(String typeReport);
    }
}
