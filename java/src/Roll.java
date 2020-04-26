package src;


import java.util.regex.Pattern;

public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }
    //comments
    /*
        Ce kata est une catastrophe pour moi, j'ai beau lire 30fois le readme,
        je comprends très bien le sujet mais n'ai aucune idée d'où faire quoi.

        A quel niveau le retour de la valeur -1 peut-elle se faire ? qu'est ce qui
        influe sur cette valeur retournée ?

        Les méthodes sont de type Void donc un return n'est pas possible et je n'identifie
        pas vraiment comment forcer la valeur -1

        Les différentes syntaxes/fonctions (les .quelqueChose) applicables ne sont pas du
        tout maitrisées/identifiées

        Globalement mon énorme problème est vraiment de savoir où faire quoi, j'ai créé l'attribut
        'goodValue' pour définir si la formule entrée est correcte ou non, dans le cas d'une simple roll
        je ne sais même pas en quoi changer sa valeur impacte le résultat vu qu'on se situe dans le constructeur
        et que cela ne retourne pas de valeur.

        Effectivement beaucoup d'interrogations pour ma part malgré aucune question posée...
        je m'y suis bien trop tard, j'avais regardé les premiers jours et n'avais pas compris et
        désormais c'est trop tard :').

        PS: le thème fut sympa tout de même

     */

    // Attributes
    private int diceValue;
    private int nbrRoll;
    private int modifier;
    private boolean goodValue;

    public Roll(String formula) {

        //if the given formula matches the required one
        if(this.goodValue = Pattern.matches("[0-9]*d[0-9]+([+|-][0-9]+)?", formula)){

            //get the number of rolls (before the 'd')/ index 0 of formula (if there is one)
            char[] stringArray = formula.toCharArray();

            if (Pattern.matches("d[0-9]+([+|-][0-9]+)?", formula)){

                this.nbrRoll = 1;

            }else this.nbrRoll = Integer.parseInt(String.valueOf(stringArray[0]));

            //the modifier, char after the '+' or '-'
              //if it's a bonus
            if(formula.contains("+")){

                this.modifier = Integer.parseInt(String.valueOf(stringArray[4]));

              //if it's a minus
            }else if (formula.contains("-")){

                this.modifier = Integer.parseInt(String.valueOf(-stringArray[4]));

              //if there is no modifier

            }else {
                this.modifier = 0;
            }
            //dice value after the 'd'
            this.diceValue = Integer.parseInt(String.valueOf(stringArray[2]));;

        }else
            //if it doesn't match the regex, setting it this way, i hoped it would return -1
            this.diceValue = -1; this.nbrRoll = 0; this.modifier = 0;

    }

    public Roll(int diceValue, int nbRoll, int modifier) {

        this.goodValue = nbRoll > 0;
        //case the value is not correct => negative or 0 (doesn't exist on a dice)
        if(diceValue <= 0){
            this.goodValue = false;
        }
        this.diceValue = diceValue;
        this.nbrRoll = nbRoll;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {

        //no idea on how to manipulate emulations
        //looked for something like instanceof()
        //looked for examples on stackoverflow but didn't understand any

        return 0;
    }

}
