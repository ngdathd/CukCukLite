package vn.misa.nadat.cukcuklite.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import vn.misa.nadat.cukcuklite.R;

/**
 * Dialog thêm hoặc sửa đơn vị.
 *
 * @created_by nadat on 12/04/2019
 */
public class AddEditUnitDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private AlertDialog mAlertDialog;
    private OnClickAcceptDialogAddEditUnitListener mOnClickAcceptDialogAddEditUnitListener;
    private String mUnit = null;
    private EditText dialogNote;

    /**
     * Khởi tạo dialog với 2 tham số, khi thêm đơn vị.
     *
     * @param context:                                context mà dialog hiển thị
     * @param onClickAcceptDialogAddEditUnitListener: interface lắng nghe sự kiện chấp nhận
     * @created_by nadat on 12/04/2019
     */
    public AddEditUnitDialog(@NonNull Context context,
                             OnClickAcceptDialogAddEditUnitListener onClickAcceptDialogAddEditUnitListener) {
        super(context);
        if (onClickAcceptDialogAddEditUnitListener == null) {
            return;
        }
        try {
            mContext = context;
            mOnClickAcceptDialogAddEditUnitListener = onClickAcceptDialogAddEditUnitListener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo dialog với 3 tham số, khi sửa đơn vị.
     *
     * @param context:                                context mà dialog hiển thị
     * @param unit:                                   đơn vị cần được sửa
     * @param onClickAcceptDialogAddEditUnitListener: interface lắng nghe sự kiện chấp nhận
     * @created_by nadat on 12/04/2019
     */
    public AddEditUnitDialog(@NonNull Context context, @NonNull String unit,
                             OnClickAcceptDialogAddEditUnitListener onClickAcceptDialogAddEditUnitListener) {
        super(context);
        if (onClickAcceptDialogAddEditUnitListener == null) {
            return;
        }
        try {
            mContext = context;
            mUnit = unit;
            mOnClickAcceptDialogAddEditUnitListener = onClickAcceptDialogAddEditUnitListener;
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
            Builder builder = new Builder(mContext);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            @SuppressLint("InflateParams")
            View view = inflater.inflate(R.layout.dialog_add_edit_unit, null);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            dialogNote = view.findViewById(R.id.dialogNote);
            if (mUnit != null) {
                tvTitle.setText(mContext.getString(R.string.edit_unit_title));
                dialogNote.setText(mUnit);
            } else {
                tvTitle.setText(mContext.getString(R.string.add_unit_title));
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
                    String unit = dialogNote.getText().toString();
                    if (!unit.equals("")) {
                        mOnClickAcceptDialogAddEditUnitListener
                                .onClickAcceptDialogAddEditUnit(unit);
                        mAlertDialog.dismiss();
                    } else {
                        Toast toast = Toast.makeText(mContext, "Không được bỏ trống đơn vị", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
                        toast.show();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnClickAcceptDialogAddEditUnitListener {
        void onClickAcceptDialogAddEditUnit(String newNameUnit);
    }
}
