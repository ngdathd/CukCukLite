package vn.misa.nadat.cukcuklite.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.items.InputKeys;

/**
 * Adapter của danh sách các key.
 *
 * @created_by nadat on 15/04/2019
 */
public class PaymentKeyboardAdapter extends RecyclerView.Adapter {
    private OnClickInputKeyListener mOnClickInputKeyListener;
    private List<InputKeys> mKeys;
    private Context mContext;

    public PaymentKeyboardAdapter(List<InputKeys> keys) {
        this.mKeys = keys;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_input_key, viewGroup, false);
        return new PaymentKeyboardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PaymentKeyboardVH paymentKeyboardVH = (PaymentKeyboardVH) viewHolder;
        paymentKeyboardVH.bind(mKeys.get(i));
    }

    @Override
    public int getItemCount() {
        return mKeys == null ? 0 : mKeys.size();
    }

    /**
     * Thay đổi label khi xong phép tính.
     *
     * @created_by nadat on 15/04/2019
     */
    public void onChangeLabel() {
        mKeys.get(19).setName("Xong");
        notifyItemChanged(19);
    }

    /**
     * Phương thức set sự kiện click vào item.
     *
     * @param onClickListener Sự kiện call back khi click item
     * @created_by nadat on 15/04/2019
     */
    public void setOnClickListener(OnClickInputKeyListener onClickListener) {
        mOnClickInputKeyListener = onClickListener;
    }

    /**
     * Interface lắng nghe sự kiện click vào key.
     *
     * @created_by nadat on 15/04/2019
     */
    public interface OnClickInputKeyListener {
        void onClickItem(int id);
    }

    /**
     * ViewHolder của danh sách key.
     *
     * @created_by nadat on 15/04/2019
     */
    private class PaymentKeyboardVH extends RecyclerView.ViewHolder {
        private TextView tvInputKey;
        private ImageView ivIconInput;
        private RelativeLayout rlItemContainer;

        private PaymentKeyboardVH(@NonNull View itemView) {
            super(itemView);

            tvInputKey = itemView.findViewById(R.id.tvInputKey);
            ivIconInput = itemView.findViewById(R.id.ivIconInput);
            rlItemContainer = itemView.findViewById(R.id.rlItemContainer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        InputKeys input = mKeys.get(getAdapterPosition());
                        if (input.getId() == 8 || input.getId() == 12) {
                            mKeys.get(19).setName("=");
                            notifyItemChanged(19);
                        }
                        mOnClickInputKeyListener.onClickItem(mKeys.get(getAdapterPosition()).getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * Khởi tạo giá trị cho view.
         *
         * @param inputKey: Đối tượng bingding
         * @created_by nadat on 15/04/2019
         */
        private void bind(InputKeys inputKey) {
            try {
                tvInputKey.setText(inputKey.getName());

                if (inputKey.getId() == 2 || inputKey.getId() == 3 || inputKey.getId() == 20) {
                    tvInputKey.setTextSize(14);
                } else {
                    tvInputKey.setTextSize(20);
                }

                if (inputKey.getId() == 20) {  // Button "Xong"
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.bg_border_radius_gray);
                    drawable.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rlItemContainer.setBackground(drawable);
                    }

                    tvInputKey.setAllCaps(true);
                    tvInputKey.setTextColor(mContext.getResources().getColor(R.color.color_white));

                } else if (inputKey.getId() == 4) {  // Button "Xoa"
                    ivIconInput.setVisibility(View.VISIBLE);
                    tvInputKey.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
