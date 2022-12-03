package vn.misa.nadat.cukcuklite.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;

/**
 * Adapter của danh sách các icon.
 *
 * @created_by nadat on 15/04/2019
 */
public class PickIconAdapter extends RecyclerView.Adapter {
    private static final String SUB_FOLDER = "icondefault";
    private List<String> mIcons;
    private Context mContext;
    private OnClickItemIconListener mOnClickItemIconListener;

    /**
     * Khởi tạo PickIconAdapter.
     *
     * @param icons: danh sách các icon
     * @created_by nadat on 15/04/2019
     */
    public PickIconAdapter(List<String> icons, OnClickItemIconListener onClickItemIconListener) {
        mIcons = icons;
        mOnClickItemIconListener = onClickItemIconListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pick, viewGroup, false);
        return new PickIconVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PickIconVH pickIconVH = (PickIconVH) viewHolder;
        pickIconVH.bindView(i);
    }

    @Override
    public int getItemCount() {
        try {
            return mIcons == null ? 0 : mIcons.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Interface lắng nghe sự kiện click vào icon.
     *
     * @created_by nadat on 15/04/2019
     */
    public interface OnClickItemIconListener {
        void onClickItemIcon(String icon);
    }

    /**
     * ViewHolder của danh sách icon.
     *
     * @created_by nadat on 15/04/2019
     */
    private class PickIconVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivItem;

        private PickIconVH(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
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
                InputStream ims = mContext.getAssets().open(SUB_FOLDER + "/" + mIcons.get(position));
                Drawable d = Drawable.createFromStream(ims, null);
                ivItem.setImageDrawable(d);
                ims.close();
            } catch (IOException ex) {
                ex.fillInStackTrace();
            }
        }

        /**
         * Bắt sự kiện click vào 1 icon.
         *
         * @param v: view được click
         * @created_by nadat on 15/04/2019
         */
        @Override
        public void onClick(View v) {
            try {
                mOnClickItemIconListener.onClickItemIcon(mIcons.get(getAdapterPosition()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
