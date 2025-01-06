package com.ngdat.cukcuklite.screen.main;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;

/**
 * MVP interface cho màn hình chính
 * Created at 18/04/2019
 */
public interface IMainContract {
    interface IView extends IBaseView {
        void goToLoginScreen();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void clearData();
    }
}
