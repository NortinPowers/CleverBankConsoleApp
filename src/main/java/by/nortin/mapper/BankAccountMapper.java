package by.nortin.mapper;

import by.nortin.dto.BankAccountDto;
import by.nortin.model.BankAccount;

public interface BankAccountMapper {

    BankAccountDto convertToDto(BankAccount bankAccount);
}
