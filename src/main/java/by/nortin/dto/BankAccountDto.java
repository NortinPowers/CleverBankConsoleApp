package by.nortin.dto;

import by.nortin.model.Bank;
import java.math.BigDecimal;
import java.util.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDto {

    private Long number;
    private BigDecimal balance;
    private Currency currency;
    private Bank bank;
}
