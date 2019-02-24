package com.lalitp.zomatosampleapp.UserInterface.Widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lalitp.zomatosampleapp.R;


/**
 * Created by atulsia on 16/8/18.
 */

public class CustomToolbar extends Toolbar {

    private AppCompatTextView centeredTitleTextView;

    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        String s = getResources().getString(resId);
        setTitle(s);
    }

    @Override
    public void setTitle(CharSequence title) {
        getCenteredTitleTextView().setText(title);
    }

    @Override
    public CharSequence getTitle() {
        return getCenteredTitleTextView().getText().toString();
    }

    public void setTypeface(Typeface font) {
        getCenteredTitleTextView().setTypeface(font);
    }

    private TextView getCenteredTitleTextView() {
        if (centeredTitleTextView == null) {
            centeredTitleTextView = new AppCompatTextView(getContext());
            centeredTitleTextView.setSingleLine();
            centeredTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
            centeredTitleTextView.setTextAppearance(getContext(), R.style.MyToolbarTitleTextStyle);

            /*Toolbar.LayoutParams lp = new Toolbar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            centeredTitleTextView.setLayoutParams(lp);*/

            addView(centeredTitleTextView);
        }
        return centeredTitleTextView;
    }
}
