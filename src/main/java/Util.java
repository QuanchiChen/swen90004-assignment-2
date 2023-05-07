import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class consisting of helper functions.
 *
 * @author team 3: Quanchi Chen
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

    /**
     * Diffuse the gain of each patch to its eight neighbours.
     * This method mimics the behaviour of <a href="https://ccl.northwestern.edu/netlogo/docs/dict/diffuse.html">diffuse</a> in NetLogo.
     *
     * @param grid           a grid of patches
     * @param diffusionRatio a number between 0 and 1
     */
    public static void diffuse(Patch[][] grid, double diffusionRatio) {
        Double[][] gridDelta = new Double[Params.MAX_COORDINATE + 1][Params.MAX_COORDINATE + 1];

        for (int x = 0; x <= Params.MAX_COORDINATE; x++) {
            for (int y = 0; y <= Params.MAX_COORDINATE; y++) {
                gridDelta[x][y] = (double) 0;
            }
        }

        for (int x = 0; x <= Params.MAX_COORDINATE; x++) {
            for (int y = 0; y <= Params.MAX_COORDINATE; y++) {
                double deltaGrain = grid[x][y].getGrain() * diffusionRatio / 8;
                gridDelta[wrap(x - 1)][wrap(y - 1)] += deltaGrain;
                gridDelta[wrap(x - 1)][wrap(y)] += deltaGrain;
                gridDelta[wrap(x - 1)][wrap(y + 1)] += deltaGrain;
                gridDelta[wrap(x)][wrap(y - 1)] += deltaGrain;
                gridDelta[wrap(x)][wrap(y + 1)] += deltaGrain;
                gridDelta[wrap(x + 1)][wrap(y - 1)] += deltaGrain;
                gridDelta[wrap(x + 1)][wrap(y)] += deltaGrain;
                gridDelta[wrap(x + 1)][wrap(y + 1)] += deltaGrain;
            }
        }

        for (int x = 0; x <= Params.MAX_COORDINATE; x++) {
            for (int y = 0; y <= Params.MAX_COORDINATE; y++) {
                double newGrain = grid[x][y].getGrain() * (1 - diffusionRatio) + gridDelta[x][y];
                grid[x][y].setGrain((int) Math.round(newGrain));
            }
        }
    }

    /**
     * Check if a coordinate is out of the bound.
     *
     * @param coordinate a horizontal or vertical coordinate
     * @return the original coordinate if not out of the bound
     */
    private static int wrap(int coordinate) {
        if (coordinate == -1)
            return Params.MAX_COORDINATE - 1;
        else if (coordinate == Params.MAX_COORDINATE + 1)
            return 0;
        else
            return coordinate;
    }
}
