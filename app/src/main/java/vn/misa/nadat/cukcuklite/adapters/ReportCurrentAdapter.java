package vn.misa.nadat.cukcuklite.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.CukCukLiteApp;
import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.items.ItemReportCurrent;

/**
 * Adapter của danh sách các báo cáo gần đây.
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportCurrentAdapter extends RecyclerView.Adapter {
    private List<ItemReportCurrent> mItemReportCurrents;
    private OnClickItemReportCurrent mOnClickItemReportCurrent;

    /**
     * Khởi tạo ReportCurrentAdapter
     *
     * @param itemReportCurrents:       danh sách các báo cáo gần đây.
     * @param onClickItemReportCurrent: interface lắng nghe sự kiện click ItemReportCurrent
     * @created_by nadat on 24/04/2019
     */
    public ReportCurrentAdapter(List<ItemReportCurrent> itemReportCurrents, OnClickItemReportCurrent onClickItemReportCurrent) {
        mItemReportCurrents = itemReportCurrents;
        mOnClickItemReportCurrent = onClickItemReportCurrent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_report_current, viewGroup, false);
        return new ReportCurrentVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ReportCurrentVH reportCurrentVH = (ReportCurrentVH) viewHolder;
        reportCurrentVH.bindView(i);
    }

    @Override
    public int getItemCount() {
        if (mItemReportCurrents != null) {
            return mItemReportCurrents.size();
        } else {
            return 0;
        }
    }

    public interface OnClickItemReportCurrent {
        void clickItemReportCurrent(String nameItem);
    }

    /**
     * ViewHolder của ReportCurrentAdapter
     *
     * @created_by nadat on 24/04/2019
     */
    private class ReportCurrentVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivBackgroundColor;
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvAmount;
        private View vSparator;

        private ReportCurrentVH(@NonNull View itemView) {
            super(itemView);
            ivBackgroundColor = itemView.findViewById(R.id.ivBackgroundColor);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            vSparator = itemView.findViewById(R.id.vSparator);
            itemView.setOnClickListener(this);
        }

        /**
         * Gắn sự kiện vào view
         *
         * @param position: vị trí view
         * @created_by nadat on 24/04/2019
         */
        private void bindView(int position) {
            Drawable drawable = CukCukLiteApp.getInstance().getResources().getDrawable(R.drawable.bg_circle_image_view);
            drawable.setColorFilter(Color.parseColor(mItemReportCurrents.get(position).getDateColor()), PorterDuff.Mode.SRC);
            ivBackgroundColor.setBackgroundDrawable(drawable);
            ivIcon.setImageResource(mItemReportCurrents.get(position).getDateIcon());
            tvTitle.setText(mItemReportCurrents.get(position).getDateName());
            String revenue = mItemReportCurrents.get(position).getRevenue();
            if (revenue == null || revenue.equals("")) {
                tvAmount.setText("0");
            } else {
                tvAmount.setText(mItemReportCurrents.get(position).getRevenue());
            }
            if (position == mItemReportCurrents.size() - 1) {
                vSparator.setVisibility(View.INVISIBLE);
            } else {
                vSparator.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            String revenue = mItemReportCurrents.get(getAdapterPosition()).getRevenue();
            if (revenue != null && !revenue.equals("")) {
                mOnClickItemReportCurrent.clickItemReportCurrent(mItemReportCurrents.get(getAdapterPosition()).getDateName());
            }
        }
    }
}
