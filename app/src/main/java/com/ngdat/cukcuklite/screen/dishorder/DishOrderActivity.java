package com.ngdat.cukcuklite.screen.dishorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.listeners.IOnItemClickListener;
import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;
import com.ngdat.cukcuklite.data.local.prefs.SharedPrefersManager;
import com.ngdat.cukcuklite.screen.authentication.login.LoginActivity;
import com.ngdat.cukcuklite.screen.dialogs.caculator.InputNumberDialog;
import com.ngdat.cukcuklite.screen.main.MainActivity;
import com.ngdat.cukcuklite.screen.pay.PayActivity;
import com.ngdat.cukcuklite.screen.sale.SaleFragment;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình thêm món ăn cho hóa đơn
 * Created at 12/04/2019
 */
public class DishOrderActivity extends AppCompatActivity implements DishOrderContract.IView, IOnItemClickListener<Integer>, View.OnClickListener {

    private DishOrderPresenter mPresenter;
    private ProgressDialog mDialog;
    private Navigator mNavigator;
    private DishOrderAdapter mAdapter;
    private TextView tvTotalMoney, tvPay, tvTable, tvPerson;
    private ImageButton btnBack;
    private ConstraintLayout clWaterMark;
    private Button btnSave, btnPay;
    private Bill mBill;
    private boolean mIsEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_order);
        mNavigator = new Navigator(this);
        initViews();
        initEvents();
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 12/04/2019
     */
    private void initEvents() {
        tvPay.setOnClickListener(this);
        tvTable.setOnClickListener(this);
        tvPerson.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 12/04/2019
     */
    private void initViews() {
        try {
            tvTotalMoney = (TextView) findViewById(R.id.tvTotalMoney);
            tvPay = (TextView) findViewById(R.id.tvPay);
            tvTable = (TextView) findViewById(R.id.tvTable);
            tvPerson = (TextView) findViewById(R.id.tvPerson);
            btnBack = (ImageButton) findViewById(R.id.btnBack);
            clWaterMark = (ConstraintLayout) findViewById(R.id.clWaterMark);
            btnSave = (Button) findViewById(R.id.btnSave);
            btnPay = (Button) findViewById(R.id.btnPay);
            //khởi tạo recycler view
            RecyclerView rvDishOrder = (RecyclerView) findViewById(R.id.rvDishOrder);
            rvDishOrder.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new DishOrderAdapter(this);
            mAdapter.setOnItemClickListener(this);
            rvDishOrder.setAdapter(mAdapter);
            initProgressBar();
            //kiểm tra đầu vào r mới khởi tạo presenter
            getBillIdFromAnotherScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Phuương thức nhận bill id từ màn hình khác gửi đến
     * nếu không có sẽ tạo mới 1 bill
     * Created at 15/04/2019
     */
    private void getBillIdFromAnotherScreen() {
        try {
            Intent intent = getIntent();
            String billId = intent.getStringExtra(AppConstants.EXTRA_BILL_ID);
            if (billId != null) {
                mIsEdit = true;
                mPresenter = new DishOrderPresenter(null);
                mPresenter.setView(this);
                mPresenter.getBillById(billId);
            } else {
                onCreateNew();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo mặc định cho màn hình
     * Created at 17/04/2019
     */
    private void onCreateNew() {
        try {
            mAdapter.clearData();
            tvTotalMoney.setText(R.string.price_default);
            tvTable.setText("");
            tvPerson.setText("");
            mIsEdit = false;
            mBill = new Bill();
            mPresenter = null;
            mPresenter = new DishOrderPresenter(mBill.getBillId());
            mPresenter.setView(this);
            mPresenter.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo progressbar
     * Created at 09/04/2019
     */
    private void initProgressBar() {
        try {
            mDialog = new ProgressDialog(this) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                }
            };
            mDialog.setMessage(getString(R.string.init_dish_list));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created at 12/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 09/04/2019
     */
    @Override
    public void showLoading() {
        try {
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created at 09/04/2019
     */
    @Override
    public void hideLoading() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý sự kiện khi item món ăn được click
     * Created at 12/04/2019
     *
     * @param data là vị trí item được click
     */
    @Override
    public void onItemClick(Integer data) {
        try {
            tvTotalMoney.setText(NumberFormat.getNumberInstance(Locale.US).format(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị danh sách các món ăn của hóa đơn chi tiết
     * Created at 12/04/2019
     *
     * @param billDetails - danh sách món ăn của hóa đơn chi tiết
     */
    @Override
    public void showListDishOrder(List<BillDetail> billDetails) {
        try {
            if (billDetails != null && billDetails.size() > 0) {
                mAdapter.setListData(billDetails);
                clWaterMark.setVisibility(View.GONE);
            } else {
                clWaterMark.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý khi thêm Order thành công
     * Created at 12/04/2019
     */
    @Override
    public void saveOrderSuccess() {
        try {
            checkIfIsOnlyActivity();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý khi thêm Order thất bại
     * Created at 12/04/2019
     */
    @Override
    public void saveOrderFailed() {
        mNavigator.showToastOnTopScreen(R.string.something_went_wrong);
    }

    /**
     * Phương thức gán hóa đơn
     * Created at 16/04/2019
     *
     * @param bill - hóa đơn
     */
    @Override
    public void setBill(Bill bill) {
        try {
            if (bill != null) {
                mBill = bill;
                tvTable.setText(bill.getTableNumber() > 0 ? String.valueOf(bill.getTableNumber()) : "");
                tvPerson.setText(bill.getNumberCustomer() > 0 ? String.valueOf(bill.getNumberCustomer()) : "");
                tvTotalMoney.setText(NumberFormat.getNumberInstance(Locale.US).format(bill.getTotalMoney()));
                mPresenter.setListDishOrder(bill.getBillId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức thanh toán hóa đơn qua hóa đơn id
     * Created at 16/04/2019
     *
     * @param billId - hóa đơn Id
     */
    @Override
    public void pay(String billId) {
        try {
            Intent intent = new Intent();
            intent.setClass(this, PayActivity.class);
            intent.putExtra(AppConstants.EXTRA_BILL_ID, billId);
            mNavigator.startActivityForResult(intent, SaleFragment.REQUEST_PAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 12/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                try {
                    saveOrder(false);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvPerson:
                try {
                    showDialogNumber(InputNumberDialog.FLAG_PERSON, tvPerson.getText(), new InputNumberDialog.DialogCallBack() {
                        @Override
                        public void setAmount(String amount) {
                            tvPerson.setText(amount);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvTable:
                try {
                    showDialogNumber(InputNumberDialog.FLAG_TABLE, tvTable.getText(), new InputNumberDialog.DialogCallBack() {
                        @Override
                        public void setAmount(String amount) {
                            tvTable.setText(amount);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnBack:
                try {
                    checkIfIsOnlyActivity();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvPay:
            case R.id.btnPay:
                try {
                    if (SharedPrefersManager.getInstance(this).getIsLoginSuccess()) {
                        saveOrder(true);
                    } else {
                        long totalMoney = (long) NumberFormat.getNumberInstance(Locale.US).parse(tvTotalMoney.getText().toString());
                        if (totalMoney > 0) {
                            mNavigator.startActivity(LoginActivity.class);
                        } else {
                            mNavigator.showToastOnTopScreen(R.string.you_have_not_select_dish_yet);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Phương thức lưu order
     * Created at 16/04/2019
     *
     * @param isPayNow - nếu isPayNow = true thì sẽ lưu và thanh toán, isPayNow = false sẽ chỉ lưu
     */
    private void saveOrder(boolean isPayNow) {
        try {
            long totalMoney = (long) NumberFormat.getNumberInstance(Locale.US).parse(tvTotalMoney.getText().toString().trim());
            if (totalMoney > 0) {
                mBill.setTotalMoney((int) totalMoney);
                mBill.setDateCreated(Calendar.getInstance().getTimeInMillis());
                if (!tvTable.getText().toString().isEmpty()) {
                    mBill.setTableNumber(Integer.parseInt(tvTable.getText().toString()));
                }
                if (!tvPerson.getText().toString().isEmpty()) {
                    mBill.setNumberCustomer(Integer.parseInt(tvPerson.getText().toString()));
                }
                if (mIsEdit) {
                    mPresenter.updateOrder(mBill, mAdapter.getListData(), isPayNow);
                } else {
                    mPresenter.saveOrder(mBill, mAdapter.getListData(), isPayNow);
                }
            } else {
                mNavigator.showToastOnTopScreen(R.string.you_have_not_select_dish_yet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Phương thức kiểm tra liệu đây có phải là activity duy nhất đang hoạt động
     * nếu đúng thì khi bấm nút trở về sẽ mở màn hình main activity
     * Created at 15/04/2019
     */
    private void checkIfIsOnlyActivity() {
        try {
            if (isTaskRoot()) {
                startActivity(new Intent(this, MainActivity.class));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        checkIfIsOnlyActivity();
        super.onBackPressed();
    }

    /**
     * Phương thức xử lý kết quả trả về từ 1 activity khác
     * Created at 17/04/2019
     *
     * @param requestCode - mã yêu cầu
     * @param resultCode  - mã kết quả trả về
     * @param data        - dữ liệu được trả về
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SaleFragment.REQUEST_PAY) {
                if (resultCode == Activity.RESULT_OK) {
                    //nếu kết quả là ok(thanh toán hóa đơn thành công) thì sẽ khởi tạo lại màn hình
                    onCreateNew();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    //nếu kết quả là cancel(không thanh toán luôn) thì sẽ gán lại hóa đơn là đang sửa
                    mIsEdit = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
