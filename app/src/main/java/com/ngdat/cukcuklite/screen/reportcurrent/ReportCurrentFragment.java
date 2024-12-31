package com.ngdat.cukcuklite.screen.reportcurrent;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.models.ReportCurrent;

/**
 * Màn hình Báo cáo gần đây
 * Created at 18/04/2019
 */
public class ReportCurrentFragment extends Fragment implements IReportCurrentContract.IView,
        ReportCurrentAdapter.OnCLickReport {

    private static final String TAG = "ReportCurrentFragment";
    private IReportCurrentContract.IPresenter mPresenter;
    private ReportCurrentAdapter mAdapter;
    private OnClickCurrentReport mOnClickCurrentReport;

    public static ReportCurrentFragment newInstance() {
        return new ReportCurrentFragment();
    }

    /**
     * Phương thức gán listener lắng nghe xử lý sự kiện cho item view
     * Created at 18/04/2019
     *
     * @param onClickCurrentReport - listener
     */
    public void setOnClickCurrentReport(OnClickCurrentReport onClickCurrentReport) {
        try {
            if(onClickCurrentReport!=null){
            mOnClickCurrentReport = onClickCurrentReport;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_current, container, false);
        initViews(v);
        return v;
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 18/04/2019
     */
    private void initViews(View v) {
        try {
            mPresenter = new ReportCurrentPresenter();
            mPresenter.setView(this);
            mPresenter.getListReportCurrent();
            mAdapter = new ReportCurrentAdapter(getContext());
            mAdapter.setCLickReport(this);
            RecyclerView rvReport = v.findViewById(R.id.rvReport);
            rvReport.setAdapter(mAdapter);
            rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Đổ dữ liệu lên adapter khi lấy xong
     * Created at 18/04/2019
     *
     * @param reportCurrents: danh sách ReportCurrent
     */
    @Override
    public void onLoadReportCurrentDone(List<ReportCurrent> reportCurrents) {
        try {
            if (reportCurrents != null) {
                mAdapter.setListData(reportCurrents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử li khi click vào item bất kì
     * Created at 18/04/2019
     *
     * @param reportCurrent: trả về ReportCurrent ở vị trí đó
     */
    @Override
    public void onClick(ReportCurrent reportCurrent) {
        mOnClickCurrentReport.onClick(reportCurrent);
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

    /**
     * Mục đích method: interface Callback khi click vào item
     * Created at 18/04/2019
     */
    public interface OnClickCurrentReport {

        void onClick(ReportCurrent reportCurrent);
    }
}
