package com.ngdat.cukcuklite.screen.adddish;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.models.Unit;
import com.ngdat.cukcuklite.screen.dialogs.caculator.InputNumberDialog;
import com.ngdat.cukcuklite.screen.dialogs.color.ColorSelectorDialog;
import com.ngdat.cukcuklite.screen.dialogs.delete.ConfirmDeleteDialog;
import com.ngdat.cukcuklite.screen.dialogs.icon.DishIconSelectorDialog;
import com.ngdat.cukcuklite.screen.selectunit.SelectUnitActivity;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.ImageUtils;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình thêm/sửa món ăn
 * Created at 09/04/2019
 */
public class AddDishActivity extends AppCompatActivity implements IAddDishContract.IView, View.OnClickListener, ColorSelectorDialog.IColorSelectedCallBack, DishIconSelectorDialog.IDishIconCallBack, ConfirmDeleteDialog.IConfirmDeleteCallBack {

    public static final String ACTION_OK = "ACTION_OK";
    public static final String EXTRA_UNIT_ID = "EXTRA_UNIT_ID";
    private static final String COLOR_DIALOG = "COLOR_DIALOG";
    private static final String DISH_ICON_DIALOG = "DISH_ICON_DIALOG";
    private static final String DELETE_DIALOG = "DELETE_DIALOG";
    private ImageButton btnBack;
    private TextView tvTitle, tvSave, tvUnit, tvStateTitle, tvPrice;
    private EditText etDishName;
    private ImageView ivSelectUnit, ivSelectPrice, ivColor, ivIcon;
    private CheckBox cbState;
    private Button btnDelete, btnSave;
    private ProgressDialog mDialog;

