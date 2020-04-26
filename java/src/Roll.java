//packages
package src;

//import
import java.util.regex.Pattern;

public class Roll {

    int nbRoll;
    int diceValue;
    int modifier;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes

    /*
    * Constructeur quand on reçoit une string
    */
    public Roll(String formula) {
        // TODO : ex : "2d6-2"

        String firstRegex = "^[0-9]*d[0-9]+$";
        String secondRegex = "^[0-9]*d[0-9]+[-+][0-9]+$";

        // pattern sans modifier
        if (Pattern.matches(firstRegex, formula)){
            String[] allValues = formula.split("d"); // on coupe la chaine au niveau du "d"

            if (!allValues[0].isEmpty()){
                this.nbRoll = Integer.parseInt(allValues[0]); // on le fait devenir un int
            } else {
                this.nbRoll = 1; // si pas d'information devant le "d" c'est qu'il y à qu'un seul dé
            }
            this.diceValue = Integer.parseInt(allValues[1]); // on le fait devenir un int
        }

        // pattern avec modifier
        if (Pattern.matches(secondRegex, formula)){
            String[] allValues = formula.split("d"); // on coupe la chaine au niveau du "d"

            if (!allValues[0].isEmpty()){ // si pas de valeur avant le d
                this.nbRoll = Integer.parseInt(allValues[0]); // on le fait devenir un int
            } else {
                this.nbRoll = 1; // si pas d'information devant le "d" c'est qu'il y à qu'un seul dé
            }

            if (allValues[1].indexOf('-') > 0){ // s'il trouve un "-"
                String[] otherValues = allValues[1].split("-");
                this.diceValue = Integer.parseInt(otherValues[0]);
                this.modifier = Integer.parseInt(otherValues[1]);
                this.modifier *= -1; // pour garder le "-"
            }

            if (allValues[1].indexOf('+') > 0){ // s'il trouve un "+"
                String[] otherValues = allValues[1].split("\\+");
                this.diceValue = Integer.parseInt(otherValues[0]);
                this.modifier = Integer.parseInt(otherValues[1]);
            }
        }
    }

    /*
     * Constructeur quand on lui donne nous même les paramètres
     */
    public Roll(int diceValue, int nbRoll, int modifier) {
        // TODO
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;

    }

    public int makeRoll(RollType rollType) {
        // TODO
        int resultFinal = 0;

        if (this.diceValue <= 0 || this.nbRoll <= 0){ // pour éviter les valeurs négatives pour le nombre de lancé et la "valeur" du dé
            return -1;
        }

        for ( int i = 0; i < this.nbRoll; i++){
            Dice dice = new Dice(this.diceValue); // on créer un nouveau dés qui fait un random de 0 à dicevalue
            int firstRoll = dice.rollDice();
            int secondRoll = dice.rollDice();

            switch (rollType){
                case NORMAL:
                    resultFinal += firstRoll;
                    break;
                case ADVANTAGE:
                    resultFinal = Math.max(firstRoll, secondRoll);
                    break;
                case DISADVANTAGE:
                    resultFinal = Math.min(firstRoll, secondRoll);
                    break;
            }
        }
        resultFinal += this.modifier;
        return resultFinal < 0 ? 0:resultFinal;

    }

}
