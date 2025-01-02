package com.ngdat.cukcuklite.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.prefs.SharedPrefersManager;
import com.ngdat.cukcuklite.screen.authentication.login.LoginActivity;
import com.ngdat.cukcuklite.screen.chooserestauranttype.ChooseRestaurantTypeActivity;
import com.ngdat.cukcuklite.screen.dishorder.DishOrderActivity;
import com.ngdat.cukcuklite.screen.introduction.IntroductionActivity;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình chờ
 * Created at 03/04/2019
 */
public class SplashActivity extends AppCompatActivity implements ISplashContract.IView {

    private static final String TAG = "SplashActivity";
    private Navigator mNavigator;
    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(this);
        mPresenter.setView(this);
        mNavigator = new Navigator(this);
        startMainScreen();
    }

    /**
     * Phương thức hiển thị màn hình chính của ứng dụng sau khi màn hình splash kết thúc
     * Created at 03/04/2019
     */
    private void startMainScreen() {
        try {
            final boolean isLoginBefore = SharedPrefersManager.getInstance(this).getIsLoginSuccess();
            final boolean isAlreadyHasData = SharedPrefersManager.getInstance(this).getIsAlreadyHasData();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isLoginBefore) {
                        if (isAlreadyHasData) {
                            //kiểm tra user đã đăng nhập và đã chọn loại nhà hàng
                            //hiển thị màn hình chính của ứng dụng
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), DishOrderActivity.class);
                            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
                            finish();
                        } else {
                            //kiểm tra user đã đăng nhập và chưa chọn loại nhà hàng(chưa có dữ liệu gì cả)
                            //thì hiển thị màn hình chọn loại nhà hàng
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), ChooseRestaurantTypeActivity.class);
                            intent.putExtra(AppConstants.EXTRA_LOGIN_SUCCESS, true);
                            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
                            finish();
                        }
                    } else {
                        if (isAlreadyHasData) {
                            //kiểm tra user đã đăng nhập và đã chọn loại nhà hàng
                            //hiển thị đăng nhập
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), LoginActivity.class);
                            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
                            finish();
                        } else {
                            //kiểm tra user chưa đăng nhập và chưa chọn loại nhà hàng(chưa có dữ liệu gì cả)
                            //thì hiển thị màn hình giới thiệu
                            mNavigator.startActivity(IntroductionActivity.class, Navigator.ActivityTransition.NONE);
                            finish();
                        }
                    }
                }
            }, 1500);
            //
            mPresenter.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created at 15/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 15/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created at 15/04/2019
     */
    @Override
    public void hideLoading() {

    }
}
