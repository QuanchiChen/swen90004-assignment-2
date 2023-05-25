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
                                    "\t-p      <num-people>              the initial population size\n" +
                                    "\t-v      <max-vision>              the maximum vision\n" +
                                    "\t-me     <metabolism-max>          the maximum metabolism\n" +
                                    "\t-lmin   <life_expectancy_min>     the minimum life expectancy\n" +
                                    "\t-lmax   <life_expectancy_max>     the maximum life expectancy\n" +
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
                            int modeValue = Integer.parseInt(param);
                            if (modeValue != 0 && modeValue != 1) {
                                System.out.println("The execution mode must be either 0 or 1.");
                                System.exit(1);
                            }
                            argsMap.put("mode", modeValue);
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
                default:
                    System.out.println("Invalid Arguments");
                    System.exit(1);
            }
        }

        return argsMap;
    }
}
