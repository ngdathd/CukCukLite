package com.ngdat.cukcuklite.screen.dialogs.icon;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.base.listeners.IOnItemClickListener;
import com.ngdat.cukcuklite.utils.ImageUtils;

/**
 * Adapter cho danh sách icon món ăn
 * Created at 27/03/2019
 */
public class IconAdapter extends ListAdapter<String> {

    private IOnItemClickListener<String> mItemClickListener;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public IconAdapter(Context context, List<String> listImage) {
        super(context);
        mListData = listImage;
    }

    public void setItemClickListener(IOnItemClickListener<String> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dish_icon, viewGroup, false);
        return new DishIconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DishIconViewHolder dishIconViewHolder = (DishIconViewHolder) viewHolder;
        final String fileName = mListData.get(i);
        dishIconViewHolder.bind(fileName);
        dishIconViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(fileName);
            }
        });
    }

    /**
     * Lớp Item cho danh sách icon món ăn
     * Created at 27/03/2019
     */
    public class DishIconViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;

        DishIconViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }

        /**
         * Phương thức gán icon cho ImageView
         * Created at 27/03/2019
         *
         * @param imageFileName - tên file icon
         */
        void bind(String imageFileName) {
            if (imageFileName != null) {
                ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(mContext, imageFileName));
            }
        }
    }
}
