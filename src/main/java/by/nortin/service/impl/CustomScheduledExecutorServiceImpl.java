package by.nortin.service.impl;

import static by.nortin.util.MessageUtils.getErrorMessageToLog;

import by.nortin.dto.BankAccountDto;
import by.nortin.service.BankAccountService;
import by.nortin.service.CustomScheduledExecutorService;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class CustomScheduledExecutorServiceImpl implements CustomScheduledExecutorService {

    private final BankAccountService bankAccountService;
    private final double interestRate;
    private ScheduledExecutorService scheduler;

    /**
     * Implementation of the method starts the asynchronous task scheduler.
     */
    @Override
    public void start() {
        try {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(this::calculateInterest, 0, 30, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(getErrorMessageToLog("ScheduledExecutor.start()"), e);
        }
    }

    /**
     * Implementation of the method stop the asynchronous task scheduler.
     */
    public void stop() {
        try {
            scheduler.shutdown();
        } catch (Exception e) {
            log.error(getErrorMessageToLog("ScheduledExecutor.stop()"), e);
        }
    }

    /**
     * The method charges interest to the bank accounts if necessary.
     */
    private void calculateInterest() {
        bankAccountService.findAll().forEach(account -> {
            LocalDateTime currentDate = LocalDate.now().atStartOfDay();
            Duration duration = getDuration(account, currentDate);
            if (duration.toDays() >= 30) {
                BigDecimal balance = account.getBalance();
                BigDecimal interest = balance.multiply(new BigDecimal(interestRate / 100.0));
                account.setBalance(balance.add(interest));
                bankAccountService.save(account);
            }
        });
    }

    /**
     * The method returns information about the time of the last bank account maintenance.
     *
     * @param account     BankAccountDto
     * @param currentDate LocalDateTime
     * @return Duration
     */
    private Duration getDuration(BankAccountDto account, LocalDateTime currentDate) {
        Duration duration;
        if (account.getDateOfLastService() != null) {
            duration = Duration.between(account.getDateOfLastService().atStartOfDay(), currentDate);
        } else {
            duration = Duration.between(account.getAccountOpeningDate().atStartOfDay(), currentDate);
        }
        return duration;
    }
}
