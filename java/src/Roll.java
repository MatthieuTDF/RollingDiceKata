package src;

import java.util.ArrayList;

public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    private Dice dice;
    private int numberOfDices;
    private int modifierValue;
    private boolean addModValue;

    public Roll(String formula) {
        if (!formula.matches("\\d*d\\d+((\\+|-)\\d+)?")) { // check if formula is valid
            System.out.println("ERROR : THIS IS NOT A VALID ROLL COMMAND");
            this.dice = new Dice(-1); // if not, output error & set all params to either default or invalid
            this.numberOfDices = -1;
            this.modifierValue = 0;
            this.addModValue = true;
        }
        else {
            String[] split1 = formula.split("d");   // since we know formula is valid, we can split accordingly
            if (formula.matches("^\\d.*")) {        // if formula starts with a number
                this.numberOfDices = Integer.parseInt(split1[0]); // the first argument in split is the number of dices
            }
            else {
                this.numberOfDices = 1; // else, it is implied we will use one dice
            }
            if (formula.contains("+")) {                            // if formula contains +, then we have a positive modifier
                String [] split2 = split1[1].split("\\+");          // split on + to separate dice value from modifier
                this.dice = new Dice(Integer.parseInt(split2[0]));  // dice value is on the left of the +
                this.modifierValue = Integer.parseInt(split2[1]);   // modifier value is on its right
                this.addModValue = true;                            // positive modifier
            }
            else if (formula.contains("-")) {                       // if formula contains -, then we have a negative modifier
                String [] split2 = split1[1].split("-");            // split on - to separate dice value from modifier
                this.dice = new Dice(Integer.parseInt(split2[0]));  // dice value is on the left
                this.modifierValue = Integer.parseInt(split2[1]);   // modifier value on the right
                this.addModValue = false;                           // negative modifier, so add is set to false
            }
            else {
                this.dice = new Dice(Integer.parseInt(split1[1]));  // else there is no modifier, so no need to do an additional split
                this.modifierValue = 0;                             // no mod, modvalue = 0
                this.addModValue = true;                            // we could also set it to false
            }
            
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.dice = new Dice(diceValue);
        this.numberOfDices = nbRoll;
        if (modifier >= 0) {
            modifierValue = modifier;
            addModValue = true;
        }
        else {
            this.modifierValue = Math.abs(modifier);
            this.addModValue = false;
        }
    }

    public int makeRoll(RollType rollType) {
        if (this.dice.getValue() <= 1) {
            return -1;
        }
        else if (this.numberOfDices <= 0) {
            return -1;
        }
        else { // this whole section could be a LOT smoother and cleaner if i had bothered to use function pointers, and setting function pointers accordingly in the switches, but i cba :) (actually, just done it in JS sooo)
            int rolls = 0;
            switch(rollType) {
                case NORMAL:
                    if (addModValue) {
                        for (int i=0; i<numberOfDices; i++) {
                            rolls += dice.rollDice();   // increment rolls since we only care about the sum and not the detail of rolls
                        }
                        rolls += modifierValue;         // add modifier since addMod is true
                    }
                    else {
                        for (int i=0; i<numberOfDices; i++) {
                            rolls += dice.rollDice();
                        }
                        rolls -= modifierValue;         // addMod is false, so we substract modifier
                    }
                    break;

                case ADVANTAGE:
                    if (addModValue) {
                        for (int i=0; i<numberOfDices; i++) {
                            rolls += Math.max(dice.rollDice(), dice.rollDice()); // could be improved using function pointers but im far too lazy esp since i literally just coded the exact same thing in JS
                        }
                        rolls += modifierValue;
                    }
                    else {
                        for (int i=0; i<numberOfDices; i++) {
                            rolls += Math.max(dice.rollDice(), dice.rollDice());    // roll twice and take maximum of the two rolls for each dice
                        }
                        rolls -= modifierValue;
                    }
                    break;

                case DISADVANTAGE:
                    for (int i=0; i<numberOfDices; i++) {
                        rolls += Math.min(dice.rollDice(), dice.rollDice());        // roll twice and take only minimum of two rolls for each dice
                    }
                    rolls += modifierValue;
                    break;
                
                default:
                    System.out.println("THIS IS NOT SUPPOSED TO HAPPEN");           // default gotta be there just in case, might have left it empty
                    break;
            }
            if (rolls < 0) {    // avoid negative values because of negative modifiers
                return 0;
            }else return rolls;
        }
    }
}
