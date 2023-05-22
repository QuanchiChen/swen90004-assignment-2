import java.util.*;

/**
 * Entry to run the program.
 *
 * @author team 3: Quanchi Chen
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
     * @return an integer indicating the mode
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
                            "Options:\n" +
                                    "-m <mode>: execution mode. 0 for original model and 1 for extended model.\n" +
                                    "-t <ticks>: number of ticks to run the model for."
                    );
                    System.exit(0);
                case "-m": // mode
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            argsMap.put("mode", Integer.parseInt(param));
                        } catch (NumberFormatException e) {
                            System.out.println("Expect an integer. Get: " + param);
                            System.exit(1);
                        }
                    } else {
                        System.out.println("No mode provided.");
                        System.exit(1);
                    }
                    break;
                case "-t": // ticks
                    if (iterator.hasNext()) {
                        param = iterator.next();
                        try {
                            int ticks = Integer.parseInt(param);
                            if (ticks <= 0) {
                                System.out.println("ticks must be positive.");
                                System.exit(1);
                            }
                            argsMap.put("ticks", ticks);
                        } catch (NumberFormatException e) {
                            System.out.println("Expect an integer. Get: " + param);
                            System.exit(1);
                        }
                    } else {
                        System.out.println("No number of ticks provided.");
                        System.exit(1);
                    }
                    break;
                default:
                    System.out.println("Invalid arguments.");
                    System.exit(1);
            }
        }

        return argsMap;
    }
}
