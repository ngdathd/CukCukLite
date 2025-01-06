package com.ngdat.cukcuklite.screen.chooserestauranttype;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.prefs.SharedPrefersManager;
import com.ngdat.cukcuklite.data.models.RestaurantType;
import com.ngdat.cukcuklite.screen.choosedishdefault.ChooseDishDefaultActivity;
import com.ngdat.cukcuklite.screen.main.MainActivity;
import com.ngdat.cukcuklite.utils.AppConstants;

/**
 * Màn hình lựa chọn loại nhà hàng
 * Created at 05/04/2019
 */
public class ChooseRestaurantTypeActivity extends AppCompatActivity implements IChooseRestaurantTypeContract.IView, View.OnClickListener {

    private static final String TAG = "ChooseRestaurantTypeAct";
    private ImageButton btnBack;
    private TextView tvContinue;
    private ChooseRestaurantTypePresenter mPresenter;
    private Button btnContinue;
    private RecyclerView rvRestaurantType;
    private RestaurantTypeAdapter mRestaurantTypeAdapter;
    private ProgressDialog mDialog;
    private SharedPrefersManager mSharedPrefersManager;
    private int mRestaurantTypeDifferent;
    private List<RestaurantType> mRestaurantTypeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_restaurant_type);
        mSharedPrefersManager = SharedPrefersManager.getInstance(this);
        mPresenter = new ChooseRestaurantTypePresenter(this);
        mPresenter.setView(this);
        initViews();
        initEvents();
        if (mRestaurantTypeList == null) {
            mPresenter.onStart();
            Log.d(TAG, "onCreate: ");
        }
    }


    /**
     * Phương thức gắn sự kiện cho view
     * Created at 03/04/2019
     */
    private void initEvents() {
        try {
            btnBack.setOnClickListener(this);
            btnContinue.setOnClickListener(this);
            tvContinue.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 03/04/2019
     */
    private void initViews() {
        try {
            btnBack = (ImageButton) findViewById(R.id.btnBack);
            tvContinue = (TextView) findViewById(R.id.tvContinue);
            btnContinue = (Button) findViewById(R.id.btnContinue);
            rvRestaurantType = (RecyclerView) findViewById(R.id.rvRestaurantType);

            rvRestaurantType.setLayoutManager(new LinearLayoutManager(this));
            mRestaurantTypeAdapter = new RestaurantTypeAdapter(this);
            rvRestaurantType.setAdapter(mRestaurantTypeAdapter);
            initProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức khởi tạo progressbar
     * Created at 05/04/2019
     */
    private void initProgressBar() {
        try {
            mDialog = new ProgressDialog(this) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                }
            };
            mDialog.setMessage(getString(R.string.init_restaurant_type));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị danh sách quán ăn/nhà hàng
     * Created at 08/04/2019
     *
     * @param restaurantTypeList - danh sách quán ăn/nhà hàng
     */
    @Override
    public void showListRestaurantType(List<RestaurantType> restaurantTypeList) {
        try {
            if (restaurantTypeList != null) {
                mRestaurantTypeList = restaurantTypeList;
                mRestaurantTypeDifferent = restaurantTypeList.get(restaurantTypeList.size() - 1).getRestaurantTypeId();
                mRestaurantTypeAdapter.setListData(restaurantTypeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi chạy màn hình chọn món ăn
     * Created at 09/04/2019
     */
    @Override
    public void showDishDefaultActivity() {
        try {
            Intent intent = new Intent();
            intent.setClass(this, ChooseDishDefaultActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created at 08/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 05/04/2019
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
     * Created at 05/04/2019
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
     * Created at 05/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnContinue:
            case R.id.tvContinue:
                try {
                    //bắt đầu main activity
                    RestaurantType restaurantType = mRestaurantTypeAdapter.getRestaurantTypeIdSelected();
                    //khởi động màn hình chọn món ăn hoặc main;
                    startMainApp(restaurantType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Phương thức khởi động màn hình chính hoặc màn hình chọn/chỉnh sửa món ăn cho quán ăn
     * Created at 08/04/2019
     *
     * @param restaurantType - loại quán ăn
     */
    private void startMainApp(RestaurantType restaurantType) {
        try {
            if (restaurantType != null) {
                //chèn toàn bộ Đơn vị vào cơ sở dữ liệu
                mPresenter.insertAllUnit(restaurantType.getUnits());
                //nếu mà id của nhà hàng trùng với loại nhà hàng đặc biệt thì sẽ vào luôn màn hình chính ừng dụng
                if (restaurantType.getRestaurantTypeId() == mRestaurantTypeDifferent) {
                    mSharedPrefersManager.setAlreadyHasData(true);
                    //hiển thị màn hình chính
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    //ngược lại thì sẽ insert toàn bộ món ăn vào cơ sở dữ liệu -> thành công sẽ điều
                    //hướng tới màn hình chọn món ăn
                    mPresenter.insertAllDish(restaurantType.getDishes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
