package org.dave3heaton.rpgdiceroller.utils.typography;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import static org.dave3heaton.rpgdiceroller.utils.typography.TypefaceUtils.retrieveCustomTypeface;


public class JosefinTypefaceButton extends Button {
    public JosefinTypefaceButton(Context context) {
        super(context);
        setTypeface(retrieveCustomTypeface(context, null));
    }

    public JosefinTypefaceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(retrieveCustomTypeface(context, attrs));
    }

    public JosefinTypefaceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(retrieveCustomTypeface(context, attrs));
    }

    public JosefinTypefaceButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTypeface(retrieveCustomTypeface(context, attrs));
    }
}
