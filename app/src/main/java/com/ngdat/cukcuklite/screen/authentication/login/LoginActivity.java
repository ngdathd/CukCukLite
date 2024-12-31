package com.ngdat.cukcuklite.screen.authentication.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.Collections;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.prefs.SharedPrefersManager;
import com.ngdat.cukcuklite.screen.authentication.register.RegisterActivity;
import com.ngdat.cukcuklite.screen.chooserestauranttype.ChooseRestaurantTypeActivity;
import com.ngdat.cukcuklite.screen.loginemail.LoginEmailActivity;
import com.ngdat.cukcuklite.screen.main.MainActivity;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.CommonsUtils;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình đăng nhập
 * Created at 03/04/2019
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginContract.IView {

    private static final String TAG = "LoginActivity";
    private ImageView ivBack;
    private LinearLayout llFacebookLogin;
    private LinearLayout llEmailPhoneLogin;
    private TextView tvRegister;
    private CallbackManager callbackManager;
    private LoginPresenter mPresenter;
    private Navigator mNavigator;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNavigator = new Navigator(this);
        mPresenter = new LoginPresenter(this);
        mPresenter.setView(this);
        initViews();
        initEvents();
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 04/04/2019
     */
    private void initEvents() {
        try {
            ivBack.setOnClickListener(this);
            llFacebookLogin.setOnClickListener(this);
            llEmailPhoneLogin.setOnClickListener(this);
            tvRegister.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 04/04/2019
     */
    private void initViews() {
        try {
            ivBack = (ImageView) findViewById(R.id.ivBack);
            llFacebookLogin = (LinearLayout) findViewById(R.id.llFacebookLogin);
            llEmailPhoneLogin = (LinearLayout) findViewById(R.id.llEmailPhoneLogin);
            tvRegister = findViewById(R.id.tvRegister);
            initFacebookLoginCallback();
            initProgressBar();
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
            mDialog.setMessage(getString(R.string.login_in_process));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo trình đăng nhập facebook
     * Created at 04/04/2019
     */
    private void initFacebookLoginCallback() {
        try {
            callbackManager = CallbackManager.Factory.create();
            LoginManager loginManager = LoginManager.getInstance();
            //đăng kí callback
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    mPresenter.loginWithFacebook(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    //nếu người dùng dừng việc đăng nhập thì không làm gì cả
                }

                @Override
                public void onError(FacebookException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created at 04/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.llFacebookLogin:
                try {
                    if (CommonsUtils.isNetworkAvailable(this)) {
                        loginWithFacebook();
                    } else {
                        mNavigator.showToastOnTopScreen(R.string.device_currently_has_no_network_available);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.llEmailPhoneLogin:
                gotoLoginEmailScreen();
                break;
            case R.id.tvRegister:
                gotoRegisterScreen();
                break;
        }
    }


    /**
     * Phương thức đăng nhập với facebook
     * Created at 04/04/2019
     */
    private void loginWithFacebook() {
        LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    private void gotoLoginEmailScreen() {
        mNavigator.startActivity(LoginEmailActivity.class, Navigator.ActivityTransition.NONE);
    }

    private void gotoRegisterScreen() {
        mNavigator.startActivity(RegisterActivity.class, Navigator.ActivityTransition.NONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created at 04/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToastOnTopScreen(message);
    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 04/04/2019
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
     * Created at 04/04/2019
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
     * Phương thức xử lý khi đăng nhập thành công
     * Created at 04/04/2019
     */
    @Override
    public void loginSuccess() {
        try {
            //check user has data before
            mPresenter.checkUserHasDataBefore();
            goToChooseRestaurentType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goToChooseRestaurentType() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (SharedPrefersManager.getInstance(this).getIsAlreadyHasData()) {
                intent.setClass(this, MainActivity.class);
            } else {
                intent.setClass(this, ChooseRestaurantTypeActivity.class);
            }
            intent.putExtra(AppConstants.EXTRA_LOGIN_SUCCESS, true);
            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi chạy màn hình chính ứng dụng
     * Created at 25/04/2019
     */
    @Override
    public void goToMainScreen() {
        mNavigator.startActivity(MainActivity.class);
    }
}
