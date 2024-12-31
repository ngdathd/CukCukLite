package com.ngdat.cukcuklite.screen.introduction.introfragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngdat.cukcuklite.R;

/**
 * Màn hình giới thiệu thứ năm
 * Created at 03/04/2019
 */
public class IntroFifthFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro_fifth, container, false);
    }
}
