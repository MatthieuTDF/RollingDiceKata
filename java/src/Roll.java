package src;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes
    // TODO
    private int nbRoll;
    private int diceValue;
    private int modifier;

    public Roll(String formula) {
        // TODO
        Pattern regex = Pattern.compile("([1-9][0-9]*)?d([1-9][0-9]*)([+-][1-9][0-9]*)?");
        Matcher match = regex.matcher(formula);

        if (match.matches()){
            this.nbRoll = match.group(1) == null ? 1:Integer.parseInt(match.group(1));
            this.diceValue = Integer.parseInt(match.group(2));
            this.modifier = match.group(3) != null ? Integer.parseInt(match.group(3)):0;
        } else {
            this.nbRoll = -1;
            this.diceValue = -1;
            this.modifier = -1;
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        // TODO
        this.nbRoll = nbRoll;
        this.diceValue = diceValue;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        // TODO
        if (this.diceValue <= 0 || this.nbRoll <= 0){
            return -1;
        }
        int total = this.modifier;

        for (int i = 0; i < this.nbRoll; i++) {
            Dice d = new Dice(this.diceValue);

            int [] tab = new int[2];
            for (int j = 0; j < tab.length; j++){
                tab[j] = d.rollDice();
            }

            switch(rollType){
                case NORMAL:
                    total += tab[0];
                    break;
                case ADVANTAGE:
                    total += Math.max(tab[0], tab[1]);
                    break;
                case DISADVANTAGE:
                    total += Math.min(tab[0], tab[1]);
                    break;
            }
        }
        return Math.max(total, 0);
    }
}
