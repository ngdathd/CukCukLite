package com.ngdat.cukcuklite.screen.authentication.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.remote.service.EmailLoginServices;
import com.ngdat.cukcuklite.data.remote.service.EmailRegisterListener;
import com.ngdat.cukcuklite.data.remote.service.EmailRegisterServices;
import com.ngdat.cukcuklite.screen.authentication.login.LoginActivity;
import com.ngdat.cukcuklite.utils.CommonsUtils;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình đăng ký
 * Created at 03/04/2019
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private ImageView ivBack;
    private TextView tvLogin;
    private AppCompatButton mBtnRegister;
    private TextInputEditText mEdtEmail;
    private TextInputEditText mEdtPass;
    private TextInputEditText mEdtConfirmPass;

    private Navigator mNavigator;
    private ProgressDialog mDialog;

    private String email;
    private String passWord;
    private EmailRegisterServices emailRegisterServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNavigator = new Navigator(this);
        emailRegisterServices = new EmailRegisterServices(this);
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
            tvLogin = (TextView) findViewById(R.id.tvLogin);
            mBtnRegister = (AppCompatButton) findViewById(R.id.btn_email_register);
            mEdtEmail = (TextInputEditText) findViewById(R.id.edt_email_register);
            mEdtPass = (TextInputEditText) findViewById(R.id.edt_pass_register);
            mEdtConfirmPass = (TextInputEditText) findViewById(R.id.edt_pass_register1);
            initProgressBar();
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
            tvLogin.setOnClickListener(this);
            mBtnRegister.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo progressbar
     * Created at 04/04/2019
     */
    private void initProgressBar() {
        try {
            mDialog = new ProgressDialog(this) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                }
            };
            mDialog.setMessage(getString(R.string.register_in_process));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
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
            case R.id.btn_email_register:
                try {
                    if (CommonsUtils.isNetworkAvailable(this)) {
                        registerWithEmail();
                    } else {
                        mNavigator.showToastOnTopScreen(R.string.device_currently_has_no_network_available);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvLogin:
                gotoLoginScreen();
                break;
        }
    }

    private void registerWithEmail() {
        if (checkInputData()) {
            mDialog.show();
            emailRegisterServices.registerAccount(email, passWord, new EmailRegisterListener() {
                @Override
                public void registerSuccess() {
                    mDialog.dismiss();
                    mEdtEmail.setText("");
                    mEdtPass.setText("");
                    mEdtConfirmPass.setText("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle(getResources().getString(R.string.notification));
                    builder.setMessage(getResources().getString(R.string.verifiation));
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.create().show();
                }

                @Override
                public void registerFailure(String message) {
                    mDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Phương thức khởi chạy màn hình đăng nhập
     * Created at 03/04/2019
     */
    private void gotoLoginScreen() {
        try {
            mNavigator.startActivity(LoginActivity.class, Navigator.ActivityTransition.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkInputData() {
        if (CommonsUtils.isNotEmpty(mEdtEmail) && CommonsUtils.isNotEmpty(mEdtPass) && CommonsUtils.isNotEmpty(mEdtConfirmPass)) {
            email = mEdtEmail.getText().toString().trim();
            passWord = mEdtPass.getText().toString().trim();
            if (!CommonsUtils.isEmailValid(email)) {
                mEdtEmail.requestFocus();
                mEdtEmail.setError(getResources().getString(R.string.email_error));
                return false;
            } else {
                if (passWord.length() < 6) {
                    mEdtPass.requestFocus();
                    mEdtPass.setError(getResources().getString(R.string.pass_error));
                    return false;
                } else {
                    if (!mEdtConfirmPass.getText().toString().trim().equals(passWord)) {
                        mEdtConfirmPass.requestFocus();
                        mEdtConfirmPass.setError(getResources().getString(R.string.passconfirm_error));
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}