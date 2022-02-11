package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import TownBuilder.UI.BoardUI;

public class Utility {
    private static ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
    // Utility object to coordinate synchronized threads
    private static final Object notifier = new Object();
    public static Object getNotifier() {
        return notifier;
    }

    // generic printer for 2d arrays
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
    // appends spaces to the end of given string t until string is given length
    public static String lengthResizer(String t, int length) {
        StringBuilder target = new StringBuilder(t);
        int targetLength = target.length();
        target.append(" ".repeat(Math.max(0, length - targetLength)));
        return target.toString();
    }
    // feeds all buildings in b, given that each building is feedable and is not already fed
    public static void feedBuildings(Building ...b) {
        for (Building building : b) {
            if (building.isFeedable() && !building.getCondition()) {
                building.setCondition(true);
            }
        }
    }
    // converts a Collection of Building objects into an ArrayList of their enum type
    public static ArrayList<BuildingEnum> convertBuildingListToEnumList(Collection<Building> buildingCollection) {
        ArrayList<BuildingEnum> buildingEnums = new ArrayList<>();

        for (Building building : buildingCollection) {
            buildingEnums.add(building.getType());
        }
        return buildingEnums;
    }
    // used for prompting the picking of resources in game. blacklistResources refers to resources that the user cannot pick from (used with Banks)
    public static ResourceEnum resourcePicker(ArrayList<ResourceEnum> blacklistedResources, Board board, String string) throws IOException {
        resetResourceArray();
        ResourceEnum resourceChoice;
        boolean validResource = true;
        // prompts GUI for user prompt
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
            // verify that given resource is NOT on the blacklist if the blacklist exists
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
        return resourceChoice;
    }
    // randomly determines a resource
    public static ResourceEnum randomResource() {
        ResourceEnum result;
        try {
            int random = (int) (Math.random() * resourceArray.size());
            result = resourceArray.get(random);
            resourceArray.remove(random);
            // you'll eventually need to call resetResourceArray to avoid a softlock in user selection or a crash from OutOfBounds
            // this call occurs when the user is prompted for resource selection
        }
        // if something fails, then just return Glass as a failsafe
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
