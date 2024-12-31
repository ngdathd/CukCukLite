package com.ngdat.cukcuklite.screen.choosedishdefault;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.base.listeners.IOnItemClickListener;
import com.ngdat.cukcuklite.data.models.Dish;
import com.ngdat.cukcuklite.data.local.prefs.SharedPrefersManager;
import com.ngdat.cukcuklite.screen.adddish.AddDishActivity;
import com.ngdat.cukcuklite.screen.main.MainActivity;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình lựa chọn các món ăn mặc định cho quán ăn
 * Created at 08/04/2019
 */
public class ChooseDishDefaultActivity extends AppCompatActivity implements View.OnClickListener, IOnItemClickListener<Dish>, IChooseDishDefaultContract.IView {

    private ImageButton btnBack;
    private TextView tvDone;
    private Button btnDone;
    private DishAdapter mDishAdapter;
    private ChooseDishDefaultPresenter mPresenter;
    private Navigator mNavigator;
    private SharedPrefersManager mSharedPrefersManager;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dish_default);
        mNavigator = new Navigator(this);
        mSharedPrefersManager = SharedPrefersManager.getInstance(this);
        mPresenter = new ChooseDishDefaultPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //load lại dữ liệu khi màn hình quay lại
        mPresenter.onStart();
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 09/04/2019
     */
    private void initEvents() {
        try {
            tvDone.setOnClickListener(this);
            btnDone.setOnClickListener(this);
            btnBack.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 09/04/2019
     */
    private void initViews() {
        try {
            btnBack = (ImageButton) findViewById(R.id.btnBack);
            tvDone = (TextView) findViewById(R.id.tvDone);
            btnDone = (Button) findViewById(R.id.btnDone);
            RecyclerView rvDish = (RecyclerView) findViewById(R.id.rvDish);
            rvDish.setLayoutManager(new LinearLayoutManager(this));
            mDishAdapter = new DishAdapter(this);
            mDishAdapter.setItemClickListener(this);
            rvDish.setAdapter(mDishAdapter);
            initProgressBar();
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
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnDone:
            case R.id.tvDone:
                showMainScreen();
                break;
            default:
                break;
        }
    }

    /**
     * Phương thức xử lý khi item món ăn được tương tác, sẽ chuyển hướng tới màn hình chỉnh sửa món ăn
     * Created at 09/04/2019
     *
     * @param data - là món ăn được truyền sang màn hình sửa món ăn
     */
    @Override
    public void onItemClick(Dish data) {
        try {
            Intent intent = new Intent();
            intent.setClass(this, AddDishActivity.class);
            intent.putExtra(AppConstants.EXTRA_DISH, data);
            startActivity(intent);
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
     * Phương thức hiển thị màn hình chính của ứng dụng sau khi đã khởi tạo các món ăn thành công
     * Created at 09/04/2019
     */
    public void showMainScreen() {
        try {
            mSharedPrefersManager.setAlreadyHasData(true);
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(this, MainActivity.class);
            mNavigator.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Hiển thị danh sách món ăn của cửa hàng
     * Created at 09/04/2019
     *
     * @param dishes - danh sách món ăn của cửa hàng
     */
    @Override
    public void showDish(List<Dish> dishes) {
        try {
            mDishAdapter.setListData(dishes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
