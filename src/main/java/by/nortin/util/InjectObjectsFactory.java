package by.nortin.util;

import by.nortin.service.UserService;
import by.nortin.service.impl.UserServiceImpl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InjectObjectsFactory {

    private static final Map<Class<?>, Object> CLASS_OBJECT_MAP = new ConcurrentHashMap<>();

    public static <T> Object getInstance(Class<T> serviceClass) {
        return putInstanceToMapIfAbsentAndReturn(serviceClass);
    }

    private static <T> Object putInstanceToMapIfAbsentAndReturn(Class<T> serviceClass) {
        Object value = CLASS_OBJECT_MAP.get(serviceClass);
        if (value == null) {
            value = createInstance(serviceClass);
            CLASS_OBJECT_MAP.put(serviceClass, value);
        }
        return value;
    }

    private static <T> Object createInstance(Class<T> serviceClass) {
        if (UserService.class == serviceClass) {
            return new UserServiceImpl();
        }
//        } else if (OrderService.class == serviceClass) {
//            return new OrderServiceImpl();
        throw new IllegalArgumentException("Can not create instance of class " + serviceClass);
    }
}
