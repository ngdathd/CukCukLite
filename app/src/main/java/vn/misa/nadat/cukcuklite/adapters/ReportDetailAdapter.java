package vn.misa.nadat.cukcuklite.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import vn.misa.nadat.cukcuklite.items.ItemReportDetail;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Adapter cho danh sách chi tiết báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportDetailAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ItemReportDetail> mItemReportDetails;

    /**
     * Khởi tạo ReportDetailAdapter
     *
     * @param context context của lớp gọi đến ReportDetailAdapter
     * @created_by nadat on 24/04/2019
     */
    public ReportDetailAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_report_detail, parent, false);
        return new ReportDetailVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReportDetailVH reportDetailVH = (ReportDetailVH) holder;
        reportDetailVH.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mItemReportDetails != null ? mItemReportDetails.size() : 0;
    }

    /**
     * Thiết lập danh sách chi tiết báo cáo
     *
     * @param itemReportDetails: danh sách chi tiết báo cáo
     * @created_by nadat on 24/04/2019
     */
    public void setDate(List<ItemReportDetail> itemReportDetails) {
        try {
            if (itemReportDetails != null) {
                mItemReportDetails = itemReportDetails;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ViewHolder của danh sách chi tiết báo cáo
     *
     * @created_by nadat on 24/04/2019
     */
    private class ReportDetailVH extends RecyclerView.ViewHolder {
        private ImageView ivBackgroundColor;
        private TextView tvNumber, tvInventoryItemName, tvQuantity, tvAmount;
        private View vSparator;

        private ReportDetailVH(View itemView) {
            super(itemView);
            try {
                ivBackgroundColor = itemView.findViewById(R.id.ivBackgroundColor);
                tvNumber = itemView.findViewById(R.id.tvNumber);
                tvInventoryItemName = itemView.findViewById(R.id.tvInventoryItemName);
                tvQuantity = itemView.findViewById(R.id.tvQuantity);
                tvAmount = itemView.findViewById(R.id.tvAmount);
                vSparator = itemView.findViewById(R.id.vSparator);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Gắn dữ liệu vào view.
         *
         * @param position: vị trí view
         * @created_by nadat on 24/04/2019
         */
        @SuppressLint("SetTextI18n")
        private void bindView(int position) {
            try {
                String[] colors = mContext.getResources().getStringArray(R.array.color_detail_report);
                ItemReportDetail detailReport = mItemReportDetails.get(position);

                Drawable drawable = CukCukLiteApp.getInstance().getResources().getDrawable(R.drawable.bg_circle_image_view);

                if (position < 6) {
                    int index = position % 6;
                    drawable.setColorFilter(Color.parseColor(colors[index]), PorterDuff.Mode.SRC);
                    ivBackgroundColor.setBackgroundDrawable(drawable);
                } else {
                    drawable.setColorFilter(Color.parseColor(colors[6]), PorterDuff.Mode.SRC);
                    ivBackgroundColor.setBackgroundDrawable(drawable);
                }

                tvNumber.setText(String.valueOf(position + 1));
                tvInventoryItemName.setText(detailReport.getNameDish());
                tvQuantity.setText(detailReport.getNumberDish() + " " + detailReport.getUnitDish());
                tvAmount.setText(PriceUtils.formatPrice(String.valueOf(detailReport.getTotalMoney())));
                if (position == mItemReportDetails.size() - 1) {
                    vSparator.setVisibility(View.INVISIBLE);
                } else {
                    vSparator.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
