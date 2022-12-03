package vn.misa.nadat.cukcuklite.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;

/**
 * Adapter của bảng màu.
 *
 * @created_by nadat on 15/04/2019
 */
public class PickColorAdapter extends RecyclerView.Adapter {
    private List<String> mColors;
    private Drawable mDrawable;
    private String mColorChecked;
    private OnClickItemColorListener mOnClickItemColorListener;

    /**
     * Khởi tạo PickColorAdapter.
     *
     * @param colors: danh sách mã màu
     * @created_by nadat on 15/04/2019
     */
    public PickColorAdapter(List<String> colors, OnClickItemColorListener onClickItemColorListener) {
        mColors = colors;
        mOnClickItemColorListener = onClickItemColorListener;
    }

    /**
     * Setup màu đã được chọn.
     *
     * @param colorChecked: màu được chọn
     * @created_by nadat on 15/04/2019
     */
    public void setColorChecked(String colorChecked) {
        mColorChecked = colorChecked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mDrawable = viewGroup.getContext().getResources().getDrawable(R.drawable.bg_circle_image_view);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pick, viewGroup, false);
        return new PickColorVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PickColorVH pickColorVH = (PickColorVH) viewHolder;
        pickColorVH.bindView(i);
    }

    @Override
    public int getItemCount() {
        try {
            return mColors == null ? 0 : mColors.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Interface lắng nghe sự kiện click vào màu.
     *
     * @created_by nadat on 15/04/2019
     */
    public interface OnClickItemColorListener {
        void onClickItemColor(String color);
    }

    /**
     * ViewHolder của danh sách mã màu.
     *
     * @created_by nadat on 15/04/2019
     */
    private class PickColorVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivItem;
        private ImageView ivCheckAccept;

        private PickColorVH(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            ivCheckAccept = itemView.findViewById(R.id.ivCheckAccept);
            itemView.setOnClickListener(this);
        }

        /**
         * Gắn dữ liệu vào view.
         *
         * @param position: vị trí view
         * @created_by nadat on 15/04/2019
         */
        private void bindView(int position) {
            try {
                mDrawable.setColorFilter(Color.parseColor(mColors.get(position)), PorterDuff.Mode.SRC);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ivItem.setBackground(mDrawable);
                }
                if (mColors.get(position).equals(mColorChecked)) {
                    ivCheckAccept.setVisibility(View.VISIBLE);
                } else {
                    ivCheckAccept.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Bắt sự kiện click vào 1 màu.
         *
         * @param v: view được click
         * @created_by nadat on 15/04/2019
         */
        @Override
        public void onClick(View v) {
            try {
                mOnClickItemColorListener.onClickItemColor(mColors.get(getAdapterPosition()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
