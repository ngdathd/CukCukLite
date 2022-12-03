package vn.misa.nadat.cukcuklite.ui.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.PaymentAdapter;
import vn.misa.nadat.cukcuklite.dialogs.DeleteItemDialog;
import vn.misa.nadat.cukcuklite.dialogs.MoneyReturnDialog;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;
import vn.misa.nadat.cukcuklite.ui.main.MainActivity;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.PriceUtils;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, IPaymentContract.IView {
    private IPaymentContract.IPresenter mPaymentPresenter;
    private int mSaleIdPay;
    private int mTypePay;
    private RecyclerView rcvDetail;
    private TextView tvTotalAmount;
    private TextView tvCustomerAmount;
    private TextView tvReturnAmount;
    private int moneyReturn;
    private String mDatePayed;
    private SparseIntArray mListNumberDishesInSale;
    private ItemSale mItemSale;
    private SharedPreferences mSharedPreferences;
    private int mIndexSale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        rcvDetail = findViewById(R.id.rcvDetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDetail.setLayoutManager(linearLayoutManager);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvCustomerAmount = findViewById(R.id.tvCustomerAmount);
        tvReturnAmount = findViewById(R.id.tvReturnAmount);

        mPaymentPresenter = new PaymentPresenter(this);

        Intent intent = getIntent();
        mSaleIdPay = intent.getIntExtra(Constant.ID_SALE, -1);
        mTypePay = intent.getIntExtra(Constant.TYPE_BACK_PAY, -1);

        mListNumberDishesInSale = new SparseIntArray();
        if (mTypePay == Constant.PAY_OLD_SALE) {
            HashMap<Integer, Integer> hashMap = (HashMap<Integer, Integer>) intent.getSerializableExtra(Constant.LIST_NUMBER_DISHES_IN_SALE);
            for (int key : hashMap.keySet()) {
                mListNumberDishesInSale.append(key, hashMap.get(key));
            }
            mItemSale = (ItemSale) intent.getSerializableExtra(Constant.ITEM_SALE_UPDATE);
            mSaleIdPay = mItemSale.getIdSale();
        }

        if (mSaleIdPay != -1) {
            mPaymentPresenter.getItemSalePaymentById(mSaleIdPay);
            mPaymentPresenter.getItemDishesInSalePaymentById(mSaleIdPay);
        }

        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mIndexSale = mSharedPreferences.getInt(Constant.SHARED_PREFERENCES_SALE_INDEX, 0);
        mIndexSale++;
        @SuppressLint("DefaultLocale")
        String noSale = String.format("%05d", mIndexSale);
        TextView tvRefNo = findViewById(R.id.tvRefNo);
        tvRefNo.setText(noSale);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy (hh:mm a)");
        Date date = new Date();
        TextView tvRefDate = findViewById(R.id.tvRefDate);
        tvRefDate.setText(formatter.format(date));

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatterSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mDatePayed = formatterSQL.format(date);

        AppCompatImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        LinearLayout lnCustomerAmount = findViewById(R.id.lnCustomerAmount);
        lnCustomerAmount.setOnClickListener(this);
        TextView btnActionDone = findViewById(R.id.btnActionDone);
        btnActionDone.setOnClickListener(this);
        Button btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnBack: {
                    if (mTypePay == Constant.PAY_OLD_SALE) {
                        mPaymentPresenter.restoreItemSalePayment(mSaleIdPay, mItemSale, mListNumberDishesInSale);
                    } else if (mTypePay == Constant.PAY_NEW_SALE) {
                        mPaymentPresenter.deleteItemSalePayment(mSaleIdPay);
                    } else {
                        finish();
                    }
                    break;
                }
                case R.id.lnCustomerAmount: {
                    new MoneyReturnDialog(this,
                            PriceUtils.formatNumber(tvTotalAmount.getText().toString()),
                            new MoneyReturnDialog.CallBack() {
                                @Override
                                public void onClick(String result) {
                                    tvCustomerAmount.setText(PriceUtils.formatPrice(result));
                                    moneyReturn = Integer.parseInt(result)
                                            - Integer.parseInt(PriceUtils.formatNumber(tvTotalAmount.getText().toString()));
                                    tvReturnAmount.setText(PriceUtils.formatPrice(String.valueOf(moneyReturn)));
                                }
                            }).show();
                    break;
                }
                case R.id.btnActionDone:
                case R.id.btnDone: {
                    if (moneyReturn >= 0) {
                        mPaymentPresenter.payItemSalePayed(mSaleIdPay, mDatePayed);
                    } else {
                        new DeleteItemDialog(this, Constant.TYPE_PAY, new DeleteItemDialog.OnClickAcceptDialogDeleteItemListener() {
                            @Override
                            public void onClickAcceptDialogDeleteItem() {
                                mPaymentPresenter.payItemSalePayed(mSaleIdPay, mDatePayed);
                            }
                        }).show();
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showItemSalePayment(ItemSale itemSale) {
        LinearLayout lnTable = findViewById(R.id.lnTable);
        TextView tvTableName = findViewById(R.id.tvTableName);
        if (itemSale.getNumberOfTable() == null || itemSale.getNumberOfTable().equals("")) {
            lnTable.setVisibility(View.GONE);
        } else {
            tvTableName.setText(itemSale.getNumberOfTable());
        }
    }

    @Override
    public void showItemDishesPayment(List<ItemDish> itemDishes) {
        PaymentAdapter paymentAdapter = new PaymentAdapter(itemDishes);
        paymentAdapter.setTvTotalCustomAmount(tvTotalAmount, tvCustomerAmount);
        rcvDetail.setAdapter(paymentAdapter);
    }

    @Override
    public void showNotificationError() {
        try {
            Toast.makeText(this, "Đã có lỗi mời bạn thử lại!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showItemSalePayed() {
        Intent intent = new Intent();
        intent.setAction(Constant.BACK_ACTION_ADD_EDIT_SALE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(Constant.SHARED_PREFERENCES_SALE_INDEX, mIndexSale);
        editor.apply();
        startActivity(new Intent(PaymentActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void backChooseDishAct() {
        finish();
    }
}
