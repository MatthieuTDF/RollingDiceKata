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



    public Roll(String formula) {
        if(Pattern.matches("[0-9]*d[0-9]+([+|-][0-9]+)?",formula)) { //On rentre quoi qu'il arrive dedans
            String[] splitb = formula.split("d");



            if(Pattern.matches("d[0-9]+([+|-][0-9]+)?",formula)){ //si il n'y a pas de nbRoll
                this.nbRoll = 1;
                if(formula.contains("+")){ //si il n'y a pas de nbRoll et qu'il y a un "+"
                    String[] splitPositive = formula.split("[+]");
                    String splitPositive0=splitPositive[0];
                    String[] splitPositiveAfterd= splitPositive0.split("[d]");
                    this.modifier = Integer.parseInt(splitPositive[1]);
                    this.diceValue= Integer.parseInt(splitPositiveAfterd[1]);
                }else if (formula.contains("-")){ //si il n'y a pas de nbRoll et qu'il y a un "-"
                    String[] splitNegative =formula.split("-");
                    String splitNegative0=splitNegative[0];
                    String[] splitNegativeAfterd= splitNegative0.split("[d]");
                    this.modifier = -Integer.parseInt(splitNegative[1]);
                    this.diceValue= Integer.parseInt(splitNegativeAfterd[1]);
                }else{
                    this.diceValue = Integer.parseInt(splitb[1]);
                }




            }else if(Pattern.matches("[0-9]*d[0-9]",formula)){ //si il y a un nbRoll, un diceValue, mais pas de modifier
                this.nbRoll = Integer.parseInt(splitb[0]);
                this.diceValue = Integer.parseInt(splitb[1]);



            }else if(Pattern.matches("[0-9]*d[0-9]+([+|-][0-9]+)?",formula)){ //si il y a un nbRoll, un diceValue, un modifier et ****
                this.nbRoll = Integer.parseInt(splitb[0]);
                if(formula.contains("+")){ //si le modifier = "+"
                    String[] splitPositive = formula.split("[+]");
                    String splitPositive0=splitPositive[0];
                    String[] splitPositiveAfterd= splitPositive0.split("[d]");
                    this.modifier = Integer.parseInt(splitPositive[1]);
                    this.diceValue= Integer.parseInt(splitPositiveAfterd[1]);
                }
                if(formula.contains("-")){ //si le modifier = '-'
                    String[] splitNegative =formula.split("-");
                    String splitNegative0=splitNegative[0];
                    String[] splitNegativeAfterd= splitNegative0.split("[d]");
                    this.modifier = -Integer.parseInt(splitNegative[1]);
                    this.diceValue= Integer.parseInt(splitNegativeAfterd[1]);
                }
            }
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.diceValue = diceValue ;
        this.nbRoll = nbRoll ;
        this.modifier =  modifier ;
    }

    public int makeRoll(RollType rollType) {

        int nbRoll = this.nbRoll ;
        int diceValue = this.diceValue ;
        int modifier = this.modifier ;

        if(nbRoll <=0 ||diceValue <=0){
            return -1 ;
        }

        int result = 0;
        for( int i = 1 ;  i<=nbRoll ; i++){
            result += RNG.random(diceValue)  ;
        }

        int finalResult = result + modifier;
        if(finalResult <0){
            finalResult =0 ;
        }

        return finalResult ;
    }
}
