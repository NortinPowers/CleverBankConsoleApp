package by.nortin.dto;

import by.nortin.model.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TransactionDto {

    private Long id;
    private LocalDateTime date;
    private Long currencyId;
    private Currency currency;
    private BigDecimal monies;
    private OperationType operationType;
    private BankDto sendingBankDto;
    private BankAccountDto sendingBankAccountDto;
    private BankDto recipientBankDto;
    private BankAccountDto recipientBankAccountDto;
}
