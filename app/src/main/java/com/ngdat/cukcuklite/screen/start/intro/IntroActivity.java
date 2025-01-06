package com.ngdat.cukcuklite.screen.start.intro;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.ngdat.cukcuklite.R;

public class IntroActivity extends AppIntro {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStatusBar(true);
        showSeparator(false);
        setBarColor(getResources().getColor(R.color.colorBlueFooter));

        addSlide(SampleSlide.newInstance(R.layout.fragment_intro_first, 1));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro_second, 2));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro_third, 3));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro_fourth, 4));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro_fifth, 5));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}