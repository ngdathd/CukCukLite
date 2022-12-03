package vn.misa.nadat.cukcuklite.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.CukCukLiteApp;
import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.utils.Utils;

/**
 * Adapter của danh sách các món ăn.
 *
 * @created_by nadat on 16/04/2019
 */
public class DishAdapter extends RecyclerView.Adapter {
    private List<ItemDish> mItemDishes;
    private OnItemDishClickListener mOnItemDishClickListener;

    /**
     * Khởi tạo DishAdapter
     *
     * @param onItemDishClickListener: interface lắng nghe sự kiện click trên ItemDish
     * @created_by nadat on 16/04/2019
     */
    public DishAdapter(OnItemDishClickListener onItemDishClickListener) {
        mOnItemDishClickListener = onItemDishClickListener;
    }

    /**
     * Setup danh sách các món ăn.
     *
     * @param itemDishes: danh sách món ăn
     * @created_by nadat on 16/04/2019
     */
    public void setItemDishes(List<ItemDish> itemDishes) {
        mItemDishes = itemDishes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_dish, viewGroup, false);
        return new DishHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DishHolder dishHolder = (DishHolder) viewHolder;
        dishHolder.bindView(i);
    }

    @Override
    public int getItemCount() {
        if (mItemDishes != null) {
            return mItemDishes.size();
        } else {
            return 0;
        }
    }

    /**
     * interface lắng nghe sự kiện click vào ItemDish.
     *
     * @created_by nadat on 16/04/2019
     */
    public interface OnItemDishClickListener {
        void onClickItemDish(int id);
    }

    /**
     * ViewHolder của danh sách các món ăn.
     *
     * @created_by nadat on 16/04/2019
     */
    private class DishHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatImageView imgBgIconColor;
        private AppCompatImageView imgIcon;
        private TextView tvInventoryItemName;
        private TextView tvUnitPrice;
        private LinearLayout lnInactiveNotify;

        private DishHolder(@NonNull View itemView) {
            super(itemView);
            imgBgIconColor = itemView.findViewById(R.id.imgBgIconColor);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvInventoryItemName = itemView.findViewById(R.id.tvInventoryItemName);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            lnInactiveNotify = itemView.findViewById(R.id.lnInactiveNotify);
            itemView.setOnClickListener(this);
        }

        /**
         * Gắn dữ liệu vào view.
         *
         * @param position: vị trí view
         * @created_by nadat on 16/04/2019
         */
        private void bindView(int position) {
            try {
                Drawable drawable = CukCukLiteApp.getInstance().getResources().getDrawable(R.drawable.bg_circle_image_view);
                drawable.setColorFilter(Color.parseColor(mItemDishes.get(position).getItemDishColor()), PorterDuff.Mode.SRC);
                imgBgIconColor.setBackgroundDrawable(drawable);

                imgIcon.setImageBitmap(Utils.getBitmapFromAssets(mItemDishes.get(position).getItemDishIcon()));

                tvInventoryItemName.setText(mItemDishes.get(position).getItemDishName());

                tvUnitPrice.setText(mItemDishes.get(position).getItemDishPrice());

                if (mItemDishes.get(position).isInactive() == 0) {
                    lnInactiveNotify.setVisibility(View.INVISIBLE);
                } else {
                    lnInactiveNotify.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Bắt sự kiện click vào các view.
         *
         * @param v: view được click
         * @created_by nadat on 20/04/2019
         */
        @Override
        public void onClick(View v) {
            mOnItemDishClickListener.onClickItemDish(mItemDishes.get(getAdapterPosition()).getItemDishId());
        }
    }
}
