package org.dave3heaton.rpgdiceroller.utils.typography;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import static org.dave3heaton.rpgdiceroller.utils.typography.TypefaceUtils.retrieveCustomTypeface;


public class JosefinTypefaceRadioButton extends RadioButton {
    public JosefinTypefaceRadioButton(Context context) {
        super(context);
        setTypeface(retrieveCustomTypeface(context, null));
    }

    public JosefinTypefaceRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(retrieveCustomTypeface(context, attrs));
    }

    public JosefinTypefaceRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(retrieveCustomTypeface(context, attrs));
    }

    public JosefinTypefaceRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTypeface(retrieveCustomTypeface(context, attrs));
    }
}
