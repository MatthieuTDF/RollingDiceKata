package src;


import javax.swing.tree.DefaultTreeCellEditor;
import java.util.regex.Pattern;

public class Roll {

    int Value;
    int number;
    int modify;

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
                this.number = Integer.parseInt(tab[0]);
            }else{
                this.number = 1;
            }
             this.Value = Integer.parseInt(tab[1]);
        }

        if (Pattern.matches(regex2, formula)) {
            String[] tab = formula.split("d");
            if (!tab[0].isEmpty()) {
                this.number = Integer.parseInt(tab[0]);
            } else {
                this.number = 1;
            }

            if(tab[1].indexOf('-') > 0){
                String[] tab1 = tab[1].split("-");
                this.Value = Integer.parseInt(tab1[0]);
                this.modify = Integer.parseInt(tab1[1]);
                this.modify *= -1;
            }

            if(tab[1].indexOf('+') > 0){
                String[] tab2 = tab[1].split("\\+");
                this.Value = Integer.parseInt(tab2[0]);
                this.modify = Integer.parseInt(tab2[1]);
            }

        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.Value = diceValue; //valeur du dé
        this.number = nbRoll;// nombre de dé
        this.modify = modifier; // modificateur
    }

    public int makeRoll(RollType rollType) {

        int result =0;
        int resfinal =0;
        if (this.Value <= 0 || this.number <= 0){ //verifie si le nombre dé lancers et si la valeur du dé est negatif
            return -1;
        }

        Dice newDice = new Dice(this.Value);
        for (int i =0; i < number; i++){
            resfinal += newDice.rollDice();// ajout au resultat final les resulats des lancers
        }

        if (rollType != rollType.NORMAL ){ // verifie si c'est un lancer normal sinon
            for (int i =0; i < number; i++) {
                result += newDice.rollDice();
            }
            if (rollType == rollType.ADVANTAGE){ // prend le max des deux lancers si c'est advantage sinon prend le min
                resfinal = Math.max(resfinal, result);
            } else {
                resfinal = Math.min(resfinal,result);
            }
        }



        resfinal += this.modify; // ajoute le modifictateur

        return resfinal < 0 ? 0:resfinal; //si le res final est negatif return 0 sinon return le res final

    }

}
