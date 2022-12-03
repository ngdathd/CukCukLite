package vn.misa.nadat.cukcuklite.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Màn hình máy tính nhập số lượng
 *
 * @created_by nadat on 18/04/2019
 */
public class NumberKeyboardDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mDialog;
    private CallBack mCallBack;
    private String type;

    private View view;
    private TextView tvTitle, tvReduce, tvIncrease, tvClear, tvZero,
            tvOne, tvTwo, tvThree, tvFour, tvFive,
            tvSix, tvSeven, tvEight, tvNine, tvFinish;
    private ImageView ivDelete;
    private EditText etResult;
    private AppCompatImageButton btnTitleClose;

    private String result;

    /**
     * Contructor của lớp NumberKeyboardDialog
     *
     * @param context  context của lớp gọi đến CalculatorDialog
     * @param type     kiểu hiển thị của CalculatorDialog
     * @param number   số lượng hiện tại
     * @param callBack interface trong lớp CalculatorDialog
     * @created_by nadat on 18/04/2019
     */
    public NumberKeyboardDialog(@NonNull Context context, String type, String number, CallBack callBack) {
        super(context);
        try {
            this.type = type;

            if (TextUtils.isEmpty(number)) {
                result = "";
            } else {
                result = number;
            }

            mCallBack = callBack;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị dialog
     *
     * @created_by nadat on 18/04/2019
     */
    @Override
    public void show() {
        try {
            initView();
            initEvent();
            init();

            Builder builder = new Builder(getContext());
            builder.setView(view);
            mDialog = builder.create();
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo giá trị ban đầu
     *
     * @created_by nadat on 18/04/2019
     */
    private void init() {
        try {
            if (type.equals(getContext().getString(R.string.type_table))) {
                tvTitle.setText(getContext().getString(R.string.title_table));
            } else if (type.equals(getContext().getString(R.string.type_people))) {
                tvTitle.setText(getContext().getString(R.string.title_people));
            }

            if (!TextUtils.isEmpty(result)) {
                showResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gán sự kiện item click cho view
     *
     * @created_by nadat on 18/04/2019
     */
    private void initEvent() {
        try {
            tvReduce.setOnClickListener(this);
            tvIncrease.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
            tvClear.setOnClickListener(this);
            tvZero.setOnClickListener(this);
            tvOne.setOnClickListener(this);
            tvTwo.setOnClickListener(this);
            tvThree.setOnClickListener(this);
            tvFour.setOnClickListener(this);
            tvFive.setOnClickListener(this);
            tvSix.setOnClickListener(this);
            tvSeven.setOnClickListener(this);
            tvEight.setOnClickListener(this);
            tvNine.setOnClickListener(this);
            tvFinish.setOnClickListener(this);
            btnTitleClose.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ánh xạ view
     *
     * @created_by nadat on 18/04/2019
     */
    @SuppressLint("InflateParams")
    private void initView() {
        try {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.dialog_keyboard_number, null, false);

            tvTitle = view.findViewById(R.id.tvTitle);
            tvReduce = view.findViewById(R.id.tvReduce);
            tvIncrease = view.findViewById(R.id.tvIncrease);
            ivDelete = view.findViewById(R.id.ivDelete);
            tvClear = view.findViewById(R.id.tvClear);
            tvZero = view.findViewById(R.id.tvZero);
            tvOne = view.findViewById(R.id.tvOne);
            tvTwo = view.findViewById(R.id.tvTwo);
            tvThree = view.findViewById(R.id.tvThree);
            tvFour = view.findViewById(R.id.tvFour);
            tvFive = view.findViewById(R.id.tvFive);
            tvSix = view.findViewById(R.id.tvSix);
            tvSeven = view.findViewById(R.id.tvSeven);
            tvEight = view.findViewById(R.id.tvEight);
            tvNine = view.findViewById(R.id.tvNine);
            tvFinish = view.findViewById(R.id.tvFinish);
            btnTitleClose = view.findViewById(R.id.btnTitleClose);
            etResult = view.findViewById(R.id.etResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lý sự kiện khi view được click
     *
     * @param v view được click
     * @created_by nadat on 18/04/2019
     */
    @Override
    public void onClick(View v) {
        try {
            int number;

            switch (v.getId()) {
                case R.id.tvReduce:
                    if (TextUtils.isEmpty(result)) {
                        result = "0";
                    } else {
                        number = Integer.parseInt(result);
                        if (number > 0) {
                            number--;
                            result = number + "";
                        }
                    }
                    showResult();
                    break;
                case R.id.tvIncrease:
                    if (TextUtils.isEmpty(result)) {
                        result = "1";
                    } else {
                        number = Integer.parseInt(result);
                        number++;
                        result = number + "";
                    }
                    showResult();
                    break;
                case R.id.ivDelete:
                    if (!TextUtils.isEmpty(result)) {
                        result = result.substring(0, result.length() - 1);
                        showResult();
                    }
                    break;
                case R.id.tvClear:
                    result = "";
                    showResult();
                    break;
                case R.id.tvZero:
                    showResult("0");
                    break;
                case R.id.tvOne:
                    showResult("1");
                    break;
                case R.id.tvTwo:
                    showResult("2");
                    break;
                case R.id.tvThree:
                    showResult("3");
                    break;
                case R.id.tvFour:
                    showResult("4");
                    break;
                case R.id.tvFive:
                    showResult("5");
                    break;
                case R.id.tvSix:
                    showResult("6");
                    break;
                case R.id.tvSeven:
                    showResult("7");
                    break;
                case R.id.tvEight:
                    showResult("8");
                    break;
                case R.id.tvNine:
                    showResult("9");
                    break;
                case R.id.tvFinish:
                    mCallBack.onAmount(PriceUtils.formatNumber(etResult.getText().toString()));
                    mDialog.dismiss();
                    break;
                case R.id.btnTitleClose:
                    mDialog.dismiss();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị số lượng
     *
     * @created_by nadat on 18/04/2019
     */
    private void showResult() {
        try {
            etResult.setText(PriceUtils.formatPrice(result));
            etResult.setSelection(etResult.getText().length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị số lượng
     *
     * @param s thông tin số lượng
     * @created_by nadat on 18/04/2019
     */
    private void showResult(String s) {
        try {
            if (!TextUtils.isEmpty(s)) {
                result = result.concat(s);
                etResult.setText(PriceUtils.formatPrice(result));
                etResult.setSelection(etResult.getText().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallBack {
        void onAmount(String amount);
    }
}
