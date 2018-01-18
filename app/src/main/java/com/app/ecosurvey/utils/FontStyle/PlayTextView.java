package com.app.ecosurvey.utils.FontStyle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class PlayTextView extends TextView {

    public PlayTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PlayTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/GothamRounded_Book.otf");
            setTypeface(tf);
            //setFontType(tf);
        }
    }

    /*private void setFontType(Typeface font) {
        if (fontType == TYPE_BOLD) {
            setTypeface(font, Typeface.BOLD);
        } else if (fontType == TYPE_ITALIC) {
            setTypeface(font, Typeface.ITALIC);
        } else {
            setTypeface(font);
        }
    }*/

}