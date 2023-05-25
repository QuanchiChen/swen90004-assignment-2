/**
 * Parameter class used for setting the model.
 * Adjust the values of parameters in this class to observe different model behaviours.
 *
 * @author team 3: Quanchi Chen, Yijie Xie
 */
public final class Params {
    // The maximum clock tick
    public static int NUM_TICKS = 800; // 5000 is suggested for the extended model
    // The maximum horizontal and vertical coordinate
    // The coordinates range from 0 to MAX_COORDINATE inclusively.
    public static int MAX_COORDINATE = 50;
    // The initial number of people
    public static int NUM_PEOPLE = 250;
    // The shortest number of clock ticks a person could live
    public static int LIFE_EXPECTANCY_MIN = 1;
    // The longest number of clock ticks a person could live
    public static int LIFE_EXPECTANCY_MAX = 83;
    // The furthest possible distance that any person could see
    public static int MAX_VISION = 5;
    // The highest possible amount of grain a person could eat per clock tick
    public static int METABOLISM_MAX = 15;
    // The initial density of patches seeded with the maximum amount of grain
    public static int PERCENT_BEST_LAND = 10;
    // The maximum amount of grain any patch can hold
    public static int MAX_GRAIN = 50;
    // The interval in which the grain grows
    public static int GRAIN_GROWTH_INTERVAL = 1;
    // The amount of grown grain per GRAIN_GROWTH_INTERVAL
    public static int NUM_GRAIN_GROWN = 4;
}
