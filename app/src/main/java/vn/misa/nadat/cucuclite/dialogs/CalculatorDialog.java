package vn.misa.nadat.cucuclite.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import vn.misa.nadat.cucuclite.R;

public class CalculatorDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mAlertDialog;
    private IDialogCalculator mIDialogCalculator;
    private EditText dialog_key_txtMoney;

    public CalculatorDialog(@NonNull Context context, IDialogCalculator mIDialogCalculator) {
        super(context);
        this.mIDialogCalculator = mIDialogCalculator;
    }

    @Override
    public void show() {
        try {
            Context context = getContext();
            Builder builder = new Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_payment_caculator, null);
            ImageButton btn_title_close = view.findViewById(R.id.btn_title_close);
            btn_title_close.setOnClickListener(this);
            TextView dialog_key_btnAccept = view.findViewById(R.id.dialog_key_btnAccept);
            dialog_key_btnAccept.setOnClickListener(this);
            dialog_key_txtMoney = view.findViewById(R.id.dialog_key_txtMoney);
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
                case R.id.btn_title_close: {
                    mAlertDialog.dismiss();
                    break;
                }
                case R.id.dialog_key_btnAccept: {
                    mIDialogCalculator.priceDish(dialog_key_txtMoney.getText().toString().trim());
                    mAlertDialog.dismiss();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IDialogCalculator {
        void priceDish(String price);
    }
}
