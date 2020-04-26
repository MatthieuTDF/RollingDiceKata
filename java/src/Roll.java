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

            this.rollNb = !diceComposants[0].isEmpty() ? Integer.parseInt(diceComposants[0]) : 0;
            //Si le nombre de des a un nombre dedans on store ce numero dans la variable rollNb
            //Sinon on store la variable 0

            this.value = Integer.parseInt(diceComposants[1]);
            //Dans tout les cas on store la valeur du dé dans la variable value
        }

        if (Pattern.matches(diceAndModifier, formula)){
        //On fait la meme chose pour le deuxieme cas de regex
            String[] diceComposants = formula.split("d");
            //On split la chaine en 2; le nombre de dés en diceComposants[0] et la valeur du dé en diceComposants[1]

            this.rollNb = !diceComposants[0].isEmpty() ? Integer.parseInt(diceComposants[0]) : 0;
            //Si le nombre de des a un nombre dedans on store ce numero dans la variable rollNb
            //Sinon on store la variable 0


            //Mais dans ce cas vu que derriere le d on a des modifiers aussi on va verifier pour les
            //deux cas "+" ou "-"
            if (diceComposants[1].indexOf('-') > 0 || diceComposants[1].indexOf('+') > 0){
            //Si Il y a un + ou un moins dans la formula
                String[] diceModifiers = diceComposants[1].split("[+-]");
                //On separe la chaine de caracteres diceComposants[1] en se basant sur soir le + soit le -
                this.value = Integer.parseInt(diceModifiers[0]);
                //On attribue a la variable value diceModifiers[0];
                this.modifier = diceComposants[1].indexOf('-') > 0 ? -1 * Integer.parseInt(diceModifiers[1]) : Integer.parseInt(diceModifiers[1]);
                //Si il y a un moins on prends la valeur de la droite de ce moins et on la multiplie par -1 pour avoir une valeur negative
            }


        }
    }

    public Roll(int value, int rollNb, int modifier) {
        this.value = value;
        this.rollNb = rollNb;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        int one, two;
        int res = 0;
        if(this.value <= 0 || this.rollNb <= 0){
        //tout d'abord on verifie si le nombre de lancers et la valeur du dés ne sont pas négatives ou nulles
            return -1;
            //si oui on retourne -1
        }
        for (int i = 0; i < this.rollNb; i++){
        //ensuite on fait une bouvle for sur le nombre de lancers
            Dice dice = new Dice(this.value);
            //a chaque lancer on crée un dé
            if (rollType == RollType.NORMAL){
            //si le type de lancer est de type NORMAL 1 seul lancer est effectue et le resultat est celui ci
                res += dice.rollDice();
            }else if(rollType == RollType.ADVANTAGE){
            //sinon si le type de lancer est de type ADVANTAGE 2 lancers sont effectues et le resultat est le plus grand
                res += Math.max(dice.rollDice(), dice.rollDice());
            }else if(rollType == RollType.DISADVANTAGE){
            //sinon si le type de lancer est du type DISAVANTAGE 2 lancers sont effectués et le resultat est le plus petit
                res += Math.min(dice.rollDice(), dice.rollDice());
            }
        }

        res += this.modifier;
        //on ajoute donc le modificateur au resultat


        return Math.max(res, 0);
        //ensuite si res est superieur a 0 on retourne res et si il est inferieur on retourne 0
    }

}
