package com.ngdat.cukcuklite.screen.dialogs.delete;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.utils.AppConstants;

/**
 * Dialog hiển thị xác nhận xóa
 * Created at 27/03/2019
 */
public class ConfirmDeleteDialog extends DialogFragment implements View.OnClickListener {
    private IConfirmDeleteCallBack mCallBack;
    private Button btnClose, btnYes, btnNo;

    /**
     * Phương thức khởi tạo dialog xác nhận xóa
     * Created at 10/04/2019
     *
     * @param name - tên muốn xóa - có thể null
     * @param type - loại muốn xóa
     * @return - dialog
     */
    public static ConfirmDeleteDialog newInstance(@Nullable String name, String type) {
        ConfirmDeleteDialog colorSelectorDialog = new ConfirmDeleteDialog();
        try {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ARG_NAME, name);
            bundle.putString(AppConstants.ARG_TYPE_DELETE, type);
            colorSelectorDialog.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colorSelectorDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 09/04/2019
     */
    private void initEvents() {
        try {
            btnClose.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            btnYes.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo các view
     * Created at 27/03/2019
     */
    private void initViews(View view) {
        try {
            TextView tvConfirm = view.findViewById(R.id.tvConfirm);
            btnClose = view.findViewById(R.id.btnClose);
            btnYes = view.findViewById(R.id.btnYes);
            btnNo = view.findViewById(R.id.btnNo);
            Bundle bundle = getArguments();
            String name = "";
            if (bundle != null) {
                name = bundle.getString(AppConstants.ARG_NAME);
                String type = bundle.getString(AppConstants.ARG_TYPE_DELETE);
                if (type != null) {
                    if (type.equals(IDBUtils.ITableUnitUtils.COLUMN_UNIT_NAME)) {
                        tvConfirm.setText(Html.fromHtml(getString(R.string.confirm_delete_unit, name)));
                    } else if (type.equals(IDBUtils.ITableDishUtils.COLUMN_DISH_NAME)) {
                        tvConfirm.setText(Html.fromHtml(getString(R.string.confirm_delete_dish, name)));
                    } else {
                        tvConfirm.setText(R.string.confirm_delete_order);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Chỉnh lại kích thước dialog khi hiển thị trên thiết bị
     * Created at 09/04/2019
     */
    @Override
    public void onResume() {
        super.onResume();
        try {
            Window window = getDialog().getWindow();
            int width = getResources().getDimensionPixelSize(R.dimen.color_dialog_width);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            assert window != null;
            window.setLayout(width, height);
            window.setGravity(Gravity.CENTER);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Ghi đè hàm khởi tạo dialog, thiết lập dialog
     * Created at 10/04/2019
     *
     * @param savedInstanceState - gói dữ liệu
     * @return - dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    // Disable Back key and Search key
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_SEARCH:
                            return true;
                        case KeyEvent.KEYCODE_BACK:
                            return true;
                        default:
                            return false;
                    }
                }
            });
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNo:
            case R.id.btnClose:
                dismiss();
                break;
            case R.id.btnYes:
                mCallBack.acceptDelete();
                dismiss();
                break;
        }
    }

    /**
     * Phương thức gán callback cho sự kiện xóa
     * Created at 10/04/2019
     *
     * @param callBack - callback
     */
    public void setCallBack(IConfirmDeleteCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * Call back được gọi để thực hiện tác vụ xóa
     * Created at 09/04/2019
     */
    public interface IConfirmDeleteCallBack {
        void acceptDelete();
    }
}