package TownBuilder.Buildings;

import TownBuilder.BuildingColor;
import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import java.util.Scanner;

public class RedBuilding extends Building {
    private BuildingEnum buildingEnum;
    private static Scanner sc = new Scanner(System.in);

    public RedBuilding(BuildingEnum b, BuildingColor c) {
        super(b, c);
    }


    public int scorer() {
        BuildingEnum scoredType = this.buildingEnum;
        if (scoredType == BuildingEnum.FARM) {
            //return 1;
        }
        return 0;
    }
//    public static boolean redDetection() {
//        int rotationTry = 0;
//        boolean buildingFormed = false;
//        do {
//            buildingFormed = findBuilding();
//            if (buildingFormed) {
//                return true;
//                break;
//            }
//            // we didn't form a building so try rotate
//            farmArray = buildingFlipper(farmArray);
//            rotationTry++;
//        } while (!buildingFormed && rotationTry < 3);
//        if(!buildingFormed) {
//            // fail
//        }
//    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
    public static void redPlacement(TownResource[][] rArray, Building[][] bArray) {
        String userInput = "";
        int row = 0;
        int col = 0;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Farm?");
            userInput = sc.nextLine().toLowerCase();
            String[] coordinateHelper = userInput.split("", 2);

//            for (String character : coordinateHelper) {
//                System.out.println(character);
//            }
            switch (coordinateHelper[0]) {
                case "a" -> {
                    col = 0;
                    //System.out.println("Case A");
                }
                case "b" -> {
                    col = 1;
                    //System.out.println("Case B");
                }
                case "c" -> {
                    col = 2;
                    //System.out.println("Case C");
                }
                case "d" -> {
                    col = 3;
                    //System.out.println("Case D");
                }
            }
            switch (coordinateHelper[1]) {
                case "1" -> {
                    row = 0;
                    //System.out.println("Case 0");
                }
                case "2" -> {
                    row = 1;
                    //.out.println("Case 1");
                }
                case "3" -> {
                    row = 2;
                    //System.out.println("Case 2");
                }
                case "4" -> {
                    row = 3;
                    //System.out.println("Case 3");
                }
            }
            if (rArray[row][col].getScannedBuilding() == BuildingEnum.FARM) {
                validInput = true;
            }
        }
        while (!validInput);


        for (int r = 0; r < rArray.length; r++){
            for (int c = 0; c < rArray[r].length; c++) {
                if (rArray[r][c].getScannedBuilding() == BuildingEnum.FARM) {
                    rArray[r][c].setResource(ResourceEnum.NONE);
                    rArray[r][c].setScannedBuilding(BuildingEnum.NONE);
                }
            }
        }
        bArray[row][col] = new RedBuilding(BuildingEnum.FARM, BuildingColor.RED);
    }
    public static boolean redDetection(int row, int col, TownResource[][] rArray) {
        ResourceEnum[][] farmArray = new ResourceEnum[2][2];
        farmArray[0][0] = ResourceEnum.WHEAT;
        farmArray[0][1] = ResourceEnum.WHEAT;
        farmArray[1][0] = ResourceEnum.WOOD;
        farmArray[1][1] = ResourceEnum.WOOD;
        //System.out.println("["+farmArray[0][0]+"]["+farmArray[0][1]+"]");
        //System.out.println("["+farmArray[1][0]+"]["+farmArray[1][1]+"]");
        //.out.println("Row: " + row + " Col: " + col);
            try {
                for (int i = 0; i < rArray.length; i++) {
                    if (rArray[row][col].getResource() == farmArray[0][0] && rArray[row+1][col].getResource() == farmArray[1][0] &&
                            rArray[row][col+1].getResource() == farmArray[0][1] &&rArray[row+1][col+1].getResource() == farmArray[1][1]) {

                        rArray[row][col].setScannedBuilding(BuildingEnum.FARM);
                        rArray[row+1][col].setScannedBuilding(BuildingEnum.FARM);
                        rArray[row][col+1].setScannedBuilding(BuildingEnum.FARM);
                        rArray[row+1][col+1].setScannedBuilding(BuildingEnum.FARM);
                        return true;
                    }
                    else {
                        farmArray = buildingRotation(farmArray);
                    }
                }
            }
            catch (Exception e) {
            }





        return false;
     }
}
