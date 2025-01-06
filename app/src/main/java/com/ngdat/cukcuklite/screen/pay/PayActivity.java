package com.ngdat.cukcuklite.screen.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;
import com.ngdat.cukcuklite.screen.dialogs.caculator.InputNumberDialog;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.Navigator;
import com.ngdat.cukcuklite.utils.StringUtils;

/**
 * Màn hình thanh toán hóa đơn
 * Created at 16/04/2019
 */
public class PayActivity extends AppCompatActivity implements View.OnClickListener, IPayContract.IView {
    private ImageButton btnBack;
    private Button btnDone;
    private TextView tvMoneyHaveToPay, tvBillNumber, tvDone, tvDateCreated, tvMoneyReturn, tvMoneyGuestPay;
    private RecyclerView rvBill;
    private PayPresenter mPresenter;
    private BillDetailAdapter mAdapter;
    private Navigator mNavigator;
    private Bill mBill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mNavigator = new Navigator(this);
        initViews();
        initEvents();
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 16/04/2019
     */
    private void initViews() {
        try {
            btnBack = (ImageButton) findViewById(R.id.btnBack);
            btnDone = (Button) findViewById(R.id.btnDone);
            tvMoneyHaveToPay = (TextView) findViewById(R.id.tvMoneyHaveToPay);
            tvBillNumber = (TextView) findViewById(R.id.tvBillNumber);
            tvDone = (TextView) findViewById(R.id.tvDone);
            tvDateCreated = (TextView) findViewById(R.id.tvDateCreated);
            tvMoneyReturn = (TextView) findViewById(R.id.tvMoneyReturn);
            tvMoneyGuestPay = (TextView) findViewById(R.id.tvMoneyGuestPay);
            rvBill = (RecyclerView) findViewById(R.id.rvBill);
            rvBill.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new BillDetailAdapter(this);
            rvBill.setAdapter(mAdapter);
            getBillId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy hóa đơn id để lấy và gán dữ liệu cho view
     * Created at 16/04/2019
     */
    private void getBillId() {
        Intent intent = getIntent();
        String billId = intent.getStringExtra(AppConstants.EXTRA_BILL_ID);
        if (billId != null) {
            mPresenter = new PayPresenter(billId);
            mPresenter.setView(this);
            mPresenter.onStart();
        }
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 16/04/2019
     */
    private void initEvents() {
        try {
            btnBack.setOnClickListener(this);
            btnDone.setOnClickListener(this);
            tvDone.setOnClickListener(this);
            tvMoneyReturn.setOnClickListener(this);
            tvMoneyGuestPay.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 16/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                try {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvMoneyGuestPay:
                try {
                    showDialogNumber(InputNumberDialog.FLAG_MONEY_GUEST_PAY,
                            NumberFormat.getNumberInstance(Locale.US).parse(tvMoneyGuestPay.getText().toString()).toString(), new InputNumberDialog.DialogCallBack() {
                                @Override
                                public void setAmount(String amount) {
                                    if (TextUtils.isEmpty(amount)) {
                                        amount = "0";
                                    }
                                    tvMoneyGuestPay.setText(NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(amount)));
                                    try {
                                        long moneyHavePay = (long) NumberFormat.getNumberInstance(Locale.US).parse(tvMoneyHaveToPay.getText().toString());
                                        long moneyGuestPay = (long) NumberFormat.getNumberInstance(Locale.US).parse(tvMoneyGuestPay.getText().toString());
                                        tvMoneyReturn.setText(NumberFormat.getNumberInstance(Locale.US).format(moneyGuestPay - moneyHavePay));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvDone:
            case R.id.btnDone:
                try {
                    payBill();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    /**
     * Phương thức thanh toán hóa đơn
     * Created at 16/04/2019
     */
    private void payBill() {
        try {
            long moneyHaveToPay = (long) NumberFormat.getNumberInstance(Locale.US).parse(tvMoneyHaveToPay.getText().toString());
            String moneyGuestPay = tvMoneyGuestPay.getText().toString();
            long moneyGuestPay2 = 0;
            if (!moneyGuestPay.equals("")) {
                moneyGuestPay2 = (long) NumberFormat.getNumberInstance(Locale.US).parse(moneyGuestPay);
            }
            if (moneyHaveToPay > moneyGuestPay2) {
                mNavigator.showToastOnTopScreen(R.string.the_amount_of_guest_money_must_not_be_less_than_the_amount_payable);
            } else {
                mBill.setCustomerPay((int) moneyGuestPay2);
                mPresenter.pay(mBill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý khi thanh toán thành công
     * Created at 17/04/2019
     */
    @Override
    public void paySuccess() {
        try {
            //Đóng màn hình thanh toán và trở về màn hình trước
            mNavigator.showToastOnTopScreen(R.string.pay_success);
            setResult(Activity.RESULT_OK);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị, gán danh sách hóa đơn chi tiết vào hóa đơn
     * Created at 16/04/2019
     *
     * @param bill           - hóa đơn
     * @param billDetailList - danh sách hóa đơn chi tiết
     * @param billNumber     - số hóa đơn
     */
    @Override
    public void setBill(Bill bill, List<BillDetail> billDetailList, int billNumber) {
        try {
            if (bill != null && billDetailList != null) {
                mBill = bill;
                mBill.setBillNumber(billNumber + 1);
                mAdapter.setListData(billDetailList);
                String money = NumberFormat.getNumberInstance(Locale.US).format(bill.getTotalMoney());
                tvMoneyHaveToPay.setText(money);
                tvMoneyGuestPay.setText(money);
                DecimalFormat decimalFormat = new DecimalFormat("00000");
                tvBillNumber.setText(decimalFormat.format(billNumber + 1));
                long dateCreated = Calendar.getInstance().getTimeInMillis();
                mBill.setDateCreated(dateCreated);
                tvDateCreated.setText(StringUtils.getDate(dateCreated));
            } else {
                mNavigator.showToastOnTopScreen(R.string.something_went_wrong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created at 16/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        try {
            mNavigator.showToastOnTopScreen(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 16/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created at 16/04/2019
     */
    @Override
    public void hideLoading() {

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    /**
     * HIển thị dialog nhập số
     * Created at 19/04/2019
     *
     * @param flag           - cờ cho title dialog
     * @param input          - text từ edittext bàn phím
     * @param dialogCallBack - callback cho dialog
     */
    private void showDialogNumber(int flag, CharSequence input,
                                  InputNumberDialog.DialogCallBack dialogCallBack) {
        try {
            InputNumberDialog inputNumberDialog = new InputNumberDialog(flag, dialogCallBack,
                    input);
            FragmentManager fm = getSupportFragmentManager();
            inputNumberDialog.show(fm, InputNumberDialog.NUMBER_INPUT_DIALOG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
