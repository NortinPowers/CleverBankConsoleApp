package by.nortin.mapper;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.BankDto;
import by.nortin.model.Bank;
import by.nortin.model.BankAccount;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankAccountMapper {

//    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);
    BankAccountDto convertToDto(BankAccount bankAccount);

    BankAccount convertToModel(BankAccountDto bankAccountDto);
}
