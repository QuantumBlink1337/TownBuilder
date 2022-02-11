package TownBuilder;

import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.UI.BoardUI;
import com.diogonunes.jcolor.*;

@SuppressWarnings({"EnhancedSwitchMigration", "unused"})
public class Utility {
    // variable to control whether colorized strings can be generated.
    // intended to be triggered upon running in a terminal environment
    // lacking RGB color support

    private static boolean color = !System.getProperty("os.name").contains("Windows");
    private static ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));

    private static final Object notifier = new Object();
    // (potentially obsolete)

    public static boolean isColor() {
        return color;
    }
    public static Object getNotifier() {
        return notifier;
    }
    public static void setColor(boolean color) {
        Utility.color = color;
    }


    public static <T> void arrayPrinter(T[][] array) {
        for (T[] ts : array) {
            for (int col = 0; col < ts.length; col++) {
                if (col == (ts.length - 1)) {
                    System.out.println("[" + ts[col] + "]");
                } else {
                    System.out.print("[" + ts[col] + "]");
                }
            }
        }
    }
    /*
        Rotate pattern 90 degrees to the right.
    */
    public static ResourceEnum[][] rotate90(ResourceEnum[][] a) {
        final int M = a.length;
        final int N = a[0].length;
        ResourceEnum[][] ret = new ResourceEnum[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = a[r][c];
            }
        }
        return ret;
    }
    public static ResourceEnum[] reverseRow(ResourceEnum[] row) {
        ResourceEnum[] mirroredRow = new ResourceEnum[row.length];
        for (int i = row.length - 1; i >= 0; i--) {
            mirroredRow[row.length - 1 - i] = row[i];
        }
        return mirroredRow;
    }
    /*
        Vertically mirror pattern.
    */
    public static ResourceEnum[][] vertMirror(ResourceEnum[][] array) {
        ResourceEnum[][] mirrored2DArray = new ResourceEnum[array.length][array[0].length];
        for (int r = 0; r < array.length; r++) {
            mirrored2DArray[r] = reverseRow(array[r]);
        }
        return mirrored2DArray;
    }
    // prints each element in a generic ArrayList
    public static <T> void printMembersOfArrayList(ArrayList<T> arrayList) {
        for (T t : arrayList) {
            System.out.println(t);
        }
    }


    @SuppressWarnings({"unused", "StringOperationCanBeSimplified"})
    public static <T> void printMembersof3dArrayList(ArrayList<T[][]> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayPrinter(arrayList.get(i));
            System.out.println("");
            // after the 4th iteration
            if (i + 1 == 4) {
                System.out.println("MIRRORS:\n");
            }
        }
    }
    public static Attribute generateAttributeFromColor(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return Attribute.TEXT_COLOR(r, g, b);
    }
    public static void displayValidResources(Resource[][] rArray, BuildingFactory buildingFactory) throws IOException {
        for (int i = 0; i < buildingFactory.getValidResources().size(); i++) {
            Resource resource = buildingFactory.getValidResources().get(i);
            int r = resource.getRow();
            int c = resource.getCol();
            DebugTools.logging("Displaying resource: " + DebugTools.resourceInformation(resource));
            if (i != (buildingFactory.getValidResources().size() -1)) {
                System.out.print(Utility.MachineIndexesToHumanCoords(r, c) + ", ");
            }
            else {
                System.out.println(Utility.MachineIndexesToHumanCoords(r, c));
            }
        }
    }
    public static String lengthResizer(String t, int length) {
        StringBuilder target = new StringBuilder(t);
        int targetLength = target.length();
        target.append(" ".repeat(Math.max(0, length - targetLength)));
        return target.toString();
    }

    public static void feedBuildings(Building ...b) {
        for (Building building : b) {
            if (building.isFeedable() && !building.getCondition()) {
                building.setCondition(true);
            }
        }
    }


    public static String lowerCaseLetters(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
    // converts a human coordinate input to machine readable indexes
    // example: 'a4' becomes [0, 3]
    public static int[] humanCoordsToMachineIndexes(String i) {
        String[] input = i.split("", 2);
        int[] coords = new int[2];
        switch (input[0]) {
            case "a": //System.out.println("Case A");
                break;
            case "b": //System.out.println("Case B");
                    coords[1] = 1;
                    break;
            case "c": //System.out.println("Case C");
                    coords[1] = 2;
                    break;
            case "d": //System.out.println("Case D");
                    coords[1] = 3;
                    break;
            default: coords[1] = -1;
                break;

        }
        switch (input[1]) {
            case "1": //System.out.println("Case 0");
                break;
            case "2": //.out.println("Case 1");
                    coords[0] = 1;
                    break;
            case "3": //System.out.println("Case 2");
                    coords[0] = 2;
                    break;
            case "4": //System.out.println("Case 3");
                    coords[0] = 3;
                    break;
            default: coords[0] = -1;
                    break;
        }
        return coords;
    }
    // converts machine indexes into human understandable coordinates
    // example: 2, 3 == 'c4'
    public static String MachineIndexesToHumanCoords(int r, int c) {
        String row = "";
        String col = "";
        switch (r) {
            case 0: //System.out.println("Case A");
                    row = "1";
                break;
            case 1: //System.out.println("Case B");
                    row = "2";
                break;
            case 2: //System.out.println("Case C");
                    row = "3";
                break;
            case 3: //System.out.println("Case D");
                    row = "4";
                break;
        }
        switch (c) {
            case 0: //System.out.println("Case 0");
                    col = "a";
                break;
            case 1: //.out.println("Case 1");
                    col = "b";
                break;
            case 2: //System.out.println("Case 2");
                    col = "c";
                break;
            case 3: //System.out.println("Case 3");
                    col = "d";
                break;
        }
        return col+row;
    }

    public static ArrayList<BuildingEnum> convertBuildingListToEnumList(Collection<Building> buildingCollection) {
        ArrayList<BuildingEnum> buildingEnums = new ArrayList<>();

        for (Building building : buildingCollection) {
            buildingEnums.add(building.getType());
        }
        return buildingEnums;
    }
    public static ResourceEnum resourcePicker(ArrayList<ResourceEnum> blacklistedResources, Board board, String string) throws IOException {
        resetResourceArray();
        ResourceEnum resourceChoice;
        boolean validResource = true;
        BoardUI boardUI = board.getBoardUI();
        do {
            boardUI.promptResourceSelection(true);
            boardUI.setSecondaryTextLabel(string);
            synchronized (notifier) {
                try {
                    notifier.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            resourceChoice = boardUI.getUserSelectedResource();
            if (blacklistedResources != null) {
                for (ResourceEnum blackListedResource : blacklistedResources) {
                    validResource = (blackListedResource != resourceChoice);
                    if (!validResource) {
                        break;
                    }
                }
            }
            if (!validResource) {
                boardUI.getErrorTextLabel().setText("That resource is unavailable to choose. Please pick another.");
                boardUI.getErrorTextLabel().setVisible(true);
            }
        }
        while (!validResource);
        boardUI.getErrorTextLabel().setVisible(false);
        //System.out.println("Chosen resource: " + resourceChoice);
        return resourceChoice;
    }

    public static ResourceEnum randomResource() {
        ResourceEnum result;
        try {
            int random = (int) (Math.random() * resourceArray.size());
            result = resourceArray.get(random);
            resourceArray.remove(random);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            result = ResourceEnum.GLASS;
        }
        return result;
    }

    public static void resetResourceArray() {
       resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
    }
}
