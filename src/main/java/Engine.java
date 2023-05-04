/**
 * The engine consists of people and a grid of patches.
 *
 * @author team 3
 */
public class Engine {
    // A grid of patches
    private final Patch[][] grid = new Patch[Params.MAX_COORDINATE + 1][Params.MAX_COORDINATE + 1];

    public Engine() {
        init();
    }

    /**
     * Initialise the grid of patches according to the setup-patches procedure.
     */
    private void init() {
        for (int x = 0; x <= Params.MAX_COORDINATE; x++) {
            for (int y = 0; y <= Params.MAX_COORDINATE; y++) {
                grid[x][y] = new Patch();
            }
        }

        for (int i = 0; i < 5; i++)
            Util.diffuse(grid, 0.25);

        for (int i = 0; i < 10; i++)
            Util.diffuse(grid, 0.25);

        for (int x = 0; x <= Params.MAX_COORDINATE; x++) {
            for (int y = 0; y <= Params.MAX_COORDINATE; y++) {
                grid[x][y].setMaxGrain(grid[x][y].getGrain());
            }
        }
    }

    public void start() {
        for (int x = 0; x <= Params.MAX_COORDINATE; x++) {
            for (int y = 0; y <= Params.MAX_COORDINATE; y++) {
                System.out.println(grid[x][y].getGrain() + "   " + grid[x][y].getMaxGrain());
            }
        }
    }
}
