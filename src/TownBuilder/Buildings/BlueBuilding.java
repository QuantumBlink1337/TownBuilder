package TownBuilder.Buildings;

import TownBuilder.BuildingColor;
import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

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
            System.out.println("Where would you like to place your Cottage?");
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
                    rArray[r][c].setResource(ResourceEnum.NONE);
                    rArray[r][c].setScannedBuilding(BuildingEnum.NONE);
                }
            }
        }
        rArray[row][col].setResource(ResourceEnum.OVERRULED);
        bArray[row][col] = new BlueBuilding(BuildingEnum.COTTAGE, BuildingColor.BLUE);
    }
    public static boolean blueDetection(int row, int col, TownResource[][] rArray) {
        ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
        cottageArray[0][0] = ResourceEnum.GLASS;
        cottageArray[0][1] = ResourceEnum.BRICK;
        cottageArray[1][0] = ResourceEnum.WHEAT;
        cottageArray[1][1] = ResourceEnum.NONE;
        try {
            for (int i = 0; i < (rArray.length + 3); i++) {
                if (i == 4) {
                    cottageArray[0][0] = ResourceEnum.GLASS;
                    cottageArray[0][1] = ResourceEnum.WHEAT;
                    cottageArray[1][0] = ResourceEnum.BRICK;
                    cottageArray[1][1] = ResourceEnum.NONE;
                }
                if (rArray[row][col].getResource() == cottageArray[0][0]) {
                    rArray[row][col].setScannedBuilding(BuildingEnum.COTTAGE);
                    if (rArray[row][col+1].getResource() == cottageArray[0][1]) {
                        rArray[row][col+1].setScannedBuilding(BuildingEnum.COTTAGE);
                        if (rArray[row+1][col].getResource() == cottageArray[1][0]) {
                            rArray[row+1][col].setScannedBuilding(BuildingEnum.COTTAGE);
                            if (ResourceEnum.PatternRandomCheck(rArray[row+1][col+1].getResource())) {
                                return true;
                            }
                        }
                    }
                }
                else if (rArray[row][col+1].getResource() == cottageArray[0][0]) {
                    rArray[row][col+1].setScannedBuilding(BuildingEnum.COTTAGE);
                    if (rArray[row+1][col+1].getResource() == cottageArray[0][1]) {
                        rArray[row+1][col+1].setScannedBuilding(BuildingEnum.COTTAGE);
                        if (ResourceEnum.PatternRandomCheck(rArray[row+1][col].getResource())) {
                            if (rArray[row][col].getResource() == cottageArray[1][0]) {
                                rArray[row][col].setScannedBuilding(BuildingEnum.COTTAGE);
                                return true;
                            }
                        }
                    }
                }
                else if (rArray[row+1][col+1].getResource() == cottageArray[0][0]) {
                    rArray[row+1][col+1].setScannedBuilding(BuildingEnum.COTTAGE);
                    if (rArray[row+1][col].getResource() == cottageArray[0][1]) {
                        rArray[row+1][col].setScannedBuilding(BuildingEnum.COTTAGE);
                        if (ResourceEnum.PatternRandomCheck(rArray[row][col].getResource())) {
                            if (rArray[row][col+1].getResource() == cottageArray[1][0]) {
                                return true;
                            }
                        }
                    }
                }
                else if (rArray[row+1][col].getResource() == cottageArray[0][0]) {
                    rArray[row+1][col].setScannedBuilding(BuildingEnum.COTTAGE);
                    if (rArray[row][col].getResource() == cottageArray[0][1]) {
                        rArray[row][col].setScannedBuilding(BuildingEnum.COTTAGE);
                        if (ResourceEnum.PatternRandomCheck(rArray[row+1][col].getResource())) {
                            if (rArray[row+1][col+1].getResource() == cottageArray[1][0]) {
                                rArray[row+1][col+1].setScannedBuilding(BuildingEnum.COTTAGE);
                                return true;
                            }
                        }
                    }
                }
            }



        }
        catch (Exception e) {
        }
        System.out.println("Moving on to next index");
        return false;
    }

}
