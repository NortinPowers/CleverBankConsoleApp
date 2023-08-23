package by.nortin.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bank {

    private Long id;
    private String name;
    private List<BankAccount> bankAccounts;
}
