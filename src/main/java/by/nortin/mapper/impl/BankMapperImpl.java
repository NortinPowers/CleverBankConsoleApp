package by.nortin.mapper.impl;

import by.nortin.dto.BankDto;
import by.nortin.mapper.BankMapper;
import by.nortin.model.Bank;
import by.nortin.util.InjectObjectsFactory;
import org.modelmapper.ModelMapper;

public class BankMapperImpl implements BankMapper {

    private final ModelMapper modelMapper;

    {
        modelMapper = (ModelMapper) InjectObjectsFactory.getInstance(ModelMapper.class);
    }

    /**
     * Implementation of a method that converts the Bank object to the bankDto object.
     *
     * @param bank - object to convert
     * @return BankDto object
     */
    @Override
    public BankDto convertToDto(Bank bank) {
        return modelMapper.map(bank, BankDto.class);
    }

    /**
     * Implementation of a method that converts the BankDto object to the bank object.
     *
     * @param bankDto - object to convert
     * @return Bank object
     */
    @Override
    public Bank convertToModel(BankDto bankDto) {
        return modelMapper.map(bankDto, Bank.class);
    }
}
