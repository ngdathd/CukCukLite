package vn.misa.nadat.cukcuklite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.items.ItemReportTime;
import vn.misa.nadat.cukcuklite.ui.revenue.ReportRevenueActivity;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Adapter cho danh sách báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportTotalAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ItemReportTime> mItemReportTimes;
    private String typeReport;

    /**
     * Khởi tạo ReportTotalAdapter
     *
     * @param context context của lớp gọi đến ReportTotalAdapter
     */
    public ReportTotalAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_report_current, parent, false);
        return new ReportTotalVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReportTotalVH reportDetailVH = (ReportTotalVH) holder;
        reportDetailVH.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mItemReportTimes != null ? mItemReportTimes.size() : 0;
    }

    /**
     * Thiết lập danh sách báo cáo
     *
     * @param itemReportTimes danh sách báo cáo
     * @created_by nadat on 24/04/2019
     */
    public void setData(List<ItemReportTime> itemReportTimes, String typeReport) {
        try {
            if (itemReportTimes != null) {
                this.typeReport = typeReport;
                mItemReportTimes = itemReportTimes;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ViewHolder của ReportTotalAdapter
     *
     * @created_by nadat on 24/04/2019
     */
    private class ReportTotalVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvAmount;
        private View vSparator;

        private ReportTotalVH(View itemView) {
            super(itemView);
            try {
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvAmount = itemView.findViewById(R.id.tvAmount);
                itemView.findViewById(R.id.rlIcon).setVisibility(View.GONE);
                vSparator = itemView.findViewById(R.id.vSparator);
                itemView.setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Gắn dữ liệu vào view
         *
         * @param position: vị trí view
         * @created_by nadat on 24/04/2019
         */
        private void bindView(int position) {
            try {
                ItemReportTime itemReportTime = mItemReportTimes.get(position);
                String money = itemReportTime.getMoney();

                tvTitle.setText(itemReportTime.getTitle());
                if (!TextUtils.isEmpty(money)) {
                    tvAmount.setText(PriceUtils.formatPrice(itemReportTime.getMoney()));
                } else {
                    tvAmount.setText("0");
                }
                if (position == mItemReportTimes.size() - 1) {
                    vSparator.setVisibility(View.INVISIBLE);
                } else {
                    vSparator.setVisibility(View.VISIBLE);
                }
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
                String money = mItemReportTimes.get(getAdapterPosition()).getMoney();
                if (!TextUtils.isEmpty(money)) {
                    Intent intent = new Intent(mContext, ReportRevenueActivity.class);
                    intent.putExtra(Constant.KEY_DATA_REPORT, mItemReportTimes.get(getAdapterPosition()));
                    intent.putExtra(Constant.KEY_TYPE_REPORT, typeReport);
                    mContext.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
