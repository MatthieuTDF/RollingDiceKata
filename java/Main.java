import Random.RNG;
import Random.RNGImpl;
import src.Roll;

public class Main {

    public static void main(String[] args) {
        RNG.setImpl(new RNGImpl());

        Roll r = new Roll("2d6-2");
        r.makeRoll(Roll.RollType.NORMAL);
    }
}
