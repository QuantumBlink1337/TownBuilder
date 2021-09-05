package TownBuilder.Buildings;

import TownBuilder.BuildingColor;
import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;

import java.util.Scanner;

public class BlueBuilding extends Building{
    private static Scanner sc = new Scanner(System.in);
    public BlueBuilding(BuildingEnum b, BuildingColor c) {
        super(b, c);

    }
    public static void bluePlacement(TownResource[][] rArray, Building[][] bArray) {
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
            if (rArray[row][col].getScannedBuilding() == BuildingEnum.COTTAGE) {
                validInput = true;
            }
        }
        while (!validInput);


        for (int r = 0; r < rArray.length; r++){
            for (int c = 0; c < rArray[r].length; c++) {
                if (rArray[r][c].getScannedBuilding() == BuildingEnum.COTTAGE) {
                    rArray[r][c].setResource(ResourceEnum.NULL);
                    rArray[r][c].setScannedBuilding(BuildingEnum.NULL);
                }
            }
        }
        bArray[row][col] = new RedBuilding(BuildingEnum.COTTAGE, BuildingColor.BLUE);
    }
    public static boolean blueDetection(int row, int col, TownResource[][] rArray) {
        ResourceEnum[][] cottageArray = new ResourceEnum[4][4];
        cottageArray[0][0] = ResourceEnum.WHEAT;
        cottageArray[0][1] = ResourceEnum.WHEAT;
        cottageArray[1][0] = ResourceEnum.GLASS;
        cottageArray[1][1] = ResourceEnum.BRICK;
        System.out.println(cottageArray[0][0]);
        System.out.println(cottageArray[0][1]);
        System.out.println(cottageArray[1][0]);
        System.out.println(cottageArray[1][1]);

        try {
            for (int i = 0; i < rArray.length; i++) {
                if (i == 4) {
                    cottageArray[0][0] = ResourceEnum.BRICK;
                    cottageArray[0][1] = ResourceEnum.NULL;
                    cottageArray[1][0] = ResourceEnum.GLASS;
                    cottageArray[1][1] = ResourceEnum.WHEAT;

                    System.out.println("Inverse invoked");
                }
                if (rArray[row][col].getResource() == cottageArray[0][0]) {
                    //System.out.println("1/4 match");
                    for (int a = 0; a < rArray.length; a++) {
                        if (rArray[row+1][col].getResource() == cottageArray[1][0]) {
                            //System.out.println("2/4 match");
                            for (int b = 0; b < rArray.length; b++) {
                                    //System.out.println("3/4 match");
                                    if (rArray[row+1][col+1].getResource() == cottageArray[1][1]) {
                                        //system.out.println("FARM FOUND!");
                                        rArray[row][col].setScannedBuilding(BuildingEnum.COTTAGE);
                                        rArray[row+1][col].setScannedBuilding(BuildingEnum.COTTAGE);
                                        rArray[row][col+1].setScannedBuilding(BuildingEnum.COTTAGE);
                                        rArray[row+1][col+1].setScannedBuilding(BuildingEnum.COTTAGE);
                                        return true;
                                    }
                                    else {
                                        cottageArray = buildingRotation(cottageArray);
                                    }
                            }
                        }
                        else {
                            cottageArray = buildingRotation(cottageArray);
                        }
                    }
                }
                else {
                    cottageArray = buildingRotation(cottageArray);
                }
//                    if (i == 3) {
//                        //System.out.println("Completed index search");
//                    }
            }
        }
        catch (Exception e) {
        }
        return false;
    }

}
