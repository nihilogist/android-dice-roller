package org.dave3heaton.rpgdiceroller.utils.typography;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TypefaceTextView extends TextView {
    public TypefaceTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TypefaceUtils.getTypeface("JosefinSans-Regular.ttf", context);
        setTypeface(customFont);
    }
}
