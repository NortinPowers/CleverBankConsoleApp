package by.nortin.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction extends BaseModel {

    //    private Long id;
    private LocalDateTime date;
    private Long currencyId;
    private Currency currency;
    private BigDecimal monies;
    private OperationType operationType;
    private Bank sendingBank;
    private BankAccount sendingBankAccount;
    private Bank recipientBank;
    private BankAccount recipientBankAccount;
}
