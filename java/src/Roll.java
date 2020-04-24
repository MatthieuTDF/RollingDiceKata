package src;


import java.util.regex.Pattern;

public class Roll {
    int dice_value;
    int roll_nb;
    int modifier;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    public Roll(int dice_value, int roll_nb, int modifier) {   /// constructor

        this.dice_value = dice_value;
        this.roll_nb = roll_nb;
        this.modifier = modifier;
    }

    // Attributes

    public Roll(String formula) {

        String pattern1 = "^[0-9]*d[0-9]+$";
        String pattern2 = "^[0-9]*d[0-9]+[-+][0-9]+$";



        if(Pattern.matches(pattern1,formula)){              /// No modifier pattern

            String[] variables = formula.split("d");

            if (variables[0].isEmpty()){
                this.roll_nb = 1;
            }else{
                this.roll_nb = Integer.parseInt(variables[0]);
            }

            this.dice_value = Integer.parseInt(variables[1]);
        }



        if(Pattern.matches(pattern2, formula)){              /// With modificator pattern

            String[] variables = formula.split("d");

            if (variables[0].isEmpty()){
                this.roll_nb = 1;
            }else{
                this.roll_nb = Integer.parseInt(variables[0]);
            }

            /// Check operator before splitting
            if (variables[1].indexOf('-') != -1){

                String[] sub_variables = variables[1].split("-");

                this.dice_value = Integer.parseInt(sub_variables[0]);
                this.modifier = Integer.parseInt(sub_variables[1]) * -1;
            }

            if (variables[1].indexOf('+') != -1){

                String[] sub_variables = variables[1].split("\\+");
                this.dice_value = Integer.parseInt(sub_variables[0]);
                this.modifier = Integer.parseInt(sub_variables[1]);
            }
        }

    }



    public int makeRoll(RollType rollType) {

        int total = 0;

        if (this.dice_value <= 0 || this.roll_nb <= 0){         /// avoid errors
            return -1;
        }

        for (int i = 0; i < this.roll_nb; i++){

            Dice main_dice = new Dice(this.dice_value);

            switch(rollType){

                case NORMAL:
                    total += main_dice.rollDice();
                    break;

                case ADVANTAGE:                                 /// second roll optimised just in case of ADVANTAGE ; DISAVANTAGE
                    total += Math.max(main_dice.rollDice(), main_dice.rollDice());
                    break;

                case DISADVANTAGE:
                    total += Math.min(main_dice.rollDice(), main_dice.rollDice());
                    break;
            }
        }

        total += this.modifier;

        return Math.max(total, 0);

    }

}