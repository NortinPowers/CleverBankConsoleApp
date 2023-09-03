package by.nortin.repository;

import by.nortin.model.Bank;
import java.util.List;

public interface BankRepository {

    /**
     * The method returns a list of all banks.
     *
     * @return List of Bank
     */
    List<Bank> getAll();
}
