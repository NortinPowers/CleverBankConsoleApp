package by.nortin;

import by.nortin.config.AppConfig;
import by.nortin.model.CleverBankEnvironment;
import by.nortin.repository.ConnectionPool;
import by.nortin.service.BankAccountService;
import by.nortin.service.CleverBankEnvironmentService;
import by.nortin.service.CustomScheduledExecutorService;
import by.nortin.service.EnvironmentUIService;
import by.nortin.service.impl.BankAccountServiceImpl;
import by.nortin.service.impl.CleverBankEnvironmentServiceImpl;
import by.nortin.service.impl.CustomScheduledExecutorServiceImpl;
import by.nortin.service.impl.EnvironmentUIServiceImpl;

public class Main {

    private static final AppConfig APP_CONFIG;
    private static final int INTEREST_RATE;

    static {
        APP_CONFIG = new AppConfig();
        INTEREST_RATE = (int) APP_CONFIG.getProperty("bank").get("interestRate");
    }

    /**
     * The application entry point.
     *
     * @param args null
     */
    public static void main(String[] args) {
        CleverBankEnvironment bankEnvironment = new CleverBankEnvironment();
        CleverBankEnvironmentService cleverBankEnvironmentService = new CleverBankEnvironmentServiceImpl(bankEnvironment);
        EnvironmentUIService environmentUIService = new EnvironmentUIServiceImpl(bankEnvironment);
        BankAccountService bankAccountService = new BankAccountServiceImpl();
        CustomScheduledExecutorService customScheduledExecutorService = new CustomScheduledExecutorServiceImpl(bankAccountService, INTEREST_RATE);
        customScheduledExecutorService.start();
        while (cleverBankEnvironmentService.isWorking()) {
            environmentUIService.openBankEnvironment();
        }
        System.out.println("Clever Bank is closed");
        customScheduledExecutorService.stop();
        ConnectionPool.getInstance().closeAllConnection();
    }
}
