package by.nortin.service;

public interface CleverBankEnvironmentService {

    /**
     * The method checks whether the banking application is working.
     *
     * @return boolean result
     */
    boolean isWorking();

    /**
     * The method terminates the banking application.
     */
    void closeApp();
}
