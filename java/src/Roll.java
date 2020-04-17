package src;

import Random.RNGMock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private boolean fomulaCheck = true;
    private static Pattern pattern;
    private static Matcher matcher;

    public Roll(String formula) {
        Pattern p = Pattern.compile("([1-9][0-9]*)?d([1-9][0-9]*)([+-][1-9][0-9]*)?");
        Matcher m = p.matcher(formula);
        if(m.matches()){
            this.diceValue = Integer.parseInt(m.group(2));
            this.nbRoll = m.group(1) != null ? Integer.parseInt(m.group(1)) : 1 ;
            this.modifier = m.group(3) != null ? Integer.parseInt(m.group(3)) : 0;
        }else{
            this.fomulaCheck = false;
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        if(diceValue <= 0 || nbRoll <= 0){
            this.fomulaCheck = false;
        }else{
            this.diceValue = diceValue;
            this.nbRoll = nbRoll;
            this.modifier = modifier;
        }
    }

    public int makeRoll(RollType rollType) {
        if (this.fomulaCheck){
            Dice dice = new Dice(this.diceValue);
            int[] playsValues = new int[2];
            int result = 0;
            int plays = rollType != RollType.NORMAL ? 2 : 1;
            for(int playNum = 0; playNum < plays; playNum++){
                result = 0;
                for(int rollNum = 0; rollNum < this.nbRoll; rollNum++){
                    result += dice.rollDice();
                }
                playsValues[playNum] = result = result + this.modifier < 0 ? 0 : result + this.modifier;
            }
            if(rollType == RollType.ADVANTAGE){
                return playsValues[0] <= playsValues[1] ? playsValues[1] : playsValues[0];
            }else if (rollType == RollType.DISADVANTAGE){
                return playsValues[0] <= playsValues[1] ? playsValues[0] : playsValues[1];
            }else{
                return result;
            }
        }
        return -1;
    }
}
