package com.ngdat.cukcuklite.screen.sale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.adapters.ListAdapter;
import com.ngdat.cukcuklite.data.models.Order;

public class OrderAdapter extends ListAdapter<Order> {

    private static final String TAG = "OrderAdapter";
    private IOrderClickListener mIOrderClickListener;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public OrderAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order, viewGroup, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        OrderHolder orderHolder = (OrderHolder) viewHolder;
        orderHolder.bind(mListData.get(i));
    }

    /**
     * Phương thức gán listener lắng nghe xử lý sự kiện cho item view
     * Created at 13/04/2019
     *
     * @param orderClickListener - listener
     */
    public void setIOrderClickListener(IOrderClickListener orderClickListener) {
        mIOrderClickListener = orderClickListener;
    }

    @Override
    public void setListData(List<Order> listData) {
        Collections.sort(listData, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return (int) (o1.getDateCreated() - o2.getDateCreated());
            }
        });
        mListData.clear();
        mListData.addAll(listData);
        notifyDataSetChanged();
    }

    public interface IOrderClickListener {
        void orderClick(String billId);

        void cancelOrder(String billId);

        void payOrder(String billId);
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        private ImageView ivBackground;
        private TextView tvTable, tvPerson, tvContent, tvTotalMoney;
        private LinearLayout lnCancel, lnPay;
        private Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);
        private String[] colorArr = mContext.getResources().getStringArray(R.array.color_list);

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            initEvents(itemView);
        }


        /**
         * Phương thức khởi tạo gắn các giá trị từ hóa đơn vào view
         * Created at 27/03/2019
         *
         * @param order - hóa đơn
         */
        void bind(Order order) {
            if (order == null) {
                return;
            }
            if (order.getBillId() != null) {
                Log.d(TAG, "bind: " + order.getTableNumber());
                //Lấy danh sách tên món ăn chi tiết, số lượng, tổng tiền của
                tvTotalMoney.setText(NumberFormat.getNumberInstance(Locale.US).format(order.getTotalMoney()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvContent.setText(Html.fromHtml(order.getContent(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvContent.setText(Html.fromHtml(order.getContent()));
                }
                tvPerson.setText(String.valueOf(order.getNumberCustomer()));
                if (order.getTableNumber() > 0) {
                    tvTable.setText(String.valueOf(order.getTableNumber()));
                    drawable.setColorFilter(Color.parseColor(colorArr[getAdapterPosition() % colorArr.length]), PorterDuff.Mode.SRC);
                    ivBackground.setBackground(drawable);
                } else {
                    drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);
                    tvTable.setText("");
                    ivBackground.setBackground(drawable);
                }
            }
        }

        /**
         * Phương thức gắn sự kiện cho view
         * Created at 13/04/2019
         *
         * @param itemView - view
         */
        private void initEvents(View itemView) {
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mIOrderClickListener.orderClick(mListData.get(getAdapterPosition()).getBillId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            lnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mIOrderClickListener.cancelOrder(mListData.get(getAdapterPosition()).getBillId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            lnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mIOrderClickListener.payOrder(mListData.get(getAdapterPosition()).getBillId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * Phương thức tham chiếu, khởi tạo view
         * Created at 13/04/2019
         */
        private void initViews(View view) {
            try {
                ivBackground = view.findViewById(R.id.ivBackground);
                tvTable = view.findViewById(R.id.tvTable);
                tvPerson = view.findViewById(R.id.tvPerson);
                tvContent = view.findViewById(R.id.tvContent);
                tvTotalMoney = view.findViewById(R.id.tvTotalMoney);
                lnCancel = view.findViewById(R.id.lnCancel);
                lnPay = view.findViewById(R.id.lnPay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
