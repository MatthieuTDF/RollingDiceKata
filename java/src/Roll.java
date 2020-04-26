package src;

import java.util.regex.Pattern;

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
        // TODO

        String regexNoModif = "^[0-9]*d[0-9]+$";
        String regexModif = "^[0-9]*d[0-9]+[-+][0-9]+$";



        if(Pattern.matches(regexNoModif,formula)){

            String[] variables = formula.split("d");

            if (variables[0].isEmpty()){
                this.nbRoll = 1;
            }else{
                this.nbRoll = Integer.parseInt(variables[0]);
            }
            this.diceValue = Integer.parseInt(variables[1]);
        }



        if(Pattern.matches(regexModif, formula)){

            String[] variables = formula.split("d");

            if (variables[0].isEmpty()){
                this.nbRoll = 1;
            }else{
                this.nbRoll = Integer.parseInt(variables[0]);
            }
            
            if (variables[1].indexOf('-') != -1){

                String[] sub_variables = variables[1].split("-");

                this.diceValue = Integer.parseInt(sub_variables[0]);
                this.modifier = Integer.parseInt(sub_variables[1]) * -1;
            }

            if (variables[1].indexOf('+') != -1){

                String[] sub_variables = variables[1].split("\\+");

                this.diceValue = Integer.parseInt(sub_variables[0]);
                this.modifier = Integer.parseInt(sub_variables[1]);
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

        int result = 0;
        int result_firstRoll = 0;
        int result_secondRoll = 0;

        if(this.diceValue <= 0 || this.nbRoll <= 0 ) return -1;

        for (int roll_done = 0; roll_done < this.nbRoll; roll_done ++){

            Dice theDice = new Dice(this.diceValue);
            result_firstRoll += theDice.rollDice();

        }

        if (rollType == RollType.NORMAL)result+=result_firstRoll;

        else{

            for (int roll_done = 0; roll_done < this.nbRoll; roll_done ++){

                Dice theDice = new Dice(this.diceValue);
                result_secondRoll += theDice.rollDice();

            }

            switch (rollType){

                case ADVANTAGE:
                    result = Math.max(result_firstRoll,result_secondRoll);
                    break;

                case DISADVANTAGE:
                    result = Math.min(result_firstRoll,result_secondRoll);
                    break;

            }

        }

        result += this.modifier;

        return Math.max(result,0);
    }

}
