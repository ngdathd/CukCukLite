package com.ngdat.cukcuklite.screen.authentication.login;

import com.facebook.AccessToken;

import com.ngdat.cukcuklite.base.IBasePresenter;
import com.ngdat.cukcuklite.base.IBaseView;

/**
 * MVP interface cho màn hình đăng nhập
 * Created at 9/04/2019
 */
public interface ILoginContract {
    interface IView extends IBaseView {
        void loginSuccess();

        void goToChooseRestaurentType();

        void goToMainScreen();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void loginWithFacebook(AccessToken accessToken);

        void getAllDataFromFireBase();

        void login(String emailPhone, String password);

        void checkUserHasDataBefore();
    }
}
