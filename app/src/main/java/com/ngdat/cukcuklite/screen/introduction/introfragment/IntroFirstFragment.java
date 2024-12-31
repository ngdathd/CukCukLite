package com.ngdat.cukcuklite.screen.introduction.introfragment;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngdat.cukcuklite.R;

/**
 * Màn hình giới thiệu thứ nhất
 * Created at 03/04/2019
 */
public class IntroFirstFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_intro_first, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    /**
     * Phương thức tham chiếu, khởi tạo cho view
     * Created at 03/04/2019
     */
    private void initViews() {
        try {
            TextView tvIntro = mView.findViewById(R.id.tvIntroFirst);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvIntro.setText(Html.fromHtml(getString(R.string.intro_msg_1), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvIntro.setText(Html.fromHtml(getString(R.string.intro_msg_1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
