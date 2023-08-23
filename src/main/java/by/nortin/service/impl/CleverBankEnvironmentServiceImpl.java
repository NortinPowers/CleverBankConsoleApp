package by.nortin.service.impl;

import by.nortin.model.CleverBankEnvironment;
import by.nortin.service.CleverBankEnvironmentService;

public class CleverBankEnvironmentServiceImpl implements CleverBankEnvironmentService {
    private final CleverBankEnvironment bankEnvironment;

    public CleverBankEnvironmentServiceImpl(CleverBankEnvironment bankEnvironment) {
        this.bankEnvironment = bankEnvironment;
    }

    @Override
    public boolean isWorking() {
        return bankEnvironment.isWorking();
    }
}
