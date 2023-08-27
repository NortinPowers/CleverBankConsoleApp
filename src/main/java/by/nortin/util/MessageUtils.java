package by.nortin.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageUtils {

    public static void getErrorMessageToUser(String errorMessage) {
        System.out.println(errorMessage);
    }
}
