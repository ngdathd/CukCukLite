package com.ngdat.cukcuklite.base.animation;

import com.ngdat.cukcuklite.R;

public enum ScreenAnimation {

    OPEN_FULL(R.anim.enter_to_right, R.anim.exit_to_right,
            R.anim.enter_to_left, R.anim.exit_to_left),
    NONE(0, 0, 0, 0);

    private int mEnterToRight;
    private int mExitToRight;
    private int mEnterToLeft;
    private int mExitToLeft;

    ScreenAnimation(int mEnterToRight, int mExitToRight, int mEnterToLeft, int mExitToLeft) {
        this.mEnterToRight = mEnterToRight;
        this.mExitToRight = mExitToRight;
        this.mEnterToLeft = mEnterToLeft;
        this.mExitToLeft = mExitToLeft;
    }

    public int getmEnterToRight() {
        return mEnterToRight;
    }

    public int getmExitToRight() {
        return mExitToRight;
    }

    public int getmEnterToLeft() {
        return mEnterToLeft;
    }

    public int getmExitToLeft() {
        return mExitToLeft;
    }
}