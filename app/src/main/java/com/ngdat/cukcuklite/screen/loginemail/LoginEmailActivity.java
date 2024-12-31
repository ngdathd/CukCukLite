package com.ngdat.cukcuklite.screen.loginemail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.screen.authentication.register.RegisterActivity;
import com.ngdat.cukcuklite.utils.CommonsUtils;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình đăng ký
 * Created at 03/04/2019
 */
public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginEmailActivity";
    private ImageView ivBack;
    private TextView tvForgotPass;
    private AppCompatButton mBtnLogin;
    private TextInputEditText mEdtEmail;
    private TextInputEditText mEdtPass;

    private Navigator mNavigator;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        mNavigator = new Navigator(this);
        initViews();
        initEvents();
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 04/04/2019
     */
    private void initViews() {
        try {
            ivBack = (ImageView) findViewById(R.id.ivBack);
            tvForgotPass = (TextView) findViewById(R.id.tv_forget_pass);
            mBtnLogin = (AppCompatButton) findViewById(R.id.btn_email_login);
            mEdtEmail = (TextInputEditText) findViewById(R.id.edt_email);
            mEdtPass = (TextInputEditText) findViewById(R.id.edt_pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 04/04/2019
     */
    private void initEvents() {
        try {
            ivBack.setOnClickListener(this);
            tvForgotPass.setOnClickListener(this);
            mBtnLogin.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btn_email_login:
                try {
                    if (CommonsUtils.isNetworkAvailable(this)) {
                        loginWithEmail();
                    } else {
                        mNavigator.showToastOnTopScreen(R.string.device_currently_has_no_network_available);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_forget_pass:
                gotoRegisterScreen();
                break;
        }
    }

    private void loginWithEmail() {
        Log.i(TAG, "onClick: btn_email_login");
    }

    /**
     * Phương thức khởi chạy màn hình đăng nhập
     * Created at 03/04/2019
     */
    private void gotoRegisterScreen() {
        try {
            mNavigator.startActivity(RegisterActivity.class, Navigator.ActivityTransition.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}