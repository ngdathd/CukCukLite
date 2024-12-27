package vn.misa.nadat.cucuclite.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import vn.misa.nadat.cucuclite.R;

public class EditUnitDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mAlertDialog;

    public EditUnitDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void show() {
        try {
            Context context = getContext();
            Builder builder = new Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_unit, null);
            TextView tv_title = view.findViewById(R.id.tv_title);
            tv_title.setText("Sửa đơn vị tính");
            ImageButton btn_title_close = view.findViewById(R.id.btn_title_close);
            btn_title_close.setOnClickListener(this);
            Button btnCancelDialog = view.findViewById(R.id.btnCancelDialog);
            btnCancelDialog.setOnClickListener(this);
            Button btnAcceptDialog = view.findViewById(R.id.btnAcceptDialog);
            btnAcceptDialog.setOnClickListener(this);
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_title_close:
                case R.id.btnCancelDialog: {
                    mAlertDialog.dismiss();
                    break;
                }
                case R.id.btnAcceptDialog: {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
