package vn.misa.nadat.cukcuklite.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.ui.choosedish.ChooseDishActivity;
import vn.misa.nadat.cukcuklite.ui.chooserestaurant.ChooseRestaurantTypeActivity;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Màn hình chờ
 *
 * @created_by nadat on 24/04/2019
 */
public class SplashActivity extends AppCompatActivity {
    private int mIsFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mIsFirst = sharedPreferences.getInt(Constant.SHARED_PREFERENCES_FIRST, -1);
        startMainScreen();
    }

    /**
     * Hiển thị màn hình chính của ứng dụng sau khi màn hình splash kết thúc
     *
     * @created_by nadat on 24/04/2019
     */
    private void startMainScreen() {
        try {
            int SPLASH_DISPLAY_LENGTH = 1500;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mIsFirst != -1) {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, ChooseDishActivity.class));
                    } else {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, ChooseRestaurantTypeActivity.class));
                    }
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
