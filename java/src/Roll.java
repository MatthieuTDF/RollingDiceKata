// packages
package src;

// imports
import java.util.regex.Pattern;

public class Roll {

    int diceValue;
    int nbRoll;
    int modifier;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes

    public Roll(String formula) {
        String regex1 = "^[0-9]*d[0-9]+$";           // Regex sans valeur de modification
        String regex2 = "^[0-9]*d[0-9]+[-+][0-9]+$"; // Regex avec valeur de modification

        if (Pattern.matches(regex1, formula)){              // Sans modification 2d6-2
            String[] values = formula.split("d");     // Met la string splitté dans le tableau
            if(!values[0].isEmpty()){                       // Si la valeur de la premiere case != 0
                this.nbRoll = Integer.parseInt(values[0]);  // Transformation en int
            }else{                                          // Sinon
                this.nbRoll = 1;                            // Set nombre de jet à 1
            }
            this.diceValue = Integer.parseInt(values[1]);   // Transformer en int
        }

        if (Pattern.matches(regex2, formula)) {             // Avec modifier
            String[] values = formula.split("d");     // Met la string splitté dans le tableau
            if (!values[0].isEmpty()) {                     // Si la valeur de la premiere case != 0
                this.nbRoll = Integer.parseInt(values[0]);  // Transformation en int
            } else {                                        // Sinon
                this.nbRoll = 1;                            // Set nombre de jet à 1
            }

            if(values[1].indexOf('-') > 0){                 // Si dans la seconde case du tableau "-"
                String[] tab1 = values[1].split("-"); // On recréer un tableau a partir de la case
                this.diceValue = Integer.parseInt(tab1[0]); // Transformer en int
                this.modifier = Integer.parseInt(tab1[1]);  // Transformer en int
                this.modifier *= -1;                        // Concerver le néatif
            }

            if(values[1].indexOf('+') > 0){                     // Si dans la seconde case du tableau "+"
                String[] tab2 = values[1].split("\\+");   // On recréer un tableau a partir de la case
                this.diceValue = Integer.parseInt(tab2[0]);     // Transformer en int
                this.modifier = Integer.parseInt(tab2[1]);      // Transformer en int
            }

        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.diceValue = diceValue;                         // taille du dé
        this.nbRoll = nbRoll;                               // nombre de jet
        this.modifier = modifier;                           // bonus/malus
    }

    public int makeRoll(RollType rollType) {

        int result =0;
        int finalResult =0;
        if (this.diceValue <= 0 || this.nbRoll <= 0){ // Si la taille du dé négatif OU nombre de lancé négatif
            return -1;
        }

        Dice myDice = new Dice(this.diceValue);
        for (int i = 0; i < nbRoll; i++){    // Pour i vaut 0, tant que i n'atteind pas nombre de lancé, on incrémente
            finalResult += myDice.rollDice();// Ajout à la suite les résultats
        }

        if (rollType != rollType.NORMAL ){                  // Si le lancé est de type normal
            for (int i = 0; i < nbRoll; i++) {
                result += myDice.rollDice();                // On additionne le result avec le resultat de la fonction
            }
            if (rollType == rollType.ADVANTAGE){            // Si le lancé est de typ eavantageux
                finalResult = Math.max(finalResult, result);// Le resultat final prend la valeur la plus élever
            } else {                                        // Sinon
                finalResult = Math.min(finalResult,result); // Le rsultat le plus faible
            }
        }



        finalResult += this.modifier; // ajoute le modifictateur

        return finalResult < 0 ? 0:finalResult; //si le res final est negatif return 0 sinon return le res final

    }

}