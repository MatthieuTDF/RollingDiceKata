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
    private boolean error = true;

    public Roll(String formula) {
        String regex = "^[0-9]*d[0-9]+$";
        String regex2 = "[0-9]*d[0-9]+([+|-][0-9]+)?";
        String[] formula2 = formula.split("d");

        // Dans un premier temps on check si il y a un modifier ou non
        if(Pattern.matches(regex,formula)) {
            // On check le nombre de roll effectué
            if(formula2[0].isEmpty()) {
                this.nbRoll = 1;
            } else {
                this.nbRoll = Integer.parseInt(formula2[0]);
            }

            // On attribue la valeur correspondante à diceVallue
            this.diceValue = Integer.parseInt(formula2[1]);

        } else if (Pattern.matches(regex2,formula)){
            // Dans le cas où nous avons un modifier :

            // On check le nombre de roll dans le cas où nous avons un modifier
            if(formula2[0].isEmpty()) {
                this.nbRoll = 1;

            } else {
                this.nbRoll = Integer.parseInt(formula2[0]);
            }

            // On attribue la valeur correspondante à diceVallue
            this.diceValue = Integer.parseInt(formula2[1]);

            // On va voir si le modifier est positif ou negatif et l'affecter à modifier
            if (formula2[1].indexOf('+') > 0) {
                String[] formula3 = formula2[1].split("\\+");
                this.modifier = Integer.parseInt(formula3[1]);

            } else if (formula2[1].indexOf('-') > 0) {
                String[] formula3 = formula2[1].split("-");
                this.modifier = -(Integer.parseInt(formula3[1]));

            } else {
                this.modifier = 0;
            }

        } else {
            // Cas où la formule ne correspond pas :
            this.error = false;
        }

    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        // on créer les erreurs pour la suite
        if( diceValue <= 0 || nbRoll <= 0 ) {
            this.error = false;

        } else {
            // On affecte les bonnes valeurs
            this.diceValue = diceValue;
            this.nbRoll = nbRoll;
            this.modifier = modifier;
        }
    }

    public int makeRoll(RollType rollType) {
        if(this.error) {
            // Cas où il n'y a pas d'erreur :
            int i;
            int value1 = 0;
            int value2 = 0;

            // on attribue deux valeurs qui correspondront aux lancé 1 et 2 respectivement
            for(i = 0; i < this.nbRoll ; i++) {
                value1 += RNG.random(this.diceValue);
                value2 += RNG.random(this.diceValue);
            }

            // On ajoute le modifier
            value1 += this.modifier;
            value2 += this.modifier;

            // On va retourner la valeur en fonction du type de lancé choisi 
            if (rollType == RollType.NORMAL && value1 > 0) {
                return value1;

            } else if (rollType == RollType.ADVANTAGE && value1 > 0 && value2 > 0) {
                return value1 >= value2 ? value1 : value2;

            } else if (rollType == RollType.DISADVANTAGE && value1 > 0 && value2 > 0) {
                return value1 >= value2 ? value2 : value1;
            }
        }
        return -1;

    }

}
