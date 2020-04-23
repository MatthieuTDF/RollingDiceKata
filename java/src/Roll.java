package src;

import java.util.regex.Pattern;


public class Roll {
    //declaration des varibles a utiliser plus tard
    int value, rollNb, modifier;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes

    public Roll(String formula) {
        //debut fociton roll
        //declaration des 2 regex possibles
        String dice = "^[0-9]*d[0-9]+$";
        String diceAndModifier = "^[0-9]*d[0-9]+[-+][0-9]+$";

        //traitement du premier cas de regex
        if (Pattern.matches(dice, formula)){
        //si le formula correspond au regex
            String[] diceComposants = formula.split("d");
            //On split la chaine en 2; le nombre de dés en diceComposants[0] et la valeur du dé en diceComposants[1]

            if (!diceComposants[0].isEmpty()){
            //Si le nombre de des a un nombre dedans
                this.rollNb = Integer.parseInt(diceComposants[0]);
                //on store ce numero dans la variable rollNb

            }else{
            //Sinon
                this.rollNb = 0;
                //on store la variable -1
            }
            this.value = Integer.parseInt(diceComposants[1]);
            //Dans tout les cas on store la valeur du dé dans la variable value
        }

        if (Pattern.matches(diceAndModifier, formula)){
        //On fait la meme chose pour le deuxieme cas de regex
            String[] diceComposants = formula.split("d");
            if (!diceComposants[0].isEmpty()){
                this.rollNb = Integer.parseInt(diceComposants[0]);
                //on store ce numero dans la variable rollNb
            }else{
            //Sinon
                //on store la variable -1
                this.rollNb = -1;
            }
            //Mais dans ce cas vu que derriere le d on a des modifiers aussi on va verifier pour les
            //deux cas "+" ou "-"

            //ensuite le cas du -, si on trouve un '-' dans
            if (diceComposants[1].indexOf('-') > 0){
                String[] diceModifiers = diceComposants[1].split("-");
            }


        }
    }

    public Roll(int value, int rollNb, int modifier) {
        this.value = value;
        this.rollNb = rollNb;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        // TODO
        return 0;
    }

}
