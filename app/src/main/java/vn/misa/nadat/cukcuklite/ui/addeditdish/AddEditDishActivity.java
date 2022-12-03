package vn.misa.nadat.cukcuklite.ui.addeditdish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.misa.nadat.cukcuklite.CukCukLiteApp;
import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.dialogs.DeleteItemDialog;
import vn.misa.nadat.cukcuklite.dialogs.PaymentKeyboardDialog;
import vn.misa.nadat.cukcuklite.dialogs.PickIconColorDialog;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.ui.unit.UnitActivity;
import vn.misa.nadat.cukcuklite.utils.Constant;
import vn.misa.nadat.cukcuklite.utils.Utils;

/**
 * Màn hình thêm hoặc sửa món ăn.
 *
 * @created_by nadat on 10/04/2019
 */
public class AddEditDishActivity extends AppCompatActivity implements
        IAddEditContract.IView,
        View.OnClickListener,
        PickIconColorDialog.OnChooseColorListener,
        PickIconColorDialog.OnChooseIconListener,
        PaymentKeyboardDialog.OnClickDoneListener {
    private static final int REQUEST_CODE_UNIT = 648;
    private int mItemDishId;
    private Intent mData;
    private int mFirstItemUnitId;

    private TextView tvTitle;
    private TextView tvUnitName;
    private LinearLayout lnInactive;
    private TextView btnDone;
    private TextView btnDelete;
    private TextView btnSave;
    private EditText edtItemDishName;
    private TextView tvPrice;
    private AppCompatImageView imgBgColor;
    private AppCompatImageView imgBgIconColor;
    private AppCompatImageView imgSelectIcon;
    private CheckBox cbInactive;

    private IAddEditContract.IPresenter mAddEditDishPresenter;

    private String mColorDefault = "#039be5";
    private String mIconDefault = "ic_default.png";

    private int mItemUnitId;

    /**
     * Khởi tạo AddEditDishActivity.
     *
     * @param savedInstanceState: trạng thái của Activity
     * @created_by nadat on 11/04/2019
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_add_edit_dish);

            tvTitle = findViewById(R.id.tvTitle);
            tvUnitName = findViewById(R.id.tvUnitName);
            lnInactive = findViewById(R.id.lnInactive);
            btnDone = findViewById(R.id.btnDone);
            btnDelete = findViewById(R.id.btnDelete);
            btnSave = findViewById(R.id.btnSave);
            edtItemDishName = findViewById(R.id.edtItemDishName);
            tvPrice = findViewById(R.id.tvPrice);
            imgBgColor = findViewById(R.id.imgBgColor);
            imgBgIconColor = findViewById(R.id.imgBgIconColor);
            imgSelectIcon = findViewById(R.id.imgSelectIcon);
            cbInactive = findViewById(R.id.cbInactive);

            Intent intent = getIntent();
            mItemDishId = intent.getIntExtra(Constant.ID_DISH, -1);

            mAddEditDishPresenter = new AddEditDishPresenter(this);

            if (mItemDishId == -1) {
                showViewAddItemDish();
            } else {
                showViewEditItemDish();
            }

            AppCompatImageButton imgBtnBack = findViewById(R.id.imgBtnBack);
            imgBtnBack.setOnClickListener(this);
            LinearLayout lnUnitPrice = findViewById(R.id.lnUnitPrice);
            lnUnitPrice.setOnClickListener(this);
            LinearLayout lnUnitName = findViewById(R.id.lnUnitName);
            lnUnitName.setOnClickListener(this);
            RelativeLayout realSelectColor = findViewById(R.id.realSelectColor);
            realSelectColor.setOnClickListener(this);
            RelativeLayout realSelectIcon = findViewById(R.id.realSelectIcon);
            realSelectIcon.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nhận dữ liệu trả vể từ UnitActivity
     *
     * @param requestCode: mã yêu cầu
     * @param resultCode:  kết quả yêu cầu
     * @param data:        dữ liệu nhận được
     * @created_by nadat on 12/04/2019
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE_UNIT) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        mData = data;
                        tvUnitName.setText(data.getStringExtra(Constant.ITEM_UNIT_NAME));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị màn hình thêm món ăn.
     *
     * @created_by nadat on 11/04/2019
     */
    private void showViewAddItemDish() {
        try {
            tvTitle.setText(R.string.add_inventory_item);
            lnInactive.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);

            mAddEditDishPresenter.getFirstItemUnitName();

            View.OnClickListener onClickSaveListenerAddDish = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtItemDishName.getText().toString().trim().length() == 0) {
                        Toast toast = Toast.makeText(AddEditDishActivity.this, "Tên món không được để trống.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
                        toast.show();
                    } else {
                        ItemDish itemDish = new ItemDish();
                        itemDish.setItemDishName(edtItemDishName.getText().toString().trim());
                        itemDish.setItemDishPrice(tvPrice.getText().toString());
                        if (mData != null) {
                            itemDish.setItemUnitId(mData.getIntExtra(Constant.ITEM_UNIT_ID, mFirstItemUnitId));
                        } else {
                            itemDish.setItemUnitId(mFirstItemUnitId);
                        }
                        itemDish.setItemDishColor(mColorDefault);
                        itemDish.setItemDishIcon(mIconDefault);
                        itemDish.setInactive(0);
                        mAddEditDishPresenter.saveItemDish(itemDish);
                    }
                }
            };
            btnDone.setOnClickListener(onClickSaveListenerAddDish);
            btnSave.setOnClickListener(onClickSaveListenerAddDish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị mà hình sửa món ăn.
     *
     * @created_by nadat on 11/04/2019
     */
    private void showViewEditItemDish() {
        try {
            tvTitle.setText(R.string.edit_inventory_item);
            lnInactive.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            mAddEditDishPresenter.getItemDishById(mItemDishId);

            View.OnClickListener onClickSaveListenerEditDish = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemDish itemDish = new ItemDish();
                    itemDish.setItemDishName(edtItemDishName.getText().toString().trim());
                    itemDish.setItemDishPrice(tvPrice.getText().toString());
                    if (mData != null) {
                        itemDish.setItemUnitId(mData.getIntExtra(Constant.ITEM_UNIT_ID, mItemUnitId));
                    } else {
                        itemDish.setItemUnitId(mItemUnitId);
                    }
                    itemDish.setItemDishColor(mColorDefault);
                    itemDish.setItemDishIcon(mIconDefault);
                    if (cbInactive.isChecked()) {
                        itemDish.setInactive(1);
                    } else {
                        itemDish.setInactive(0);
                    }
                    mAddEditDishPresenter.updateItemDishById(mItemDishId, itemDish);
                }
            };
            btnDone.setOnClickListener(onClickSaveListenerEditDish);
            btnSave.setOnClickListener(onClickSaveListenerEditDish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị đơn vị mặc định
     * là đơn vị đầu tiên trong DB và được sắp xếp ABC
     * khi thêm mới món ăn.
     *
     * @param itemUnitId:   Id đơn vị
     * @param itemUnitName: tên đơn vị
     * @created_by nadat on 11/04/2019
     */
    @Override
    public void showFirstItemUnitName(int itemUnitId, String itemUnitName) {
        if (itemUnitName == null) {
            return;
        }
        try {
            mFirstItemUnitId = itemUnitId;
            tvUnitName.setText(itemUnitName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị món ăn cần sửa thông tin.
     *
     * @param itemDish: món ăn được hiển thị để sửa
     * @created_by nadat on 11/04/2019
     */
    @Override
    public void showItemDishNeedUpdate(ItemDish itemDish) {
        if (itemDish == null) {
            return;
        }
        try {
            mItemUnitId = itemDish.getItemUnitId();
            mAddEditDishPresenter.getUnitNameItemDishById(itemDish.getItemUnitId());
            edtItemDishName.setText(itemDish.getItemDishName());
            tvPrice.setText(itemDish.getItemDishPrice());

            Drawable drawable = CukCukLiteApp.getInstance().getResources().getDrawable(R.drawable.bg_circle_image_view);
            drawable.setColorFilter(Color.parseColor(itemDish.getItemDishColor()), PorterDuff.Mode.SRC);
            imgBgColor.setBackgroundDrawable(drawable);
            imgBgIconColor.setBackgroundDrawable(drawable);

            imgSelectIcon.setImageBitmap(Utils.getBitmapFromAssets(itemDish.getItemDishIcon()));

            mColorDefault = itemDish.getItemDishColor();
            mIconDefault = itemDish.getItemDishIcon();

            if (itemDish.isInactive() == 0) {
                cbInactive.setChecked(false);
            } else {
                cbInactive.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông báo đã có lỗi mời bạn thử lại.
     *
     * @created_by nadat on 11/04/2019
     */
    @Override
    public void showNotificationError() {
        try {
            Toast.makeText(this, "Đã có lỗi mời bạn thử lại!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm mới hoặc sửa món ăn thành công.
     *
     * @created_by nadat on 15/04/2019
     */
    @Override
    public void showItemDishInsertedOrUpdated() {
        Intent intent = new Intent();
        intent.setAction(Constant.BACK_ACTION_ADD_EDIT_DISH);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

    /**
     * Hiển thị tên đơn vị.
     *
     * @param itemUnitName: tên đơn vị trước đó
     * @created_by nadat on 16/04/2019
     */
    @Override
    public void showItemUnitName(int itemUnitId, String itemUnitName) {
        if (itemUnitName == null) {
            return;
        }
        try {
            mItemUnitId = itemUnitId;
            tvUnitName.setText(itemUnitName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showItemDishCanDeleted() {
        mAddEditDishPresenter.deleteItemDishById(mItemDishId);
    }

    @Override
    public void showNotificationDishExist() {
        Toast toast = Toast.makeText(this,
                "Món ăn " + edtItemDishName.getText().toString() + " đang được sử dụng!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
        toast.show();
    }

    /**
     * Bắt sự kiện click vào các view trong AddEditDishActivity.
     *
     * @param v: view được click
     * @created_by nadat on 11/04/2019
     */
    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        try {
            switch (v.getId()) {
                case R.id.imgBtnBack: {
                    finish();
                    break;
                }
                case R.id.lnUnitPrice: {
                    FragmentManager fm = getSupportFragmentManager();
                    PaymentKeyboardDialog paymentKeyboardDialog =
                            PaymentKeyboardDialog.createInstance(tvPrice.getText().toString(), this);
                    paymentKeyboardDialog.show(fm, "PaymentKeyboardDialog");
                    break;
                }
                case R.id.lnUnitName: {
                    Intent intent = new Intent(this, UnitActivity.class);
                    intent.putExtra(Constant.ITEM_UNIT_NAME, tvUnitName.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE_UNIT);
                    break;
                }
                case R.id.realSelectColor: {
                    FragmentManager fm = getSupportFragmentManager();
                    PickIconColorDialog pickColorDialog = PickIconColorDialog.newInstance(4, mColorDefault);
                    pickColorDialog.setOnChooseColorListener(this);
                    pickColorDialog.show(fm, "PickColorDialog");
                    break;
                }
                case R.id.realSelectIcon: {
                    FragmentManager fm = getSupportFragmentManager();
                    PickIconColorDialog pickIconDialog = PickIconColorDialog.newInstance(5, "");
                    pickIconDialog.setOnChooseIconListener(this);
                    pickIconDialog.show(fm, "PickIconDialog");
                    break;
                }
                case R.id.btnDelete: {
                    new DeleteItemDialog(this, Constant.TYPE_DISH, edtItemDishName.getText().toString().trim(), new DeleteItemDialog.OnClickAcceptDialogDeleteItemListener() {
                        @Override
                        public void onClickAcceptDialogDeleteItem() {
                            mAddEditDishPresenter.checkItemDishExist(mItemDishId);
                        }
                    }).show();
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bắt sự kiện thay đổi màu món ăn.
     *
     * @param color: màu được chọn
     * @created_by nadat on 15/04/2019
     */
    @Override
    public void onChooseColor(String color) {
        mColorDefault = color;
        Drawable drawable = CukCukLiteApp.getInstance().getResources().getDrawable(R.drawable.bg_circle_image_view);
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC);
        imgBgColor.setBackgroundDrawable(drawable);
        imgBgIconColor.setBackgroundDrawable(drawable);
    }

    /**
     * Bắt sự kiện thay đổi icon món ăn.
     *
     * @param icon: icon được chọn
     * @created_by nadat on 15/04/2019
     */
    @Override
    public void onChooseIcon(String icon) {
        mIconDefault = icon;
        imgSelectIcon.setImageBitmap(Utils.getBitmapFromAssets(mIconDefault));
    }

    /**
     * Bắt sự kiện thay đổi giá món ăn.
     *
     * @param price: giá được chọn
     * @created_by nadat on 15/04/2019
     */
    @Override
    public void onClickDone(String price) {
        tvPrice.setText(price);
    }
}
