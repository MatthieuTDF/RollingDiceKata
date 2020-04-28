package src;


public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    private int nbRoll;
    private int modifier;
    //private int diceValue;
    private Dice dice;
    // Attributes

    public Roll(String formula) {
        String regex = "\\d{0,1}[d]\\d{1,2}(\\-\\d{1,2}|\\+\\d{1,2})?";
        if(formula.matches(regex))
        {
            if (formula.length() >= 3) // Handle modifier
            {
                //code
            }
            else
            {
                if(formula.charAt(0) == 'd')
                {
                    this.nbRoll=1;
                    this.dice=new Dice(Integer.parseInt(""+formula.charAt(1)));
                }
                else
                {
                    this.nbRoll= Integer.parseInt(""+formula.charAt(0));
                    this.dice=new Dice(Integer.parseInt(""+formula.charAt(2)));
                }
            }
            this.modifier=0;

        }
        else
        {
            this.nbRoll=0;
            this.dice=new Dice(0);
        }

    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        //this.diceValue=diceValue
        this.dice=new Dice(diceValue);
        //this.diceValue=diceValue;
        this.nbRoll=nbRoll;
        this.modifier=modifier;
    }

    public int makeRoll(RollType rollType) {
        // TODO
        int premier = calc(this);
        int deuxieme = calc(this);

        switch(rollType) {
            case NORMAL:
                return premier;
            case ADVANTAGE:
                if (premier > deuxieme)
                {
                    return premier;
                }
                return deuxieme;
            case DISADVANTAGE:
                if (premier > deuxieme)
                {
                    return deuxieme;
                }
                return premier;
            default:
                return -1;
        }
    }
    private int calc(Roll roll)
    {
        int value=0;
        if (roll.nbRoll <= 0 || roll.dice.getValue() <= 0)
        {
            return -1;
        }
        else
        {
            for(int i=0;i<nbRoll;i++)
            {
                value+=roll.dice.rollDice();
            }
            value+=roll.modifier;
            if(value < 0)
            {
                return 0;
            }
            return value;
        }
    }




}
