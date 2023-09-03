package by.nortin.service;

import by.nortin.dto.BankDto;
import java.util.List;

public interface BankService {

    /**
     * The method returns a list of all banks.
     *
     * @return List of BankDto
     */
    List<BankDto> getAll();
}
