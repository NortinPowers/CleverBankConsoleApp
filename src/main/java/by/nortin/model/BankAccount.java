package by.nortin.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BankAccount extends BaseModel {

    //    private Long id;
    private Long number;
    private BigDecimal balance;
    private Currency currency;
    private LocalDate accountOpeningDate;
    private User user;
    private Bank bank;
    private List<Transactional> transactions;
}
