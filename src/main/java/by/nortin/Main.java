package by.nortin;

import by.nortin.model.CleverBankEnvironment;
import by.nortin.service.CleverBankEnvironmentService;
import by.nortin.service.impl.CleverBankEnvironmentServiceImpl;
import by.nortin.service.impl.EnvironmentUIServiceImpl;

public class Main {

    public static void main(String[] args) {
        CleverBankEnvironment bankEnvironment = new CleverBankEnvironment();
        CleverBankEnvironmentService cleverBankEnvironmentService = new CleverBankEnvironmentServiceImpl(bankEnvironment);
        EnvironmentUIServiceImpl environmentUIService = new EnvironmentUIServiceImpl(bankEnvironment);
        while (cleverBankEnvironmentService.isWorking()) {
            environmentUIService.openBankEnvironment();
        }
        System.out.println("Clever Bank is closed");
    }
}
