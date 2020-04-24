package src;
import java.lang.*;

public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes
    String formula;

    public Roll(String formula) {
        // TODO
        this.formula = formula;
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        // TODO
        this(""+nbRoll+"d"+diceValue+(modifier == 0 ? "": (modifier > 0 ? "+":"-")+Math.abs(modifier)));
    }

    public int makeRoll(RollType rollType) {
        // TODO
        int[] tab;
        int res = 0;
        int res2 = 0;

        tab = formulaTrad(this.formula);
        if(tab[0]==-1|| tab[0]==0|| tab[1] == 0){return -1;} //-1 = erreur formule, 0 lancer ou valeur
        Dice dice = new Dice(tab[1]);
        for(int i = 0; i<tab[0]; i++) { // on ajoute au résultat final les résultats des lancers
            res+= dice.rollDice();
        }
        if(rollType != RollType.NORMAL){ //s'il y a avantage (max des lancers) ou désavantage (min des lancers)
            for(int i = 0; i<tab[0]; i++) {
                res2+= dice.rollDice();//deuxième lancer
            }
            if(rollType == RollType.ADVANTAGE){
                res = Math.max(res,res2);
            }else{res = Math.min(res,res2);}
        }
        res+=tab[2];//ajout du modificateur
        return (Math.max(res, 0));
    }

    private int[] formulaTrad(String formula) {
        //tableau de la formule (nb de lancers, valeur du dé, modificateur)
        //initialisés à n'importe quel nombres sauf le modificateur dans le cas où il n'y en aurait pas
        int[] tab = {0, 0, 0};
        String val = "";//valeur qui sera convertie en int
        if (formula.length() <= 1) {// si on ne met rien ou 1 caractère on met une erreur.
            tab[0] = -1;
            return tab;
        }
        if (formula.charAt(0) == '-') {//si le premier caratère est un '-', lancer négatif -> erreur
            tab[0] = -1;//a été séparé de l'erreur précédente pour les cas de chaîne vide
            return tab;
        }
        int i = 0;// curseur
        while(formula.charAt(i)!='d'){//tant qu'on ne voit pas le 'd'
            if(i>=formula.length()||!Character.isDigit(formula.charAt(i))){
                tab[0] = -1;//si le caractère n'est pas un nombres ou si on a atteint la fin de la formule
                return tab;
            }
            val+=formula.charAt(i);//on ajoute les caractères numériques
            i++;
        }
        i++;//on passe le curseur après 'd'
        if(val.length()==0)val="1";//si 'd' est seul, on a alors qu'un lancer
        tab[0] = Integer.parseInt(val,10);//conversion string -> int
        val = "";
        if(i>=formula.length()) {// si il n'y a rien après le 'd'
            tab[0] = -1;
            return tab;
        }
        while(formula.charAt(i)!='-'&&formula.charAt(i)!='+'){//jusqu'à ce qu'on atteingne le modificateur
            if(!Character.isDigit(formula.charAt(i))){//si le caractère n'est pas un nombres(hormis '-' et '+')
                tab[0] = -1;
                return tab;
            }
            val+=formula.charAt(i);
            i++;
            if(i>=formula.length())break;//s'il n'y a pas de modificateur on sors de la boucle
        }
        if(val.length()==0){//s'il y a un modificateur mais pas de valeur pour le dé
            tab[0] = -1;
            return tab;
        }
        tab[1] = Integer.parseInt(val,10);
        val = "";
        if(i<formula.length()){//s'il y a un signe de modificateur
            val+=formula.charAt(i);//on met '+' ou '-' au début de val parce qu'on sait qu'il existe
            i++;
            if(formula.length()<=i){//s'il n'y a rien après le signe
                tab[0] = -1;
                return tab;
            }
            while(i<formula.length()){//jusqu'à le fin de la formule
                if(!Character.isDigit(formula.charAt(i))){//si le charactère n'est pas un chiffre
                    tab[0] = -1;
                    return tab;
                }
                val +=formula.charAt(i);
                i++;
            }
            tab[2]=Integer.parseInt(val,10);
        }
        return tab;// on retourne le tableau,
    }
}
