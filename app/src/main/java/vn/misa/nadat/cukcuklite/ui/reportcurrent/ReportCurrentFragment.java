package vn.misa.nadat.cukcuklite.ui.reportcurrent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.ReportCurrentAdapter;
import vn.misa.nadat.cukcuklite.items.ItemReportCurrent;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Màn hình danh sách báo cáo gần đây
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportCurrentFragment extends Fragment implements IReportCurrentContract.IView, ReportCurrentAdapter.OnClickItemReportCurrent {
    private static CallBack mCallBack;
    private RecyclerView rvReport;

    /**
     * Khởi tạo ReportCurrentFragment
     */
    public ReportCurrentFragment() {

    }

    /**
     * Lấy ra đối tượng ReportCurrentFragment
     *
     * @param callBack: interface trả về khi click item
     * @return ReportCurrentFragment
     * @created_by nadat on 24/04/2019
     */
    public static ReportCurrentFragment getInstance(CallBack callBack) {
        mCallBack = callBack;
        return new ReportCurrentFragment();
    }

    @Override
    public void clickItemReportCurrent(String nameItem) {
        try {
            switch (nameItem) {
                case Constant.YESTERDAY: {
                    mCallBack.onDetailYesterday();
                    break;
                }
                case Constant.TODAY: {
                    mCallBack.onDetailToday();
                    break;
                }
                case Constant.THISWEEK: {
                    mCallBack.onDetailWeek();
                    break;
                }
                case Constant.THISMONTH: {
                    mCallBack.onDetailMonth();
                    break;
                }
                case Constant.THISYEAR: {
                    mCallBack.onDetailYear();
                    break;
                }
                default: {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_current, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvReport = view.findViewById(R.id.rvReport);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReport.setLayoutManager(linearLayoutManager);

        IReportCurrentContract.IPresenter presenter = new ReportCurrentPresenter(this);
        presenter.getItemReportCurrents();
    }

    /**
     * Hiển thị danh sách báo cáo gần đây
     *
     * @param itemReportCurrents: danh sách báo cáo gần đây
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void showItemReportCurrents(List<ItemReportCurrent> itemReportCurrents) {
        ReportCurrentAdapter reportCurrentAdapter = new ReportCurrentAdapter(itemReportCurrents, this);
        rvReport.setAdapter(reportCurrentAdapter);
    }

    /**
     * Hiển thị thông báo có lỗi
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

    public interface CallBack {
        void onDetailYesterday();

        void onDetailToday();

        void onDetailWeek();

        void onDetailMonth();

        void onDetailYear();
    }
}
