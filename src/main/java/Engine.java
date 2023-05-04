import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The engine consists of people and a grid of patches.
 *
 * @author team 3
 */
public class Engine {
    // A grid of patches
    private final Patch[][] grid = new Patch[Params.MAX_COORDINATE + 1][Params.MAX_COORDINATE + 1];
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
     * Start the engine.
     */
    public void start() {
        calculateNumericalValues();
    }
}
