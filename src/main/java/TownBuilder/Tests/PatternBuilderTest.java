package TownBuilder.Tests;


import java.util.ArrayList;
import TownBuilder.*;
import TownBuilder.Buildings.*;

public class PatternBuilderTest {
    public static void main(String[] args) {
        // vertically symmetric
        // total should be 4: pattern definition (1) and its rotations (3)
        ArrayList<ResourceEnum[][]> b1 = (new Theater()).getBuildingPatternsList();
        System.out.println("[ All transformations of Theater ]\n");
        Utility.printMembersof3dArrayList(b1);
        assert b1.size() == 4;
        System.out.println("----\n");

        // horizontally symmetric
        // total should be 4: pattern definition (1) and its rotations (3)
        ArrayList<ResourceEnum[][]> b2 = (new Tavern(0, 0)).getBuildingPatternsList();
        System.out.println("[ All transformations of Tavern ]\n");
        Utility.printMembersof3dArrayList(b2);
        assert b2.size() == 4;
        System.out.println("----\n");

        // non-symmetrical
        // total should be 8: pattern definition (1) and its rotations (3), and mirror (1) and its rotations (3)
        ArrayList<ResourceEnum[][]> b3 = (new Chapel(0, 0)).getBuildingPatternsList();
        System.out.println("[ All transformations of Chapel ]\n");
        Utility.printMembersof3dArrayList(b3);
        assert b3.size() == 8;
        System.out.println("----\n");
    }
}
