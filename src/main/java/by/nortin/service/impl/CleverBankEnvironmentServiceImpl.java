package by.nortin.service.impl;

import by.nortin.model.CleverBankEnvironment;
import by.nortin.service.CleverBankEnvironmentService;

public class CleverBankEnvironmentServiceImpl implements CleverBankEnvironmentService {
    private final CleverBankEnvironment bankEnvironment;

    public CleverBankEnvironmentServiceImpl(CleverBankEnvironment bankEnvironment) {
        this.bankEnvironment = bankEnvironment;
    }

    /**
     * Implementation of the method checks whether the banking application is working.
     *
     * @return boolean result
     */
    @Override
    public boolean isWorking() {
        return bankEnvironment.isWorking();
    }

    /**
     * Implementation of the method terminates the banking application.
     */
    @Override
    public void closeApp() {
        bankEnvironment.setWorking(false);
    }
}
