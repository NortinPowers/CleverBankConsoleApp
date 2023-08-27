package by.nortin.util;

import by.nortin.mapper.BankAccountMapper;
import by.nortin.mapper.BankMapper;
import by.nortin.mapper.TransactionMapper;
import by.nortin.mapper.UserMapper;
import by.nortin.mapper.impl.BankAccountMapperImpl;
import by.nortin.mapper.impl.BankMapperImpl;
import by.nortin.mapper.impl.TransactionMapperImpl;
import by.nortin.mapper.impl.UserMapperImpl;
import by.nortin.repository.BankAccountRepository;
import by.nortin.repository.BankRepository;
import by.nortin.repository.TransactionRepository;
import by.nortin.repository.UserRepository;
import by.nortin.repository.impl.BankAccountRepositoryImpl;
import by.nortin.repository.impl.BankRepositoryImpl;
import by.nortin.repository.impl.TransactionRepositoryImpl;
import by.nortin.repository.impl.UserRepositoryImpl;
import by.nortin.service.BankAccountService;
import by.nortin.service.BankService;
import by.nortin.service.ReceiptSavingService;
import by.nortin.service.TransactionService;
import by.nortin.service.UserService;
import by.nortin.service.impl.BankAccountServiceImpl;
import by.nortin.service.impl.BankServiceImpl;
import by.nortin.service.impl.ReceiptSavingServiceImpl;
import by.nortin.service.impl.TransactionServiceImpl;
import by.nortin.service.impl.UserServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

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
        Object result;
        if (UserService.class == serviceClass) {
            result = new UserServiceImpl();
        } else if (UserRepository.class == serviceClass) {
            result = new UserRepositoryImpl();
        } else if (UserMapper.class == serviceClass) {
            result = new UserMapperImpl();
        } else if (BankAccountService.class == serviceClass) {
            result = new BankAccountServiceImpl();
        } else if (BankAccountRepository.class == serviceClass) {
            result = new BankAccountRepositoryImpl();
        } else if (BankAccountMapper.class == serviceClass) {
            result = new BankAccountMapperImpl();
        } else if (TransactionService.class == serviceClass) {
            result = new TransactionServiceImpl();
        } else if (TransactionRepository.class == serviceClass) {
            result = new TransactionRepositoryImpl();
        } else if (TransactionMapper.class == serviceClass) {
            result = new TransactionMapperImpl();
        } else if (BankMapper.class == serviceClass) {
            result = new BankMapperImpl();
        } else if (ModelMapper.class == serviceClass) {
            result = new ModelMapper();
        } else if (ReceiptSavingService.class == serviceClass) {
            result = new ReceiptSavingServiceImpl();
        } else if (BankService.class == serviceClass) {
            result = new BankServiceImpl();
        } else if (BankRepository.class == serviceClass) {
            result = new BankRepositoryImpl();
        } else {
            throw new IllegalArgumentException("Can not create instance of class " + serviceClass);
        }
        return result;
    }

//    private static final Map<Class<?>, Object> serviceMap = new HashMap<>();
//    static {
//        serviceMap.put(UserService.class, new UserServiceImpl());
//        serviceMap.put(UserRepository.class, new UserRepositoryImpl());
//        serviceMap.put(UserMapper.class, new UserMapperImpl());
//        serviceMap.put(BankAccountService.class, new BankAccountServiceImpl());
//        serviceMap.put(BankAccountRepository.class, new BankAccountRepositoryImpl());
//        serviceMap.put(BankAccountMapper.class, new BankAccountMapperImpl());
//        serviceMap.put(TransactionService.class, new TransactionServiceImpl());
//        serviceMap.put(TransactionRepository.class, new TransactionRepositoryImpl());
//        serviceMap.put(TransactionMapper.class, new TransactionMapperImpl());
//        serviceMap.put(BankMapper.class, new BankMapperImpl());
//        serviceMap.put(ModelMapper.class, new ModelMapper());
//        serviceMap.put(ReceiptSavingService.class, new ReceiptSavingServiceImpl());
//        serviceMap.put(BankService.class, new BankServiceImpl());
//    }
//
//    private static <T> Object createInstance1(Class<T> serviceClass) {
//        return serviceMap.getOrDefault(serviceClass,
//                () -> { throw new IllegalArgumentException("Can not create instance of class " + serviceClass); }
//        );
//    }
}
