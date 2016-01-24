package org.dave3heaton.rpgdiceroller.game.seventhsea;


import org.dave3heaton.diceengine.game.aeg.seventhsea.SeventhSeaRoll;

public class SeventhSeaRollCard {

    private SeventhSeaRoll seventhSeaRoll;

    public SeventhSeaRollCard(int diceToRoll, int diceToKeep) {
        this.seventhSeaRoll = new SeventhSeaRoll(diceToRoll, diceToKeep);
    }

    public SeventhSeaRoll getSeventhSeaRoll() {
        return this.seventhSeaRoll;
    }

}
