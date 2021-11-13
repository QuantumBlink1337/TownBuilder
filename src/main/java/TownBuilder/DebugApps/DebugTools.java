package TownBuilder.DebugApps;

import TownBuilder.Buildings.Building;
import TownBuilder.Utility;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class DebugTools {
    /*
        0 = no logging at all
        1 = notifications of protocols triggering
        2 = notification of successful conditions of a protocol
        3 = notifications of everything

        each level of verbosity supersedes the next (e.g. verbosity of 2 also includes 1)

     */
    private static final File log = new File("log.txt");
    private static BufferedWriter bufferedWriter = null;
    private static final int verbose = 3;
    private static final AnsiFormat textColor = new AnsiFormat(Attribute.RED_TEXT(), Attribute.BOLD());
    static {
        initFile();
    }

    public static void initFile() {
        try {
            if (log.createNewFile()) {
                //logging("File created.", 2);
            }
            else {
                //logging("File already exists.", 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logging(String string, int verbosity) throws IOException {
        if (verbose >= verbosity) {
            System.out.print(Ansi.colorize("DEBUGGING: ", textColor));
            System.out.println(string);
        }
        bufferedWriter.write(string);
        bufferedWriter.newLine();

    }
    public static <T> String createStringOfObjectsInArray(T[] array) {
        String string = "";
        StringBuilder t = new StringBuilder(string);
        for (T object : array) {
            t.append(object);
            t.append(',');
        }
        return t.toString();

    }
    public static <T> void printMembersOfArrayList(ArrayList<T> arrayList, int verbosity, String optionalMessage) throws IOException {
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
