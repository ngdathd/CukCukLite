package com.ngdat.cukcuklite.screen.introduction;

import androidx.annotation.IntDef;

import static com.ngdat.cukcuklite.screen.introduction.PageType.FIFTH;
import static com.ngdat.cukcuklite.screen.introduction.PageType.FIRST;
import static com.ngdat.cukcuklite.screen.introduction.PageType.FOURTH;
import static com.ngdat.cukcuklite.screen.introduction.PageType.SECOND;
import static com.ngdat.cukcuklite.screen.introduction.PageType.THIRD;

/**
 * Type cho các màn hình giới thiệu
 * Created at 18/04/2019
 */
@IntDef({FIRST, SECOND, THIRD, FOURTH, FIFTH})
public @interface PageType {
    int FIRST = 0;
    int SECOND = 1;
    int THIRD = 2;
    int FOURTH = 3;
    int FIFTH = 4;
}
