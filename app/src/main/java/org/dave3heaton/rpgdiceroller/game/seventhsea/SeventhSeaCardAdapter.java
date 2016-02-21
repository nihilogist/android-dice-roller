package org.dave3heaton.rpgdiceroller.game.seventhsea;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.apmem.tools.layouts.FlowLayout;
import org.dave3heaton.diceengine.game.aeg.seventhsea.SeventhSeaDie;
import org.dave3heaton.diceengine.game.aeg.seventhsea.SeventhSeaRoll;
import org.dave3heaton.rpgdiceroller.R;

import java.util.List;


public class SeventhSeaCardAdapter extends RecyclerView.Adapter<SeventhSeaCardAdapter.RollCardHolder> {

    private List<SeventhSeaRollCard> rollCards;

    public SeventhSeaCardAdapter(List<SeventhSeaRollCard> rollCardLists) {
        this.rollCards = rollCardLists;
    }

    public static class RollCardHolder extends RecyclerView.ViewHolder {

        TextView rollDetails;
        TextView rollResult;
        Button rollButton;
        Switch explodeSwitch;
        Button addDramaDieButton;
        FlowLayout diceList;
        SeventhSeaRoll seventhSeaRollForCard;

        RollCardHolder(View cardView) {
            super(cardView);
            rollButton = (Button) cardView.findViewById(R.id.seventh_sea_rollcard_roll_button);
            rollDetails = (TextView) cardView.findViewById(R.id.seventh_sea_result_card_details);
            rollResult = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_roll_score);
            diceList = (FlowLayout) cardView.findViewById(R.id.seventh_sea_rollcard_dicelist);
            explodeSwitch = (Switch) cardView.findViewById(R.id.seventh_sea_result_card_explode_switch);
            addDramaDieButton = (Button) cardView.findViewById(R.id.seventh_sea_rollcard_dramadice_button);
        }

        public void rollAndUpdateResult() {
            seventhSeaRollForCard.roll();
            updateResult();
        }

        public void setRollExplosionAndUpdateResult(boolean isExploding) {
            seventhSeaRollForCard.setExploding(isExploding);
            updateResult();
        }

        public void addDramaDieAndUpdateResult() {
            seventhSeaRollForCard.addDramaDice();
            updateResult();
        }

        public void updateResult() {
            rollResult.setText(Integer.toString(seventhSeaRollForCard.getFacingNumber()));

            // Clear the linear layout
            diceList.removeAllViews();

            // Add the drama dice to the layout first
            for (SeventhSeaDie dramaDie : seventhSeaRollForCard.getDramaDice()) {
                TextView dieResult = new TextView(this.itemView.getContext());
                dieResult.setId(View.generateViewId());
                dieResult.setText(dramaDie.getFacingNumber() + "");
                float dieResultSize = dieResult.getContext().getResources().getDimension(R.dimen.seventh_sea_roll_result_card_dice_text_size);
                dieResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dieResultSize);

                // measure the textview and set the width equal to the height
                dieResult.measure(0, 0);
                dieResult.setWidth(dieResult.getMeasuredHeight());
                dieResult.setBackground(ContextCompat.getDrawable(this.itemView.getContext(), R.drawable.dice_result_background_lozenge_drama));

                FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(0, 4, 8, 0);
                dieResult.setLayoutParams(params);
                dieResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                diceList.addView(dieResult);
            }

            // Add new text layouts for the regular dice
            for (SeventhSeaDie die : seventhSeaRollForCard.getAllDice()) {
                TextView dieResult = new TextView(this.itemView.getContext());
                dieResult.setId(View.generateViewId());
                dieResult.setText(die.getFacingNumber() + "");
                float dieResultSize = dieResult.getContext().getResources().getDimension(R.dimen.seventh_sea_roll_result_card_dice_text_size);
                dieResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dieResultSize);

                // measure the textview and set the width equal to the height
                dieResult.measure(0, 0);
                dieResult.setWidth(dieResult.getMeasuredHeight());

                if(die.isKept()) {
                    dieResult.setBackground(ContextCompat.getDrawable(this.itemView.getContext(), R.drawable.dice_result_background_lozenge_seventh_sea_keep));
                } else {
                    dieResult.setBackground(ContextCompat.getDrawable(this.itemView.getContext(), R.drawable.dice_result_background_lozenge_seventh_sea_discard));
                }

                // Set a little bit of margin
                FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(0, 4, 8, 0);
                dieResult.setLayoutParams(params);
                dieResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                diceList.addView(dieResult);

            }
        }


    }

    @Override
    public RollCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seventh_sea_roll_card, parent, false);
        RollCardHolder rollCardHolder = new RollCardHolder(v);
        return rollCardHolder;
    }

    @Override
    public void onBindViewHolder(final RollCardHolder holder, int position) {
        // Bind the seventh sea roll
        holder.seventhSeaRollForCard = rollCards.get(position).getSeventhSeaRoll();

        // Update the roll and keep descriptions
        String rollDetailsDescription = holder.seventhSeaRollForCard.getDiceToRoll() + "k" + holder.seventhSeaRollForCard.getDiceToKeep();
        holder.rollDetails.setText(rollDetailsDescription);

        // Set exploding to true by default
        holder.explodeSwitch.setChecked(true);

        // Make the roll, if it hasn't been made already
        if(holder.seventhSeaRollForCard.getFacingNumber() == 0) {
            holder.rollAndUpdateResult();
        } else {
            holder.updateResult();
        }

        // Bind the roll button click listener
        holder.rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rollAndUpdateResult();
            }
        });

        // Bind the explosion click listener
        holder.explodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.setRollExplosionAndUpdateResult(isChecked);
            }
        });

        // Bind the drama dice button click listener
        holder.addDramaDieButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                holder.addDramaDieAndUpdateResult();
            }
        });

    }

    @Override
    public int getItemCount() {
        return rollCards.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
