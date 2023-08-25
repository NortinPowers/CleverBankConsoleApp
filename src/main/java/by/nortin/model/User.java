package by.nortin.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel {

    //    private Long id;
    private String passportId;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String patronymic;
    private List<BankAccount> bankAccounts;
}
