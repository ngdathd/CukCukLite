package vn.misa.nadat.cukcuklite.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Dialog xóa đơn vị.
 *
 * @created_by nadat on 12/04/2019
 */
public class DeleteItemDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mAlertDialog;
    private OnClickAcceptDialogDeleteItemListener mOnClickAcceptDialogDeleteItemListener;
    private int mType;
    private String mItem;

    /**
     * Khởi tạo dialog.
     *
     * @param context:                               context mà dialog hiển thị
     * @param type:                                  loại item được xóa
     * @param item:                                  item được xóa
     * @param onClickAcceptDialogDeleteItemListener: interface lắng nghe sự kiện chấp nhận
     * @created_by nadat on 12/04/2019
     */
    public DeleteItemDialog(@NonNull Context context, int type, @NonNull String item,
                            OnClickAcceptDialogDeleteItemListener onClickAcceptDialogDeleteItemListener) {
        super(context);
        if (onClickAcceptDialogDeleteItemListener == null) {
            return;
        }
        try {
            mType = type;
            mItem = item;
            mOnClickAcceptDialogDeleteItemListener = onClickAcceptDialogDeleteItemListener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo dialog.
     *
     * @param context:                               context mà dialog hiển thị
     * @param type:                                  loại item được xóa
     * @param onClickAcceptDialogDeleteItemListener: interface lắng nghe sự kiện chấp nhận
     * @created_by nadat on 12/04/2019
     */
    public DeleteItemDialog(@NonNull Context context, int type,
                            OnClickAcceptDialogDeleteItemListener onClickAcceptDialogDeleteItemListener) {
        super(context);
        if (onClickAcceptDialogDeleteItemListener == null) {
            return;
        }
        try {
            mType = type;
            mOnClickAcceptDialogDeleteItemListener = onClickAcceptDialogDeleteItemListener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị dialog.
     *
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void show() {
        try {
            Context context = getContext();
            Builder builder = new Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            @SuppressLint("InflateParams")
            View view = inflater.inflate(R.layout.dialog_delete_item, null);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView dialogNote = view.findViewById(R.id.dialogNote);
            if (mType == Constant.TYPE_UNIT) {
                tvTitle.setText(context.getString(R.string.delete_unit));
                dialogNote.setText(context.getString(R.string.msg_confirm_delete_unit, mItem));
            } else if (mType == Constant.TYPE_DISH) {
                tvTitle.setText(context.getString(R.string.delete_dish));
                dialogNote.setText(context.getString(R.string.msg_confirm_delete_dish, mItem));
            } else if (mType == Constant.TYPE_SALE) {
                tvTitle.setText(context.getString(R.string.delete_sale));
                dialogNote.setText(context.getString(R.string.msg_confirm_delete_sale));
            } else {
                tvTitle.setText(context.getString(R.string.pay_sale));
                dialogNote.setText(context.getString(R.string.msg_confirm_pay_sale));
            }
            ImageButton btnTitleClose = view.findViewById(R.id.btnTitleClose);
            btnTitleClose.setOnClickListener(this);
            Button btnCancelDialog = view.findViewById(R.id.btnCancelDialog);
            btnCancelDialog.setOnClickListener(this);
            Button btnAcceptDialog = view.findViewById(R.id.btnAcceptDialog);
            btnAcceptDialog.setOnClickListener(this);
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.setCancelable(false);
            mAlertDialog.setCanceledOnTouchOutside(false);
            mAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bắt sự kiện click vào view trong dialog.
     *
     * @param v: view được click
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnTitleClose:
                case R.id.btnCancelDialog: {
                    mAlertDialog.dismiss();
                    break;
                }
                case R.id.btnAcceptDialog: {
                    mOnClickAcceptDialogDeleteItemListener.onClickAcceptDialogDeleteItem();
                    mAlertDialog.dismiss();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnClickAcceptDialogDeleteItemListener {
        void onClickAcceptDialogDeleteItem();
    }
}
