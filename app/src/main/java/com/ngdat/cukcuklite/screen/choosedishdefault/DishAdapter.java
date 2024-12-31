package com.ngdat.cukcuklite.screen.choosedishdefault;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Locale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.base.listeners.IOnItemClickListener;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.utils.ImageUtils;

/**
 * Adapter cho danh sách món ăn
 * Created at 27/03/2019
 */
public class DishAdapter extends ListAdapter<Dish> {

    private IOnItemClickListener<Dish> mItemClickListener;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public DishAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dish, viewGroup, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        DishViewHolder dishViewHolder = (DishViewHolder) viewHolder;
        final Dish dish = mListData.get(i);
        dishViewHolder.bind(dish);
        dishViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(dish);
            }
        });
    }

    /**
     * Phương thức truyền listener cho sự kiện onclick
     * Created at 27/03/2019
     *
     * @param itemClickListener - listener
     */
    public void setItemClickListener(IOnItemClickListener<Dish> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * Lớp Item cho danh sách
     * Created at 27/03/2019
     */
    public class DishViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnDish;
        private TextView tvDishName, tvPrice, tvStopSale;
        private Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);

        DishViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDish = itemView.findViewById(R.id.btnDish);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStopSale = itemView.findViewById(R.id.tvState);
        }

        /**
         * Phương thức khởi tạo gắn các giá trị từ món ăn vào view
         * Created at 27/03/2019
         *
         * @param dish - món ăn
         */
        void bind(Dish dish) {
            try {
                if (dish != null) {
                    if (dish.isSale()) {
                        tvStopSale.setVisibility(View.GONE);
                    } else {
                        tvStopSale.setVisibility(View.VISIBLE);
                    }
                    tvDishName.setText(dish.getDishName());
                    tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(dish.getPrice()));
                    drawable.setColorFilter(Color.parseColor(dish.getColorCode()), PorterDuff.Mode.SRC);
                    btnDish.setBackground(drawable);
                    btnDish.setImageDrawable(ImageUtils.getDrawableFromImageAssets(mContext, dish.getIconPath()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
