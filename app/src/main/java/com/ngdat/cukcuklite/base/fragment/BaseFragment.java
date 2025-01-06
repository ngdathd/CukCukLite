package com.ngdat.cukcuklite.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ngdat.cukcuklite.base.IViewMain;
import com.ngdat.cukcuklite.base.activity.BaseActivity;
import com.ngdat.cukcuklite.base.animation.ScreenAnimation;

import java.util.List;

public abstract class BaseFragment extends Fragment
        implements IViewMain {

    protected boolean mIsDestroy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        setHasOptionsMenu(true);
        mIsDestroy = false;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inflateComponents();
        findViewByIds();
        initComponents();
        setEvents();
    }

    @Override
    public void onDestroyView() {
        mIsDestroy = true;
        super.onDestroyView();
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public abstract int getIdLayout();

    public static BaseFragment getCurrentFragment(FragmentManager fManager) {
        //noinspection RestrictedApi
        List<Fragment> fragmentList = fManager.getFragments();
        if (null == fragmentList) {
            return null;
        }
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            BaseFragment baseFragment = (BaseFragment) fragmentList.get(i);
            if (null != baseFragment && baseFragment.isVisible()) {
                return baseFragment;
            }
        }
        return null;
    }

    public static void openFragment(FragmentManager fManager,
                                    FragmentTransaction fTransaction,
                                    Class<? extends BaseFragment> fOpen,
                                    ScreenAnimation screenAnimation,
                                    Bundle data,
                                    boolean isAddBackStack,
                                    boolean isCommit) {
        BaseFragment fragment
                = (BaseFragment) fManager.findFragmentByTag(fOpen.getName());
        if (null == fragment) {
            //noinspection TryWithIdenticalCatches
            try {
                fragment = fOpen.newInstance();
                fTransaction.setCustomAnimations(
                        screenAnimation.getmEnterToLeft(),
                        screenAnimation.getmExitToLeft(),
                        screenAnimation.getmEnterToRight(),
                        screenAnimation.getmExitToRight());
                fragment.setArguments(data);
                fTransaction.replace(fragment.getIdLayout(), fragment, fOpen.getName());
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isVisible()) {
                return;
            }
            fTransaction.setCustomAnimations(
                    screenAnimation.getmEnterToLeft(),
                    screenAnimation.getmExitToLeft(),
                    screenAnimation.getmEnterToRight(),
                    screenAnimation.getmExitToRight());
            fTransaction.show(fragment);
        }
        if (isAddBackStack) {
            fTransaction.addToBackStack(fOpen.getName());
        }
        if (isCommit) {
            fTransaction.commit();
        }
    }

    public static void hideFragment(FragmentManager fManager,
                                    FragmentTransaction fTransaction,
                                    Class<? extends BaseFragment> fHide,
                                    ScreenAnimation screenAnimation,
                                    boolean isAddBackStack,
                                    boolean isCommit) {
        BaseFragment fragment
                = (BaseFragment) fManager.findFragmentByTag(fHide.getName());
        if (null != fragment && fragment.isVisible()) {
            fTransaction.setCustomAnimations(
                    screenAnimation.getmEnterToLeft(),
                    screenAnimation.getmExitToLeft(),
                    screenAnimation.getmEnterToRight(),
                    screenAnimation.getmExitToRight());
            fTransaction.hide(fragment);
            if (isAddBackStack) {
                fTransaction.addToBackStack(fHide.getName());
            }
            if (isCommit) {
                fTransaction.commit();
            }
        }
    }

    public static void openFragment(FragmentTransaction fTransaction,
                                    BaseFragment fragment,
                                    ScreenAnimation screenAnimation,
                                    Bundle data,
                                    boolean isAddBackStack,
                                    boolean isCommit) {
        fTransaction.setCustomAnimations(
                screenAnimation.getmEnterToLeft(),
                screenAnimation.getmExitToLeft(),
                screenAnimation.getmEnterToRight(),
                screenAnimation.getmExitToRight());
        fragment.setArguments(data);
        fTransaction.add(fragment.getIdLayout(), fragment, fragment.getClass().getName());
        if (isAddBackStack) {
            fTransaction.addToBackStack(fragment.getClass().getName());
        }
        if (isCommit) {
            fTransaction.commit();
        }
    }

    public static void hideFragment(FragmentTransaction fTransaction,
                                    BaseFragment fragment,
                                    ScreenAnimation screenAnimation,
                                    boolean isAddBackStack,
                                    boolean isCommit) {
        if (null != fragment && fragment.isVisible()) {
            fTransaction.setCustomAnimations(
                    screenAnimation.getmEnterToLeft(),
                    screenAnimation.getmExitToLeft(),
                    screenAnimation.getmEnterToRight(),
                    screenAnimation.getmExitToRight());
            fTransaction.hide(fragment);
            if (isAddBackStack) {
                fTransaction.addToBackStack(fragment.getClass().getName());
            }
            if (isCommit) {
                fTransaction.commit();
            }
        }
    }

    //  implements IViewMain
    @Override
    public void showProgressDialog(String message) {
        if (mIsDestroy) {
            return;
        }
        getBaseActivity().showProgressDialog(message);
    }

    //  implements IViewMain
    @Override
    public void hideProgressDialog() {
        if (mIsDestroy) {
            return;
        }
        getBaseActivity().hideProgressDialog();
    }

    //  implements IViewMain
    @Override
    public void showMessage(String message) {
        Toast.makeText(getBaseActivity(), message, Toast.LENGTH_SHORT).show();
    }

    //  implements IViewMain
    @Override
    public void showMessage(@StringRes int message) {
        Toast.makeText(getBaseActivity(), message, Toast.LENGTH_SHORT).show();
    }

    //  implements IViewMain
    @Override
    public void onBackRoot() {
        getBaseActivity().onBackMain();
    }
}