    private Dish mDish;
    private AddDishPresenter mPresenter;
    private boolean isEdit;
    private Navigator mNavigator;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction() != null) {
                    try {
                        String unitId = intent.getStringExtra(SelectUnitActivity.EXTRA_UNIT_SELECTED);
                        if (unitId != null) {
                            //thiết lập đơn vị cho món ăn
                            mDish.setUnitId(unitId);
                            tvUnit.setText(mPresenter.getUnitName(unitId));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        mNavigator = new Navigator(this);
        mPresenter = new AddDishPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        //đăng kí lắng nghe sự kiện thay đổi đơn vị của món ăn
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(SelectUnitActivity.ACTION_UNIT_SELECTED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //hủy đăng kí lắng nghe sự thay đôi của đơn vị món ăn
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 09/04/2019
     */
    private void initEvents() {
        btnBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvStateTitle.setOnClickListener(this);
        tvUnit.setOnClickListener(this);
        etDishName.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        ivSelectUnit.setOnClickListener(this);
        ivSelectPrice.setOnClickListener(this);
        ivColor.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        cbState.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 09/04/2019
     */
    private void initViews() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvStateTitle = (TextView) findViewById(R.id.tvStateTitle);
        tvUnit = (TextView) findViewById(R.id.tvUnit);
        etDishName = (EditText) findViewById(R.id.etDishName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        ivSelectUnit = (ImageView) findViewById(R.id.ivSelectUnit);
        ivSelectPrice = (ImageView) findViewById(R.id.ivSelectPrice);
        ivColor = (ImageView) findViewById(R.id.ivColor);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        cbState = (CheckBox) findViewById(R.id.cbState);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSave = (Button) findViewById(R.id.btnSave);

        initProgressBar();
        setPaddingForTextToRightOfCheckBox();
        Dish dish = getIntent().getParcelableExtra(AppConstants.EXTRA_DISH);
        if (dish != null) {
            isEdit = true;
            tvTitle.setText(R.string.edit_dish);
            setUpView(dish);
        } else {
            hideViews();
            isEdit = false;
            mDish = new Dish.Builder().setColorCode(AppConstants.COLOR_DEFAULT)
                    .setIconPath(AppConstants.ICON_DEFAULT)
                    .build();
            Drawable drawable = getResources().getDrawable(R.drawable.background_dish_icon);
            drawable.setColorFilter(Color.parseColor(AppConstants.COLOR_DEFAULT), PorterDuff.Mode.SRC);
            ivColor.setBackground(drawable);
            ivIcon.setBackground(drawable);
            mPresenter.onStart();
            tvTitle.setText(R.string.add_dish);

        }
    }

    /**
     * Tăng khoảng cách giữa text và icon của checkbox
     * Created at 20/03/2019
     */
    private void setPaddingForTextToRightOfCheckBox() {
        final float scale = this.getResources().getDisplayMetrics().density;
        cbState.setPadding(cbState.getPaddingLeft() + (int) (8.0f * scale + 0.5f),
                cbState.getPaddingTop(),
                cbState.getPaddingRight(),
                cbState.getPaddingBottom());
    }

    /**
     * Phương thức gán các giá trị cho view khi món ăn được chỉnh sửa
     * Created at 27/03/2019
     *
     * @param dish - món ăn
     */
    private void setUpView(Dish dish) {
        try {
            if (dish != null) {
                mDish = dish;
                cbState.setChecked(!dish.isSale());
                etDishName.setText(dish.getDishName());
                tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format((long) (dish.getPrice())));
                tvUnit.setText(mPresenter.getUnitName(dish.getUnitId()));
                Drawable drawable = getResources().getDrawable(R.drawable.background_dish_icon);
                drawable.setColorFilter(Color.parseColor(dish.getColorCode()), PorterDuff.Mode.SRC);
                ivColor.setBackground(drawable);
                ivIcon.setBackground(drawable);
                ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(this, dish.getIconPath()));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ẩn view khi người dùng chọn chức năng thêm món
     * Created at 27/03/2019
     */
    private void hideViews() {
        cbState.setVisibility(View.GONE);
        tvStateTitle.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
    }

    /**
     * Thông báo khi người dùng không nhập tên món ăn
     * Created at 27/03/2019
     */
    @Override
    public void dishNameEmpty() {
        mNavigator.showToastOnTopScreen(R.string.dish_name_not_allow_empty);
        etDishName.requestFocus();
    }

    /**
     * Thông báo cho người dùng đã thêm món ăn thành công và quay về màn hình thực đơn
     * Created at 27/03/2019
     */
    @Override
    public void addDishSuccess() {
        try {
            mNavigator.showToast(R.string.add_dish_success);
            finishTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDishFailed(int error) {
        mNavigator.showToastOnTopScreen(error);
    }

    /**
     * Phương thức gán Đơn vị cho món ăn
     * Created at 10/04/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void setUnit(Unit unit) {
        try {
            if (unit != null) {
                String unitId = unit.getUnitId();
                mDish.setUnitId(unitId);
                tvUnit.setText(mPresenter.getUnitName(unitId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thông báo cho người dùng đã cập nhật món ăn thành công và quay về màn hình thực đơn
     * Created at 27/03/2019
     */
    @Override
    public void upDateDishSuccess() {
        try {
            mNavigator.showToastOnTopScreen(R.string.update_dish_success);
            finishTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thoát màn hình và bắn sự kiện hoàn thành tác vụ
     * Created at 27/03/2019
     */
    private void finishTask() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thông báo cho người dùng đã xóa món ăn thành công và quay về màn hình thực đơn
     * Created at 27/03/2019
     */
    @Override
    public void deleteDishSuccess() {
        try {
            mNavigator.showToastOnTopScreen(R.string.delete_success);
            finishTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức nhận 1 thông điệp
     * Created at 09/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToastOnTopScreen(message);
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
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 09/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                //trở về màn hình trước đó
                finish();
                break;
            case R.id.ivColor:
                showColorSelector();
                break;
            case R.id.ivSelectPrice:
            case R.id.tvPrice:
                try {
                    showDialogNumber(InputNumberDialog.FLAG_PRICE, NumberFormat.getNumberInstance(Locale.US).parse(tvPrice.getText().toString()).toString(), new InputNumberDialog.DialogCallBack() {
                        @Override
                        public void setAmount(String amount) {
                            if (TextUtils.isEmpty(amount)) {
                                amount = "0";
                            }
                            tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(amount)));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ivSelectUnit:
            case R.id.tvUnit:
                selectUnit();
                break;
            case R.id.ivIcon:
                showDishIconSelector();
                break;
            case R.id.btnSave:
            case R.id.tvSave:
                save();
                break;
            case R.id.btnDelete:
                showDeleteDishDialogConfirm();
                break;
            default:
                break;
        }
    }


    /**
     * Phương thức hiển thị dialog xác nhận việc xóa món ăn
     * Created at 27/03/2019
     */
    private void showDeleteDishDialogConfirm() {
        try {
            ConfirmDeleteDialog f = ConfirmDeleteDialog.newInstance(mDish.getDishName(), IDBUtils.ITableDishUtils.COLUMN_DISH_NAME);
            f.setCallBack(this);
            getSupportFragmentManager().beginTransaction().add(f, DELETE_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức Cất với cả 2 trường hợp là sửa món và thêm món
     * Created at 27/03/2019
     */
    private void save() {
        if (isEdit) {
            //sửa món
            updateDish();
        } else {
            //thêm món
            addDish();
        }
    }

    /**
     * Phương thức thêm món
     * Created at 27/03/2019
     */
    private void addDish() {
        try {
            mDish.setDishId(UUID.randomUUID().toString());
            mDish.setDishName(etDishName.getText().toString().trim());
            mDish.setPrice((int) ((long) NumberFormat.getNumberInstance(Locale.US).parse(tvPrice.getText().toString().trim())));
            mDish.setSale(!cbState.isChecked());
            mPresenter.addDish(mDish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức sửa món
     * Created at 27/03/2019
     */
    private void updateDish() {
        try {
            mDish.setDishName(etDishName.getText().toString().trim());
            mDish.setPrice((int) ((long) NumberFormat.getNumberInstance(Locale.US).parse(tvPrice.getText().toString().trim())));
            mDish.setSale(!cbState.isChecked());
            mPresenter.updateDish(mDish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi chạy màn hình lựa chọn đơn vị cho món ăn
     * Created at 27/03/2019
     */
    private void selectUnit() {
        try {
            Intent intent = new Intent();
            intent.setClass(this, SelectUnitActivity.class);
            intent.putExtra(EXTRA_UNIT_ID, mDish.getUnitId());
            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị dialog lựa chọn background cho món ăn
     * Created at 27/03/2019
     */
    private void showColorSelector() {
        try {
            ColorSelectorDialog f = ColorSelectorDialog.newInstance(mDish.getColorCode());
            f.setCallBack(this);
            getSupportFragmentManager().beginTransaction().add(f, COLOR_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị dialog lựa chọn icon cho món ăn
     * Created at 27/03/2019
     */
    private void showDishIconSelector() {
        try {
            DishIconSelectorDialog f = new DishIconSelectorDialog();
            f.setCallBack(this);
            getSupportFragmentManager().beginTransaction().add(f, DISH_ICON_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật màu cho món ăn
     * Created at 09/04/2019
     *
     * @param colorCode - mã màu món ăn
     */
    @Override
    public void onColorSelected(String colorCode) {
        try {
            if (colorCode != null) {
                mDish.setColorCode(colorCode);
                Drawable drawable = getResources().getDrawable(R.drawable.background_dish_icon);
                drawable.setColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.SRC);
                ivColor.setBackground(drawable);
                ivIcon.setBackground(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật icon cho món ăn
     * Created at 09/04/2019
     *
     * @param iconPath - đường dẫn icon của món ăn
     */
    @Override
    public void onDishIconSelected(String iconPath) {
        try {
            if (iconPath != null) {
                mDish.setIconPath(iconPath);
                ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(this, iconPath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xác nhận việc xóa món ăn
     * Created at 4/04/2019
     */
    @Override
    public void acceptDelete() {
        try {
            mPresenter.deleteDish(mDish.getDishId());
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
