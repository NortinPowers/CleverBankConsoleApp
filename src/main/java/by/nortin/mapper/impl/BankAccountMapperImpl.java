package by.nortin.mapper.impl;

import by.nortin.dto.BankAccountDto;
import by.nortin.mapper.BankAccountMapper;
import by.nortin.model.BankAccount;

public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccountDto convertToDto(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        if (bankAccount.getNumber() != null) {
            bankAccountDto.setNumber(bankAccount.getNumber());
        }
        if (bankAccount.getBalance() != null) {
            bankAccountDto.setBalance(bankAccount.getBalance());
        }
        if (bankAccount.getCurrency() != null) {
            bankAccountDto.setCurrency(bankAccount.getCurrency());
        }
        if (bankAccount.getBank() != null) {
            bankAccountDto.setBank(bankAccount.getBank());
        }
        return bankAccountDto;
    }
}
