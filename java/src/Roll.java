package src;

import java.util.regex.*;

public class Roll {

        /*
        Je n'ai pas compris le système de plusieurs lancer sachant qu'on peut jouer avec avantage ou non.
        Donc j'ai travaillé comme si le lancer était fait uniquement une fois.

        Donc mes test là où il y a plusieurs lancer ne sont pas validés.

        Sinon tout mes tests sont bons
        */

    private int DiceValue;
    private int Rolls;
    private int mod;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes

    public Roll(String formula) {
        Pattern wo_mod = Pattern.compile("^[0-9]*d[0-9]+$");
        Pattern wi_mod = Pattern.compile("^[0-9]*d[0-9][+-][0-9]+$");

        Matcher wo = wo_mod.matcher(formula);
        Matcher wi = wi_mod.matcher(formula);

        String s1;
        String[] tab_mod;

        if (wo.matches()){
            String[] form = formula.split("d");
            if (form[0].isEmpty()) {
                this.Rolls = 1;
            }else{
                this.Rolls = Integer.parseInt(form[0]);
            }

            this.DiceValue = Integer.parseInt(form[1]);
        }

        if (wi.matches()){
            String[] form = formula.split("d");

            if (form[0].isEmpty()) {
                this.Rolls = 1;
            }else{
                this.Rolls = Integer.parseInt(form[0]);
            }

            if (form[1].contains("+")){
                s1 = form[1];
                tab_mod = s1.split("\\+");
                this.mod = Integer.parseInt(tab_mod[1]);
                this.DiceValue = Integer.parseInt(tab_mod[0]);
            }
            else if (form[1].contains("-")){
                s1 = form[1];
                tab_mod = s1.split("-");
                this.mod = Integer.parseInt(tab_mod[1]);
                this.DiceValue = Integer.parseInt(tab_mod[0]);
            }
            else{
                this.mod = 0;
                this.DiceValue = Integer.parseInt(form[1]);
            }

        }

    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.DiceValue = diceValue;
        this.Rolls = nbRoll;
        this.mod = modifier;
    }

    public int makeRoll(RollType rollType) {

        int second_roll;
        Dice dice = new Dice(this.DiceValue);
        int final_result = dice.rollDice();

        if(this.DiceValue <= 0 || this.Rolls <= 0) return -1;


        switch (rollType){

            case ADVANTAGE:
                second_roll = dice.rollDice();
                final_result = Math.max(final_result,second_roll);
            case DISADVANTAGE:
                second_roll = dice.rollDice();
                final_result = Math.min(final_result,second_roll);
        }

        final_result += this.mod;

        if (final_result < 0){
            final_result = 0;
        }

        return final_result;
    }

}
