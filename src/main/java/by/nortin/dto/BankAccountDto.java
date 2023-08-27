package by.nortin.dto;

import java.math.BigDecimal;
import java.util.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDto {

    private Long id;
    private Long number;
    private BigDecimal balance;
    private Long currencyId;
    private Currency currency;
    private BankDto bankDto;
}
