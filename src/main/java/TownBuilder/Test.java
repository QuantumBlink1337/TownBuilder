package TownBuilder;

import TownBuilder.Buildings.*;

public class Test {
    public static void main(String[] args) {
        // example of a symmetrical one (since the mirror is the same, there
        // are only 3 transformations, 4 total patterns)
        Theater t = new Theater();
        System.out.println("All transformations of Theater:\n");
        Utility.printMembersof3dArrayList(t.getBuildingPatternsList());

        // example of non-symmetrical one (7 transformations, 8 total patterns)
        Chapel c = new Chapel(0, 0);
        System.out.println("All transformations of Chapel:\n");
        Utility.printMembersof3dArrayList(c.getBuildingPatternsList());
    }
}