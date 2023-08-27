package by.nortin.util;

import java.math.BigDecimal;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InputUtils {

    private static final Scanner SCANNER;

    static {
        SCANNER = new Scanner(System.in);
    }

    public static String readStringFromConsole(String message) {
        System.out.println(message + " and press Enter:");
        return SCANNER.next().toLowerCase();
    }

    public static BigDecimal readBigDecimalFromConsole(String message) {
        BigDecimal value = BigDecimal.ZERO;
        do {
            System.out.println(message + " and press Enter:");
            try {
                value = new BigDecimal(SCANNER.next().toLowerCase());
            } catch (NumberFormatException e) {
                System.out.println("Incorrect value entered");
                readBigDecimalFromConsole(message);
            }
        } while (isNotCorrectBigDecimalValue(value));
        return value;
    }

    private static boolean isNotCorrectBigDecimalValue(BigDecimal value) {
        boolean condition = value.compareTo(BigDecimal.ZERO) == 0 || value.compareTo(BigDecimal.ZERO) < 0;
        if (condition) {
            System.out.println("Incorrect input data.");
        }
        return condition;
    }

    public static void waitQKeyPressed() {
        System.out.println("***               Press Q and ENTER to continue                ***");
        SCANNER.next();
    }

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

    private static boolean isNotInBounds(int number, int bound) {
        boolean condition = number < 1 || number > bound;
        if (condition) {
            System.out.println("Incorrect input data.");
        }
        return condition;
    }
}
