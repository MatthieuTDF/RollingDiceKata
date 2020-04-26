package src;


import Random.RNG;

import java.util.regex.Pattern;

public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes
    private int nbRoll;
    private int diceValue;
    private int modifier;
    private boolean valid = true;

    public Roll(String formula) {

        // On check la formule à l'aide d'une regex, si ça correspond on continue, sinon on fait passer valid à false
        if (Pattern.matches("[0-9]*d[0-9]+([+|-][0-9]+)?",formula)) {

            // Si il n'y a pas de nbRoll spécifié dans la formule, on considère que c'est 1
            if (Pattern.matches("d[0-9]+([+|-][0-9]+)?",formula)) {
                this.nbRoll = 1;
            } else {
                //Sinon, on récupère en integer la partie à gauche du "d"
                this.nbRoll = Integer.parseInt(formula.split("d")[0]);
            }
            //On récupère le premier caractère à droite du "d" pour le diceValue. Probablement à modifier si la valeur du dé dépasse 10
            this.diceValue = Integer.parseInt(formula.split("d")[1].substring(0, 1));

            // En fonction de s'il y a un + ou un -, on récupère en integer la partie à droite pour le modifier. Sinon rien du tout
            if (formula.contains("+")) this.modifier = Integer.parseInt(formula.split("\\+")[1]);
            else if (formula.contains("-")) this.modifier = (-1) * Integer.parseInt(formula.split("-")[1]);
            else this.modifier = 0;
        }
        else {
            valid = false;
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        // On passe valid à false si la valeur du dé ou si le nombre de lancer sont négatifs ou nuls
        if (diceValue <= 0) valid = false;
        if (nbRoll <= 0) valid = false;
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        int result;
        //On retourne -1 si valid est égal à false, c'est à dire qu'une des conditions de lancer n'a pas été respectée
        if(!valid) return -1;
        else {

            // On fait le lancer
            result = (this.nbRoll * RNG.random(this.diceValue)) + this.modifier;

            // Si le résultat est négatif, le lancer vaut zéro
            if (result < 0) result = 0;

            return result;
        }
    }

}
