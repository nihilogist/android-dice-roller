package org.dave3heaton.rpgdiceroller.game.seventhsea;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.dave3heaton.diceengine.game.aeg.seventhsea.SeventhSeaDie;
import org.dave3heaton.diceengine.game.aeg.seventhsea.SeventhSeaRoll;
import org.dave3heaton.rpgdiceroller.R;
import org.dave3heaton.rpgdiceroller.utils.logging.LogUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.dave3heaton.rpgdiceroller.utils.logging.LogUtils.debug;


public class SeventhSeaCardAdapter extends RecyclerView.Adapter<SeventhSeaCardAdapter.RollCardHolder> {

    private List<SeventhSeaRollCard> rollCards;

    public SeventhSeaCardAdapter(List<SeventhSeaRollCard> rollCardLists) {
        this.rollCards = rollCardLists;
    }

    public static class RollCardHolder extends RecyclerView.ViewHolder {

        TextView rollDice;
        TextView keepDice;
        TextView rollResult;
        Button rollButton;
        RelativeLayout diceList;
        SeventhSeaRoll seventhSeaRollForCard;

        RollCardHolder(View cardView) {
            super(cardView);
            rollButton = (Button) cardView.findViewById(R.id.seventh_sea_rollcard_roll_button);
            rollDice = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_roll_dice);
            keepDice = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_keep_dice);
            rollResult = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_roll_score);
            diceList = (RelativeLayout) cardView.findViewById(R.id.seventh_sea_rollcard_dicelist);
        }

        public void rollAndUpdateResult() {
            debug("ROLL", "rollAndUpdateResult called");
            seventhSeaRollForCard.roll();
            updateResult();
        }

        public void updateResult() {
            rollResult.setText(Integer.toString(seventhSeaRollForCard.getFacingNumber()));

            // Clear the linear layout
            diceList.removeAllViews();


            // Add new text layouts for the dice
            TextView previousTextView = null;
            for (SeventhSeaDie die : seventhSeaRollForCard.getAllDice()) {
                TextView dieResult = new TextView(this.itemView.getContext());
                dieResult.setId(View.generateViewId());
                dieResult.setText(die.getFacingNumber() + "");

                // measure the textview and set the width equal to the height
                dieResult.measure(0, 0);
                dieResult.setWidth(dieResult.getMeasuredHeight());



                // Centre the text
                dieResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


                if(die.isKept()) {
                    dieResult.setBackground(ContextCompat.getDrawable(this.itemView.getContext(), R.drawable.dice_result_background_lozenge_seventh_sea_keep));
                } else {
                    dieResult.setBackground(ContextCompat.getDrawable(this.itemView.getContext(), R.drawable.dice_result_background_lozenge_seventh_sea_discard));
                }


                // Set a little bit of margin
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(6, 2, 0, 0);
//                dieResult.setLayoutParams(params);

                // Try to align if possible
                if (previousTextView != null) {
                    LogUtils.debug("DiceList", "Aligning textLayout to right of " + previousTextView.getId());
                    params.addRule(RelativeLayout.RIGHT_OF, previousTextView.getId());
                    dieResult.setLayoutParams(params);
                } else {
                    params.addRule(RelativeLayout.ALIGN_PARENT_START);
                    dieResult.setLayoutParams(params);
                }

                diceList.addView(dieResult);


                // Update the previous view
                previousTextView = dieResult;
            }

            reLayout();

        }

        private void reLayout() {
            int totalWidth = 0;
            int currentWidth;
            int layoutXPadding;
            int measuredWidth;
            diceList.measure(0,0);
            measuredWidth = diceList.getMeasuredWidth();
            LogUtils.debug("Reflow layout", "Measured width of layout = " + measuredWidth);
            layoutXPadding = diceList.getPaddingLeft() + diceList.getPaddingRight();
            LogUtils.debug("Reflow layout", "Padding on layout = " + layoutXPadding);
            int layoutWidthWithoutPadding = measuredWidth - layoutXPadding;

            int numberOfChildViews = diceList.getChildCount();

            if (numberOfChildViews > 0) {
                TextView rootTextView = (TextView)diceList.getChildAt(0);
                TextView rowStartView = null; // placeholder view
                // Iterate through all child views
                for (int i = 0; i < numberOfChildViews; i++) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)diceList.getChildAt(i).getLayoutParams();
                    currentWidth = diceList.getChildAt(i).getMeasuredWidth();
                    LogUtils.debug("Reflow layout", "width of child " + i + " is " + currentWidth);
                    if (i > 0) {
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, diceList.getChildAt(i-1).getId()); // align to the right of the prev element
                    }
                    if (totalWidth + currentWidth > layoutWidthWithoutPadding) {
                        LogUtils.debug("Reflow layout", "Total width (" + totalWidth + ") + current child width (" + currentWidth + ") exceeds layout width of " + layoutWidthWithoutPadding + " so wrapping here.");
                        // If the width so far means that we chould wrap to a new line...
                        rowStartView = rootTextView;
                        rootTextView = (TextView)diceList.getChildAt(i);
                        totalWidth = currentWidth;
                        layoutParams.removeRule(RelativeLayout.RIGHT_OF);
                    } else {
                        LogUtils.debug("Reflow layout", "OK to place current child at end of row; incrementing total width.");
                        totalWidth += currentWidth;
                    }

                    if (rowStartView != null) {
                        LogUtils.debug("Reflow layout", "Dropping down one row.");
                        layoutParams.addRule(RelativeLayout.BELOW, rowStartView.getId());
                    } else {
                        totalWidth += currentWidth;
                    }

                    diceList.getChildAt(i).setLayoutParams(layoutParams);
                }
            }


        }
    }

    @Override
    public RollCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        debug("CardCreate", "Card created and inflating view");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seventh_sea_roll_card, parent, false);
        RollCardHolder rollCardHolder = new RollCardHolder(v);
        return rollCardHolder;
    }

    @Override
    public void onBindViewHolder(final RollCardHolder holder, int position) {
        debug("CardCreate", "Card created and binding view");
        // Bind the seventh sea roll
        holder.seventhSeaRollForCard = rollCards.get(position).getSeventhSeaRoll();

        // Update the roll and keep descriptions
        holder.rollDice.setText(Integer.toString(holder.seventhSeaRollForCard.getDiceToRoll()));
        holder.keepDice.setText(Integer.toString(holder.seventhSeaRollForCard.getDiceToKeep()));

        // Make the roll, if it hasn't been made already
        if(holder.seventhSeaRollForCard.getFacingNumber() == 0) {
            holder.rollAndUpdateResult();
        } else {
            holder.updateResult();
        }

        // Bind the button click listener
        holder.rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rollAndUpdateResult();
            }
        });

    }

    @Override
    public int getItemCount() {
        return rollCards.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        debug("CardCreate", "attachedToRecyclerView called");
        super.onAttachedToRecyclerView(recyclerView);
    }
}
