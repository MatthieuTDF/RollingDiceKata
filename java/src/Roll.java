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
        if (!formula.matches("\\d*d\\d+((\\+|-)\\d+)?")) {
            System.out.println("ERROR : THIS IS NOT A VALID ROLL COMMAND");
            this.dice = new Dice(-1);
            this.numberOfDices = -1;
            this.modifierValue = 0;
            this.addModValue = true;
        }
        else {
            String[] split1 = formula.split("d");
            if (formula.matches("^\\d.*")) {
                this.numberOfDices = Integer.parseInt(split1[0]);
            }
            else {
                this.numberOfDices = 1;
            }
            if (formula.contains("+")) {
                String [] split2 = split1[1].split("\\+");
                this.dice = new Dice(Integer.parseInt(split2[0]));
                this.modifierValue = Integer.parseInt(split2[1]);
                this.addModValue = true;
            }
            else if (formula.contains("-")) {
                String [] split2 = split1[1].split("-");
                this.dice = new Dice(Integer.parseInt(split2[0]));
                this.modifierValue = Integer.parseInt(split2[1]);
                this.addModValue = false;
            }
            else {
                this.dice = new Dice(Integer.parseInt(split1[1]));
                this.modifierValue = 0;
                this.addModValue = true;
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
        else { // this whole section could be a LOT smoother and cleaner if i had bothered to use function pointers, and setting function pointers accordingly in the switches, but i cba :)
            int rolls = 0;
            switch(rollType) {
                case NORMAL:
                    if (addModValue) {
                        for (int i=0; i<numberOfDices; i++) {
                            rolls += dice.rollDice(); // could be improved using function pointers but im far too lazy esp since i literally just coded the exact same thing in JS
                        }
                        rolls += modifierValue;
                    }
                    else {
                        for (int i=0; i<numberOfDices; i++) {
                            rolls += dice.rollDice();
                        }
                        rolls -= modifierValue;
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
                            rolls += Math.max(dice.rollDice(), dice.rollDice());
                        }
                        rolls -= modifierValue;
                    }
                    break;

                case DISADVANTAGE:
                    for (int i=0; i<numberOfDices; i++) {
                        rolls += Math.min(dice.rollDice(), dice.rollDice());
                    }
                    rolls += modifierValue;
                    break;
                
                default:
                    System.out.println("THIS IS NOT SUPPOSED TO HAPPEN");
                    break;
            }
            if (rolls < 0) {
                return 0;
            }else return rolls;
        }
    }
}
