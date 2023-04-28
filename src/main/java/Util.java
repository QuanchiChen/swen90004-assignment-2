import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class consisting of helper functions.
 *
 * @author team 3
 */
public final class Util {
    /**
     * A private constructor to prevent developers from accidentally instantiating the utility class.
     */
    private Util() {
    }

    /**
     * Return a pseudorandom integer between zero (inclusive) and the specified bound (exclusive).
     * This method mimics the behaviour of <a href="https://ccl.northwestern.edu/netlogo/docs/dict/random.html">random</a> in NetLogo.
     *
     * @param bound the upper bound (exclusive)
     * @return a pseudorandom integer between zero (inclusive) and the bound (exclusive)
     */
    public static int random(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
