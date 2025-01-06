package com.ngdat.cukcuklite.screen.dishorder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Locale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.base.listeners.IOnItemClickListener;
import com.ngdat.cukcuklite.data.local.dish.DishDataSource;
import com.ngdat.cukcuklite.data.models.BillDetail;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.screen.dialogs.caculator.InputNumberDialog;
import com.ngdat.cukcuklite.utils.ImageUtils;

/**
 * Adapter cho danh sách món ăn
 * Created at 18/04/2019
 */
public class DishOrderAdapter extends ListAdapter<BillDetail> {

    private IOnItemClickListener<Integer> mOnItemClickListener;


    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public DishOrderAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dish_order, viewGroup, false);
        return new DishOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DishOrderHolder dishOrderHolder = (DishOrderHolder) viewHolder;
        dishOrderHolder.bind(mListData.get(i));
    }

    /**
     * Phương thức gán listener lắng nghe khi click vào item
     * Created at 12/04/2019
     *
     * @param onItemClickListener - listener
     */
    public void setOnItemClickListener(IOnItemClickListener<Integer> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * Tính tổng tiền hóa đơn
     * Created at 12/04/2019
     */
    private void totalMoney() {
        try {
            int totalMoney = 0;
            for (int i = 0; i < mListData.size(); i++) {
                totalMoney += mListData.get(i).getTotalMoney();
            }
            mOnItemClickListener.onItemClick(totalMoney);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HIển thị dialog nhập số
     * Created at 19/04/2019
     *
     * @param flag           - cờ cho title dialog
     * @param input          - text từ edittext bàn phím
     * @param dialogCallBack - callback cho dialog
     */
    private void showDialogNumber(int flag, CharSequence input,
                                  InputNumberDialog.DialogCallBack dialogCallBack) {
        try {
            InputNumberDialog inputNumberDialog = new InputNumberDialog(flag, dialogCallBack,
                    input);
            FragmentManager fm = ((DishOrderActivity) mContext).getSupportFragmentManager();
            inputNumberDialog.show(fm, InputNumberDialog.NUMBER_INPUT_DIALOG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class DishOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivICon, ivDefault;
        private TextView tvDishName, tvPrice, tvQuantity;
        private LinearLayout lnQuantity;
        private Button btnMinus, btnPlus;
        private ConstraintLayout clDishOrder;
        private BillDetail mBillDetail;
        private DishDataSource mDishDataSource;
        private int mPrice;

        public DishOrderHolder(@NonNull View itemView) {
            super(itemView);
            mDishDataSource = DishDataSource.getInstance();
            initViews(itemView);
            initEvents();
        }

        /**
         * Phương thức gắn sự kiện cho view
         * Created at 12/04/2019
         */
        private void initEvents() {
            clDishOrder.setOnClickListener(this);
            btnMinus.setOnClickListener(this);
            btnPlus.setOnClickListener(this);
            tvQuantity.setOnClickListener(this);
            ivDefault.setOnClickListener(this);
        }

        /**
         * Phương thức tham chiếu, khởi tạo view
         * Created at 12/04/2019
         */
        private void initViews(View view) {
            ivICon = view.findViewById(R.id.ivIcon);
            ivDefault = view.findViewById(R.id.ivDefault);
            tvDishName = view.findViewById(R.id.tvDishName);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            lnQuantity = view.findViewById(R.id.lnQuantity);
            tvPrice = view.findViewById(R.id.tvPrice);
            btnMinus = view.findViewById(R.id.btnMinus);
            btnPlus = view.findViewById(R.id.btnPlus);
            clDishOrder = view.findViewById(R.id.clDishOrder);
        }

        /**
         * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
         * Created at 12/04/2019
         *
         * @param v - view xảy ra sự kiện
         */
        @Override
        public void onClick(View v) {
            //lấy số lượng món ăn từ text view
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            switch (v.getId()) {
                case R.id.clDishOrder:
                    try {
                        if (quantity == 0) {
                            //khi click vào item, nếu hiện tại số lượng là 0 thì thay đổi background
                            clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_button_gray));
                            lnQuantity.setVisibility(View.VISIBLE);
                            ivDefault.setVisibility(View.VISIBLE);
                            ivICon.setVisibility(View.INVISIBLE);
                        }
                        //tăng số lượng lên 1
                        tvQuantity.setText(String.valueOf(++quantity));
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tvQuantity:
                    try {
                        showDialogNumber(InputNumberDialog.FLAG_QUANTITY, String.valueOf(quantity), new InputNumberDialog.DialogCallBack() {
                            @Override
                            public void setAmount(String amount) {
                                try {
                                    if (TextUtils.isEmpty(amount)) {
                                        amount = "0";
                                    }
                                    int quantity = Integer.parseInt(amount);
                                    mBillDetail.setQuantity(quantity);
                                    mBillDetail.setTotalMoney(quantity * mPrice);
                                    mListData.set(getAdapterPosition(), mBillDetail);
                                    tvQuantity.setText(amount);
                                    if (quantity == 0) {
                                        ivICon.setVisibility(View.VISIBLE);
                                        ivDefault.setVisibility(View.GONE);
                                        lnQuantity.setVisibility(View.GONE);
                                        clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                                    }
                                    totalMoney();
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                } catch (Resources.NotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.ivDefault:
                    try {
                        ivDefault.setVisibility(View.GONE);
                        ivICon.setVisibility(View.VISIBLE);
                        quantity = 0;
                        tvQuantity.setText(String.valueOf(0));
                        clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                        lnQuantity.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btnPlus:
                    try {
                        //tăng số lượng lên 1
                        tvQuantity.setText(String.valueOf(++quantity));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btnMinus:
                    try {
                        --quantity;
                        if (quantity == 0) {
                            //giảm số lượng nếu bằng 0 thì thay đổi background và hiện icon phủ xanh
                            clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                            lnQuantity.setVisibility(View.GONE);
                            ivDefault.setVisibility(View.INVISIBLE);
                            ivICon.setVisibility(View.VISIBLE);
                        } else if (quantity < 0) {
                            quantity = 0;
                        }
                        tvQuantity.setText(String.valueOf(quantity));
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            //gán số lượng và tổng tiền món ăn của hóa đơn chi tiết
            mBillDetail.setQuantity(quantity);
            mBillDetail.setTotalMoney(quantity * mPrice);
            mListData.set(getAdapterPosition(), mBillDetail);
            totalMoney();
        }

        /**
         * Phương thức kết gán dữ liệu cho item
         * Created at 12/04/2019
         *
         * @param billDetail - hóa đơn chi tiết bao gồm các thông tin về món ăn trong hóa đơn
         */
        void bind(BillDetail billDetail) {
            try {
                if (billDetail == null) {
                    return;
                }
                mBillDetail = billDetail;
                if (billDetail.getDishId() != null) {
                    int quantity = billDetail.getQuantity();
                    Dish dish = mDishDataSource.getDishById(billDetail.getDishId());
                    if (dish != null) {
                        tvDishName.setText(dish.getDishName());
                        Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);
                        drawable.setColorFilter(Color.parseColor(dish.getColorCode()), PorterDuff.Mode.SRC);
                        ivICon.setBackground(drawable);
                        ivICon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(mContext, dish.getIconPath()));
                        if (quantity > 0) {
                            mPrice = billDetail.getTotalMoney() / quantity;
                            tvQuantity.setText(String.valueOf(billDetail.getQuantity()));
                            ivDefault.setVisibility(View.VISIBLE);
                            ivICon.setVisibility(View.INVISIBLE);
                            clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_button_gray));
                            lnQuantity.setVisibility(View.VISIBLE);
                        } else {
                            mPrice = dish.getPrice();
                            ivICon.setVisibility(View.VISIBLE);
                            ivDefault.setVisibility(View.GONE);
                            lnQuantity.setVisibility(View.GONE);
                            tvQuantity.setText(R.string.price_default);
                            clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                        }
                        tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(mPrice));
                    }
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
