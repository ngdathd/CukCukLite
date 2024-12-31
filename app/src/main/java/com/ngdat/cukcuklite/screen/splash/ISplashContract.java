package com.ngdat.cukcuklite.screen.splash;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;

public interface ISplashContract {
    interface IView extends IBaseView {
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
