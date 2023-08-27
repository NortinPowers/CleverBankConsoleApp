package by.nortin.model;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseModel {

    //    private Long id;
    private String name;
    private List<BankAccount> bankAccounts;

    public Bank(String name) {
        this.name = name;
    }
}
