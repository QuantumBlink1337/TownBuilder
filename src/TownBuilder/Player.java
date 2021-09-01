package TownBuilder;

import java.util.Scanner;


public class Player {
    private int score;
    private String name;
    private Scanner sc = new Scanner(System.in);
    public Player() {
        System.out.println("What's your name?");
        name = sc.nextLine();
        score = 0;
    }
    public void addScore(int s) {
        score = score + s;
    }
}
