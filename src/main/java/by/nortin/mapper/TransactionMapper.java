package by.nortin.mapper;

import by.nortin.dto.TransactionDto;
import by.nortin.dto.UserDto;
import by.nortin.model.Transaction;
import by.nortin.model.User;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

//    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionDto convertToDto(Transaction transaction);

    Transaction convertToModel(TransactionDto transactionDto);
}
