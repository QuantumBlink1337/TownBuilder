package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;

public class RedBuilding extends Building {
    private BuildingEnum buildingEnum;
    public RedBuilding(BuildingEnum b) {
        buildingEnum = b;
    }
    public int scorer() {
        BuildingEnum scoredType = this.buildingEnum;
        if (scoredType == BuildingEnum.FARM) {
            //return 1;
        }
        return 0;
    }
    public static boolean redDetection(int row, int col, TownResource[][] resourceArray) {
        System.out.println("Row: " + row + " Col: " + col);
        try {
            if (resourceArray[row][col].getResource() == ResourceEnum.WHEAT && !resourceArray[row][col].getScannedStatus()) {
                System.out.println("Wheat found. Beginning scan sequence.");
                System.out.println("Modded Row: " + (row) + " Col: " + col);
                if (resourceArray[row][col + 1].getResource() == ResourceEnum.WHEAT) {
                    System.out.println("Row: " + row + " Modded Col: " + (col + 1));
                    System.out.println("Wheat found. 2/4 found");
                    if (resourceArray[row - 1][col].getResource() == ResourceEnum.WOOD)
                    {
                        System.out.println("Wood found. 3/4 found");
                        System.out.println("Modded Row: " + (row) + " Modded Col: " + (col + 1));
                        if (resourceArray[row - 1][col - 1].getResource() == ResourceEnum.WOOD) {
                            System.out.println("Modded Row: " + (row + 1) + " Modded Col: " + (col + 1));
                            resourceArray[row][col].setScannedStatus();
                            resourceArray[row + 1][col].setScannedStatus();
                            resourceArray[row][col + 1].setScannedStatus();
                            resourceArray[row + 1][col + 1].setScannedStatus();
                            System.out.println("FARM FOUND!");
                            return true;
                        }
                    }
                }
            }
        }
            catch (Exception e) {
            }
            try {
                if (resourceArray[row][col+1].getResource() == ResourceEnum.WHEAT) {
                    System.out.println("Row: " + row + " Modded Col: " + (col+1));
                    System.out.println("Wheat found. 2/4 found");
                    if (resourceArray[row-1][col].getResource() == ResourceEnum.WOOD) {
                        System.out.println("Wood found. 3/4 found");
                        System.out.println("Modded Row: " + (row) + " Modded Col: " + (col+1));
                        if (resourceArray[row-1][col+1].getResource() == ResourceEnum.WOOD) {
                            System.out.println("Modded Row: " + (row+1) + " Modded Col: " + (col+1));
                            resourceArray[row][col].setScannedStatus();
                            resourceArray[row][col+1].setScannedStatus();
                            resourceArray[row-1][col].setScannedStatus();
                            resourceArray[row-1][col+1].setScannedStatus();
                            System.out.println("FARM FOUND!");
                            return true;
                        }
                    }
                }
            }
            catch (Exception e) {
            }
            try {
                if (resourceArray[row+1][col].getResource() == ResourceEnum.WHEAT) {
                    System.out.println("Row: " + row + " Modded Col: " + (col+1));
                    System.out.println("Wheat found. 2/4 found");
                    if (resourceArray[row][col+1].getResource() == ResourceEnum.WOOD) {
                        System.out.println("Wood found. 3/4 found");
                        System.out.println("Modded Row: " + (row) + " Modded Col: " + (col+1));
                        if (resourceArray[row-1][col+1].getResource() == ResourceEnum.WOOD) {
                            System.out.println("Modded Row: " + (row+1) + " Modded Col: " + (col+1));
                            resourceArray[row][col].setScannedStatus();
                            resourceArray[row][col+1].setScannedStatus();
                            resourceArray[row-1][col].setScannedStatus();
                            resourceArray[row-1][col+1].setScannedStatus();
                            System.out.println("FARM FOUND!");
                            return true;
                        }
                    }
                }
            }
            catch(Exception e) {
            }
        try {
            if (resourceArray[row+1][col].getResource() == ResourceEnum.WHEAT) {
                System.out.println("Row: " + row + " Modded Col: " + (col+1));
                System.out.println("Wheat found. 2/4 found");
                if (resourceArray[row][col-1].getResource() == ResourceEnum.WOOD) {
                    System.out.println("Wood found. 3/4 found");
                    System.out.println("Modded Row: " + (row) + " Modded Col: " + (col+1));
                    if (resourceArray[row+1][col-1].getResource() == ResourceEnum.WOOD) {
                        System.out.println("Modded Row: " + (row+1) + " Modded Col: " + (col+1));
                        resourceArray[row][col].setScannedStatus();
                        resourceArray[row][col+1].setScannedStatus();
                        resourceArray[row-1][col].setScannedStatus();
                        resourceArray[row-1][col+1].setScannedStatus();
                        System.out.println("FARM FOUND!");
                        return true;
                    }
                }
            }
        }
        catch(Exception e) {
        }

        return false;
        }





}
