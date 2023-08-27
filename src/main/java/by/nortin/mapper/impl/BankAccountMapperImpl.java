package by.nortin.mapper.impl;

import by.nortin.dto.BankAccountDto;
import by.nortin.mapper.BankAccountMapper;
import by.nortin.mapper.BankMapper;
import by.nortin.model.BankAccount;
import by.nortin.util.InjectObjectsFactory;
import org.modelmapper.ModelMapper;

public class BankAccountMapperImpl implements BankAccountMapper {

    private final BankMapper bankMapper;
    private final ModelMapper modelMapper;

    {
        bankMapper = (BankMapper) InjectObjectsFactory.getInstance(BankMapper.class);
        modelMapper = (ModelMapper) InjectObjectsFactory.getInstance(ModelMapper.class);
    }

    @Override
    public BankAccountDto convertToDto(BankAccount bankAccount) {
//        BankAccountDto bankAccountDto = new BankAccountDto();
        BankAccountDto bankAccountDto = modelMapper.map(bankAccount, BankAccountDto.class);
        if (bankAccount.getBank() != null) {
            bankAccountDto.setBankDto(bankMapper.convertToDto(bankAccount.getBank()));
        }
//        if (bankAccount.getNumber() != null) {
//            bankAccountDto.setNumber(bankAccount.getNumber());
//        }
//        if (bankAccount.getBalance() != null) {
//            bankAccountDto.setBalance(bankAccount.getBalance());
//        }
//        if (bankAccount.getCurrency() != null) {
//            bankAccountDto.setCurrency(bankAccount.getCurrency());
//        }
//        if (bankAccount.getBank() != null) {
//            bankAccountDto.setBank(bankAccount.getBank());
//        }
//        return bankAccountDto;
//        return MapperUtils.setValue(bankAccount, bankAccountDto);
        return bankAccountDto;
    }

    @Override
    public BankAccount convertToModel(BankAccountDto bankAccountDto) {
//        BankAccount bankAccount = new BankAccount();
        BankAccount bankAccount = modelMapper.map(bankAccountDto, BankAccount.class);
        if (bankAccountDto.getBankDto() != null) {
            bankAccount.setBank(bankMapper.convertToModel(bankAccountDto.getBankDto()));
        }
//        return MapperUtils.setValue(bankAccountDto, bankAccount);
        return bankAccount;
    }
}
