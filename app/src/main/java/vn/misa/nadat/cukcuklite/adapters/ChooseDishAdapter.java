package vn.misa.nadat.cukcuklite.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.dialogs.PaymentKeyboardDialog;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;
import vn.misa.nadat.cukcuklite.utils.Utils;

/**
 * Adapter của danh sách chọn món ăn.
 *
 * @created_by nadat on 20/04/2019
 */
public class ChooseDishAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ItemDish> mItemDishes;
    private SparseIntArray mListNumberDishesInSale;
    private CallbackListDishesInSale mCallbackListDishesInSale;
    private TextView tvTotalMoney;
    private int mTotalMoney = 0;

    /**
     * Khởi tạo ChooseDishAdapter
     *
     * @param context:                  Context hiển thị
     * @param callbackListDishesInSale: interface lắng nghe chọn món
     * @created_by nadat on 20/04/2019
     */
    public ChooseDishAdapter(Context context, CallbackListDishesInSale callbackListDishesInSale) {
        mContext = context;
        mCallbackListDishesInSale = callbackListDishesInSale;
        mListNumberDishesInSale = new SparseIntArray();
    }

    /**
     * Setup tổng số tiền.
     *
     * @param tvTotalMoney: textview hiển thị tổng số tiền
     * @created_by nadat on 20/04/2019
     */
    public void setTvTotalMoney(TextView tvTotalMoney) {
        this.tvTotalMoney = tvTotalMoney;
    }

    /**
     * Setup danh sách món ăn được chọn.
     *
     * @param itemDishes: danh sách món ăn
     * @created_by nadat on 20/04/2019
     */
    public void setItemDishes(List<ItemDish> itemDishes) {
        mItemDishes = itemDishes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_choose_dish, viewGroup, false);
        return new ChooseDishHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ChooseDishHolder chooseDishHolder = (ChooseDishHolder) viewHolder;
        chooseDishHolder.bindView(i);
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
     * Hoàn thành chọn món ăn.
     *
     * @created_by nadat on 20/04/2019
     */
    public void completedListDishesInSale(int idItemSale, int type) {
        mCallbackListDishesInSale.callbackListNumberDishesInSale(mListNumberDishesInSale, idItemSale, type);
    }

    /**
     * interface lắng nghe chọn món ăn.
     *
     * @created_by nadat on 20/04/2019
     */
    public interface CallbackListDishesInSale {
        void callbackListNumberDishesInSale(SparseIntArray listNumberDishesInSale, int idItemSale, int type);
    }

    /**
     * ViewHolder của danh sách chọn món.
     *
     * @created_by nadat on 20/04/2019
     */
    private class ChooseDishHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int mNumber = 0;
        private int mNumberBefore = 0;
        private ImageView imgAvatar;
        private ImageView imgIcon;
        private TextView tvName;
        private TextView tvPrice;
        private ImageView imgSelected;
        private ImageView imgMinus;
        private ImageView imgPlus;
        private TextView tvQuantity;
        private LinearLayout lnContent;
        private RelativeLayout realIcon;
        private LinearLayout lnQuantity;

        private ChooseDishHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            imgSelected = itemView.findViewById(R.id.imgSelected);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            imgSelected.setOnClickListener(this);
            imgMinus.setOnClickListener(this);
            imgPlus.setOnClickListener(this);
            tvQuantity.setOnClickListener(this);
            itemView.setOnClickListener(this);

            lnContent = itemView.findViewById(R.id.lnContent);
            realIcon = itemView.findViewById(R.id.realIcon);
            lnQuantity = itemView.findViewById(R.id.lnQuantity);
        }

        /**
         * Gắn dữ liệu vào view.
         *
         * @param position: vị trí view
         * @created_by nadat on 16/04/2019
         */
        private void bindView(int position) {
            try {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.bg_circle_image_view);
                drawable.setColorFilter(Color.parseColor(mItemDishes.get(position).getItemDishColor()), PorterDuff.Mode.SRC);
                imgAvatar.setBackgroundDrawable(drawable);
                imgIcon.setImageBitmap(Utils.getBitmapFromAssets(mItemDishes.get(position).getItemDishIcon()));
                tvName.setText(mItemDishes.get(position).getItemDishName());
                tvPrice.setText(mItemDishes.get(position).getItemDishPrice());

                int count = mItemDishes.get(position).getUseCount();
                if (count != 0) {
                    enableTool();
                    tvQuantity.setText(PriceUtils.formatPrice(String.valueOf(count)));
                    mNumber = count;
                    mListNumberDishesInSale.append(mItemDishes.get(getAdapterPosition()).getItemDishId(), mNumber);
                }
                mTotalMoney = mTotalMoney + (mNumber - mNumberBefore) * PriceUtils.formatPriceToInt(mItemDishes.get(getAdapterPosition()).getItemDishPrice());
                tvTotalMoney.setText(PriceUtils.formatPrice(mTotalMoney + ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Bắt sự kiện click vào các view.
         *
         * @param v: view được click
         * @created_by nadat on 20/04/2019
         */
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.imgMinus: {
                        handleMinus();
                        break;
                    }
                    case R.id.imgPlus: {
                        handlePlus();
                        break;
                    }
                    case R.id.imgSelected: {
                        disableTool();
                        break;
                    }
                    case R.id.tvQuantity: {
                        FragmentManager fm = ((AppCompatActivity) mContext).getSupportFragmentManager();
                        PaymentKeyboardDialog paymentKeyboardDialog =
                                PaymentKeyboardDialog.createInstance(tvQuantity.getText().toString(), new PaymentKeyboardDialog.OnClickDoneListener() {
                                    @Override
                                    public void onClickDone(String quantity) {
                                        tvQuantity.setText(quantity);
                                        mNumberBefore = mNumber;
                                        mNumber = Integer.parseInt(PriceUtils.formatNumber(quantity));
                                        mListNumberDishesInSale.append(mItemDishes.get(getAdapterPosition()).getItemDishId(), mNumber);
                                        mTotalMoney = mTotalMoney + (mNumber - mNumberBefore) * PriceUtils.formatPriceToInt(mItemDishes.get(getAdapterPosition()).getItemDishPrice());
                                        tvTotalMoney.setText(PriceUtils.formatPrice(mTotalMoney + ""));
                                        if (mNumber == 0) {
                                            disableTool();
                                        }
                                    }
                                });
                        paymentKeyboardDialog.show(fm, "PaymentKeyboardDialog");
                        break;
                    }
                    default: {
                        enableTool();
                        handlePlus();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Xử lí giảm số lượng món ăn.
         *
         * @created_by nadat on 17/04/2019
         */
        private void handleMinus() {
            try {
                mNumberBefore = mNumber;
                mNumber--;
                if (mNumber == 0) {
                    disableTool();
                } else {
                    tvQuantity.setText(PriceUtils.formatPrice(String.valueOf(mNumber)));
                    mListNumberDishesInSale.append(mItemDishes.get(getAdapterPosition()).getItemDishId(), mNumber);
                }
                mTotalMoney = mTotalMoney + (mNumber - mNumberBefore) * PriceUtils.formatPriceToInt(mItemDishes.get(getAdapterPosition()).getItemDishPrice());
                tvTotalMoney.setText(PriceUtils.formatPrice(mTotalMoney + ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Xử lí tăng số lượng món ăn.
         *
         * @created_by nadat on 17/04/2019
         */
        private void handlePlus() {
            try {
                mNumberBefore = mNumber;
                mNumber++;
                tvQuantity.setText(PriceUtils.formatPrice(String.valueOf(mNumber)));
                mListNumberDishesInSale.append(mItemDishes.get(getAdapterPosition()).getItemDishId(), mNumber);
                mTotalMoney = mTotalMoney + (mNumber - mNumberBefore) * PriceUtils.formatPriceToInt(mItemDishes.get(getAdapterPosition()).getItemDishPrice());
                tvTotalMoney.setText(PriceUtils.formatPrice(mTotalMoney + ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Hiển thị giao diện số lượng món ăn.
         *
         * @created_by nadat on 17/04/2019
         */
        private void enableTool() {
            try {
                lnContent.setBackgroundColor(Color.parseColor("#ffededed"));
                imgSelected.setVisibility(View.VISIBLE);
                realIcon.setVisibility(View.GONE);
                lnQuantity.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Ẩn giao diện số lượng món ăn.
         *
         * @created_by nadat on 17/04/2019
         */
        private void disableTool() {
            try {
                mTotalMoney = mTotalMoney - mNumber * PriceUtils.formatPriceToInt(mItemDishes.get(getAdapterPosition()).getItemDishPrice());
                tvTotalMoney.setText(PriceUtils.formatPrice(mTotalMoney + ""));
                mNumber = 0;
                lnContent.setBackgroundColor(Color.parseColor("#ffffff"));
                imgSelected.setVisibility(View.GONE);
                realIcon.setVisibility(View.VISIBLE);
                lnQuantity.setVisibility(View.INVISIBLE);
                mListNumberDishesInSale.delete(mItemDishes.get(getAdapterPosition()).getItemDishId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
