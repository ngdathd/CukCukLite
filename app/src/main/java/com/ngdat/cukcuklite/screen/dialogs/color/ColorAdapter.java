package com.ngdat.cukcuklite.screen.dialogs.color;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.base.listeners.IOnItemClickListener;

/**
 * Adapter cho danh sách màu nền món ăn
 * Created at 27/03/2019
 */
public class ColorAdapter extends ListAdapter<String> {

    private int mLastSelectedPosition;
    private IOnItemClickListener<String> mListener;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public ColorAdapter(Context context, List<String> myColors, int lastSelectedPosition) {
        super(context);
        mLastSelectedPosition = lastSelectedPosition;
        mListData = myColors;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ColorViewHolder colorViewHolder = (ColorViewHolder) holder;
        if (position == mLastSelectedPosition) {
            colorViewHolder.btnColor.setImageResource(R.drawable.ic_check_white);
        } else {
            colorViewHolder.btnColor.setImageResource(0);
        }
        colorViewHolder.bind(mListData.get(position));
        colorViewHolder.btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(mListData.get(position));
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(view);
    }


    void setListener(IOnItemClickListener<String> listener) {
        mListener = listener;
    }

    /**
     * Lớp Item cho danh sách màu
     * Created at 27/03/2019
     */
    public class ColorViewHolder extends RecyclerView.ViewHolder {

        private ImageButton btnColor;
        private Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);

        ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            btnColor = itemView.findViewById(R.id.btnColor);
        }

        /**
         * Phương thức gán màu cho ImageButton
         *
         * @param color - mã màu
         */
        void bind(String color) {
            if (color != null) {
                drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC);
                btnColor.setBackground(drawable);
            }
        }
    }
}