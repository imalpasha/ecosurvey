package com.app.ecosurvey.utils.FontStyle;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class PlayCheckBox extends CheckBox {

    public PlayCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PlayCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayCheckBox(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/GothamRounded_Book.otf");
            setTypeface(tf);
        }
    }

}