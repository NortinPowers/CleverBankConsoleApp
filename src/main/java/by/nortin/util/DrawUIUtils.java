package by.nortin.util;

import java.io.IOException;
import lombok.experimental.UtilityClass;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@UtilityClass
public class DrawUIUtils {

    private static final String BORDER;
    private static final String BLANK;
    private static final String SELECTION_MENU;

    static {
        BORDER = "************************************************************";
        BLANK = "*                                                          *";
        SELECTION_MENU = "*               select one of the menu items               *";
    }

    public static void drawWelcomeMenu() {
        System.out.println(BORDER);
        System.out.println(BLANK);
        System.out.println("*                Welcome to Clever Bank                    *");
        System.out.println(BLANK);
        System.out.println(BORDER);
    }

    public static void drawAuthenticateRequest() {
        System.out.println("*           Please authentication to start work            *");
        System.out.println(BORDER);
    }

    public static void drawErrorAuthenticateRequest() {
        System.out.println(BORDER);
        System.out.println("*The user was not found. Check the input data and try again*");
        System.out.println(BORDER);
    }

    public static void drawGreetingUser(String login) {
//        clearScreen();
//        clearConsole();
//        drawWelcomeMenu();
//        System.out.println(BORDER);
        String str = "<******    " + login + "    >";
        System.out.printf("%-60s", str);
        System.out.println();
//        System.out.println(BORDER);
    }

    public static void drawSelection() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - its 1");
        System.out.println("2 - its 2");
        System.out.println("3 - its 3");
        System.out.println("4 - its 4");
        System.out.println("5 - its 5");
        System.out.println(BORDER);
    }

    public static void drawSelectionOne() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - nothing");
        System.out.println("2 - Home");
        System.out.println(BORDER);
    }

    //for commandLine
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //not work
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {
            //to log
            System.out.println("Ooooooo! clearConsoleException");
        }
    }
}
