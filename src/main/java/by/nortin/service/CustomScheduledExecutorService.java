package by.nortin.service;

public interface CustomScheduledExecutorService {

    /**
     * The method starts the asynchronous task scheduler.
     */
    void start();

    /**
     * The method stop the asynchronous task scheduler.
     */
    void stop();
}
