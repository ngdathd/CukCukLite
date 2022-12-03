package vn.misa.nadat.cukcuklite.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;

import vn.misa.nadat.cukcuklite.R;

/**
 * Class custom AppCompatTextView giúp chữ có thêm dấu * màu đỏ.
 *
 * @created_by nadat on 15/04/2019
 */
public class TextViewRequire extends AppCompatTextView {
    public TextViewRequire(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setText(Html.fromHtml(String.format(
                getResources().getString(R.string.text_required_format),
                getText().toString().trim())));
    }
}