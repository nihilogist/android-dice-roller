package org.dave3heaton.rpgdiceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class SeventhSeaRollerMainActivity extends AppCompatActivity {


    private RadioGroup rollGroup;
    private RadioGroup keepGroup;

    private static final String LOG_CATEGORY = "SeventhSeaRoller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh_sea_roller_main);

        Log.d(LOG_CATEGORY, "Activity starting up: registering listeners");

        // Initialise the listeners on the 'Roll' radiogroup
        rollGroup = (RadioGroup)findViewById(R.id.seventh_sea_roller_roll_group);
        rollGroup.check(R.id.seventh_sea_roller_roll_1);
        rollGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateRollGroup(checkedId);
            }
        });

        // Initialise the listeners on the 'Keep' radiogroup
        keepGroup = (RadioGroup)findViewById(R.id.seventh_sea_roller_keep_group);
        keepGroup.check(R.id.seventh_sea_roller_keep_1);
        keepGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(LOG_CATEGORY, "Keep group button press: " + checkedId);
            }
        });

    }

    private void updateRollGroup(int checkedButtonId) {
        Log.d(LOG_CATEGORY, "Roll group state change: " + checkedButtonId);
        Log.d(LOG_CATEGORY, "Roll group, button " + getDiceToRoll() + " pressed.");

        setMaximumKeepValue(getDiceToRoll());
    }

    private int getDiceToRoll() {
        return getValueFromButtonGroup(rollGroup);
    }

    private int getDiceToKeep() {
        return getValueFromButtonGroup(keepGroup);
    }

    private int getValueFromButtonGroup(RadioGroup radioGroup) {
        RadioButton buttonSelected = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        String buttonText = buttonSelected.getText().toString();
        return Integer.parseInt(buttonText);
    }

    private int getValueFromButtonId(int buttonId) {
        RadioButton buttonSelected = (RadioButton)findViewById(buttonId);
        String buttonText = buttonSelected.getText().toString();
        return Integer.parseInt(buttonText);
    }

    private void setMaximumKeepValue(int maximumKeepValue) {
        Log.d(LOG_CATEGORY, "Setting maximum keep value to " + maximumKeepValue);
        boolean resetDiceToKeep = getDiceToKeep() > maximumKeepValue;

        List<RadioButton> buttonList = getRadioButtonListFromGroup(keepGroup);

        if(resetDiceToKeep) {
            Log.i(LOG_CATEGORY, "Dice to keep exceeds dice to roll, resetting.");
            keepGroup.clearCheck();
            for (RadioButton button : buttonList) {
                if (getValueFromButtonId(button.getId()) > maximumKeepValue) {
                    button.setChecked(false);
                    button.setSelected(false);
                }
            }
        }



        for (RadioButton button : buttonList) {
            // Disable all buttons for values higher than the maximum keep value, enable all others.
            if (getValueFromButtonId(button.getId()) > maximumKeepValue) {
                Log.d(LOG_CATEGORY, "Disabling button " + getValueFromButtonId(button.getId()));
                button.setEnabled(false);
            } else {
                Log.d(LOG_CATEGORY, "Enabling button " + getValueFromButtonId(button.getId()));

                button.setEnabled(true);
            }

            if (resetDiceToKeep && getValueFromButtonId(button.getId()) == maximumKeepValue) {
                Log.d(LOG_CATEGORY, "Checking new keep value.");
                button.setChecked(true);
            }



        }
        Log.d(LOG_CATEGORY, "New keep value is " + getDiceToKeep());
    }

    private List<RadioButton> getRadioButtonListFromGroup(RadioGroup radioGroup) {
        List<RadioButton> buttonList = new ArrayList<>();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View potentialRadioButton = radioGroup.getChildAt(i);
            if (potentialRadioButton instanceof RadioButton) {
                buttonList.add((RadioButton)potentialRadioButton);
            }
        }
        return buttonList;
    }


}
