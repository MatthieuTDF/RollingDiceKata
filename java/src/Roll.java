package src;

public class Roll {

    private int diceValue;
    private int nbRoll;
    private int modifier;

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes

    private boolean isANumber(String chaine, int position){
        if((int) chaine.charAt(position) >= 48 && (int) chaine.charAt(position) <= 57){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkString(String chaine){
        for(int i = 0; i < chaine.length(); i++){
            if(!isANumber(chaine, i) || (int) chaine.charAt(i) == 100 || ((int)chaine.charAt(i) == 43) || ((int)chaine.charAt(i) == 45)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAD(String chaine, int position){
        if((int) chaine.charAt(position) == 100 ){
            return true;
        }else {
            return false;
        }
    }

    private boolean isAPlus(String chaine, int position){
        if((int) chaine.charAt(position) == 43){
            return true;
        }else {
            return false;
        }
    }

    private boolean isAMinus(String chaine, int position){
        if((int) chaine.charAt(position) >= 45 ){
            return true;
        }else {
            return false;
        }
    }



    public Roll(String formula) {
        /* if(!checkString(formula)){
            this.nbRoll = -1;
            this.diceValue = -1;
            this.modifier = -1;
        }
        if(isANumber(formula, 0)){
             if(isAD(formula, 1)){
                 if(isANumber(formula, 2)){
                     if(isAPlus(formula, 3)){
                         if(isANumber(formula, 4)){
                             this.modifier = (int)formula.charAt(4) - '0';
                         }
                     }else if(isAMinus(formula, 3)){
                         if(isANumber(formula,4)){
                             this.modifier = (((int)formula.charAt(3) - '0')* -1);
                         }
                     }
                 }
             }

        }else if(isAD(formula, 0)){
            if(isANumber(formula, 1)){
                if(isAPlus(formula, 2)){
                    if(isANumber(formula, 3)){
                        this.modifier = (int)formula.charAt(4) - '0';
                    }
                }else if(isAMinus(formula, 2)){
                    if(isANumber(formula, 3)){
                        this.modifier = (((int)formula.charAt(3) - '0')* -1);
                    }
                }
            }
            this.nbRoll = 1;
        }*/
        //boolean error = false;

        /* 2 chars*/
        if (formula.length() < 3 && (int) formula.charAt(0) == 100) {
            this.nbRoll = 1;
            if (formula.charAt(1) == 54) {
                this.diceValue = (int) formula.charAt(1) - '0';
                this.modifier = 0;
            } else {
                this.diceValue = -1;
            }
        }
        /* 3 chars*/
        if (formula.length() < 4 && (int) formula.charAt(0) > 48 && (int) formula.charAt(0) <= 57) {
            if (formula.charAt(1) == 100 && (int) formula.charAt(2) == 54) {
                this.diceValue = (int) formula.charAt(2) - '0';
                this.modifier = 0;
            } else {
                this.diceValue = -1;
            }
        } else {
            this.nbRoll = -1;
        }

        /* 4 & 5 chars */
        if (formula.length() >= 4 && (int) formula.charAt(0) > 48 && (int) formula.charAt(0) <= 57) {
            this.nbRoll = (int) formula.charAt(0) - '0';
            if (formula.charAt(1) == 100 && formula.charAt(2) == 54) {
                this.diceValue = (int) formula.charAt(2) - '0';
                if ((int) formula.charAt(3) == 43 && (int) formula.charAt(4) > 48 && (int) formula.charAt(4) <= 57) {
                    this.modifier = (int) formula.charAt(4) - '0';
                } else {
                    this.modifier = -1;
                }
            } else {
                this.diceValue = -1;
            }
        } else {
            this.nbRoll = -1;
        }
    }

    public Roll(int diceValue, int nbRoll,  int modifier) {
        this.diceValue = diceValue;
        this.nbRoll = nbRoll;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        if (this.diceValue <= 0|| this.nbRoll <= 0 || this.modifier <= 0){
            return -1;
        }
        Dice myDice = new Dice(this.diceValue);
        int result = 0;
        for(int i = 0; i < this.nbRoll; i++){
            result += myDice.rollDice();
        }
        if (rollType == RollType.ADVANTAGE) {
            int result2 = 0;
            for (int i = 0; i < this.nbRoll; i++) {
                result2 += myDice.rollDice();
            }
            if (result > result2 && result + modifier >= 0) {
                return result + modifier;
            } else if (result2 + modifier >= 0){
                return result2 + modifier;
            }else{
                return -1;
            }
        }
        if (rollType == RollType.DISADVANTAGE) {
            int result3 = 0;
            for (int i = 0; i < this.nbRoll; i++) {
                result3 += myDice.rollDice();
            }
            if (result < result3 && result + modifier >= 0) {
                return result + modifier;
            } else if (result3 + modifier >= 0){
                return result3 + modifier;
            }else{
                return -1;
            }
        }
        if (result + modifier >= 0) {
            return result + modifier;
        }else{
            return -1;
        }
    }
}
