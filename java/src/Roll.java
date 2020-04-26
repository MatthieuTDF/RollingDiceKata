package src;

import Random.RNG;
import org.w3c.dom.ls.LSOutput;

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
    private boolean error;


    public Roll(String formula) {
        if(this.error = Pattern.matches("[0-9]*d[0-9]+([+|-][0-9]+)?",formula)) { //Test si la formule respecte la regex, si oui on rentre dans le if si non la variable error = false
            String[] split = formula.split("d"); // on split la formule au d
            String[] split2;
            if(Pattern.matches("d[0-9]+([+|-][0-9]+)?",formula)) //Si il n'y a pas de chiffre devant le d on met le ndroll a 1
                this.nbRoll = 1;
            else
                this.nbRoll = Integer.parseInt(split[0]); //Sinon on le split
            if (formula.contains("+") || formula.contains("-")) { //On regarde si il y a un jet de modification
                if (formula.contains("+")) { //Si il est positif on split au + et on renseigne le dicevalue et le modifier
                    split2 = split[1].split("[+]");
                    this.modifier = Integer.parseInt(split2[1]);
                    this.diceValue = Integer.parseInt(split2[0]);
                } else if (formula.contains("-")) { // Meme chose qu'au dessus mais avec un modifier negatif
                    split2 = split[1].split("-");
                    this.modifier = -(Integer.parseInt(split2[1]));
                    this.diceValue = Integer.parseInt(split2[0]);
                }
            }else //Si pas de modifier alors on renseigne dicevalue
                this.diceValue = Integer.parseInt(split[1]);
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.error = nbRoll > 0; // check si le nbroll est pas nul
        if(diceValue <= 0) // idem pour le dicevalue
            this.error = false;
        this.nbRoll = nbRoll;
        this.diceValue = diceValue;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        int value = 0;
        int value2 = 0;
        int i;
        if(!this.error) // On check la varible error, si elle vaut false on renvoie -1
            return -1;
        for (i = 0; i < this.nbRoll; i++) { // On boucle sur le nombre de rolls
            value += RNG.random(this.diceValue);
        }
        for (i = 0; i < this.nbRoll; i++) { //Idem pour les jets advantages et disavantages
            value2 += RNG.random(this.diceValue);
        }
        value += this.modifier;
        value2 += this.modifier;
        if (value > 0 && rollType == RollType.NORMAL)
            return value;
        else if(value > 0 && value2>0 && (rollType == RollType.DISADVANTAGE || rollType == RollType.ADVANTAGE)) //Juste un verification pas forcement obligatoire
            return rollType == RollType.ADVANTAGE ? Math.max(value, value2) : Math.min(value, value2);
        else
            return 0;
    }

}