package com.ngdat.cukcuklite.screen.selectunit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.data.models.Unit;
import com.ngdat.cukcuklite.screen.adddish.AddDishActivity;
import com.ngdat.cukcuklite.screen.dialogs.delete.ConfirmDeleteDialog;
import com.ngdat.cukcuklite.screen.dialogs.unit.ConfirmAddEditUnitDialog;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình chọn đơn vị
 * Created at 27/03/2019
 */
public class SelectUnitActivity extends AppCompatActivity implements IUnitContract.IView,
        View.OnClickListener, SelectUnitAdapter.IEditUnitListener, ConfirmAddEditUnitDialog.IUnitAddEditCallBack,
        ConfirmDeleteDialog.IConfirmDeleteCallBack, SelectUnitAdapter.ILongClickUnitListener {

    public static final String ACTION_UNIT_SELECTED = "ACTION_UNIT_SELECTED";
    public static final String EXTRA_UNIT_SELECTED = "EXTRA_UNIT_SELECTED";
    private static final String ADD_EDIT_DIALOG = "ADD_EDIT_DIALOG";
    private static final String DELETE_DIALOG = "DELETE_DIALOG";

    private ImageButton btnBack, btnAdd;
    private Button btnDone;
    private RecyclerView rvUnit;
    private SelectUnitAdapter mAdapter;
    private SelectUnitPresenter mPresenter;
    private Navigator mNavigator;
    private ConfirmAddEditUnitDialog mAddEditUnitDialog;
    private Unit mUnit;
    private int mUnitPositionRemove;
    private List<Unit> mUnits;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_unit);
        mNavigator = new Navigator(this);
        mPresenter = new SelectUnitPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        mPresenter.onStart(getIntent().getStringExtra(AddDishActivity.EXTRA_UNIT_ID));

    }

    /**
     * Phương thức gắn các sự kiện cho view
     * Created at 27/03/2019
     */
    private void initEvents() {
        try {
            btnDone.setOnClickListener(this);
            btnBack.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức khởi tạo cho view
     * Created at 27/03/2019
     */
    private void initViews() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnDone = (Button) findViewById(R.id.btnDone);
        rvUnit = (RecyclerView) findViewById(R.id.rvUnit);
        mUnits = new ArrayList<>();
        mAdapter = new SelectUnitAdapter(this, mUnits);
        mAdapter.setEditUnitListener(this);
        mAdapter.setItemLongClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvUnit.setLayoutManager(layoutManager);
        rvUnit.setAdapter(mAdapter);
    }

    /**
     * Phương thức hiển thị danh sách đơn vị món ăn lên màn hình
     * Created at 27/03/2019
     *
     * @param allUnit            - danh sánh các đơn vị
     * @param lastSelectPosition - vị trí đơn vị đã được chọn trước đó
     */
    @Override
    public void showUnit(List<Unit> allUnit, int lastSelectPosition) {
        mUnits = allUnit;
        mAdapter.setLastSelectPosition(lastSelectPosition);
        mAdapter.setListData(mUnits);
    }

    /**
     * Thông báo khi người dùng không nhập tên đơn vị
     * Created at 27/03/2019
     */
    @Override
    public void unitNameEmpty() {
        mNavigator.showToastOnTopScreen(R.string.unit_name_not_allow_empty);
    }

    /**
     * Thông báo cho người dùng đã thêm đơn vị thành công và quay về màn hình thực đơn
     * Created at 27/03/2019
     */
    @Override
    public void addUnitSuccess(String unitName) {
        unitSelected(unitName);
    }

    /**
     * Phương thức đóng dialog khi thêm hoặc sửa đơn vị thành công
     * Created at 27/03/2019
     *
     * @param unitName - tên đơn vị
     */
    private void unitSelected(String unitName) {
        if (mAddEditUnitDialog != null && mAddEditUnitDialog.isAdded()) {
            mAddEditUnitDialog.dismiss();
        }
        mNavigator.showToast(R.string.add_unit_success);
        setUnit(unitName);
    }

    @Override
    public void updateUnitSuccess(String unitName) {
        unitSelected(unitName);
    }

    /**
     * Thông báo cho người dùng đã xóa món ăn thành công và quay về màn hình thực đơn
     * Created at 27/03/2019
     */
    @Override
    public void deleteUnitSuccess() {
        try {
            mNavigator.showToastOnTopScreen(R.string.delete_unit_success);
            int size = mUnits.size();
            String unitId = mUnit.getUnitId();
            for (int i = 0; i < size; i++) {
                if (mUnits.get(i).getUnitId().equals(unitId)) {
                    mUnits.remove(i);
                    break;
                }
            }
            mAdapter.notifyItemRemoved(mUnitPositionRemove);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Thông báo cho người dùng đã thêm đơn vị thất bại
     * Created at 27/03/2019
     */
    @Override
    public void addUnitFailed(int error) {
        mNavigator.showToastOnTopScreen(error);
    }


    /**
     * Phương thức nhận thông điệp
     * Created at 27/03/2019
     *
     * @param message - thông điệp
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToastOnTopScreen(message);
    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 10/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created at 10/04/2019
     */
    @Override
    public void hideLoading() {

    }

    /**
     * Phương thức hiển thị dialog sửa đơn vị
     * Created at 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void onEditUnitItem(Unit unit) {
        showDialogEditAddUnit(unit);
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 10/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                try {
                    setUnit(mAdapter.getUnitId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnAdd:
                try {
                    showDialogEditAddUnit(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Phương thức lựa chọn đơn vị và trở về màn hình thêm món ăn
     * Created at 27/03/2019
     */
    private void setUnit(String unitId) {
        try {
            Intent intent = new Intent(ACTION_UNIT_SELECTED);
            intent.putExtra(EXTRA_UNIT_SELECTED, unitId);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị dialog thêm hoặc sửa đơn vị
     * Created at 27/03/2019
     *
     * @param unit - đơn vị tính(có thể null). Nếu null sẽ hiển thị dialog thêm đơn vị
     *             nếu khác null sẽ hiển thị dialog sửa đơn vị
     */
    private void showDialogEditAddUnit(@Nullable Unit unit) {
        try {
            mAddEditUnitDialog = ConfirmAddEditUnitDialog.newInstance(unit);
            mAddEditUnitDialog.setCallBack(this);
            getSupportFragmentManager().beginTransaction().add(mAddEditUnitDialog, ADD_EDIT_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị popup menu xác nhận xóa đơn vị
     * Created at 28/03/2019
     *
     * @param unit - đơn vị
     * @param view - view hiển thị popup
     */
    @Override
    public void onLongClickUnit(final Unit unit, int position, View view) {
        try {
            if (unit != null && view != null) {
                mUnit = unit;
                mUnitPositionRemove = position;
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                Point point = new Point();
                point.x = location[0];
                point.y = location[1];
                showStatusPopup(unit.getUnitName(), point);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị popup menu
     * Created at 10/04/2019
     *
     * @param p - vị trí của item được click để hiển thị popup
     */
    private void showStatusPopup(final String unitName, Point p) {
        try {
            final PopupWindow deleteUnitPopup = new PopupWindow(this);
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.item_popup_unit, null);
            LinearLayout linearLayout = layout.findViewById(R.id.lnDeleteUnit);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //hiển thị dialog xác nhận việc xóa đơn vị
                    showConfirmDeleteUnitDialog(unitName, SelectUnitActivity.this);
                    deleteUnitPopup.dismiss();
                }
            });
            deleteUnitPopup.setContentView(layout);
            deleteUnitPopup.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            deleteUnitPopup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            deleteUnitPopup.setFocusable(true);
            //khởi tạo vị trí hiển thị của popup so vói view kích hoạt
            int OFFSET_X = getResources().getDimensionPixelSize(R.dimen.action_width);
            int OFFSET_Y = getResources().getDimensionPixelSize(R.dimen.item_unit_height);
            //xóa background mặc định của popup window
            deleteUnitPopup.setBackgroundDrawable(new BitmapDrawable());
            // Hiển thị popup ở vị trí mong muốn
            deleteUnitPopup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức hiển thị dialog xác nhận việc xóa đơn vị
     * Created at 27/03/2019
     */
    private void showConfirmDeleteUnitDialog(String unitName, ConfirmDeleteDialog.IConfirmDeleteCallBack callBack) {
        try {
            ConfirmDeleteDialog f = ConfirmDeleteDialog.newInstance(unitName, IDBUtils.ITableUnitUtils.COLUMN_UNIT_NAME);
            f.setCallBack(callBack);
            getSupportFragmentManager().beginTransaction().add(f, DELETE_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật đơn vị
     * Created at 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void onUpdateUnit(Unit unit) {
        try {
            if (unit != null) {
                mPresenter.updateUnit(unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm đơn vị
     * Created at 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void onAddUnit(Unit unit) {
        try {
            if (unit != null) {
                mPresenter.addUnit(unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xóa đơn vị
     * Created at 28/03/2019
     */
    @Override
    public void acceptDelete() {
        try {
            mPresenter.deleteUnit(mUnit.getUnitId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
