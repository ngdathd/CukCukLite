package com.ngdat.cukcuklite.screen.reportcurrent;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.data.models.ReportCurrent;

/**
 * Mục đích Class :Adapter của mà Báo cáo gần đây
 * Created at 18/04/2019
 */
public class ReportCurrentAdapter extends ListAdapter<ReportCurrent> {
    private OnCLickReport mCLickReport;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public ReportCurrentAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_current, parent, false);
        return new ReportHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        final ReportCurrent reportCurrent = mListData.get(i);
        ReportHolder reportHolder = (ReportHolder) viewHolder;
        reportHolder.tvTitle.setText(reportCurrent.getTitleReportDetail());
        reportHolder.tvAmount
                .setText(NumberFormat.getNumberInstance(Locale.US).format(reportCurrent.getAmount()));
        Drawable drawableBg = mContext.getResources().getDrawable(R.drawable.bg_circle);
        switch (reportCurrent.getParamType()) {
            case TODAY:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_6),
                        PorterDuff.Mode.SRC);
                Glide.with(mContext).load(R.drawable.ic_calendar_today).into(reportHolder.ivIcon);
                break;
            case THIS_WEEK:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_2),
                        PorterDuff.Mode.SRC);
                Glide.with(mContext).load(R.drawable.ic_calendar_week).into(reportHolder.ivIcon);
                break;
            case THIS_YEAR:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_5),
                        PorterDuff.Mode.SRC);
                Glide.with(mContext).load(R.drawable.ic_calendar_year).into(reportHolder.ivIcon);
                break;
            case YESTERDAY:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_8),
                        PorterDuff.Mode.SRC);
                Glide.with(mContext).load(R.drawable.ic_calendar_yesterday).into(reportHolder.ivIcon);
                break;
            case THIS_MONTH:
                drawableBg.setColorFilter(mContext.getResources().getColor(R.color.color_report_3),
                        PorterDuff.Mode.SRC);
                Glide.with(mContext).load(R.drawable.ic_calendar_month).into(reportHolder.ivIcon);
                break;
        }
        int  size = mListData.size();
        if(i== --size){
            reportHolder.lineBottom.setVisibility(View.INVISIBLE);
        }else {
            reportHolder.lineBottom.setVisibility(View.VISIBLE);
        }
        reportHolder.ivBackgroundColor.setBackground(drawableBg);
        reportHolder.lnContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportCurrent.getAmount() > 0) {
                    mCLickReport.onClick(reportCurrent);
                }

            }
        });
    }

    public void setCLickReport(OnCLickReport CLickReport) {
        mCLickReport = CLickReport;
    }

    interface OnCLickReport {

        void onClick(ReportCurrent reportCurrent);
    }

    public class ReportHolder extends RecyclerView.ViewHolder {

        private LinearLayout lnContent;
        private ImageView ivBackgroundColor, ivIcon;
        private TextView tvTitle, tvAmount;
        private View lineBottom;

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            lnContent = itemView.findViewById(R.id.lnContent);
            ivBackgroundColor = itemView.findViewById(R.id.ivBackgroundColor);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            lineBottom = itemView.findViewById(R.id.lineBottom);
        }
    }
}
