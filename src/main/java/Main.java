import java.util.*;

/**
 * Entry to run the program.
 *
 * @author team 3: Quanchi Chen, Yijie Xie
 */
public class Main {
    /**
     * The entry to run the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Integer> argsMap = parseArgs(args);
        Integer mode = argsMap.get("mode");
        Integer ticks = argsMap.get("ticks");

        if (Params.LIFE_EXPECTANCY_MIN > Params.LIFE_EXPECTANCY_MAX) {
            System.out.println("The maximum life expectancy must be greater than or equal to the minimum life expectancy.");
            System.out.println("The default maximum life expectancy is 83, and the default minimum life expectancy is 1.");
            System.out.println("Please check the command-line arguments you passed to the program.");
            System.exit(1);
        }

        Engine engine = Engine.getEngine();

        if (mode == null || mode == 0) {
            mode = 0;
            System.out.println("Simulation Started (Original Model)");
        } else {
            System.out.println("Simulation Started (Extended Model)");
        }

        if (ticks == null) {
            ticks = Params.NUM_TICKS;
        }
        System.out.println("Number of ticks: " + ticks);

        engine.start(mode, ticks);
        System.out.println("Simulation Ended\n" +
                "You may use MATLAB to run plot_numerical_results.m to plot the numerical results produced.");
    }

    /**
     * Parse the command line arguments.
     *
     * @param args the command line arguments
     * @return the map storing parsed command line arguments
     */
    private static Map<String, Integer> parseArgs(String[] args) {
        Map<String, Integer> argsMap = new HashMap<>();
        Iterator<String> iterator = Arrays.stream(args).iterator();
        while (iterator.hasNext()) {
            String option = iterator.next();
            String param;
            switch (option) {
                case "-h": // help
                    System.out.println(
                            "Global Options:\n" +
                                    "\t-m      <mode>                    the execution mode (0: original; 1: extended)\n" +
                                    "\t-t      <ticks>                   the number of clock ticks the model will run\n" +
                                    "\t-p      <num-people>              the initial population size [2,1000]\n" +
                                    "\t-v      <max-vision>              the maximum vision          [1,15]\n" +
                                    "\t-me     <metabolism-max>          the maximum metabolism      [1,25]\n" +
                                    "\t-lmin   <life_expectancy_min>     the minimum life expectancy [1,100]\n" +
                                    "\t-lmax   <life_expectancy_max>     the maximum life expectancy [1,100]\n" +
                                    "\t-pe     <percent-best-land>       the percentage of best lands\n" +
                                    "\t-mg     <max-grain>               the maximum amount of grain each patch can grow\n" +
                                    "\t-g      <grain-growth-interval>   the interval in which the grain grows\n" +
                                    "\t-n      <num-grain-grown>         the amount of grain grown on each patch"
                    );
                    System.exit(0);
                case "-m": // mode
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int mode = Integer.parseInt(param);
                            if (mode != 0 && mode != 1) {
                                System.out.println("The execution mode must be either 0 or 1.");
                                System.exit(1);
                            }
                            argsMap.put("mode", mode);
                        } catch (NumberFormatException e) {
                            System.out.println("The execution mode must be either 0 or 1.");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Please specify the execution mode (0 or 1) following the -m option.");
                        System.exit(1);
                    }
                    break;
                case "-t": // ticks
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int ticks = Integer.parseInt(param);
                            if (ticks <= 0) {
                                System.out.println("The number of clock ticks must be a positive integer.");
                                System.exit(1);
                            }
                            argsMap.put("ticks", ticks);
                        } catch (NumberFormatException e) {
                            System.out.println("The number of clock ticks must be a positive integer.");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Please specify the number of clock ticks following the -t option.");
                        System.exit(1);
                    }
                    break;
                case "-p":
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int numPeople = Integer.parseInt(param);
                            if (numPeople < 2 || numPeople > 1000) {
                                System.out.println("The number of people must be a positive integer ranging from 2 to 1000.");
                                System.exit(1);
                            }
                            Params.NUM_PEOPLE = numPeople;
                        } catch (NumberFormatException e) {
                            System.out.println("The number of people must be a positive integer ranging from 2 to 1000.");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Please specify the number of people following the -p option.");
                        System.exit(1);
                    }
                    break;
                case "-v":
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int maxVision = Integer.parseInt(param);
                            if (maxVision < 1 || maxVision > 15) {
                                System.out.println("The maximum vision must be a positive integer ranging from 1 to 15.");
                                System.exit(1);
                            }
                            Params.MAX_VISION = maxVision;
                        } catch (NumberFormatException e) {
                            System.out.println("The maximum vision must be a positive integer ranging from 1 to 15.");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Please specify the maximum vision following the -v option.");
                        System.exit(1);
                    }
                    break;
                case "-me":
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int metabolismMax = Integer.parseInt(param);
                            if (metabolismMax < 1 || metabolismMax > 25) {
                                System.out.println("The maximum metabolism must be a positive integer ranging from 1 to 25.");
                                System.exit(1);
                            }
                            Params.METABOLISM_MAX = metabolismMax;
                        } catch (NumberFormatException e) {
                            System.out.println("The maximum metabolism must be a positive integer ranging from 1 to 25.");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Please specify the maximum metabolism following the -me option.");
                        System.exit(1);
                    }
                    break;
                case "-lmin":
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int lifeExpectancyMin = Integer.parseInt(param);
                            if (lifeExpectancyMin < 1 || lifeExpectancyMin > 100) {
                                System.out.println("The minimum life expectancy must be a positive integer ranging from 1 to 100.");
                                System.exit(1);
                            }
                            Params.LIFE_EXPECTANCY_MIN = lifeExpectancyMin;
                        } catch (NumberFormatException e) {
                            System.out.println("The minimum life expectancy must be a positive integer ranging from 1 to 100.");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Please specify the minimum life expectancy following the -lmin option.");
                        System.exit(1);
                    }
                    break;
                case "-lmax":
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int lifeExpectancyMax = Integer.parseInt(param);
                            if (lifeExpectancyMax < 1 || lifeExpectancyMax > 100) {
                                System.out.println("The maximum life expectancy must be a positive integer ranging from 1 to 100.");
                                System.exit(1);
                            }
                            Params.LIFE_EXPECTANCY_MAX = lifeExpectancyMax;
                        } catch (NumberFormatException e) {
                            System.out.println("The maximum life expectancy must be a positive integer ranging from 1 to 100.");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Please specify the maximum life expectancy following the -lmax option.");
                        System.exit(1);
                    }
                    break;
                default:
                    System.out.println("Invalid Arguments");
                    System.exit(1);
            }
        }

        return argsMap;
    }
}
