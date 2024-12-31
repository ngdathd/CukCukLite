package com.ngdat.cukcuklite.screen.introduction;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;

/**
 * MVP interface cho màn hình giới thiệu ứng dụng
 * Created at 18/04/2019
 */
public interface IIntroductionContract {
    interface IView extends IBaseView {
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
