package com.ngdat.cukcuklite.screen.chooserestauranttype;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.data.models.RestaurantType;

/**
 * Adapter cho danh sách quán ăn
 * Created at 19/04/2019
 */
public class RestaurantTypeAdapter extends ListAdapter<RestaurantType> {

    private int mLastPositionSelected;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    RestaurantTypeAdapter(Context context) {
        super(context);
    }

    RestaurantType getRestaurantTypeIdSelected() {
        try {
            return mListData.get(mLastPositionSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_restaurant_type, viewGroup, false);
        return new RestaurantTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        try {
            RestaurantTypeHolder holder = (RestaurantTypeHolder) viewHolder;
            int size = mListData.size();
            if(i == --size){
                holder.lineBottom.setVisibility(View.INVISIBLE);
            }else {
                holder.lineBottom.setVisibility(View.VISIBLE);
            }
            holder.bind(mListData.get(i));
            if (i == mLastPositionSelected) {
                holder.ivSelected.setImageResource(R.drawable.ic_check_blue);
            } else {
                holder.ivSelected.setImageResource(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lớp item cho loại nhà hàng/quán ăn
     * Created at 05/04/2019
     */
    public class RestaurantTypeHolder extends RecyclerView.ViewHolder {
        private TextView tvRestaurantType;
        private ImageView ivSelected;
        private View lineBottom;

        public RestaurantTypeHolder(@NonNull View itemView) {
            super(itemView);
            tvRestaurantType = itemView.findViewById(R.id.tvRestaurantType);
            ivSelected = itemView.findViewById(R.id.ivSelected);
            lineBottom = itemView.findViewById(R.id.lineBottom);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int beforePosition = mLastPositionSelected;
                        mLastPositionSelected = getAdapterPosition();
                        notifyItemChanged(beforePosition);
                        notifyItemChanged(mLastPositionSelected);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * phương thức gán dữ liệu cho item
         * Created at 05/04/2019
         *
         * @param restaurantType - loại quán ăn/nhà hàng
         */
        void bind(RestaurantType restaurantType) {
            try {
                if (restaurantType == null) {
                    return;
                }
                tvRestaurantType.setText(restaurantType.getRestaurantName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
