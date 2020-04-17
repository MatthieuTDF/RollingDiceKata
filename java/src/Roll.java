package src;

public class Roll {
    int diceValue;
    int nbRoll;
    int modifier;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes


    public Roll(String formula) {
        // TODO
        String regex = "^[0-9]*d[0-9]+$";
        String regexModifier = "^[0-9]*d[0-9]+[-+][0-9]+$";

        if ( formula.matches( regex ) ) {
            String[] newFormula = formula.split("d");
            if ( newFormula[0].isEmpty() ){
                this.nbRoll = 1;
            }
            else {
                this.nbRoll = Integer.parseInt(newFormula[0] );
            }
            this.diceValue = Integer.parseInt( newFormula[1] );
        }

        if ( formula.matches( regexModifier ) ){
            String[] secondFormula = formula.split("d");
            if (secondFormula[0].isEmpty()) {
                this.nbRoll = 1;
            }
            else {
                this.nbRoll = Integer.parseInt(secondFormula[0]);
            }
            this.diceValue = Integer.parseInt( secondFormula[1] );
            if ( secondFormula[2].equals("-") ){
                this.modifier = Integer.parseInt(secondFormula[3]);
                this.modifier *= -1;
            }
            else {
                this.modifier = Integer.parseInt(secondFormula[3]);
            }
        }

    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        // TODO
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;

    }

    public int makeRoll(RollType rollType) {
        // TODO
        int counter=0;
        if ( this.diceValue <= 0 ){
            return -1;
        }
        if ( this.nbRoll <= 0 ){
            return -1;
        }
        for ( int i = 0; i < this.nbRoll; i++ ){
            Dice dice = new Dice( this.diceValue );
            int roll1 = dice.rollDice();
            int roll2 = dice.rollDice();
            if ( rollType == RollType.NORMAL ){
                counter += roll1;
            }
            if ( rollType == RollType.ADVANTAGE ){
                counter += Math.max(roll1, roll2);
            }
            if ( rollType == RollType.DISADVANTAGE){
                counter += Math.min(roll1, roll2);
            }
        }
        counter += this.modifier;

        return Math.max(counter, 0);
    }

}
