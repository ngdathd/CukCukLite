package com.ngdat.cukcuklite.screen.loginoption;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.screen.authentication.login.LoginActivity;
import com.ngdat.cukcuklite.screen.authentication.register.RegisterActivity;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình lựa chọn việc đăng nhập hoặc sử dụng ứng dụng ngay
 * Created at 03/04/2019
 */
public class LoginOptionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private LinearLayout llContinue;
    private TextView tvLogin;
    private Navigator mNavigator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);
        mNavigator = new Navigator(this);
        initViews();
        initEvents();
    }


    /**
     * Phương thức gắn các sự kiện cho view
     * Created at 03/04/2019
     */
    private void initEvents() {
        try {
            ivBack.setOnClickListener(this);
            llContinue.setOnClickListener(this);
            tvLogin.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo, tham chiết cho các View
     * Created at 03/04/2019
     */
    private void initViews() {
        try {
            ivBack = (ImageView) findViewById(R.id.ivBack);
            llContinue = (LinearLayout) findViewById(R.id.llContinue);
            tvLogin = (TextView) findViewById(R.id.tvLogin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 03/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ivBack:
                    try {
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.llContinue:
                    try {
                        gotoRegisterScreen();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tvLogin:
                    try {
                        gotoLoginScreen();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi chạy màn hình đăng nhập
     * Created at 03/04/2019
     */
    private void gotoLoginScreen() {
        mNavigator.startActivity(LoginActivity.class, Navigator.ActivityTransition.NONE);
    }

    /**
     * Phương thức khởi chạy màn hình đăng ký
     * Created at 03/04/2019
     */
    private void gotoRegisterScreen() {
        mNavigator.startActivity(RegisterActivity.class, Navigator.ActivityTransition.NONE);
    }
}
