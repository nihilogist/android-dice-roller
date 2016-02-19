package org.dave3heaton.rpgdiceroller.utils.typography;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.util.HashMap;

public class TypefaceUtils {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }

    public static Typeface retrieveCustomTypeface(Context context, AttributeSet attributeSet) {
        int textStyle = attributeSet.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        return selectTypeface(context, textStyle);
    }

    public static Typeface selectTypeface(Context context, int textStyle) {
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
