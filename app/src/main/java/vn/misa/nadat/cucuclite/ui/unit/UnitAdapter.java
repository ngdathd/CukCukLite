package vn.misa.nadat.cucuclite.ui.unit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cucuclite.R;
import vn.misa.nadat.cucuclite.items.ItemUnit;

public class UnitAdapter extends RecyclerView.Adapter {
    private List<ItemUnit> mItemUnits;
    private int indexSelect;
    private OnClickUnit mOnClick;

    public UnitAdapter(List<ItemUnit> itemUnits, OnClickUnit onClick) {
        mItemUnits = itemUnits;
        mOnClick = onClick;
    }

    private void setIndexUnit(String unit) {
        if (unit == null) {
            indexSelect = 0;
            return;
        } else {
            for (int i = 0; i < mItemUnits.size(); i++) {
                if (mItemUnits.get(i).getUnit().equals(unit)) {
                    indexSelect = i;
                    return;
                }
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_unit_name, viewGroup, false);
        return new UnitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        UnitViewHolder unitViewHolder = (UnitViewHolder) viewHolder;
        unitViewHolder.bind(mItemUnits.get(i), i);
        unitViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexSelect = i;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemUnits.size();
    }

    public interface OnClickUnit {
        void onClickEdit(ItemUnit unit);
    }

    private class UnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvUnit;
        private ImageView imgCheck;
        private LinearLayout lnEdit;

        private UnitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUnit = itemView.findViewById(R.id.tvUnitName);
            imgCheck = itemView.findViewById(R.id.imgChecked);
            lnEdit = itemView.findViewById(R.id.lnEdit);
            itemView.setOnClickListener(this);
        }

        private void bind(final ItemUnit unit, final int position) {
            tvUnit.setText(unit.getUnit());
            if (position == indexSelect) {
                imgCheck.setVisibility(View.VISIBLE);
            } else {
                imgCheck.setVisibility(View.INVISIBLE);
            }
            lnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    indexSelect = position;
                    mOnClick.onClickEdit(unit);
                }
            });
        }

        @Override
        public void onClick(View v) {
            mItemUnits.get(getAdapterPosition()).setCheck(true);
            notifyDataSetChanged();
        }
    }
}
