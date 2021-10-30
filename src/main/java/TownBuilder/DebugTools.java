package TownBuilder;

import TownBuilder.Buildings.Building;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;


public class DebugTools {
    /*
        0 = no logging at all
        1 = notifications of protocols triggering
        2 = notification of successful conditions of a protocol
        3 = notifications of everything

        each level of verbosity supersedes the next (e.g. verbosity of 2 also includes 1)

     */
    private static final int verbose = 3;

    public static void logging(String string, int verbosity) {
        if (verbose >= verbosity) {
            System.out.print(Ansi.colorize("DEBUGGING: ", Attribute.RED_TEXT(), Attribute.BOLD()));
            System.out.println(string);
        }
    }
    public static String buildingInformation(Building building) {
        return building.getType() + " at row: " + building.getRow() + " and column: " + building.getCol() + " Condition: " + building.getCondition();
    }
}
