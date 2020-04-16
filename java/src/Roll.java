package src;


import java.util.regex.Pattern;

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
        String regex1 = "^[0-9]*d[0-9]+$";
        String regex2 = "^[0-9]*d[0-9]+[-+][0-9]+$";

        if(Pattern.matches(regex1,formula)){
            String[] values = formula.split("d");

            if (!values[0].isEmpty()){
                this.nbRoll = Integer.parseInt(values[0]);
            }else{
                this.nbRoll = 1;
            }
            this.diceValue = Integer.parseInt(values[1]);
        }
        if(Pattern.matches(regex2, formula)){
            String[] values = formula.split("d");
            if (values[0].isEmpty()){
                this.nbRoll = 1;
            }else{
                this.nbRoll = Integer.parseInt(values[0]);
            }

           if (values[1].indexOf('-') > 0){
               String[] values2 = values[1].split("-");
               this.diceValue = Integer.parseInt(values2[0]);
               this.modifier = Integer.parseInt(values2[1]);
               this.modifier *= -1;
           }

            if (values[1].indexOf('+') > 0){
                String[] values2 = values[1].split("\\+");
                this.diceValue = Integer.parseInt(values2[0]);
                this.modifier = Integer.parseInt(values2[1]);
           }
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        int total = 0;
        if (this.diceValue <= 0 || this.nbRoll <= 0){
            return -1;
        }
        for (int i = 0; i < this.nbRoll; i++){
            Dice d = new Dice(this.diceValue);
            int firstRoll = d.rollDice();
            int secondRoll = d.rollDice();
            switch(rollType){
                case NORMAL:
                    total += firstRoll;
                    break;
                case ADVANTAGE:
                    total += firstRoll > secondRoll ? firstRoll:secondRoll;
                    break;
                case DISADVANTAGE:
                    total += firstRoll < secondRoll ? firstRoll:secondRoll;
                    break;
            }
        }
        total += this.modifier;
        return total < 0 ? 0:total;
    }

}
