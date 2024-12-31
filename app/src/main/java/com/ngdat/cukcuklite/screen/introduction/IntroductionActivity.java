package com.ngdat.cukcuklite.screen.introduction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.screen.authentication.login.LoginActivity;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình giới thiệu ứng dụng
 * Created at 01/04/2019
 */
public class IntroductionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSkip, tvNext;
    private ImageView ivNext;
    private ViewPager vpIntroduction;
    private LinearLayout llDots;
    private IntroductionPagerAdapter mIntroductionPagerAdapter;
    private int mDotCount;
    private ImageView[] ivDots;
    private int mCurrentPage;
    private Navigator mNavigator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        mNavigator = new Navigator(this);
        try {
            initViews();
            initEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức gắn các sự kiện cho view
     * Created at 03/04/2019
     */
    private void initEvents() {
        try {
            tvSkip.setOnClickListener(this);
            tvNext.setOnClickListener(this);
            ivNext.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tham chiếu, khởi tạo các View
     * Created at 03/04/2019
     */
    private void initViews() {
        try {
            tvSkip = (TextView) findViewById(R.id.tvSkip);
            tvNext = (TextView) findViewById(R.id.tvNext);
            ivNext = (ImageView) findViewById(R.id.ivNext);
            vpIntroduction = (ViewPager) findViewById(R.id.vpIntroduction);
            llDots = (LinearLayout) findViewById(R.id.llDots);
            setupViewPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo cho viewpager
     * Created at 03/04/2019
     */
    private void setupViewPager() {
        try {
            mCurrentPage = PageType.FIRST;
            mIntroductionPagerAdapter = new IntroductionPagerAdapter(getSupportFragmentManager());
            vpIntroduction.setAdapter(mIntroductionPagerAdapter);
            vpIntroduction.setCurrentItem(mCurrentPage);
            setUpSliderDot();
            vpIntroduction.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    mCurrentPage = position;
                    updateSliderDot(position);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức cập nhật chấm trượt cho viewpager
     * Created at 03/04/2019
     *
     * @param position - index đang được chọn trên viewpager
     */
    private void updateSliderDot(int position) {
        for (int i = 0; i < mDotCount; i++) {
            ivDots[i].setImageResource(R.drawable.view_dot_non_select);
        }
        ivDots[position].setImageResource(R.drawable.view_dot_select);
    }

    /**
     * Phương thức khởi tạo chấm trượt cho viewpager
     * Created at 03/04/2019
     */
    private void setUpSliderDot() {
        try {
            mDotCount = mIntroductionPagerAdapter.getCount();
            ivDots = new ImageView[mDotCount];
            for (int i = 0; i < mDotCount; i++) {
                ivDots[i] = new ImageView(this);
                ivDots[i].setImageResource(R.drawable.view_dot_non_select);
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 0, 8, 0);
                llDots.addView(ivDots[i], params);
            }
            ivDots[PageType.FIRST].setImageResource(R.drawable.view_dot_select);
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
                case R.id.tvSkip:
                    gotoLoginScreen();
                    break;
                case R.id.tvNext: //sự kiện khi click vào Textview và icon là 1 nên không break
                case R.id.ivNext:
                    try {
                        if (mCurrentPage == PageType.FIFTH) {
                            gotoLoginScreen();
                        } else {
                            vpIntroduction.setCurrentItem(++mCurrentPage);
                        }
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
        try {
            mNavigator.startActivity(LoginActivity.class, Navigator.ActivityTransition.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
