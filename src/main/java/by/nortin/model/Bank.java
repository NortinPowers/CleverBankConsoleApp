package by.nortin.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Bank extends BaseModel {

    //    private Long id;
    private String name;
    private List<BankAccount> bankAccounts;

    public Bank(String name) {
        this.name = name;
    }
}
