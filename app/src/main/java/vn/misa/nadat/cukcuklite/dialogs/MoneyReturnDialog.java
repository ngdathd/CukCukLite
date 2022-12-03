package vn.misa.nadat.cukcuklite.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

/**
 * Dialog chọn tiền trả lại
 *
 * @created_by nadat on 24/04/2019
 */
public class MoneyReturnDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mDialog;
    private CallBack mCallBack;

    private String result;

    private View view;
    private AppCompatImageButton ivDelete;
    private EditText etResult;
    private TextView tvClear, tvDot, tvZero3, tvZero, tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, tvEight, tvNine, tvFinish,
            tvSuggestOne, tvSuggestTwo, tvSuggestThree, tvSuggestFour;

    public MoneyReturnDialog(@NonNull Context context, String price, CallBack callBack) {
        super(context);
        try {
            mCallBack = callBack;

            if (TextUtils.isEmpty(price)) {
                result = "";
            } else {
                result = price;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị dialog
     *
     * @created_by nadat on 24/04/2019
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
     * Ánh xạ view
     *
     * @created_by nadat on 24/04/2019
     */
    @SuppressLint("InflateParams")
    private void initView() {
        try {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.dialog_money_return, null, false);

            ivDelete = view.findViewById(R.id.ivDelete);
            tvClear = view.findViewById(R.id.tvClear);
            tvDot = view.findViewById(R.id.tvDot);
            tvZero3 = view.findViewById(R.id.tvZero3);
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
            etResult = view.findViewById(R.id.etResult);
            tvSuggestOne = view.findViewById(R.id.tvSuggestOne);
            tvSuggestTwo = view.findViewById(R.id.tvSuggestTwo);
            tvSuggestThree = view.findViewById(R.id.tvSuggestThree);
            tvSuggestFour = view.findViewById(R.id.tvSuggestFour);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gán sự kiện item click cho view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initEvent() {
        try {
            ivDelete.setOnClickListener(this);
            tvClear.setOnClickListener(this);
            tvDot.setOnClickListener(this);
            tvZero3.setOnClickListener(this);
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
            tvSuggestOne.setOnClickListener(this);
            tvSuggestTwo.setOnClickListener(this);
            tvSuggestThree.setOnClickListener(this);
            tvSuggestFour.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo giá trị ban đầu
     *
     * @created_by nadat on 24/04/2019
     */
    private void init() {
        try {
            if (!TextUtils.isEmpty(result)) {
                showResult();

                int price = Integer.parseInt(result);
                int check = price / 1000;

                tvSuggestOne.setText(PriceUtils.formatPrice(String.valueOf(price)));
                tvSuggestTwo.setText(PriceUtils.formatPrice(String.valueOf(price + 1000)));

                if (check % 5 == 0) {
                    tvSuggestThree.setText(PriceUtils.formatPrice(String.valueOf(price + 5000)));
                    tvSuggestFour.setText(PriceUtils.formatPrice(String.valueOf(price + 10000)));
                } else {
                    price = price + 5000 - check % 5 * 1000;
                    check = price / 1000;

                    if (check % 5 == 0) {
                        tvSuggestThree.setText(PriceUtils.formatPrice(String.valueOf(price)));
                    } else {
                        tvSuggestThree.setText(PriceUtils.formatPrice(String.valueOf(price + 5000)));
                    }

                    check = price % 10000;
                    if (check == 0) {
                        tvSuggestFour.setText(PriceUtils.formatPrice(String.valueOf(price + 20000)));
                    } else {
                        tvSuggestFour.setText(PriceUtils.formatPrice(String.valueOf(price + 15000)));
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lý xử kiện khi view được click
     *
     * @param view view được click
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
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
                case R.id.tvZero3:
                    showResult("000");
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
                    mCallBack.onClick(PriceUtils.formatNumber(etResult.getText().toString()));
                    mDialog.dismiss();
                    break;
                case R.id.tvSuggestOne:
                    result = PriceUtils.formatNumber(tvSuggestOne.getText().toString());
                    showResult();
                    break;
                case R.id.tvSuggestTwo:
                    Log.i("onClick", "onClick: " + tvSuggestTwo.getText().toString());
                    result = PriceUtils.formatNumber(tvSuggestTwo.getText().toString());
                    showResult();
                    break;
                case R.id.tvSuggestThree:
                    result = PriceUtils.formatNumber(tvSuggestThree.getText().toString());
                    showResult();
                    break;
                case R.id.tvSuggestFour:
                    result = PriceUtils.formatNumber(tvSuggestFour.getText().toString());
                    showResult();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị số lượng
     *
     * @created_by nadat on 24/04/2019
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
     * @created_by nadat on 24/04/2019
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
        void onClick(String result);
    }
}
