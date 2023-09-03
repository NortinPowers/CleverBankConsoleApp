package by.nortin.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    static {
        BORDER = "************************************************************";
        BLANK = "*                                                          *";
        SELECTION_MENU = "*               select one of the menu items               *";
        BANK_ACCOUNT_SELECTION_MENU = "*             choose one of your bank accounts             *";
        TRANSFER_BANK_SELECTION_MENU = "*             select the translation direction             *";
    }

    /**
     * The method draws the application logo.
     */
    public static void drawWelcomeMenu() {
        System.out.println();
        System.out.println(BORDER);
        System.out.println(BLANK);
        System.out.println("*                Welcome to Clever Bank                    *");
        System.out.println(BLANK);
        System.out.println(BORDER);
    }

    /**
     * The method draws the authorization request.
     */
    public static void drawAuthenticateRequest() {
        System.out.println("*           Please authentication to start work            *");
        System.out.println(BORDER);
    }

    /**
     * The method draws the start menu selection. (level 0)
     */
    public static void drawSelectionMenu() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - Replenishment of the bank account balance");
        System.out.println("2 - Withdrawal of funds from a bank account");
        System.out.println("3 - Transfer of funds between bank accounts");
        System.out.println("4 - Reports");
        System.out.println("5 - Close app");
        System.out.println(BORDER);
    }

    /**
     * The method draws the balance top-up menu. (level 10)
     */
    public static void drawReplenishmentBalanceSelection() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - Deposit cash");
        System.out.println("2 - Back");
        System.out.println(BORDER);
    }

    /**
     * The method draws balance withdrawal menu. (level 20)
     */
    public static void drawWithdrawalBalanceSelection() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - Withdrawal of funds");
        System.out.println("2 - Back");
        System.out.println(BORDER);
    }

    /**
     * The method draws transfer menu between accounts. (level 30)
     */
    public static void drawTransferTypeSelection() {
        System.out.println(BORDER);
        System.out.println(SELECTION_MENU);
        System.out.println(BORDER);
        System.out.println("1 - Transfer between your bank accounts");
        System.out.println("2 - Transfer to a Clever-bank client");
        System.out.println("3 - Transfer to a client of another bank");
        System.out.println("4 - Back");
        System.out.println(BORDER);
    }

    /**
     * The method draws bank account selection menu.
     */
    public static void drawBankAccountSelection() {
        System.out.println(BORDER);
        System.out.println(BANK_ACCOUNT_SELECTION_MENU);
        System.out.println(BORDER);
    }

    /**
     * The method draws user login.
     *
     * @param login - String username
     */
    public static void drawGreetingUser(String login) {
        System.out.printf(String.format("%31s", login));
        System.out.println();
    }

    /**
     * The method is used to display the message.
     *
     * @param message - String output message
     */
    public static void drawMessage(String message) {
        System.out.println(BORDER);
        System.out.println(message);
        System.out.println(BORDER);
    }

    /**
     * The method draws bank selection menu.
     */
    public static void drawTransferBankSelection() {
        System.out.println(BORDER);
        System.out.println(TRANSFER_BANK_SELECTION_MENU);
        System.out.println(BORDER);
    }

    /**
     * The method for displaying a line from the selection menu for BankAccount.
     *
     * @param index    - Integer menu position
     * @param number   - Long
     * @param currency - Currency
     */
    public static void drawSelectionLine(Integer index, Long number, Currency currency) {
        System.out.println(index + " - " + number + " (" + currency.getCurrencyCode() + ")");
    }

    /**
     * The method for displaying a line from the selection menu.
     *
     * @param index Integer  menu position
     * @param name  String description
     */
    public static void drawSelectionLine(Integer index, String name) {
        System.out.println(index + " - " + name);
    }

    /**
     * The method for displaying a return`s menu line.
     *
     * @param size int list size
     */
    public static void drawReturnLine(int size) {
        int menuPoint = size + 1;
        System.out.println(menuPoint + " - Back");
    }

    /**
     * The method for displaying the exit`s menu line.
     *
     * @param size int list size
     */
    public static void drawExitLine(int size) {
        int menuPoint = size + 2;
        System.out.println(menuPoint + " - Exit");
    }

    /**
     * The method displays the current balance of the bank account.
     *
     * @param bankAccountNumber Long
     * @param balance           BigDecimal
     */
    public static void drawCurrentBalanceOfBankAccount(Long bankAccountNumber, BigDecimal balance) {
        System.out.println();
        System.out.println("The account balance " + bankAccountNumber + "\n is now: " + balance.setScale(2, RoundingMode.HALF_DOWN));
        System.out.println();
    }
}
