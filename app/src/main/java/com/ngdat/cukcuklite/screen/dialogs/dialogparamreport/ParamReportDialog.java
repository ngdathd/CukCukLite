package com.ngdat.cukcuklite.screen.dialogs.dialogparamreport;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.models.ParamReport;

/**
 * Dialog chọn khoảng thời gian
 * Created at 17/04/2019
 */
public class ParamReportDialog extends DialogFragment implements ParamReportAdapter.OnClickParam {

  private ParamReportAdapter mAdapter;
  private ParamCallBack mCallBack;
  private List<ParamReport> mParamReports;

  public void setParamReports(List<ParamReport> paramReports) {
    mParamReports = paramReports;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.dialog_param_report, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    initComponent(rootView);
    initListener();
    return rootView;
  }

  private void initListener() {

  }

  private void initComponent(View rootView) {
    mAdapter = new ParamReportAdapter(getContext(), mParamReports, this);
    RecyclerView rvDialogReport = rootView.findViewById(R.id.rvDialogReport);
    rvDialogReport.setAdapter(mAdapter);
    rvDialogReport.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  /**
   * Mục đích method: Thay đổi kích cỡ cho dialog
   * Created at 17/04/2019
   */
  @Override
  public void onResume() {
    super.onResume();
    try {
      WindowManager.LayoutParams params = Objects.requireNonNull(getDialog().getWindow()).getAttributes();
      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
      params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
      getDialog().getWindow().setAttributes(params);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Truyền vào Callback
   *
   * Created at 18/04/2019
   */
  public void setCallBack(ParamCallBack callBack) {
    mCallBack = callBack;
  }

  /**
   * Mục đích method: Xử lý sự kiện
   *
   * Created at 18/04/2019
   */
  @Override
  public void onClick(ParamReport paramReport) {
    mCallBack.onClick(paramReport);
    dismiss();
  }

  public interface ParamCallBack {

    void onClick(ParamReport paramReport);
  }
}
