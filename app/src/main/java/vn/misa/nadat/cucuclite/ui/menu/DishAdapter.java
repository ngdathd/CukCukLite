package vn.misa.nadat.cucuclite.ui.menu;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import vn.misa.nadat.cucuclite.R;
import vn.misa.nadat.cucuclite.items.ItemDish;

public class DishAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ItemDish> mDishList;
    private IClickItemDish mIClickItemDish;

    public DishAdapter(Context context, List<ItemDish> dishList, IClickItemDish iClickItemDish) {
        mContext = context;
        mDishList = dishList;
        mIClickItemDish = iClickItemDish;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_dish, viewGroup, false);
        return new DishViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DishViewHolder dishViewHolder = (DishViewHolder) viewHolder;
        dishViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mDishList.size();
    }

    private void setIcon(ImageView icon, String fileName) {
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open(mContext.getResources().getString(R.string.asset_folder) + "/" + fileName);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            icon.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IClickItemDish {
        void clickDishItem(int i);
    }

    private class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgColor;
        private ImageView imgIcon;
        private TextView name;
        private TextView price;
        private LinearLayout sale;

        private DishViewHolder(@NonNull View itemView) {
            super(itemView);
            imgColor = itemView.findViewById(R.id.img_bg_icon_color);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            name = itemView.findViewById(R.id.tvInventoryItemName);
            price = itemView.findViewById(R.id.tvUnitPrice);
            sale = itemView.findViewById(R.id.lnInactiveNotify);
            itemView.setOnClickListener(this);
        }

        private void bind(int position) {
//            imgColor.setImageResource(mDishList.get(position).getImgColor());
            setIcon(imgIcon, mDishList.get(position).getImgIcon());
            name.setText(mDishList.get(position).getName());
            price.setText(mDishList.get(position).getPrice());
            if (mDishList.get(position).isSale()) {
                sale.setVisibility(View.INVISIBLE);
            } else {
                sale.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            mIClickItemDish.clickDishItem(getAdapterPosition());
        }
    }
}
