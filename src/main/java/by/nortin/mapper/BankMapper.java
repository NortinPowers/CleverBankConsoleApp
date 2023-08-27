package by.nortin.mapper;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.BankDto;
import by.nortin.dto.UserDto;
import by.nortin.model.Bank;
import by.nortin.model.BankAccount;
import by.nortin.model.User;

public interface BankMapper {

    BankDto convertToDto(Bank bank);

    Bank convertToModel(BankDto bankDto);
}
