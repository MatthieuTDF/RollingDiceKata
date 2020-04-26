package src;


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

    // Constructeur ou les paramètres sont donnés sous forme d'une formule string, il va falloir vérifier l'intégrité de la string
    // et la parser en fonction du type de formule
    public Roll(String formula) {
        /* On crée deux regex, une première pour le cas ou il n'y a pas de modificateur fixe ( +3 ou -5  à la fin) et
         * et une pour gérer ce cas de modificateur, cela nous permettra de gérer ces deux cas différemment
         * pour récupérer plus facilement les paramètres avec des split
         */
        String regex1 = "^[0-9]*d[0-9]+$";
        String regex2 = "^[0-9]*d[0-9]+[-+][0-9]+$";

        // Si on match le cas simple
        if(Pattern.matches(regex1,formula)){
            // On split sur la lettre 'd'
            String[] values = formula.split("d");

            // Si la values[0] n'est pas empty (le chiffre avant la lettre d) on attribue le chiffre au nombre de roll, sinon on met 1 par défaut
            // On oublie pas de parser le string vers un Int pour pouvoir l'utiliser plus tard
            if (!values[0].isEmpty()){
                this.nbRoll = Integer.parseInt(values[0]);
            }else{
                this.nbRoll = 1;
            }
            // La valeur du dé est toujours contenue après la lettre 'd' donc dans values[1], on l'attribue donc à diceValue
            this.diceValue = Integer.parseInt(values[1]);
        }

        // Si on match sur le second cas
        if(Pattern.matches(regex2, formula)){
            // On split encore sur la lettre 'd'
            String[] values = formula.split("d");

            // Même principe, la values[0] est le nombre de dé ou est vide, si c'est vide on la met à 1 par défaut
            if (values[0].isEmpty()){
                this.nbRoll = 1;
            }else{
                this.nbRoll = Integer.parseInt(values[0]);
            }

            // Ensuite, comme on est dans le second cas, il y a un modificateur, on vérifie sur le modificateur est un + ou un -
           if (values[1].indexOf('-') > 0){
               // On split sur le -, la valeur du dé est forcément avant le - et la valeur du modificateur, après le -, on oublie pas de parser vers des int
               String[] values2 = values[1].split("-");
               this.diceValue = Integer.parseInt(values2[0]);
               this.modifier = Integer.parseInt(values2[1]);
               this.modifier *= -1;
           }

            if (values[1].indexOf('+') > 0){
                /* On split sur le +, la valeur du dé est forcément avant le - et la valeur du modificateur, après le -, on oublie pas de parser vers des int
                 * Comme le split va split sur une regex, il faut échapper le + pour pouvoir split dessus.
                 */
                String[] values2 = values[1].split("\\+");
                this.diceValue = Integer.parseInt(values2[0]);
                this.modifier = Integer.parseInt(values2[1]);
           }
        }
    }

    // Constructeur ou tous les paramètres sont fournis directement, il suffit simplement de les attribuer.
    public Roll(int diceValue, int nbRoll, int modifier) {
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;
    }

    // Fonction qui lance le dé en utilisant les attributs
    public int makeRoll(RollType rollType) {
        // On crée une variable total qui contiendra la valeur totale du lancer
        int total = 0;

        // si le dé ou le nombre de lancers est <= 0 on renvoit directement -1, cela ne sert à rien de lancer.
        if (this.diceValue <= 0 || this.nbRoll <= 0){
            return -1;
        }

        // On crée une boucle for sur le nombre de lancers
        for (int i = 0; i < this.nbRoll; i++){
            // On crée un objet Dice avec la value du dé
            Dice d = new Dice(this.diceValue);

            // On lance d'office deux dés, le deuxième ne sera utilisé que dans le cas d'un lancer avec un type
            int firstRoll = d.rollDice();
            int secondRoll = d.rollDice();

            /*
             * En fonction du type de lancer :
             * Lancer normal : On ajoute simplement le lancer 1
             * Lancer avantage : On compare les deux lancers et on ajoute le meilleur
             * Lancer désavantage : On compare les deux lancers et on ajoute le moins bon
             */
            switch(rollType){
                case NORMAL:
                    total += firstRoll;
                    break;
                case ADVANTAGE:
                    total += firstRoll > secondRoll ? firstRoll:secondRoll;
                    break;
                case DISADVANTAGE:
                    total += firstRoll < secondRoll ? firstRoll:secondRoll;
                    break;
            }
        }

        // On oublie pas de rajouter le modifier au total à la toute fin
        total += this.modifier;

        // Si le total est inférieur à 0, on retourne 0, sinon on retourne le total
        return total < 0 ? 0:total;
    }

}
