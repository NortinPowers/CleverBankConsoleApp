package by.nortin.util;

import static by.nortin.util.Constants.PRESS_ENTER;
import static by.nortin.util.Constants.TEXT_READ_INT_FROM_CONSOLE;

import java.math.BigDecimal;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InputUtils {

    private static final Scanner SCANNER;
    private static final String INCORRECT_VALUE = "Incorrect input data. Please repeat the input";

    static {
        SCANNER = new Scanner(System.in);
    }

    /**
     * The method reads a string value from the console.
     *
     * @param message String displayed message
     * @return String read value
     */
    public static String readStringFromConsole(String message) {
        System.out.println(message + PRESS_ENTER);
        return SCANNER.next().toLowerCase();
    }

    /**
     * The method reads the int value from the console.
     *
     * @param bound int limit value
     * @return int read value
     */
    public static int getMenuPoint(int bound) {
        return readIntFromConsole(TEXT_READ_INT_FROM_CONSOLE + bound, bound);
    }

    /**
     * The method reads the BigDecimal value from the console.
     *
     * @param inputData int displayed message
     * @return BigDecimal read value
     */
    public static BigDecimal readBigDecimalFromConsole(String inputData) {
        BigDecimal value = BigDecimal.ZERO;
        do {
            System.out.println(inputData + PRESS_ENTER);
            try {
                value = new BigDecimal(SCANNER.next().toLowerCase());
            } catch (NumberFormatException e) {
                System.out.println(INCORRECT_VALUE);
                readBigDecimalFromConsole(inputData);
            }
        } while (isNotCorrectBigDecimalValue(value));
        return value;
    }

    /**
     * The method reads the Long value from the console.
     *
     * @param inputData int displayed message
     * @return Long read value
     */
    public static Long readLongFromConsole(String inputData) {
        Long value = null;
        do {
            System.out.println(inputData + PRESS_ENTER);
            try {
                value = Long.parseLong(SCANNER.next().toLowerCase());
            } catch (NumberFormatException e) {
                System.out.println(INCORRECT_VALUE);
                readLongFromConsole(inputData);
            }
        } while (isNotCorrectLongValue(value));
        return value;
    }

    /**
     * The method returns the result of comparing the value with 0.
     *
     * @param value BigDecimal
     * @return boolean comparison with 0
     */
    private static boolean isNotCorrectBigDecimalValue(BigDecimal value) {
        boolean condition = value.compareTo(BigDecimal.ZERO) == 0 || value.compareTo(BigDecimal.ZERO) < 0;
        if (condition) {
            System.out.println(INCORRECT_VALUE);
        }
        return condition;
    }

    /**
     * The method returns the result of comparing the value with 0.
     *
     * @param value Long
     * @return boolean comparison with 0
     */
    private static boolean isNotCorrectLongValue(Long value) {
        boolean condition = value == 0 || value < 0;
        if (condition) {
            System.out.println(INCORRECT_VALUE);
        }
        return condition;
    }

    /**
     * The method for managing the continuation of program.
     */
    public static void waitQKeyPressed() {
        System.out.printf("%21s", "Press Q and ENTER to continue");
        System.out.println();
        SCANNER.next();
    }

    /**
     * The method reads the int value from the console.
     *
     * @param message String displayed message
     * @param bound   int limit value
     * @return int read value
     */
    public static int readIntFromConsole(String message, int bound) {
        int number;
        do {
            System.out.println(message + ":");
            while (!SCANNER.hasNextDouble()) {
                System.out.println("The entered value is not a number.");
                SCANNER.next();
            }
            number = SCANNER.nextInt();
        } while (isNotInBounds(number, bound));
        return number;
    }

    /**
     * The method returns the result of comparing the value with 1.
     *
     * @param number int entered value
     * @param bound  int limit value
     * @return boolean comparison with 1 and limit value
     */
    private static boolean isNotInBounds(int number, int bound) {
        boolean condition = number < 1 || number > bound;
        if (condition) {
            System.out.println(INCORRECT_VALUE);
        }
        return condition;
    }
}
