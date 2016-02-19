package org.dave3heaton.rpgdiceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import org.dave3heaton.rpgdiceroller.game.seventhsea.SeventhSeaCardAdapter;
import org.dave3heaton.rpgdiceroller.game.seventhsea.SeventhSeaRollCard;

import java.util.ArrayList;
import java.util.List;

import static org.dave3heaton.rpgdiceroller.utils.logging.LogUtils.debug;

public class SeventhSeaRollerMainActivity extends AppCompatActivity {


    private RadioGroup rollGroup;
    private RadioGroup keepGroup;
    private Button makeRollButton;

    private RecyclerView cardRecyclerView;
    private RecyclerView.LayoutManager cardViewLayoutmanager;
    private List<SeventhSeaRollCard> rollsForCardView;

    private static final String LOG_CATEGORY = "SeventhSeaRoller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh_sea_roller_main);

        debug(LOG_CATEGORY, "Activity starting up: registering listeners");

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
                debug(LOG_CATEGORY, "Keep group button press: " + checkedId);
            }
        });

        // Initialise the listeners on the 'Roll' button
        makeRollButton = (Button)findViewById(R.id.seventh_sea_roller_button_roll);
        makeRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRollButtonPressed();
            }
        });

        // Initialise the card view
        debug(LOG_CATEGORY, "Creating cardRecyclerView");
        cardRecyclerView = (RecyclerView) findViewById(R.id.seventh_sea_result_recycler_view);
        debug(LOG_CATEGORY, "cardRecyclerView created, creating cardViewLayoutManager");
        cardViewLayoutmanager = new LinearLayoutManager(this);
        debug(LOG_CATEGORY, "cardViewLayoutManager created, assigning cardViewLayoutManager to it");
        cardRecyclerView.setLayoutManager(cardViewLayoutmanager);
        debug(LOG_CATEGORY, "cardViewLayoutManager set.");

        // Initialise the list of rolls
        rollsForCardView = new ArrayList<>();
        debug(LOG_CATEGORY, "List of rolls created.");

        SeventhSeaCardAdapter cardAdapter = new SeventhSeaCardAdapter(rollsForCardView);
        debug(LOG_CATEGORY, "Card Adapter created.");
        cardRecyclerView.setAdapter(cardAdapter);

        addNewRollCardToRollCardList();

        debug(LOG_CATEGORY, rollsForCardView.size() + " rolls in card view");

        // Initialise the swipe-to-dismiss code
        SwipeableRecyclerViewTouchListener swipeToDismissListener = new SwipeableRecyclerViewTouchListener(cardRecyclerView,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {

                    @Override
                    public boolean canSwipe(int position) {
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            rollsForCardView.remove(position);
                            cardRecyclerView.getAdapter().notifyItemRemoved(position);
                            cardRecyclerView.getAdapter().notifyItemRangeChanged(position, cardRecyclerView.getAdapter().getItemCount());
                        }
                        cardRecyclerView.getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            rollsForCardView.remove(position);
                            cardRecyclerView.getAdapter().notifyItemRemoved(position);
                            cardRecyclerView.getAdapter().notifyItemRangeChanged(position, cardRecyclerView.getAdapter().getItemCount());
                        }
                        cardRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });

        cardRecyclerView.addOnItemTouchListener(swipeToDismissListener);

    }

    private void updateRollGroup(int checkedButtonId) {
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
        boolean resetDiceToKeep = getDiceToKeep() > maximumKeepValue;
        List<RadioButton> buttonList = getRadioButtonListFromGroup(keepGroup);

        if(resetDiceToKeep) {
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
                button.setEnabled(false);
            } else {

                button.setEnabled(true);
            }

            if (resetDiceToKeep && getValueFromButtonId(button.getId()) == maximumKeepValue) {
                button.setChecked(true);
            }
        }
        debug(LOG_CATEGORY, "New keep value is " + getDiceToKeep());
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

    private void makeRollButtonPressed() {
        debug(LOG_CATEGORY, "Roll button pressed; adding new card.");

        addNewRollCardToRollCardList();
    }

    private void addNewRollCardToRollCardList() {
        SeventhSeaRollCard card = new SeventhSeaRollCard(getDiceToRoll(), getDiceToKeep());
        card.getSeventhSeaRoll().roll();

        this.rollsForCardView.add(card);
        this.cardRecyclerView.getAdapter().notifyDataSetChanged();
    }


}
