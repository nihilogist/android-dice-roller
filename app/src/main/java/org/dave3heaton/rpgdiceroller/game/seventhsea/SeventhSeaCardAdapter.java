package org.dave3heaton.rpgdiceroller.game.seventhsea;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.dave3heaton.diceengine.game.aeg.seventhsea.SeventhSeaDie;
import org.dave3heaton.diceengine.game.aeg.seventhsea.SeventhSeaRoll;
import org.dave3heaton.rpgdiceroller.R;

import java.util.List;

import static org.dave3heaton.rpgdiceroller.utils.LogUtils.debug;


public class SeventhSeaCardAdapter extends RecyclerView.Adapter<SeventhSeaCardAdapter.RollCardHolder> {

    private List<SeventhSeaRollCard> rollCards;

    public SeventhSeaCardAdapter(List<SeventhSeaRollCard> rollCardLists) {
        this.rollCards = rollCardLists;
    }

    public static class RollCardHolder extends RecyclerView.ViewHolder {

        TextView rollDice;
        TextView keepDice;
        TextView rollResult;
        TextView rollBreakdown;
        Button rollButton;
        SeventhSeaRoll seventhSeaRollForCard;

        RollCardHolder(View cardView) {
            super(cardView);
            rollButton = (Button) cardView.findViewById(R.id.seventh_sea_rollcard_roll_button);
            rollDice = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_roll_dice);
            keepDice = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_keep_dice);
            rollResult = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_roll_score);
            rollBreakdown = (TextView) cardView.findViewById(R.id.seventh_sea_rollcard_roll_breakdown);
        }

        public void rollAndUpdateResult() {
            debug("ROLL", "rollAndUpdateResult called");
            seventhSeaRollForCard.roll();
            updateResult();
        }

        public void updateResult() {
            rollResult.setText(Integer.toString(seventhSeaRollForCard.getFacingNumber()));
            String breakdown = "";
            for (SeventhSeaDie die : seventhSeaRollForCard.getAllDice()) {
                if (die.isKept()) {
                    breakdown += "[" + die.getFacingNumber() + "] ";
                } else {
                    breakdown += "|" + die.getFacingNumber() + "| ";
                }
            }
            rollBreakdown.setText(breakdown);
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
