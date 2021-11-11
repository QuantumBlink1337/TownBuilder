package TownBuilder.DebugApps;

import TownBuilder.Buildings.Building;
import TownBuilder.Utility;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

import java.util.ArrayList;


public class DebugTools {
    /*
        0 = no logging at all
        1 = notifications of protocols triggering
        2 = notification of successful conditions of a protocol
        3 = notifications of everything

        each level of verbosity supersedes the next (e.g. verbosity of 2 also includes 1)

     */
    private static final int verbose = 3;
    private static final AnsiFormat textColor = new AnsiFormat(Attribute.RED_TEXT(), Attribute.BOLD());

    public static void logging(String string, int verbosity) {
        if (verbose >= verbosity) {
            System.out.print(Ansi.colorize("DEBUGGING: ", textColor));
            System.out.println(string);
        }
    }
    public static <T> void printMembersOfArrayList(ArrayList<T> arrayList, int verbosity, String optionalMessage) {
        if (verbose >= verbosity) {
            if (optionalMessage != null) {
                logging(optionalMessage, verbosity);
            }
            System.out.println(Ansi.colorize("DEBUGGING: ", textColor));
            Utility.printMembersOfArrayList(arrayList);
        }
    }
    public static String buildingInformation(Building building) {
        return building.getType() + " at row: " + building.getRow() + " and column: " + building.getCol() + " Condition: " + building.getCondition();
    }
}
