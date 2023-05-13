import java.util.Objects;

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
        int mode = parseArgs(args);
        Engine engine = Engine.getEngine();
        System.out.println("Simulation Started");
        engine.start(mode);
        System.out.println("Simulation Ended\n" +
                "You may use MATLAB to run plot_numerical_results.m to plot the numerical results produced.");
    }

    /**
     * Parse the command line arguments.
     *
     * @param args the command line arguments
     * @return an integer indicating the mode
     */
    private static int parseArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Please provide the -m option and specify the mode.");
            System.exit(1);
        }

        String option = args[0];
        String mode = args[1];

        if (!Objects.equals(option, "-m")) {
            System.out.println("The only allowable option currently is -m.");
            System.exit(1);
        }

        if (!Objects.equals(mode, "0") && !Objects.equals(mode, "1")) {
            System.out.println("Please specify a supported mode, i.e., 0 (original) or 1 (extended).");
            System.exit(1);
        }

        return Integer.parseInt(mode);
    }
}
