package by.nortin.mapper.impl;

import by.nortin.dto.BankDto;
import by.nortin.mapper.BankMapper;
import by.nortin.model.Bank;
import by.nortin.util.InjectObjectsFactory;
import by.nortin.util.MapperUtils;
import org.modelmapper.ModelMapper;

public class BankMapperImpl implements BankMapper {

    private final ModelMapper modelMapper;

    {
        modelMapper = (ModelMapper) InjectObjectsFactory.getInstance(ModelMapper.class);
    }

    @Override
    public BankDto convertToDto(Bank bank) {
//        BankDto bankDto = new BankDto();
//        return MapperUtils.setValue(bank, bankDto);
        return modelMapper.map(bank, BankDto.class);
    }

    @Override
    public Bank convertToModel(BankDto bankDto) {
//        Bank bank = new Bank();
//        return MapperUtils.setValue(bankDto, bank);
        return modelMapper.map(bankDto, Bank.class);
    }
}
