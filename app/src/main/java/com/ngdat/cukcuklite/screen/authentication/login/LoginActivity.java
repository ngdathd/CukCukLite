package com.ngdat.cukcuklite.screen.authentication.login;

import static com.ngdat.cukcuklite.data.remote.firebase.firebaserealtime.FirebaseManager.USERS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.prefs.SharedPrefersManager;
import com.ngdat.cukcuklite.data.models.User;
import com.ngdat.cukcuklite.data.remote.service.EmailLoginListener;
import com.ngdat.cukcuklite.data.remote.service.EmailLoginServices;
import com.ngdat.cukcuklite.data.remote.service.FacebookLoginServices;
import com.ngdat.cukcuklite.screen.authentication.register.RegisterActivity;
import com.ngdat.cukcuklite.screen.chooserestauranttype.ChooseRestaurantTypeActivity;
import com.ngdat.cukcuklite.screen.main.MainActivity;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.CommonsUtils;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình đăng nhập
 * Created at 03/04/2019
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private ImageView ivBack;
    private TextInputEditText mEdtEmail;
    private TextInputEditText mEdtPass;
    private LinearLayout llFacebookLogin;
    private LinearLayout llEmailPhoneLogin;
    private TextView tvForgetPass;
    private TextView tvRegister;
    private LoginPresenter mPresenter;
    private Navigator mNavigator;
    private ProgressDialog mDialog;

    private EmailLoginServices emailLoginServices;
    private FacebookLoginServices facebookLoginServices;
    private CallbackManager callbackManager;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String email;
    private String passWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNavigator = new Navigator(this);

        initViews();
        initEvents();
        initAuth();
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 04/04/2019
     */
    private void initViews() {
        try {
            ivBack = (ImageView) findViewById(R.id.ivBack);
            mEdtEmail = (TextInputEditText) findViewById(R.id.edt_email);
            mEdtPass = (TextInputEditText) findViewById(R.id.edt_pass);
            llFacebookLogin = (LinearLayout) findViewById(R.id.llFacebookLogin);
            llEmailPhoneLogin = (LinearLayout) findViewById(R.id.llEmailPhoneLogin);
            tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
            tvRegister = (TextView) findViewById(R.id.tvRegister);
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
     * Phương thức gắn sự kiện cho view
     * Created at 04/04/2019
     */
    private void initEvents() {
        try {
            ivBack.setOnClickListener(this);
            llFacebookLogin.setOnClickListener(this);
            llEmailPhoneLogin.setOnClickListener(this);
            tvForgetPass.setOnClickListener(this);
            tvRegister.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAuth() {
        emailLoginServices = new EmailLoginServices();
        facebookLoginServices = new FacebookLoginServices();
        callbackManager = CallbackManager.Factory.create();
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (null != firebaseUser) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child(USERS)
                            .child(firebaseUser.getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    mDialog.dismiss();
                                    User user = dataSnapshot.getValue(User.class);
                                    if (user != null) {
                                        Log.i(TAG, "Dang nhap: " + user);
//                                        UserInstance.getInstance().setName(user.getName());
//                                        UserInstance.getInstance().setEmail(user.getEmail());
//                                        UserInstance.getInstance().setUid(user.getUid());
//                                        UserInstance.getInstance().setKey(firebaseUser.getUid());
//                                        LoginManager.getInstance().unregisterCallback(callbackManager);
//                                        isFinishLoadData = true;
//                                        if (isFinishAnimation) {
//                                            startActivity(new Intent(getBaseActivity(), MainActivity.class));
//                                            finish();
//                                        }
                                    } else {
                                        Log.i(TAG, "Dang xuat: ");
//                                        isFinishLoadData = true;
//                                        if (isFinishAnimation) {
//                                            LoginManager.getInstance().logOut();
//                                            FirebaseAuth.getInstance().signOut();
//                                            startActivity(new Intent(getBaseActivity(), StartAppActivity.class));
//                                            finish();
//                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mDialog.dismiss();
                                }
                            });
                }
            }
        };
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
            case R.id.tvForgetPass:
                mNavigator.startActivity(RegisterActivity.class, Navigator.ActivityTransition.NONE);
                break;
            case R.id.tvRegister:
                mNavigator.startActivity(RegisterActivity.class, Navigator.ActivityTransition.NONE);
                break;
        }
    }

    /**
     * Phương thức đăng nhập với facebook
     * Created at 04/04/2019
     */
    private void loginWithFacebook() {
        mDialog.show();
        facebookLoginServices.loginAccountFacebook(this, callbackManager);
    }

    /**
     * Phương thức đăng nhập với email
     * Created at 04/04/2019
     */
    private void loginWithEmail() {
        if (checkInputData()) {
            mDialog.show();
            emailLoginServices.loginAccountEmail(email, passWord, new EmailLoginListener() {
                @Override
                public void loginSuccess() {

                }

                @Override
                public void loginFailure(String message) {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkInputData() {
        if (CommonsUtils.isNotEmpty(mEdtEmail) && CommonsUtils.isNotEmpty(mEdtPass)) {
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
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
