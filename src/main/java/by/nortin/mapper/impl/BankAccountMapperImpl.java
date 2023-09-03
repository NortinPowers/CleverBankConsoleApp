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

    /**
     * Implementation of a method that converts the BankAccount object to the bankAccountDto object.
     *
     * @param bankAccount - object to convert
     * @return BankAccountDto object
     */
    @Override
    public BankAccountDto convertToDto(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = modelMapper.map(bankAccount, BankAccountDto.class);
        if (bankAccount.getBank() != null) {
            bankAccountDto.setBankDto(bankMapper.convertToDto(bankAccount.getBank()));
        }
        return bankAccountDto;
    }

    /**
     * Implementation of a method that converts the BankAccountDto object to the bankAccount object.
     *
     * @param bankAccountDto - object to convert
     * @return BankAccount object
     */
    @Override
    public BankAccount convertToModel(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = modelMapper.map(bankAccountDto, BankAccount.class);
        if (bankAccountDto.getBankDto() != null) {
            bankAccount.setBank(bankMapper.convertToModel(bankAccountDto.getBankDto()));
        }
        return bankAccount;
    }
}
