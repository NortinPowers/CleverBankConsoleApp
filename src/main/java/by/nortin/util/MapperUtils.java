package by.nortin.util;

import java.lang.reflect.Field;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class MapperUtils {

    public static <T, S> T setValue(S sourceClass, T mappedClass) {
        Field[] fields = sourceClass.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(sourceClass);
                if (value != null) {
                    Field mappedField = mappedClass.getClass().getDeclaredField(field.getName());
                    mappedField.setAccessible(true);
                    mappedField.set(mappedClass, value);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.error("Exception setValue()", e);
            }
        }
        return mappedClass;
    }
}
