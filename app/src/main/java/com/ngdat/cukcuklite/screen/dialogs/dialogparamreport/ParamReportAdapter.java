package com.ngdat.cukcuklite.screen.dialogs.dialogparamreport;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.models.ParamReport;

/**
 * Adapter của list lựa chọn thông báo cho khoảng thời gian
 * Created at 17/04/2019
 */
public class ParamReportAdapter extends RecyclerView.Adapter<ParamReportAdapter.ViewHolder> {

    private List<ParamReport> mParamReports;
    private Context mContext;
    private OnClickParam mOnClickParam;
    private LayoutInflater mInflater;

    public ParamReportAdapter(Context context, List<ParamReport> paramReports,
                              OnClickParam onClickParam) {
        mParamReports = paramReports;
        mContext = context;
        mOnClickParam = onClickParam;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_param_report_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            holder.tvName.setText(mParamReports.get(position).getTitleReportDetail());
            if (mParamReports.get(position).isSelected()) {
                holder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                holder.ivCheck.setVisibility(View.INVISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mParamReports.get(position).isSelected()) {
                        if (position != 7) {
                            setSelected(position);
                        }
                        mOnClickParam.onClick(mParamReports.get(position));
                    } else {

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Set lại select cho list
     * Created at 17/04/2019
     *
     * @param position: vị trí cần set
     */
    private void setSelected(int position) {
        for (ParamReport paramReport : mParamReports) {
            paramReport.setSelected(false);
        }
        mParamReports.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mParamReports != null ? mParamReports.size() : 0;
    }

    interface OnClickParam {

        void onClick(ParamReport paramReport);
    }

    /**
     * Holder cho các trường báo
     * Created at 19/04/2019
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView ivCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivCheck = itemView.findViewById(R.id.ivCheck);
        }
    }
}
