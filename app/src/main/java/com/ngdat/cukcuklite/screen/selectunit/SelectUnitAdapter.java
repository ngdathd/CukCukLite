package com.ngdat.cukcuklite.screen.selectunit;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.data.models.Unit;

public class SelectUnitAdapter extends ListAdapter<Unit> {

    private ILongClickUnitListener mItemLongClickListener;
    private IEditUnitListener mEditUnitListener;
    private int mLastSelectedPosition;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    SelectUnitAdapter(Context context, List<Unit> units) {
        super(context);
        mListData = units;
    }

    String getUnitId() {
        return mListData.get(mLastSelectedPosition).getUnitId();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_unit, viewGroup, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final UnitViewHolder unitViewHolder = (UnitViewHolder) viewHolder;
        if (i == mLastSelectedPosition) {
            unitViewHolder.btnTick.setImageResource(R.drawable.ic_tick);
        } else {
            unitViewHolder.btnTick.setImageResource(0);
        }
        final Unit unit = mListData.get(i);
        unitViewHolder.bind(unit);
        unitViewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditUnitListener.onEditUnitItem(unit);
            }
        });
    }

    void setItemLongClickListener(ILongClickUnitListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    void setEditUnitListener(IEditUnitListener editUnitListener) {
        mEditUnitListener = editUnitListener;
    }

    void setLastSelectPosition(int lastSelectPosition) {
        mLastSelectedPosition = lastSelectPosition;
    }

    /**
     * Lắng nghe sự kiện thao tác với nút chỉnh sửa đơn vị
     * Created at 10/04/2019
     */
    public interface IEditUnitListener {
        void onEditUnitItem(Unit unit);
    }

    /**
     * Lắng nghe sự kiện ấn giữ đơn vị tính
     * Created at 10/04/2019
     */
    public interface ILongClickUnitListener {
        void onLongClickUnit(Unit unit, int position, View view);
    }

    /**
     * Lớp item cho đơn vị
     * Created at 27/03/2019
     */
    public class UnitViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnTick, btnEdit;
        private TextView tvUnit;

        UnitViewHolder(@NonNull final View itemView) {
            super(itemView);
            btnTick = itemView.findViewById(R.id.btnTick);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newPosition = mLastSelectedPosition;
                    mLastSelectedPosition = getAdapterPosition();
                    notifyItemChanged(newPosition);
                    notifyItemChanged(mLastSelectedPosition);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    mItemLongClickListener.onLongClickUnit(mListData.get(position), position, itemView);
                    return true;
                }
            });
        }

        void bind(Unit unit) {
            if (unit != null) {
                tvUnit.setText(unit.getUnitName());
            }
        }
    }
}
