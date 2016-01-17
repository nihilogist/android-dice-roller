package org.dave3heaton.rpgdiceroller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SeventhSeaRollerMainActivity extends AppCompatActivity {


    private RadioGroup rollGroup;
    private RadioGroup keepGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh_sea_roller_main);

        Log.i("SeventhSeaActivity", "Activity starting up: registering listeners");

        // Initialise the listeners on the 'Roll' radiogroup
        rollGroup = (RadioGroup)findViewById(R.id.seventh_sea_roller_roll_group);
        rollGroup.check(R.id.seventh_sea_roller_roll_1);
        rollGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("SeventhSeaActivity", "Roll group button press: " + checkedId);
            }
        });

        // Initialise the listeners on the 'Keep' radiogroup
        keepGroup = (RadioGroup)findViewById(R.id.seventh_sea_roller_keep_group);
        keepGroup.check(R.id.seventh_sea_roller_keep_1);
        keepGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("SeventhSeaActivity", "Keep group button press: " + checkedId);
            }
        });

    }

}
