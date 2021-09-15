package TownBuilder.Buildings;

import TownBuilder.BuildingColor;
import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;

import java.util.Scanner;




public class BlueBuilding extends Building{

    private static ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private static ResourceEnum[][] cottageArrayMirror = new ResourceEnum[2][2];
    private static ResourceEnum[][][] cottagePatternList = new ResourceEnum[2][2][2];
    private static Scanner sc = new Scanner(System.in);
    public BlueBuilding(BuildingEnum b, BuildingColor c) {
        super(b, c);

    }

    public static void setCottageArray() {



    }
    public static ResourceEnum[][][] getArray() {
        cottageArray[0][0] = ResourceEnum.GLASS;
        cottageArray[0][1] = ResourceEnum.WHEAT;
        cottageArray[1][0] = ResourceEnum.BRICK;
        cottageArray[1][1] = ResourceEnum.NONE;
        cottageArrayMirror = ResourceEnum.swap(cottageArray, 0, 1, 1, 0);
        cottagePatternList[0] = cottageArray;
        cottagePatternList[1] = cottageArrayMirror;
        return cottagePatternList;
    }
    public static void bluePlacement(TownResource[][] rArray, Building[][] bArray) {
        String userInput = "";
        int row = 0;
        int col = 0;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Cottage?");
            userInput = sc.nextLine().toLowerCase();
            String[] coordinateHelper = userInput.split("", 2);

//            for (String character : coordinateHelper) {
//                System.out.println(character);
//            }
            switch (coordinateHelper[0]) {
                case "a" -> //System.out.println("Case A");
                        col = 0;
                case "b" -> //System.out.println("Case B");
                        col = 1;
                case "c" -> //System.out.println("Case C");
                        col = 2;
                case "d" -> //System.out.println("Case D");
                        col = 3;
            }
            switch (coordinateHelper[1]) {
                case "1" -> //System.out.println("Case 0");
                        row = 0;
                case "2" -> //.out.println("Case 1");
                        row = 1;
                case "3" -> //System.out.println("Case 2");
                        row = 2;
                case "4" -> //System.out.println("Case 3");
                        row = 3;
            }
            if (rArray[row][col].getScannedBuilding() == BuildingEnum.COTTAGE) {
                validInput = true;
            }
        }
        while (!validInput);


        for (int r = 0; r < rArray.length; r++){
            for (int c = 0; c < rArray[r].length; c++) {
                if (rArray[r][c].getScannedBuilding() == BuildingEnum.COTTAGE) {
                    rArray[r][c].setResource(ResourceEnum.NONE);
                    rArray[r][c].setScannedBuilding(BuildingEnum.NONE);
                }
            }
        }
        rArray[row][col].setResource(ResourceEnum.OVERRULED);
        bArray[row][col] = new BlueBuilding(BuildingEnum.COTTAGE, BuildingColor.BLUE);
    }
//    public static boolean blueDetection(int row, int col, TownResource[][] rArray) {
//
//        System.out.println("Printing mirror array:");
//        for(int a=0;a<2;a++)
//        {
//            for(int b=(2-1);b>=0;b--)
//            {
//                System.out.print(cottageArray[a][b]+"       ");
//            }
//            System.out.println();
//        }
//        try {
//            for (int i = 0; i < (rArray.length + 3); i++) {
//                if (i == 4) {
//
//                    cottageArray[0][0] = ResourceEnum.GLASS;
//                    cottageArray[0][1] = ResourceEnum.WHEAT;
//                    cottageArray[1][0] = ResourceEnum.BRICK;
//                    cottageArray[1][1] = ResourceEnum.NONE;
//                }
//            }
//        }
//        catch (Exception e) {
//        }
//        System.out.println("Moving on to next index");
//        return false;
//    }

}
