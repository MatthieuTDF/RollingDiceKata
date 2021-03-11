package src;

public class Roll {

    private int diceValue;
    private int nbRoll;
    private int modifier;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes

    public Roll(String formula) {
        if (formula.length() < 3){
            this.nbRoll = 1;
            this.diceValue = (int)formula.charAt(1) - '0';
            this.modifier = 0;
        }else {
            this.nbRoll = (int) formula.charAt(0) - '0';
            this.diceValue = (int)formula.charAt(2) - '0';
            if ((int)formula.charAt(2) - '0' == 43 && (formula.length() >= 4)){
                this.modifier = (int)formula.charAt(3) - '0';
            }else if ((int)formula.charAt(2) - '0' == 45 && (formula.length() >= 4)){
                this.modifier = (((int)formula.charAt(3) - '0')* -1);
            }else{
                this.modifier = 0;
            }
        }
    }

    public Roll(int nbRoll, int diceValue, int modifier) {
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        if (this.diceValue <= 0|| this.nbRoll <= 0){
            return -1;
        }
        Dice myDice = new Dice(this.diceValue);
        int result = 0;
        for(int i = 0; i < this.nbRoll; i++){
            result += myDice.rollDice();
        }
        if (rollType == RollType.ADVANTAGE) {
            int result2 = 0;
            for (int i = 0; i < this.nbRoll; i++) {
                result2 += myDice.rollDice();
            }
            if (result > result2 && result + modifier >= 0) {
                return result + modifier;
            } else if (result2 + modifier >= 0){
                return result2 + modifier;
            }else{
                return -1;
            }
        }
        if (rollType == RollType.DISADVANTAGE) {
            int result3 = 0;
            for (int i = 0; i < this.nbRoll; i++) {
                result3 += myDice.rollDice();
            }
            if (result < result3 && result + modifier >= 0) {
                return result + modifier;
            } else if (result3 + modifier >= 0){
                return result3 + modifier;
            }else{
                return -1;
            }
        }
        if (result + modifier >= 0) {
            return result + modifier;
        }else{
            return -1;
        }
    }
}
