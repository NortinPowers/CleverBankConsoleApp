package by.nortin.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transactional {

    private Long id;
    private LocalDate date;
    private Currency currency;
    private BigDecimal monies;
    private OperationType operationType;
    private Bank sendingBank;
    private BankAccount sendingBankAccount;
    private Bank recipientBank;
    private BankAccount recipientBankAccount;
}
