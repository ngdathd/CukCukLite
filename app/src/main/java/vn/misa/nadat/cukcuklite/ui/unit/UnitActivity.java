package vn.misa.nadat.cukcuklite.ui.unit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.UnitAdapter;
import vn.misa.nadat.cukcuklite.dialogs.AddEditUnitDialog;
import vn.misa.nadat.cukcuklite.items.ItemUnit;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Màn hình đơn vị món ăn.
 *
 * @created_by nadat on 11/04/2019
 */
public class UnitActivity extends AppCompatActivity
        implements View.OnClickListener,
        IUnitContract.IView,
        UnitAdapter.OnClickItemUnitListener {
    private IUnitContract.IPresenter mUnitPresenter;
    private UnitAdapter mUnitAdapter;
    private RecyclerView rcvUnitName;
    private String mItemUnitNameChecked;

    /**
     * Khởi tạo UnitActivity.
     *
     * @param savedInstanceState: trạng thái của Activity
     * @created_by nadat on 11/04/2019
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_unit);

            Intent intent = getIntent();
            mItemUnitNameChecked = intent.getStringExtra(Constant.ITEM_UNIT_NAME);

            rcvUnitName = findViewById(R.id.rcvUnitName);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rcvUnitName.setLayoutManager(linearLayoutManager);

            AppCompatImageButton imgBtnBack = findViewById(R.id.imgBtnBack);
            imgBtnBack.setOnClickListener(this);
            AppCompatImageButton btnAdd = findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(this);
            Button btnDoneUnit = findViewById(R.id.btnDoneUnit);
            btnDoneUnit.setOnClickListener(this);

            mUnitAdapter = new UnitAdapter(this, this);
            mUnitAdapter.setItemUnitNameFirstChoose(mItemUnitNameChecked);

            mUnitPresenter = new UnitPresenter(this);
            mUnitPresenter.getItemUnits();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sự kiện nhấn nút Back của thiết bị.
     *
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void onBackPressed() {
        try {
            setResult(Activity.RESULT_CANCELED);
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bắt sự kiện click vào các view trong UnitActivity.
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
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                    break;
                }
                case R.id.btnAdd: {
                    new AddEditUnitDialog(this, new AddEditUnitDialog.OnClickAcceptDialogAddEditUnitListener() {
                        @Override
                        public void onClickAcceptDialogAddEditUnit(String newNameUnit) {
                            mUnitPresenter.saveItemUnit(newNameUnit);
                        }
                    }).show();
                    break;
                }
                case R.id.btnDoneUnit: {
                    ItemUnit itemUnit = mUnitAdapter.getItemUnitIsCheck();
                    if (itemUnit != null) {
                        Intent data = new Intent();
                        data.putExtra(Constant.ITEM_UNIT_ID, itemUnit.getItemUnitID());
                        data.putExtra(Constant.ITEM_UNIT_NAME, itemUnit.getItemUnitName());
                        setResult(Activity.RESULT_OK, data);
                    }
                    finish();
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
     * Sửa tên đơn vị.
     *
     * @param itemUnitId:  id ItemUnit cần sửa
     * @param newNameUnit: tên đơn vị mới
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void onClickEditItemUnit(int itemUnitId, String newNameUnit) {
        try {
            mUnitPresenter.updateItemUnit(itemUnitId, newNameUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa đơn vị.
     *
     * @param itemUnit: ItemUnit cần xóa
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void onClickDeleteItemUnit(ItemUnit itemUnit) {
        if (itemUnit == null) {
            return;
        }
        try {
            mUnitPresenter.checkItemUnitExist(itemUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup dữ liệu cho mUnitAdapter và hiển thị tất cả ItemUnit.
     *
     * @param itemUnits: danh sách ItemUnit có trong DB
     * @created_by nadat on 11/04/2019
     */
    @Override
    public void showItemUnits(List<ItemUnit> itemUnits) {
        if (itemUnits == null) {
            Toast.makeText(this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
        }
        try {
            mUnitAdapter.setItemUnits(itemUnits, mItemUnitNameChecked);
            rcvUnitName.setAdapter(mUnitAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm ItemUnit vào  mUnitAdapter.
     *
     * @param itemUnit: ItemUnit đã thêm vào DB
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void showItemUnitInserted(ItemUnit itemUnit) {
        if (itemUnit == null) {
            return;
        }
        try {
            mUnitAdapter.addItemUnit(itemUnit);
            Intent data = new Intent();
            data.putExtra(Constant.ITEM_UNIT_ID, itemUnit.getItemUnitID());
            data.putExtra(Constant.ITEM_UNIT_NAME, itemUnit.getItemUnitName());
            setResult(Activity.RESULT_OK, data);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị đơn vị sau khi cập nhật thành công lên AddEditDishActivity.
     *
     * @param id:          id của ItemUnit
     * @param newNameUnit: tên mới của đơn vị
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void showItemUnitUpdated(int id, String newNameUnit) {
        try {
            Intent data = new Intent();
            data.putExtra(Constant.ITEM_UNIT_ID, id);
            data.putExtra(Constant.ITEM_UNIT_NAME, newNameUnit);
            setResult(Activity.RESULT_OK, data);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa ItemUnit trong mUnitAdapter.
     *
     * @param itemUnit: ItemUnit được xóa
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void hideItemUnitDeleted(ItemUnit itemUnit) {
        mUnitAdapter.deleteItemUnit(itemUnit);
    }

    /**
     * Xóa ItemUnit mà không là đơn vị của ItemDish nào.
     *
     * @param itemUnit: ItemUnit được xóa
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void showItemUnitCanDeleted(ItemUnit itemUnit) {
        if (itemUnit == null) {
            return;
        }
        try {
            mUnitPresenter.deleteItemUnit(itemUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông báo ItemUnit đã được sử dụng.
     *
     * @param itemUnit: ItemUnit đã được sử dụng
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void showNotificationUnitExist(ItemUnit itemUnit) {
        if (itemUnit == null) {
            return;
        }
        try {
            Toast toast = Toast.makeText(this,
                    "Đơn vị " + itemUnit.getItemUnitName() + " đang được sử dụng!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện thông báo không có dữ liệu.
     *
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void showNotificationItemsEmpty() {
        try {
            Toast.makeText(this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện thông báo unitName đã tồn tại.
     *
     * @param unitName: tên đơn vị đã tồn tại
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void showNotificationDuplicate(String unitName) {
        try {
            Toast toast = Toast.makeText(this, "Đơn vị tính <" + unitName + "> đã tồn tại", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện thông báo đã có lỗi.
     *
     * @created_by nadat on 12/04/2019
     */
    @Override
    public void showNotificationError() {
        try {
            Toast.makeText(this, "Đã có lỗi mời bạn thử lại!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
