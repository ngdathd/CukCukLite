package vn.misa.nadat.cucuclite.ui.unit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import vn.misa.nadat.cucuclite.R;
import vn.misa.nadat.cucuclite.items.ItemUnit;

public class AddUnitDialog extends AlertDialog implements View.OnClickListener, IUnitContract.IView {
    private AlertDialog mAlertDialog;
    private IUnitContract.IPresenter mIPresenter;
    private IAddUnit mIAddUnit;
    private EditText dialog_note_txtNote;

    public AddUnitDialog(@NonNull Context context, IAddUnit mIAddUnit) {
        super(context);
        mIPresenter = new UnitPresenter(this);
        this.mIAddUnit = mIAddUnit;
    }

    @Override
    public void show() {
        try {
            Context context = getContext();
            Builder builder = new Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_unit, null);
            TextView tv_title = view.findViewById(R.id.tv_title);
            tv_title.setText("Thêm đơn vị tính");
            ImageButton btn_title_close = view.findViewById(R.id.btn_title_close);
            btn_title_close.setOnClickListener(this);
            Button btnCancelDialog = view.findViewById(R.id.btnCancelDialog);
            btnCancelDialog.setOnClickListener(this);
            Button btnAcceptDialog = view.findViewById(R.id.btnAcceptDialog);
            btnAcceptDialog.setOnClickListener(this);
            dialog_note_txtNote = view.findViewById(R.id.dialog_note_txtNote);
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
                    mIPresenter.addUnitToDatabase(dialog_note_txtNote.getText().toString().trim());
                    mAlertDialog.dismiss();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showSuccess(ItemUnit unit) {
        mIAddUnit.onAddSucess(unit);
    }

    @Override
    public void showError() {
        mIAddUnit.onAddError();
    }

    public interface IAddUnit {
        void onAddSucess(ItemUnit unit);

        void onAddError();
    }
}
