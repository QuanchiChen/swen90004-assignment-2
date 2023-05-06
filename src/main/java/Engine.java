import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    // A list consisting of the Gini index of the population at each clock tick
    private final List<Double> giniIndexList = new ArrayList<>();
    // The number of upper-class people at each tick
    private final List<Integer> numUpList = new ArrayList<>();
    // The number of middle-class people at each tick
    private final List<Integer> numMidList = new ArrayList<>();
    // The number of lower-class people at each tick
    private final List<Integer> numLowList = new ArrayList<>();

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
        for (int tick = 0; tick <= Params.MAX_TICK; tick++) {
            harvest();
            moveEatAgeDie();

            if (tick % Params.GRAIN_GROWTH_INTERVAL == 0)
                growGrain();

            calculateNumericalValues();
        }

        writeToFile();
    }

    /**
     * Each person harvests the grain on the occupied patch.
     */
    private void harvest() {
        for (Person person : people) {
            int x = person.getX();
            int y = person.getY();
            person.setWealth(person.getWealth() + grid[x][y].getGrain() / getNumPeople(x, y));
        }

        for (Person person : people) {
            int x = person.getX();
            int y = person.getY();
            grid[x][y].setGrain(0);
        }
    }

    /**
     * Find the number of people in a particular patch.
     *
     * @param x the horizontal coordinate of the patch
     * @param y the vertical coordinate of the patch
     * @return the number of people in that patch
     */
    private int getNumPeople(int x, int y) {
        int numPeople = 0;

        for (Person person : people) {
            if (person.getX() == x) {
                if (person.getY() == y) {
                    numPeople++;
                }
            }
        }

        return numPeople;
    }

    /**
     * Each person moves one patch in the most profitable direction and consumes some grain.
     * A person dies and produces a single offspring when running out of lifespan or wealth.
     */
    public void moveEatAgeDie() {
        for (Person person : people) {
            movePerson(person);
            person.setWealth(person.getWealth() - person.getMetabolism());
            person.setAge(person.getAge() + 1);
            if (person.getWealth() < 0 || person.getAge() > person.getLifeExpectancy())
                person.reset();
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
                if (y + 1 <= Params.MAX_COORDINATE)
                    person.setY(y + 1);
            case SOUTH:
                if (y - 1 >= 0)
                    person.setY(y - 1);
            case WEST:
                if (x - 1 >= 0)
                    person.setX(x - 1);
            case EAST:
                if (x + 1 <= Params.MAX_COORDINATE)
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
        for (Person person : people) {
            int wealth = person.getWealth();
            if (wealth <= maxWealth / 3)
                numLow++;
            else if (wealth <= maxWealth / 3 * 2)
                numMid++;
            else
                numUp++;
            wealthList.add((double) wealth);
        }

        numUpList.add(numUp);
        numMidList.add(numMid);
        numLowList.add(numLow);
        giniIndexList.add(calculateGiniIndex(wealthList));
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
     * Write the Gini index of the population and the number of lower-class, middle-class, and upper-class people
     * in each clock tick to a file named data.csv.
     */
    public void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv"));
            int maxTick = Params.MAX_TICK;

            for (int tick = 0; tick <= maxTick; tick++) {
                if (tick < maxTick)
                    writer.write(tick + ",");
                else
                    writer.write(Integer.toString(tick));
            }
            writer.newLine();

            for (int i = 0; i <= maxTick; i++) {
                if (i < maxTick)
                    writer.write(giniIndexList.get(i) + ",");
                else
                    writer.write(giniIndexList.get(i).toString());
            }
            writer.newLine();

            writeOneLine(writer, numUpList);
            writeOneLine(writer, numMidList);
            writeOneLine(writer, numLowList);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeOneLine(BufferedWriter writer, List<Integer> list) {
        int maxTick = Params.MAX_TICK;
        try {
            for (int i = 0; i <= maxTick; i++) {
                if (i < maxTick)
                    writer.write(list.get(i) + ",");
                else
                    writer.write(list.get(i).toString());
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Directions.
     */
    public enum Direction {
        NORTH, SOUTH, WEST, EAST
    }
}
