package src;


import java.util.regex.*;

public class Roll {

    int Valeur;
    int test;
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

        if (Pattern.matches(regex1, formula)){
            String[] tab = formula.split("d");
            if(!tab[0].isEmpty()){
                this.test = Integer.parseInt(tab[0]);
            }else{
                this.test = 1;
            }
            this.Valeur = Integer.parseInt(tab[1]);
        }

        if (Pattern.matches(regex2, formula)) {
            String[] tab = formula.split("d");
            if (!tab[0].isEmpty()) {
                this.test = Integer.parseInt(tab[0]);
            } else {
                this.test = 1;
            }

            if(tab[1].indexOf('-') > 0){
                String[] tab1 = tab[1].split("-");
                this.Valeur = Integer.parseInt(tab1[0]);
                this.modifier = Integer.parseInt(tab1[1]);
                this.modifier *= -1;
            }

            if(tab[1].indexOf('+') > 0){
                String[] tab2 = tab[1].split("\\+");
                this.Valeur = Integer.parseInt(tab2[0]);
                this.modifier = Integer.parseInt(tab2[1]);
            }

        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.Valeur = diceValue;
        this.test = nbRoll;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        int result =0;
        int fin =0;
        if (this.Valeur <= 0 || this.test <= 0){
            return -1;
        }

        Dice newDice = new Dice(this.Valeur);
        for (int i =0; i < test; i++){
            fin += newDice.rollDice();
        }

        if (rollType != rollType.NORMAL ){
            for (int i =0; i < test; i++) {
                result += newDice.rollDice();
            }
            if (rollType == rollType.ADVANTAGE){
                fin = Math.max(fin, result);
            } else {
                fin = Math.min(fin,result);
            }
        }



        fin += this.modifier;

        return fin < 0 ? 0:fin;

    }

}
