import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Allow people to interact with a grid of patches that consists of 33 x 33 patches.
 * The coordinate of the left-bottom patch is (0, 0).
 *
 * @author team 3
 */
public class Engine {
    // A grid of patches
    private final Patch[][] grid = new Patch[Params.MAX_COORDINATE + 1][Params.MAX_COORDINATE + 1];
    // People in the grid
    private final List<Person> people = new ArrayList<>(Params.NUM_PEOPLE);

    public Engine() {
        init();
    }

    /**
     * Initialise the grid of patches according to the setup-patches and setup-turtles procedures.
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

        for (int i = 0; i < Params.NUM_PEOPLE; i++)
            people.add(new Person());
    }

    /**
     * Start the engine.
     */
    public void start() {
        for (int tick = 0; tick < Params.MAX_TICK; tick++) {
            calculateNumericalValues();

            if (tick % Params.GRAIN_GROWTH_INTERVAL == 0)
                growGrain();
        }
    }

    /**
     * Move a person one patch in the most profitable direction.
     *
     * @param person a Person instance
     */
    private void movePerson(Person person) {
        int vision = person.getVision();
        int x = person.getX();
        int y = person.getY();

        int totalGrainAheadNorth = calculateTotalGrainAhead(vision, x, y, Direction.NORTH);
        int totalGrainAheadSouth = calculateTotalGrainAhead(vision, x, y, Direction.SOUTH);
        int totalGrainAheadWest = calculateTotalGrainAhead(vision, x, y, Direction.WEST);
        int totalGrainAheadEast = calculateTotalGrainAhead(vision, x, y, Direction.EAST);

        int maxTotalGrainAhead = totalGrainAheadNorth;
        Direction dir = Direction.NORTH;

        if (totalGrainAheadSouth > maxTotalGrainAhead) {
            maxTotalGrainAhead = totalGrainAheadSouth;
            dir = Direction.SOUTH;
        }
        if (totalGrainAheadWest > maxTotalGrainAhead) {
            maxTotalGrainAhead = totalGrainAheadWest;
            dir = Direction.WEST;
        }
        if (totalGrainAheadEast > maxTotalGrainAhead) {
            dir = Direction.EAST;
        }

        switch (dir) {
            case NORTH:
                person.setY(y + 1);
            case SOUTH:
                person.setY(y - 1);
            case WEST:
                person.setX(x - 1);
            case EAST:
                person.setX(x + 1);
        }
    }

    /**
     * Calculate the total amount of grain ahead.
     * Note that the coordinate of the left-bottom patch is (0, 0).
     *
     * @param vision the vision of the person
     * @param x      the horizontal coordinate of the person
     * @param y      the vertical coordinate of the person
     * @param dir    the direction to check
     * @return the total amount of grain ahead in a direction
     */
    private int calculateTotalGrainAhead(int vision, int x, int y, Direction dir) {
        int totalGrain = 0;

        if (dir == Direction.NORTH) {
            for (int i = 0; i < vision; i++) {
                if (y == Params.MAX_COORDINATE) // Check the grid border.
                    break;
                y++;
                totalGrain += grid[x][y].getGrain();
            }
        } else if (dir == Direction.SOUTH) {
            for (int i = 0; i < vision; i++) {
                if (y == 0) // Check the grid border.
                    break;
                y--;
                totalGrain += grid[x][y].getGrain();
            }
        } else if (dir == Direction.WEST) {
            for (int i = 0; i < vision; i++) {
                if (x == 0) // Check the grid border.
                    break;
                x--;
                totalGrain += grid[x][y].getGrain();
            }
        } else if (dir == Direction.EAST) {
            for (int i = 0; i < vision; i++) {
                if (x == Params.MAX_COORDINATE) // Check the grid border.
                    break;
                x++;
                totalGrain += grid[x][y].getGrain();
            }
        }

        return totalGrain;
    }

    /**
     * The grain of each patch grows.
     */
    private void growGrain() {
        for (int x = 0; x <= Params.MAX_COORDINATE; x++) {
            for (int y = 0; y <= Params.MAX_COORDINATE; y++) {
                Patch patch = grid[x][y];
                int grain = patch.getGrain();
                int maxGrain = patch.getMaxGrain();

                if (grain < maxGrain) {
                    grain += Params.NUM_GRAIN_GROWN;
                    if (grain > maxGrain) // Check if the amount of grain exceeds the patch's capacity.
                        grain = maxGrain;
                    patch.setGrain(grain);
                }
            }
        }
    }

    /**
     * Calculate the Gini index of the population and the number of lower-class, middle-class, and upper-class people.
     */
    private void calculateNumericalValues() {
        int maxWealth = Objects.requireNonNull(people.stream().max(Comparator.comparing(Person::getWealth))
                .orElse(null)).getWealth();

        int numUp = 0;
        int numMid = 0;
        int numLow = 0;

        List<Double> wealthList = new ArrayList<>();

        // Calculate the numbers of lower-class, middle-class, and upper-class people
        // based on the recolor-turtles procedure.
        for (Person singlePerson : people) {
            int wealth = singlePerson.getWealth();
            if (wealth <= maxWealth / 3)
                numLow++;
            else if (wealth <= maxWealth / 3 * 2)
                numMid++;
            else
                numUp++;
            wealthList.add((double) wealth);
        }

        System.out.println(numUp + " " + numMid + " " + numLow);
        System.out.println(calculateGiniIndex(wealthList));
    }

    /**
     * Calculate the Gini index of the current population.
     *
     * @param wealthList a list consisting of the wealth of each person in the population
     * @return the Gini index of the population
     */
    private double calculateGiniIndex(List<Double> wealthList) {
        double sumOfDifference = wealthList.stream()
                .flatMapToDouble(v1 -> wealthList.stream().mapToDouble(v2 -> Math.abs(v1 - v2))).sum();
        double mean = wealthList.stream().mapToDouble(v -> v).average().orElse(0.0);
        return sumOfDifference / (2 * wealthList.size() * wealthList.size() * mean);
    }

    /**
     * Directions.
     */
    public enum Direction {
        NORTH, SOUTH, WEST, EAST
    }
}
