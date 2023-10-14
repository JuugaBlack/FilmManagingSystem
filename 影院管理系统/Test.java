package FilmHubTutorialV10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    Scanner sc = new Scanner(System.in);
    protected static List<User> users = new ArrayList<>();
    static mainUI mainUI = new mainUI();
    public static void main(String[] args) {
        while(true){
            mainUI.mainUI();
        }
    }
}
