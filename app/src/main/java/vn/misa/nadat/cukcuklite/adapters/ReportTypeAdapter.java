package vn.misa.nadat.cukcuklite.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;

/**
 * Adapter cho danh sách kiểu báo cáo
 *
 * @created_by nadat on 24/04/2019
 */
public class ReportTypeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private CallBack mCallBack;

    private String mTypeReport;
    private int count = 0;

    private List<String> mListTypeReport;

    /**
     * Khởi tạo ReportTypeAdapter
     *
     * @param context  context của lớp gọi đến ReportTypeAdapter
     * @param callBack interface trong lớp ReportTypeAdapter
     * @created_by nadat on 24/04/2019
     */
    public ReportTypeAdapter(Context context, CallBack callBack) {
        try {
            mContext = context;
            mCallBack = callBack;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_type_report, parent, false);
        return new ReportTypeVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReportTypeVH reportTypeVH = (ReportTypeVH) holder;
        reportTypeVH.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mListTypeReport != null ? mListTypeReport.size() : 0;
    }

    /**
     * Thiết lập danh sách kiểu báo cáo
     *
     * @param listTypeReport danh sách kiểu báo cáo
     * @param typeReport     kiểu báo cáo hiện tại
     * @created_by nadat on 24/04/2019
     */
    public void setDate(List<String> listTypeReport, String typeReport) {
        try {
            if (listTypeReport != null && !TextUtils.isEmpty(typeReport)) {
                mListTypeReport = listTypeReport;
                mTypeReport = typeReport;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallBack {
        void onClick(String typeReport);
    }

    private class ReportTypeVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTypeReport;
        private ImageView ivCheck;

        private ReportTypeVH(View itemView) {
            super(itemView);
            try {
                tvTypeReport = itemView.findViewById(R.id.tvTypeReport);
                ivCheck = itemView.findViewById(R.id.ivCheck);
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
                String typeReport = mListTypeReport.get(position);

                tvTypeReport.setText(typeReport);

                if (typeReport.equals(mTypeReport)) {
                    ivCheck.setVisibility(View.VISIBLE);
                    count++;
                } else {
                    ivCheck.setVisibility(View.GONE);
                }
                if (count == 0 && position == mListTypeReport.size() - 1) {
                    ivCheck.setVisibility(View.VISIBLE);
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
                mCallBack.onClick(mListTypeReport.get(getAdapterPosition()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
