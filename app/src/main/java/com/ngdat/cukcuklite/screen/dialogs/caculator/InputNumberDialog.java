package com.ngdat.cukcuklite.screen.dialogs.caculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ngdat.cukcuklite.R;

/**
 * Dialog nhập số cho các trường nhập liệu
 * Created at 19/04/2019
 */
public class InputNumberDialog extends DialogFragment implements View.OnClickListener {

    public static final int FLAG_TABLE = 0;
    public static final int FLAG_PERSON = 1;
    public static final int FLAG_QUANTITY = 2;
    public static final int FLAG_PRICE = 3;
    public static final String NUMBER_INPUT_DIALOG = "NUMBER_INPUT_DIALOG";
    public static final int FLAG_MONEY_GUEST_PAY = 4;
    private TextView tvTitle;
    private EditText etInputNumber;
    private String textInput;
    private int flag;
    private DialogCallBack mCallBack;

    /**
     * Mục đích method: Hàm khởi tạo Dialog bàn phím
     * Created at 19/04/2019
     *
     * @param flag:     Cờ phân loại nhập số người hay số bàn
     * @param callBack: Call back
     * @param input:    giá trị đầu
     */
    @SuppressLint("ValidFragment")
    public InputNumberDialog(int flag, DialogCallBack callBack, CharSequence input) {
        this.flag = flag;
        mCallBack = callBack;
        if (TextUtils.isEmpty(input)) {
            textInput = "";
        } else {
            textInput = String.valueOf(input);
        }
    }

    public InputNumberDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_keyboard_number_table_person, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initViews(rootView);
        initEvents(rootView);
        return rootView;
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 19/04/2019
     */
    private void initViews(View rootView) {
        try {
            etInputNumber = rootView.findViewById(R.id.edtScreen);
            tvTitle = rootView.findViewById(R.id.tvTitle);
            switch (flag) {
                case FLAG_TABLE:
                    tvTitle.setText(getString(R.string.title_table));
                    break;
                case FLAG_PERSON:
                    tvTitle.setText(getString(R.string.title_person));
                    break;
                case FLAG_QUANTITY:
                    tvTitle.setText(getString(R.string.title_quantiy));
                    break;
                case FLAG_PRICE:
                    tvTitle.setText(getString(R.string.price));
                    break;
                case FLAG_MONEY_GUEST_PAY:
                    tvTitle.setText(getString(R.string.guest_pay));
                    break;
            }
            showResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 19/04/2019
     */
    private void initEvents(View rootView) {
        rootView.findViewById(R.id.btnClose).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey0).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey1).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey2).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey3).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey4).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey5).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey6).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey7).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey8).setOnClickListener(this);
        rootView.findViewById(R.id.btnKey9).setOnClickListener(this);
        rootView.findViewById(R.id.btnKeyBack).setOnClickListener(this);
        rootView.findViewById(R.id.btnKeyAccept).setOnClickListener(this);
        rootView.findViewById(R.id.btnKeyMinus).setOnClickListener(this);
        rootView.findViewById(R.id.btnKeyPlus).setOnClickListener(this);
        rootView.findViewById(R.id.btnKeyClear).setOnClickListener(this);
    }

    /**
     * Mục đích method: Set kích cỡ cho dialog
     * Created at 19/04/2019
     */
    @Override
    public void onResume() {
        super.onResume();
        try {
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 19/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        try {
            int number;
            switch (v.getId()) {
                case R.id.btnKey0:
                    showResult(getString(R.string._0));
                    break;
                case R.id.btnKey1:
                    showResult(getString(R.string._1));
                    break;
                case R.id.btnKey2:
                    showResult(getString(R.string._2));
                    break;
                case R.id.btnKey3:
                    showResult(getString(R.string._3));
                    break;
                case R.id.btnKey4:
                    showResult(getString(R.string._4));
                    break;
                case R.id.btnKey5:
                    showResult(getString(R.string._5));
                    break;
                case R.id.btnKey6:
                    showResult(getString(R.string._6));
                    break;
                case R.id.btnKey7:
                    showResult(getString(R.string._7));
                    break;
                case R.id.btnKey8:
                    showResult(getString(R.string._8));
                    break;
                case R.id.btnKey9:
                    showResult(getString(R.string._9));
                    break;
                case R.id.btnKeyBack:
                    try {
                        if (textInput.length() > 0) {
                            textInput = textInput.substring(0, textInput.length() - 1);
                            showResult();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btnKeyAccept:
                    mCallBack.setAmount(textInput);
                    dismiss();
                    break;
                case R.id.btnKeyMinus:
                    if (TextUtils.isEmpty(textInput)) {
                        textInput = "0";
                    } else {
                        number = Integer.parseInt(textInput);
                        if (number > 0) {
                            number--;
                            textInput = number + "";
                        }
                    }
                    showResult();
                    break;
                case R.id.btnKeyPlus:
                    try {
                        if (TextUtils.isEmpty(textInput)) {
                            textInput = "1";
                        } else {
                            number = Integer.parseInt(textInput);
                            number++;
                            textInput = number + "";
                        }
                        showResult();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btnKeyClear:
                    textInput = "";
                    showResult();
                    break;
                case R.id.btnClose:
                    dismiss();
                    break;
            }
            etInputNumber.setText(textInput);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị số lượng
     * Created at 19/04/2019
     */
    private void showResult() {
        try {
            etInputNumber.setText(textInput);
            etInputNumber.setSelection(textInput.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị số lượng
     * Created at 19/04/2019
     *
     * @param s thông tin số lượng
     */
    private void showResult(String s) {
        try {
            if (!TextUtils.isEmpty(s)) {
                textInput = textInput.concat(s);
                etInputNumber.setText(textInput);
                etInputNumber.setSelection(textInput.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Interface Call back
     * Created at 19/04/2019
     */
    public interface DialogCallBack {

        void setAmount(String amount);
    }
}
