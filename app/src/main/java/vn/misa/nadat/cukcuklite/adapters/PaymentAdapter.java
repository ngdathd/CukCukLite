package vn.misa.nadat.cukcuklite.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Adapter của danh sách món ăn khi thanh toán.
 *
 * @created_by nadat on 24/04/2019
 */
public class PaymentAdapter extends RecyclerView.Adapter {
    private List<ItemDish> mItemDishes;
    private TextView tvTotalMoney;
    private TextView tvCustomMoney;
    private int mTotalPrice = 0;

    /**
     * Khởi tạo PaymentAdapter
     *
     * @param itemDishes: danh sách món ăn khi thanh toán
     * @created_by nadat on 24/04/2019
     */
    public PaymentAdapter(List<ItemDish> itemDishes) {
        mItemDishes = itemDishes;
    }

    /**
     * Setup textView.
     *
     * @param tvTotalMoney:  tổng tiền
     * @param tvCustomMoney: tiền khách trả
     * @created_by nadat on 24/04/2019
     */
    public void setTvTotalCustomAmount(TextView tvTotalMoney, TextView tvCustomMoney) {
        this.tvTotalMoney = tvTotalMoney;
        this.tvCustomMoney = tvCustomMoney;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_payment, viewGroup, false);
        return new PaymentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PaymentHolder dishHolder = (PaymentHolder) viewHolder;
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
     * ViewHolder của danh sách các món ăn khi thanh toán.
     *
     * @created_by nadat on 24/04/2019
     */
    private class PaymentHolder extends RecyclerView.ViewHolder {
        private TextView tvInventoryItemName;
        private TextView tvQuantity;
        private TextView tvUnitPrice;
        private TextView tvAmount;

        private PaymentHolder(@NonNull View itemView) {
            super(itemView);
            tvInventoryItemName = itemView.findViewById(R.id.tvInventoryItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }

        /**
         * Gắn dữ liệu vào view.
         *
         * @param position: vị trí view
         * @created_by nadat on 24/04/2019
         */
        private void bindView(int position) {
            try {
                tvInventoryItemName.setText(mItemDishes.get(position).getItemDishName());
                tvQuantity.setText(PriceUtils.formatPrice(mItemDishes.get(position).getUseCount() + ""));
                tvUnitPrice.setText(mItemDishes.get(position).getItemDishPrice());
                tvAmount.setText(mItemDishes.get(position).getTotalPrice());
                mTotalPrice = mTotalPrice + PriceUtils.formatPriceToInt(mItemDishes.get(position).getTotalPrice());
                tvTotalMoney.setText(PriceUtils.formatPrice(mTotalPrice + ""));
                tvCustomMoney.setText(PriceUtils.formatPrice(mTotalPrice + ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
