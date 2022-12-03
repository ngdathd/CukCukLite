package vn.misa.nadat.cukcuklite.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.PaymentKeyboardAdapter;
import vn.misa.nadat.cukcuklite.items.InputKeys;
import vn.misa.nadat.cukcuklite.items.Operators;
import vn.misa.nadat.cukcuklite.utils.Utils;

/**
 * Dialog chọn giá tiền.
 *
 * @created_by nadat on 15/04/2019
 */
public class PaymentKeyboardDialog extends DialogFragment {
    private OnClickDoneListener mOnClickDoneListener;
    private PaymentKeyboardAdapter mPaymentKeyboardAdapter;
    private EditText etInputNumber;
    private List<InputKeys> mKeys;
    private List<Operators> mOperators;
    private String mTextInput = "";

    /**
     * Khởi tạo dialog.
     *
     * @param textInput:           giá đã có.
     * @param onClickDoneListener: sư kiện lắng nghe hoàn thành.
     * @return dialog được tạo
     * @created_by nadat on 15/04/2019
     */
    public static PaymentKeyboardDialog createInstance(String textInput, OnClickDoneListener onClickDoneListener) {
        PaymentKeyboardDialog paymentKeyboardDialog = new PaymentKeyboardDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("numberColumn", 4);
        bundle.putString("mTextInput", textInput);
        paymentKeyboardDialog.setArguments(bundle);
        paymentKeyboardDialog.setOnClickDone(onClickDoneListener);
        return paymentKeyboardDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            if (dialog.getWindow() != null) {
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
            return super.onCreateDialog(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        } catch (Exception e) {
            super.onStart();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_keyboard, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            createItems();
            mPaymentKeyboardAdapter = new PaymentKeyboardAdapter(mKeys);
            setOnClickItem();

            RecyclerView rcvKeyboard = view.findViewById(R.id.rcvKeyboard);
            etInputNumber = view.findViewById(R.id.etInputNumber);
            etInputNumber.setClickable(false);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText(getString(R.string.price_title));
            AppCompatImageButton btnTitleClose = view.findViewById(R.id.btnTitleClose);

            Bundle bundle = getArguments();

            int numberColumn = 4;
            if (bundle != null) {
                if (bundle.containsKey("numberColumn")) {
                    numberColumn = bundle.getInt("numberColumn");
                }
                if (bundle.containsKey("mTextInput")) {
                    mTextInput = bundle.getString("mTextInput");
                    if (mTextInput != null && mTextInput.isEmpty()) {
                        etInputNumber.setSelection(etInputNumber.getText().length());
                        etInputNumber.selectAll();
                        return;
                    }
                    etInputNumber.setText(mTextInput);
                    etInputNumber.setSelection(etInputNumber.getText().length());
                    etInputNumber.selectAll();
                }
            }

            GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), numberColumn);
            rcvKeyboard.setLayoutManager(gridLayoutManager);
            rcvKeyboard.setAdapter(mPaymentKeyboardAdapter);

            mOperators = new ArrayList<>();

            btnTitleClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            etInputNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        hideKeyboard();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức ẩn bàn phím
     *
     * @created_by nadat on 15/04/2019
     */
    private void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getActivity().getCurrentFocus();
            if (view == null) {
                view = new View(getActivity());
            }
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Khởi tạo giá trị cho danh sách bàn phím
     *
     * @created_by nadat on 15/04/2019
     */
    private void createItems() {
        if (getContext() == null) {
            return;
        }
        try {
            mKeys = new ArrayList<>();
            String jsonString = Utils.loadJSONFromAsset(getContext(), "json/inputkeys.json");

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray datas = jsonObject.getJSONArray("data");

                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject o = (JSONObject) datas.get(i);
                        mKeys.add(new InputKeys(o.getInt("id"), o.getString("name")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    mKeys.add(new InputKeys((i + 1), String.valueOf(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức gán sự kiện nhập phím trên màn hình.
     *
     * @created_by nadat on 15/04/2019
     */
    private void setOnClickItem() {
        mPaymentKeyboardAdapter.setOnClickListener(new PaymentKeyboardAdapter.OnClickInputKeyListener() {
            @Override
            public void onClickItem(int id) {
                onChangeText(id);
            }
        });
    }

    /**
     * Phương thức xử lí sự kiện nhập giá.
     *
     * @param id: mã bàn phím đã nhập
     * @created_by nadat on 15/04/2019
     */
    private void onChangeText(int id) {
        try {
            String text = mTextInput;
            String textString = etInputNumber.getText().toString();

            switch (id) {
                case 1: // Clear
                    etInputNumber.setText("0");
                    mTextInput = "";
                    break;
                case 2: // Giảm
                    if (getNumberInput(text) < 1) {
                        return;
                    }
                    addValue(String.valueOf(getNumberInput(text) - 1));
                    break;
                case 3: // Tăng
                    addValue(String.valueOf(getNumberInput(text) + 1));
                    break;
                case 4: // Back space
                    if (textString.length() == 1) {
                        etInputNumber.setText("0");
                        mTextInput = "";
                    } else {
                        textString = textString.substring(0, textString.length() - 1);
                        etInputNumber.setText(textString);
                        etInputNumber.setSelection(etInputNumber.getText().length());
                    }
                    break;
                case 5: // 7
                    text = text + "7";
                    addValue(text);
                    break;
                case 6: // 8
                    text = text + "8";
                    addValue(text);
                    break;
                case 7: // 9
                    text = text + "9";
                    addValue(text);
                    break;
                case 8: // -
                    if (textString.charAt(textString.length() - 1) == '-') { // Đằng trước là 1 dấu -
                        return;
                    } else if (textString.charAt(textString.length() - 1) == '+') { // Đằng trước là 1 dấu +
                        text = textString.substring(0, textString.length() - 1);
                        text = text + "-";
                        etInputNumber.setText(text);
                        etInputNumber.setSelection(etInputNumber.getText().length());
                        return;
                    } else {
                        mOperators.add(new Operators(1, getNumberInput(text)));
                        mTextInput = "";
                        text = textString + "-";
                        etInputNumber.setText(text);
                        etInputNumber.setSelection(etInputNumber.getText().length());

                    }
                    break;
                case 9: // 4
                    text = text + "4";
                    addValue(text);
                    break;
                case 10: // 5
                    text = text + "5";
                    addValue(text);
                    break;
                case 11: // 6
                    text = text + "6";
                    addValue(text);
                    break;
                case 12: // +
                    if (textString.charAt(textString.length() - 1) == '+') { // Đằng trước là 1 dấu +
                        return;
                    } else if (textString.charAt(textString.length() - 1) == '-') { // Đằng trước là 1 dấu -
                        text = textString.substring(0, textString.length() - 1);
                        text = text + "+";
                        etInputNumber.setText(text);
                        etInputNumber.setSelection(etInputNumber.getText().length());
                        return;
                    } else {
                        mOperators.add(new Operators(2, getNumberInput(text)));
                        mTextInput = "";
                        text = textString + "+";
                        etInputNumber.setText(text);
                        etInputNumber.setSelection(etInputNumber.getText().length());
                    }
                    break;
                case 13: // 1
                    text = text + "1";
                    addValue(text);
                    break;
                case 14: // 2
                    text = text + "2";
                    addValue(text);
                    break;
                case 15: // 3
                    text = text + "3";
                    addValue(text);
                    break;
                case 16: // +/-
                    if (text.indexOf("-") == 0) {
                        text = text.substring(1);
                        etInputNumber.setText(text);
                    } else {
                        text = "-" + text;
                        etInputNumber.setText(text);
                    }
                    break;
                case 17: // 0
                    text = text + "0";
                    addValue(text);
                    break;
                case 18: // 000
                    text = text + "000";
                    addValue(text);
                    break;
                case 19: // .
//                    if (text.indexOf(".") > 0) { // Đã tồn tại dấu chấm
//                        return;
//                    }
//                    text = text + ".";
//                    etInputNumber.setText(text);
                    break;
                case 20: // Xong
//                    mOperators.add(new Operators(3, getNumberInput(text)));
                    onCalculate();
                    break;
                default:
                    break;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tính giá trị phép tính và kết thúc nhập
     *
     * @created_by nadat on 15/04/2019
     */
    private void onCalculate() {
        if (mOperators.size() > 0) {
            String txt = etInputNumber.getText().toString();
            txt = txt.replaceAll("\\.", "");
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                // Calculate the result and display
                double result = expression.evaluate();

                mTextInput = formatAmount((long) result);
                etInputNumber.setText(mTextInput);
                mOperators.clear();

                mPaymentKeyboardAdapter.onChangeLabel();
            } catch (ArithmeticException ex) {
                ex.fillInStackTrace();
            }
        } else {
            long price = getNumberInput(etInputNumber.getText().toString());

            if (price < 0) {
                Toast.makeText(getContext(), "Giá tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            } else {
                mOnClickDoneListener.onClickDone(etInputNumber.getText().toString());
                dismiss();
            }
        }
    }

    /**
     * Phương thức thêm giá trị vào input
     *
     * @param text: Chuỗi đang nhập
     * @created_by nadat on 15/04/2019
     */
    private void addValue(String text) {
        try {
            mTextInput = formatAmount(getNumberInput(text));

            if (mTextInput.length() > 19) {
                return;
            }

            String values = getStringBefore() + mTextInput;
            etInputNumber.setText(values);
            etInputNumber.setSelection(etInputNumber.getText().length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giá trị số từ chuỗi đã nhập
     *
     * @param text: Chuỗi đã nhập
     * @return Giá trị số
     * @created_by nadat on 15/04/2019
     */
    private long getNumberInput(String text) {
        try {
            if (text.isEmpty()) {
                return 0;
            }
            text = text.replaceAll("\\.", "");
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Định dạng số thành 1 chuỗi có dấu phẩy ngăn cách
     *
     * @param num: giá trị số muốn đổi
     * @return Chuỗi string đã xử lý
     * @created_by nadat on 15/04/2019
     */
    private String formatAmount(long num) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols decimalFormateSymbol = new DecimalFormatSymbols();
        decimalFormateSymbol.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormateSymbol);
        return decimalFormat.format(num);
    }

    /**
     * Chuỗi string đã lưu vào mảng trước toán tử gần nhất.
     *
     * @return Chuỗi đã xử lý
     * @created_by nadat on 15/04/2019
     */
    public String getStringBefore() {
        if (mOperators == null || mOperators.size() == 0) {
            return "";
        } else {
            StringBuilder text = new StringBuilder();
            for (Operators o : mOperators) {
                text.append(formatAmount(o.getValue())).append(o.getId() == 1 ? "-" : "+");
            }
            return text.toString();
        }
    }

    /**
     * Phương thức thiết lập sự kiện callbackListNumberDishesInSale khi người dùng chọn button "Xong".
     *
     * @param onClickDoneListener Sự kiện callbackListNumberDishesInSale
     * @created_by nadat on 15/04/2019
     */
    public void setOnClickDone(OnClickDoneListener onClickDoneListener) {
        mOnClickDoneListener = onClickDoneListener;
    }

    /**
     * Interface cho sự kiện callbackListNumberDishesInSale
     */
    public interface OnClickDoneListener {
        void onClickDone(String price);
    }
}
