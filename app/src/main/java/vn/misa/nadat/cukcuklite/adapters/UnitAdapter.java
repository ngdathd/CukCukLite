package vn.misa.nadat.cukcuklite.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.dialogs.AddEditUnitDialog;
import vn.misa.nadat.cukcuklite.dialogs.DeleteItemDialog;
import vn.misa.nadat.cukcuklite.items.ItemUnit;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Adapter của danh sách các đơn vị.
 *
 * @created_by nadat on 12/04/2019
 */
public class UnitAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private OnClickItemUnitListener mOnClickItemUnitListener;
    private List<ItemUnit> mItemUnits;
    private String mItemUnitNameFirstChoose;

    /**
     * Khởi tạo UnitAdapter.
     *
     * @param context:                 context mà UnitAdapter hiện thị
     * @param onClickItemUnitListener: interface lắng nghe sự kiện click trên ItemUnit
     * @created_by nadat on 16/04/2019
     */
    public UnitAdapter(Context context, OnClickItemUnitListener onClickItemUnitListener) {
        mContext = context;
        mOnClickItemUnitListener = onClickItemUnitListener;
    }

    /**
     * Setup tên đơn vị ở AddEditDishActivity.
     *
     * @param itemUnitNameFirstChoose: tên đơn vị được chọn trước đó
     * @created_by nadat on 16/04/2019
     */
    public void setItemUnitNameFirstChoose(String itemUnitNameFirstChoose) {
        mItemUnitNameFirstChoose = itemUnitNameFirstChoose;
    }

    /**
     * Lấy ra ItemUnit đang được chọn.
     *
     * @return ItemUnit
     * @created_by nadat on 12/04/2019
     */
    public ItemUnit getItemUnitIsCheck() {
        try {
            for (int i = 0; i < mItemUnits.size(); i++) {
                if (mItemUnits.get(i).isCheck()) {
                    return mItemUnits.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Setup danh sách ItemUnit cho UnitAdapter.
     *
     * @param itemUnits:           danh sách ItemUnit
     * @param itemUnitNameChecked: tên đơn vị được chọn
     * @created_by nadat on 12/04/2019
     */
    public void setItemUnits(List<ItemUnit> itemUnits, String itemUnitNameChecked) {
        if (itemUnits == null) {
            mItemUnits = new ArrayList<>();
            return;
        }
        if (itemUnitNameChecked == null) {
            mItemUnits = itemUnits;
            return;
        }
        try {
            for (int i = 0; i < itemUnits.size(); i++) {
                if (!itemUnits.get(i).getItemUnitName().equals(itemUnitNameChecked)) {
                    itemUnits.get(i).setCheck(false);
                } else {
                    itemUnits.get(i).setCheck(true);
                }
            }
            mItemUnits = itemUnits;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm và hiện thị 1 ItemUnit.
     *
     * @param itemUnit: ItemUnit được thêm
     * @created_by nadat on 12/04/2019
     */
    public void addItemUnit(ItemUnit itemUnit) {
        if (itemUnit == null) {
            return;
        }
        try {
            mItemUnits.add(itemUnit);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa và ẩn 1 ItemUnit.
     *
     * @param itemUnit: ItemUnit được xóa
     * @created_by nadat on 12/04/2019
     */
    public void deleteItemUnit(ItemUnit itemUnit) {
        if (itemUnit == null) {
            return;
        }
        try {
            mItemUnits.remove(itemUnit);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_unit, viewGroup, false);
        return new UnitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UnitHolder unitHolder = (UnitHolder) viewHolder;
        unitHolder.bindView(i);
    }

    @Override
    public int getItemCount() {
        if (mItemUnits == null) {
            return 0;
        }
        try {
            return mItemUnits.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Hiển thị popup xóa ItemUnit.
     *
     * @param view:     view mà popup hiện trên
     * @param position: vị trí ItemUnit được hiện
     * @created_by nadat on 12/04/2019
     */
    private void showPopUpDeleteItemUnit(View view, final int position) {
        try {
            PopupMenu popup = new PopupMenu(mContext, view);
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }

            popup.getMenuInflater().inflate(R.menu.menu_unit, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    new DeleteItemDialog(mContext, Constant.TYPE_UNIT, mItemUnits.get(position).getItemUnitName(),
                            new DeleteItemDialog.OnClickAcceptDialogDeleteItemListener() {
                                @Override
                                public void onClickAcceptDialogDeleteItem() {
                                    if (mItemUnits.get(position).isCheck()
                                            || mItemUnits.get(position).getItemUnitName().equalsIgnoreCase(mItemUnitNameFirstChoose)) {
                                        Toast toast = Toast.makeText(mContext,
                                                "Đơn vị " + mItemUnits.get(position).getItemUnitName() + " đang được sử dụng!",
                                                Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
                                        toast.show();
                                    } else {
                                        mOnClickItemUnitListener.onClickDeleteItemUnit(mItemUnits.get(position));
                                    }
                                }
                            }).show();
                    return true;
                }
            });
            popup.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị dialog sửa ItemUnit.
     *
     * @param position: vị trí ItemUnit được hiện
     * @created_by nadat on 12/04/2019
     */
    private void showDialogEditItemUnit(final int position) {
        try {
            new AddEditUnitDialog(mContext, mItemUnits.get(position).getItemUnitName(), new AddEditUnitDialog.OnClickAcceptDialogAddEditUnitListener() {
                @Override
                public void onClickAcceptDialogAddEditUnit(String newNameUnit) {
                    mOnClickItemUnitListener.onClickEditItemUnit(mItemUnits.get(position).getItemUnitID(), newNameUnit);
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * interface lắng nghe sự kiện click vào ItemUnit.
     *
     * @created_by nadat on 16/04/2019
     */
    public interface OnClickItemUnitListener {
        void onClickEditItemUnit(int itemUnitId, String newNameUnit);

        void onClickDeleteItemUnit(ItemUnit itemUnit);
    }

    /**
     * ViewHolder của danh sách các đơn vị.
     *
     * @created_by nadat on 12/04/2019
     */
    private class UnitHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView imgChecked;
        private TextView tvUnitName;
        private LinearLayout lnEdit;

        private UnitHolder(@NonNull final View itemView) {
            super(itemView);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            tvUnitName = itemView.findViewById(R.id.tvUnitName);
            lnEdit = itemView.findViewById(R.id.lnEdit);

            itemView.setOnClickListener(new View.OnClickListener() {    // click vào cả itemView
                @Override
                public void onClick(View v) {
                    try {
                        if (!mItemUnits.get(getAdapterPosition()).isCheck()) {
                            mItemUnits.get(getAdapterPosition()).setCheck(true);
                        }
                        for (int i = 0; i < mItemUnits.size(); i++) {
                            if (i != getAdapterPosition()) {
                                mItemUnits.get(i).setCheck(false);
                            }
                        }
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {    // long click vào cả itemView
                @Override
                public boolean onLongClick(View v) {
                    try {
                        showPopUpDeleteItemUnit(v, getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            lnEdit.setOnClickListener(new View.OnClickListener() {  // click vào cả nút sửa
                @Override
                public void onClick(View v) {
                    try {
                        showDialogEditItemUnit(getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * Gắn dữ liệu vào view.
         *
         * @param position: vị trí view
         * @created_by nadat on 12/04/2019
         */
        private void bindView(int position) {
            try {
                if (mItemUnits.get(position).isCheck()) {
                    imgChecked.setVisibility(View.VISIBLE);
                } else {
                    imgChecked.setVisibility(View.INVISIBLE);
                }
                tvUnitName.setText(mItemUnits.get(position).getItemUnitName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
