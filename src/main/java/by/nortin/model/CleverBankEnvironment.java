package by.nortin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleverBankEnvironment {

    private boolean isWorking;
    private String activeUser;

    {
        isWorking = true;
    }
}
