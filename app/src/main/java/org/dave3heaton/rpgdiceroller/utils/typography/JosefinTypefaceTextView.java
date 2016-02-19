package org.dave3heaton.rpgdiceroller.utils.typography;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import org.dave3heaton.rpgdiceroller.utils.logging.LogUtils;


public class JosefinTypefaceTextView extends TextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public JosefinTypefaceTextView(Context context) {
        super(context);
        applyCustomFont(context, null);
    }

    public JosefinTypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public JosefinTypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, attrs);
    }

    public JosefinTypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attributeSet) {
        int textStyle = attributeSet.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }



    private Typeface selectTypeface(Context context, int textStyle) {
    /*
    * information about the TextView textStyle:
    * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
    */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return TypefaceUtils.getTypeface("JosefinSans-Bold.ttf", context);

            case Typeface.ITALIC: // italic
                return TypefaceUtils.getTypeface("JosefinSans-Italic.ttf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return TypefaceUtils.getTypeface("JosefinSans-BoldItalic.ttf", context);

            case Typeface.NORMAL: // regular
            default:
                return TypefaceUtils.getTypeface("JosefinSans-Regular.ttf", context);
        }
    }
}
