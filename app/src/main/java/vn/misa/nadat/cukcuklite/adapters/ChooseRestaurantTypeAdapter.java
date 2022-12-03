package vn.misa.nadat.cukcuklite.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;

public class ChooseRestaurantTypeAdapter extends RecyclerView.Adapter {
    private List<String> mListTypeRes;
    private int mPositionClick = 0;

    public ChooseRestaurantTypeAdapter(List<String> listTypeRes) {
        mListTypeRes = listTypeRes;
    }

    public int getPositionClick() {
        return mPositionClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_restaurant_type, viewGroup, false);
        return new ChooseRestaurantTypeVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ChooseRestaurantTypeVH chooseRestaurantTypeVH = (ChooseRestaurantTypeVH) viewHolder;
        chooseRestaurantTypeVH.bindView(i);
    }

    @Override
    public int getItemCount() {
        return mListTypeRes != null ? mListTypeRes.size() : 0;
    }

    private class ChooseRestaurantTypeVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvRestaurantType;
        private ImageView ivSelected;

        private ChooseRestaurantTypeVH(@NonNull View itemView) {
            super(itemView);
            tvRestaurantType = itemView.findViewById(R.id.tvRestaurantType);
            ivSelected = itemView.findViewById(R.id.ivSelected);
            itemView.setOnClickListener(this);
        }

        private void bindView(int position) {
            try {
                tvRestaurantType.setText(mListTypeRes.get(position));
                if (position == mPositionClick) {
                    ivSelected.setVisibility(View.VISIBLE);
                } else {
                    ivSelected.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            try {
                mPositionClick = getAdapterPosition();
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
