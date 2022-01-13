package TownBuilder.UI;

public class UI_Utilities {

    public static String convertIntToPercentString(int value, int constant) {
        System.out.println((((float) value / constant) * 100) + "sp");
        return (((float) value / constant) * 100) + "sp";
    }
}
