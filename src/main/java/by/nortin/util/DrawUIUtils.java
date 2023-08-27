package by.nortin.util;

import static by.nortin.util.Constants.TEXT_READ_INT_FROM_CONSOLE;
import static by.nortin.util.InputUtils.readIntFromConsole;

import java.io.IOException;
import java.util.Currency;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@UtilityClass
@Log4j2
public class DrawUIUtils {

    private static final String BORDER;
    private static final String BLANK;
    private static final String SELECTION_MENU;
    private static final String BANK_ACCOUNT_SELECTION_MENU;
    private static final String TRANSFER_BANK_SELECTION_MENU;
    private static final String TRANSFER_BANK_ACCOUNT_SELECTION_MENU;

    static {
        BORDER = "************************************************************";
        BLANK = "*                                                          *";
        SELECTION_MENU = "*               select one of the menu items               *";
        BANK_ACCOUNT_SELECTION_MENU = "*             choose one of your bank accounts             *";
        TRANSFER_BANK_SELECTION_MENU = "*             select the translation direction             *";
        TRANSFER_BANK_ACCOUNT_SELECTION_MENU = "*           select the bank account to transfer           *";
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

    public static void drawSelectionMenu() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - Replenishment of the bank account balance");
        System.out.println("2 - Withdrawal of funds from a bank account");
        System.out.println("3 - Transfer of funds between bank accounts");
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

    public static void drawReplenishmentBalanceSelection() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - Deposit cash");
        System.out.println("2 - Transfer money from another account");
        System.out.println("3 - Back");
        System.out.println(BORDER);
    }

    public static void drawBankAccountSelection() {
        System.out.println(BORDER);
        System.out.println(BANK_ACCOUNT_SELECTION_MENU);
        System.out.println(BORDER);
    }

    public static void drawTransferBankSelection() {
        System.out.println(BORDER);
        System.out.println(TRANSFER_BANK_SELECTION_MENU);
        System.out.println(BORDER);
    }

    public static void drawCurrentSelection(String selection) {
        String message = "";
        switch (selection) {
            case "Deposit cash" -> message = BANK_ACCOUNT_SELECTION_MENU;
            case "Transfer money from another account" -> message = TRANSFER_BANK_SELECTION_MENU;
        }
        System.out.println(BORDER);
        System.out.println(message);
        System.out.println(BORDER);
    }

    public static void drawEmptyForSelectionLine() {
        System.out.println("Nothing selection");
    }

    public static void drawSelectionLine(Integer index, Long number, Currency currency) {
        System.out.println(index + " - " + number + " (" + currency.getCurrencyCode() + ")");
    }

    public static void drawReturnLine(int size) {
        int menuPoint = size + 1;
        System.out.println(menuPoint + " - Back");
    }

    public static void drawExitLine(int size) {
        int menuPoint = size + 2;
        System.out.println(menuPoint + " - Exit");
    }

    public static int getMenuPoint(int bound) {
        return readIntFromConsole(TEXT_READ_INT_FROM_CONSOLE + bound, bound);
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
        } catch (IOException | InterruptedException e) {
            log.error("Exception clearConsole()", e);
//            System.out.println("Ooooooo! clearConsoleException");
        }
    }
}
