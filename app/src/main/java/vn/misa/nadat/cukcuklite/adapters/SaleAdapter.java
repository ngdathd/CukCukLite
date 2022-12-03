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
import vn.misa.nadat.cukcuklite.items.ItemSale;

/**
 * Adapter của danh sách bán hàng.
 */
public class SaleAdapter extends RecyclerView.Adapter {
    private List<ItemSale> mItemSales;
    private OnItemSaleClickListener mOnItemSaleClickListener;

    /**
     * Khởi tạo SaleAdapter
     *
     * @param onItemSaleClickListener: interface lắng nghe sự kiện click trên ItemSale
     * @created_by nadat on 24/04/2019
     */
    public SaleAdapter(OnItemSaleClickListener onItemSaleClickListener) {
        mOnItemSaleClickListener = onItemSaleClickListener;
    }

    /**
     * Setup danh sách các ItemSale
     *
     * @param itemSales: danh sách bán hàng
     * @created_by nadat on 24/04/2019
     */
    public void setItemSales(List<ItemSale> itemSales) {
        mItemSales = itemSales;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_sale, viewGroup, false);
        return new SaleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SaleHolder saleHolder = (SaleHolder) viewHolder;
        saleHolder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (mItemSales != null) {
            return mItemSales.size();
        } else {
            return 0;
        }
    }

    /**
     * Xóa ItemSale
     *
     * @param itemSale: item được xóa
     * @created_by nadat on 24/04/2019
     */
    public void deleteItemSale(ItemSale itemSale) {
        if (itemSale == null) {
            return;
        }
        try {
            mItemSales.remove(itemSale);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * interface lắng nghe sự kiện click vào ItemSale.
     *
     * @created_by nadat on 16/04/2019
     */
    public interface OnItemSaleClickListener {
        void onClickItemSale(int id);

        void onClickCancelItemSale(ItemSale itemSale);

        void onClickTakeMoneyItemSale(int id);
    }

    /**
     * ViewHolder của danh sách bán hàng.
     *
     * @created_by nadat on 24/04/2019
     */
    private class SaleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvContent;
        private TextView tvAmount;
        private AppCompatImageView imgBackground;
        private TextView tvTable;
        private TextView tvPerson;

        private SaleHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            tvTable = itemView.findViewById(R.id.tvTable);
            tvPerson = itemView.findViewById(R.id.tvPerson);

            LinearLayout lnCancel = itemView.findViewById(R.id.lnCancel);
            LinearLayout lnTakeMoney = itemView.findViewById(R.id.lnTakeMoney);
            lnCancel.setOnClickListener(this);
            lnTakeMoney.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        /**
         * Gắn dữ liệu vào view.
         *
         * @param position: vị trí view
         * @created_by nadat on 24/04/2019
         */
        private void bindView(int position) {
            try {
                tvContent.setText(mItemSales.get(position).getContent());

                tvAmount.setText(mItemSales.get(position).getTotalMoney());

                if (mItemSales.get(position).getItemSaleColor() != null
                        && !mItemSales.get(position).getItemSaleColor().equals("")) {
                    Drawable drawable = CukCukLiteApp.getInstance().getResources().getDrawable(R.drawable.bg_circle_image_view);
                    drawable.setColorFilter(Color.parseColor(mItemSales.get(position).getItemSaleColor()), PorterDuff.Mode.SRC);
                    imgBackground.setBackgroundDrawable(drawable);
                } else {
                    imgBackground.setBackgroundResource(R.drawable.bg_circle_image_view_sale);
                }

                tvTable.setText(mItemSales.get(position).getNumberOfTable());

                if (mItemSales.get(position).getNumberOfPerson() != null
                        && !mItemSales.get(position).getNumberOfPerson().equals("")) {
                    tvPerson.setText(mItemSales.get(position).getNumberOfPerson());
                } else {
                    tvPerson.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Bắt sự kiện click vào view.
         *
         * @param v: view được click
         * @created_by nadat on 24/04/2019
         */
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.lnCancel: {
                        mOnItemSaleClickListener.onClickCancelItemSale(mItemSales.get(getAdapterPosition()));
                        break;
                    }
                    case R.id.lnTakeMoney: {
                        mOnItemSaleClickListener.onClickTakeMoneyItemSale(mItemSales.get(getAdapterPosition()).getIdSale());
                        break;
                    }
                    default: {
                        mOnItemSaleClickListener.onClickItemSale(mItemSales.get(getAdapterPosition()).getIdSale());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
