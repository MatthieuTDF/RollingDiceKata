package src;

import Random.RNG;

public class Dice {
    private int value;

    public Dice(int value) {
        this.value = value;
    }

    public int rollDice() {
        return RNG.random(value);
    }

    public int getValue() {
        return value;
    }

}
