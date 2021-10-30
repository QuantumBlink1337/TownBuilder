package TownBuilder;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import java.util.Scanner;

public class DebugTools {
    private static final int verbose = 0;
    private static final boolean debug = false;

    public static void logging(String string, int verbosity) {
        if (verbose == verbosity && debug) {
            System.out.print(Ansi.colorize("DEBUGGING:", Attribute.RED_TEXT(), Attribute.BOLD()));
            System.out.println(string);
        }
    }
}
