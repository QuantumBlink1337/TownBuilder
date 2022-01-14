package TownBuilder.DebugApps;

import TownBuilder.Buildings.Building;
import TownBuilder.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class DebugTools {
    /*
        0 = no logging at all
        1 = notifications of protocols triggering
        2 = notification of successful conditions of a protocol
        3 = notifications of everything

        each level of verbosity supersedes the next (e.g. verbosity of 2 also includes 1)

     */
    private static final File log = new File("latest.log");
    private static BufferedWriter bufferedWriter = null;


    static {
        initFile();
    }

    public static void initFile() {
        try {
            log.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("latest.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logging(String string) throws IOException {
        try {
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
    public static String buildingInformation(Building building) {
        if (building.getRow() != -1 && building.getCol() != -1) {
            return building.getType() + " at row: " + building.getRow() + " and column: " + building.getCol() + " Condition: " + building.getCondition();
        }
        else {
            return building.getType() +" [MASTER BUILDING INSTANCE] Condition: " + building.getCondition();

        }

    }
    public static String resourceInformation(Resource resource) {
        return resource.getResource() + " at row " + resource.getRow() + "and column: " + resource.getCol() +". Flagged building: " + resource.getScannedBuilding();
    }
}
