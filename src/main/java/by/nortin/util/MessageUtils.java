package by.nortin.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageUtils {

    /**
     * The method displays error messages to the user.
     *
     * @param errorMessage String
     */
    public static void getErrorMessageToUser(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * The method displays error messages to log.
     *
     * @param methodName String
     * @return String message
     */
    public static String getErrorMessageToLog(String methodName) {
        return String.format("Exception %s", methodName);
    }

    /**
     * The method displays error messages to log.
     *
     * @param methodName String
     * @return String message
     */
    public static String getErrorMessageToLog(String methodName, String secondMethodName) {
        return String.format("Exception %s / %s", methodName, secondMethodName);
    }
}
