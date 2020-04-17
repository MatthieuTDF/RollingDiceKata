package src;

import Random.RNG;

import java.util.regex.*;
public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes
    private int diceValue;
    private int nbRoll;
    private int modifier;
    private boolean error;


    public Roll(String formula) {
        if(this.error = Pattern.matches("[0-9]*d[0-9]+([+|-][0-9]+)?",formula)) {
            String[] split = formula.split("d");
            String[] split2;
            if(Pattern.matches("d[0-9]+([+|-][0-9]+)?",formula))
                this.nbRoll = 1;
            else
                this.nbRoll = Integer.parseInt(split[0]);
            if (formula.contains("+") || formula.contains("-")) {
                if (formula.contains("+")) {
                    split2 = split[1].split("[+]");
                    this.modifier = Integer.parseInt(split2[1]);
                    this.diceValue = Integer.parseInt(split2[0]);
                } else if (formula.contains("-")) {
                    split2 = split[1].split("-");
                    this.modifier = -(Integer.parseInt(split2[1]));
                    this.diceValue = Integer.parseInt(split2[0]);
                }
            }else
                this.diceValue = Integer.parseInt(split[1]);
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.error = nbRoll > 0;
        if(diceValue <= 0)
            this.error = false;
        this.nbRoll = nbRoll;
        this.diceValue = diceValue;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        int value = 0;
        int value2 = 0;
        int i;
        if(!this.error)
            return -1;
        for (i = 0; i < this.nbRoll; i++) {
            value += RNG.random(this.diceValue);
        }
        for (i = 0; i < this.nbRoll; i++) {
            value2 += RNG.random(this.diceValue);
        }
        value += this.modifier;
        value2 += this.modifier;
        if (value > 0 && rollType == RollType.NORMAL)
            return value;
        else if(value > 0 && value2>0 && (rollType == RollType.DISADVANTAGE ||rollType == RollType.ADVANTAGE))
            return rollType == RollType.ADVANTAGE ? Math.max(value, value2) : Math.min(value, value2);
        else
            return 0;
    }

}