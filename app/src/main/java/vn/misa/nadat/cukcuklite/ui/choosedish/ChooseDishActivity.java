package vn.misa.nadat.cukcuklite.ui.choosedish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.ChooseDishAdapter;
import vn.misa.nadat.cukcuklite.dialogs.NumberKeyboardDialog;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;
import vn.misa.nadat.cukcuklite.ui.main.MainActivity;
import vn.misa.nadat.cukcuklite.ui.payment.PaymentActivity;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Màn hình chọn món ăn.
 *
 * @created_by nadat on 20/04/2019
 */
public class ChooseDishActivity extends AppCompatActivity implements IChooseDishContract.IView,
        View.OnClickListener, ChooseDishAdapter.CallbackListDishesInSale {
    private IChooseDishContract.IPresenter mChooseDishPresenter;
    private RecyclerView rcvItemChooseDishes;
    private TextView tvTable;
    private TextView tvPerson;
    private LinearLayout llRecognitionView;
    private ChooseDishAdapter mChooseDishAdapter;
    private SharedPreferences mSharedPreferences;
    private List<String> mColors;
    private int mAddSale;
    private int mItemSaleId;
    private List<ItemDish> mItemDishesInSale;
    private String mColorSale;
    private ItemSale mItemSale;
    private HashMap<Integer, Integer> mListNumberDishesInSale;

    /**
     * Khởi tạo ChooseDishActivity.
     *
     * @param savedInstanceState: trạng thái của Activity
     * @created_by nadat on 20/04/2019
     */
    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_choose_dish);

            rcvItemChooseDishes = findViewById(R.id.rcvItemChooseDishes);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rcvItemChooseDishes.setLayoutManager(linearLayoutManager);

            tvTable = findViewById(R.id.tvTable);
            tvPerson = findViewById(R.id.tvPerson);
            tvTable.setOnClickListener(this);
            tvPerson.setOnClickListener(this);

            mChooseDishAdapter = new ChooseDishAdapter(this, this);
            TextView tvTotalMoney = findViewById(R.id.tvTotalMoney);
            mChooseDishAdapter.setTvTotalMoney(tvTotalMoney);

            AppCompatImageButton imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(this);
            ImageButton btnSound = findViewById(R.id.btnSound);
            btnSound.setOnClickListener(this);
            Button btnSave = findViewById(R.id.btnSave);
            btnSave.setOnClickListener(this);
            TextView btnActionTakeMoney = findViewById(R.id.btnActionTakeMoney);
            btnActionTakeMoney.setOnClickListener(this);
            Button btnTakeMoney = findViewById(R.id.btnTakeMoney);
            btnTakeMoney.setOnClickListener(this);
            AppCompatImageView ivCloseVoice = findViewById(R.id.ivCloseVoice);
            ivCloseVoice.setOnClickListener(this);
            llRecognitionView = findViewById(R.id.llRecognitionView);

            Intent intent = getIntent();
            mAddSale = intent.getIntExtra(Constant.ADD_SALE, -1);
            mItemSaleId = intent.getIntExtra(Constant.ID_SALE, -1);
            mItemDishesInSale = new ArrayList<>();

            mListNumberDishesInSale = new HashMap<>();

            mChooseDishPresenter = new ChooseDishPresenter(this);
            mChooseDishPresenter.getItemChooseDishes();

            if (mItemSaleId != -1) {
                mChooseDishPresenter.getItemSaleById(mItemSaleId);
            }

            mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            mColors = Arrays.asList(getResources().getStringArray(R.array.color_default));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy ra màu trong danh sách màu.
     *
     * @return mã màu
     * @created_by nadat on 21/04/2019
     */
    private String getItemSaleColor() {
        try {
            int indexColor = mSharedPreferences.getInt(Constant.SHARED_PREFERENCES_COLOR_SALE_INDEX, 0);
            String color = mColors.get(indexColor);
            indexColor++;
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            if (indexColor >= 32) {
                indexColor = 0;
            }
            editor.putInt(Constant.SHARED_PREFERENCES_COLOR_SALE_INDEX, indexColor);
            editor.apply();
            return color;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Hiển thị danh sách các món ăn.
     *
     * @param itemDishes: danh sách món ăn
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void showItemChooseDishes(List<ItemDish> itemDishes) {
        if (itemDishes == null) {
            return;
        }
        try {
            if (mItemSaleId != -1) {
                mItemDishesInSale.addAll(itemDishes);
                mChooseDishPresenter.getItemDishesInSale(mItemSaleId);
            } else {
                mChooseDishAdapter.setItemDishes(itemDishes);
                rcvItemChooseDishes.setAdapter(mChooseDishAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm mới thành công.
     *
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void showItemSaleInsertedOrUpdate() {
        Intent intent = new Intent();
        intent.setAction(Constant.BACK_ACTION_ADD_EDIT_SALE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        if (mAddSale == -1) {
            startActivity(new Intent(ChooseDishActivity.this, MainActivity.class));
        }
        finish();
    }

    /**
     * Hiển thị danh sách các món ăn trong ItemSale.
     *
     * @param itemDishes: danh sách món ăn
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void showItemDishesInSale(List<ItemDish> itemDishes) {
        if (itemDishes == null) {
            return;
        }
        try {
            for (int i = 0; i < itemDishes.size(); i++) {
                for (int j = 0; j < mItemDishesInSale.size(); j++) {
                    if (itemDishes.get(i).getItemDishId() == mItemDishesInSale.get(j).getItemDishId()) {
                        mItemDishesInSale.remove(j);
                        break;
                    }
                }
            }
            for (int i = 0; i < itemDishes.size(); i++) {
                mListNumberDishesInSale.put(itemDishes.get(i).getItemDishId(), itemDishes.get(i).getUseCount());
            }
            itemDishes.addAll(itemDishes.size(), mItemDishesInSale);
            mChooseDishAdapter.setItemDishes(itemDishes);
            rcvItemChooseDishes.setAdapter(mChooseDishAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông báo lỗi mời bạn thử lại.
     *
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void showNotificationError() {
        try {
            Toast.makeText(this, "Đã có lỗi mời bạn thử lại!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showItemSaleNeedUpdate(ItemSale itemSale) {
        mColorSale = itemSale.getItemSaleColor();
        tvTable.setText(itemSale.getNumberOfTable());
        tvPerson.setText(itemSale.getNumberOfPerson());
    }

    @Override
    public void showItemSalePay(int rowId, int type) {
        Intent intent = new Intent(ChooseDishActivity.this, PaymentActivity.class);
        intent.putExtra(Constant.ID_SALE, rowId);
        intent.putExtra(Constant.TYPE_BACK_PAY, type);
        if (type == Constant.PAY_OLD_SALE) {
            intent.putExtra(Constant.LIST_NUMBER_DISHES_IN_SALE, mListNumberDishesInSale);
            intent.putExtra(Constant.ITEM_SALE_UPDATE, mItemSale);
        }
        startActivity(intent);
    }

    /**
     * Bắt sự kiện click vào các view.
     *
     * @param v: view được click
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.imgBack: {
                    if (mAddSale == -1) {
                        startActivity(new Intent(ChooseDishActivity.this, MainActivity.class));
                        finish();
                    } else {
                        onBackPressed();
                    }
                    break;
                }
                case R.id.btnActionTakeMoney:
                case R.id.btnTakeMoney: {
                    mChooseDishAdapter.completedListDishesInSale(mItemSaleId, Constant.BTN_TAKE_MONEY);
                    break;
                }
                case R.id.btnSound: {
                    llRecognitionView.setVisibility(View.VISIBLE);
                    break;
                }
                case R.id.ivCloseVoice: {
                    llRecognitionView.setVisibility(View.GONE);
                    break;
                }
                case R.id.tvTable: {
                    new NumberKeyboardDialog(this, "table", tvTable.getText().toString(),
                            new NumberKeyboardDialog.CallBack() {
                                @Override
                                public void onAmount(String amount) {
                                    tvTable.setText(amount);
                                }
                            }).show();
                    break;
                }
                case R.id.tvPerson: {
                    new NumberKeyboardDialog(this, "people", tvPerson.getText().toString(),
                            new NumberKeyboardDialog.CallBack() {
                                @Override
                                public void onAmount(String amount) {
                                    tvPerson.setText(amount);
                                }
                            }).show();
                    break;
                }
                case R.id.btnSave: {
                    mChooseDishAdapter.completedListDishesInSale(mItemSaleId, Constant.BTN_SAVE);
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

    /**
     * Bắt sự kiện hoàn thành chọn món.
     *
     * @param listNumberDishesInSale: danh sách món ăn kèm số lượng
     * @created_by nadat on 20/04/2019
     */
    @Override
    public void callbackListNumberDishesInSale(SparseIntArray listNumberDishesInSale, int itemSaleId, int type) {
        if (listNumberDishesInSale == null) {
            return;
        }
        if (listNumberDishesInSale.size() == 0) {
            Toast toast = Toast.makeText(this, "Vui lòng chọn món", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
            toast.show();
            return;
        }
        try {
            mItemSale = new ItemSale();
            mItemSale.setPaymentStatus(0);
            mItemSale.setNumberOfTable(tvTable.getText().toString());
            mItemSale.setNumberOfPerson(tvPerson.getText().toString());
            if (!tvTable.getText().toString().equals("") && mColorSale == null) {
                mItemSale.setItemSaleColor(getItemSaleColor());
            } else {
                mItemSale.setItemSaleColor(mColorSale);
            }

            if (itemSaleId != -1) {
                mItemSale.setSaleId(itemSaleId);
                if (type == Constant.BTN_TAKE_MONEY) {
                    mChooseDishPresenter.payOldItemSale(itemSaleId, mItemSale, listNumberDishesInSale, Constant.PAY_OLD_SALE);
                } else {
                    mChooseDishPresenter.updateItemSale(itemSaleId, mItemSale, listNumberDishesInSale);
                }
            } else {
                if (type == Constant.BTN_TAKE_MONEY) {
                    mChooseDishPresenter.payNewItemSale(mItemSale, listNumberDishesInSale, Constant.PAY_NEW_SALE);
                } else {
                    mChooseDishPresenter.saveItemSale(mItemSale, listNumberDishesInSale);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}