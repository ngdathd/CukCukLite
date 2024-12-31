package com.ngdat.cukcuklite.screen.introduction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ngdat.cukcuklite.screen.introduction.introfragment.IntroFifthFragment;
import com.ngdat.cukcuklite.screen.introduction.introfragment.IntroFirstFragment;
import com.ngdat.cukcuklite.screen.introduction.introfragment.IntroFourthFragment;
import com.ngdat.cukcuklite.screen.introduction.introfragment.IntroSecondFragment;
import com.ngdat.cukcuklite.screen.introduction.introfragment.IntroThirdFragment;

/**
 * Adapter cho view pager giới thiệu ứng dụng
 * Created at 03/04/2019
 */
public class IntroductionPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_COUNT = 5;

    /**
     * Phương thức khởi tạo
     * Created at 03/04/2019
     *
     * @param fm - fragment được truyền vào cho adapter hiển thị
     */
    public IntroductionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Phương thức lấy/khởi tạo fragment theo chỉ số của adapter
     * Created at 03/04/2019
     *
     * @param i - chỉ số khi viewpager được chọn
     * @return - fragment tương ứng với chỉ số
     */
    @Override
    public Fragment getItem(int i) {
        try {
            switch (i) {
                case PageType.FIRST:
                    return new IntroFirstFragment();
                case PageType.SECOND:
                    return new IntroSecondFragment();
                case PageType.THIRD:
                    return new IntroThirdFragment();
                case PageType.FOURTH:
                    return new IntroFourthFragment();
                case PageType.FIFTH:
                    return new IntroFifthFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức trả về size của viewpager
     * Created at 03/04/2019
     *
     * @return - size viewpager
     */
    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
