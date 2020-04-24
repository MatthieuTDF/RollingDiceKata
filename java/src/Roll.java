package src;


public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes
    public int diceValue;
    public int nbRoll = 1;
    public int modifier;

    public Roll(String formula) {
        // sort according to formula's size and for each , check if syntax is correct
        switch (formula.length()) {
            case 2 -> {
                if (formula.charAt(0) == 'd' && charToInteger(formula.charAt(1)) > 0)
                    this.diceValue = charToInteger(formula.charAt(1));
            }
            case 3 -> {
                if (formula.charAt(1) == 'd' && charToInteger(formula.charAt(0)) > 0 && charToInteger(formula.charAt(2)) > 0) {
                    this.diceValue = charToInteger(formula.charAt(2));
                    this.nbRoll = charToInteger(formula.charAt(0));
                }
            }
            case 4 -> {
                if (formula.charAt(0) == 'd' && charToInteger(formula.charAt(1)) > 0 && charToInteger(formula.charAt(3)) > 0 && (formula.charAt(2) == '+' || formula.charAt(3) == '-') && charToInteger(formula.charAt(3)) > 0) {
                    this.diceValue = charToInteger(formula.charAt(1));
                    if (formula.charAt(2) == '-') this.modifier = charToInteger(formula.charAt(3)) * -1;
                    else this.modifier = charToInteger(formula.charAt(3));
                }
            }
            case 5 -> {
                if (formula.charAt(1) == 'd' && charToInteger(formula.charAt(0)) > 0 && charToInteger(formula.charAt(2)) > 0 && (formula.charAt(3) == '+' || formula.charAt(3) == '-')) {
                    this.diceValue = charToInteger(formula.charAt(2));
                    this.nbRoll = charToInteger(formula.charAt(0));
                    if (formula.charAt(3) == '-') this.modifier = charToInteger(formula.charAt(4)) * -1;
                    else this.modifier = charToInteger(formula.charAt(4));
                }
            }
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        if ( this.diceValue > 0 && this.nbRoll > 0) {
            int firstLaunch = 0, secondLaunch = 0;
            // roll the dice according to the number of rolls
            for (int i = 0; i < this.nbRoll; i++) {
                firstLaunch += Random.RNG.random(this.diceValue);
                secondLaunch += Random.RNG.random(this.diceValue);
            }
            // return value according to type of roll or 0 if value < 0
            switch (rollType) {
                case NORMAL -> {
                    return Math.max(firstLaunch + this.modifier, 0);
                }
                case ADVANTAGE -> {
                    return Math.max(Math.max(secondLaunch, firstLaunch) + this.modifier, 0);
                }
                case DISADVANTAGE -> {
                    return Math.max(Math.min(secondLaunch, firstLaunch) + this.modifier, 0);
                }
            }
        }
        //if we arrive here it is that something is wrongly informed so we return an error value -1
        return -1;
    }

    // convert digit to integer return -1 if not a digit
    public int charToInteger(char c) {
        if (Character.isDigit(c))
            return Integer.parseInt(String.valueOf(c));
        else return -1;
    }
}
