package by.nortin.service.impl;

import static by.nortin.util.InjectObjectsFactory.getInstance;

import by.nortin.dto.TransactionDto;
import by.nortin.mapper.TransactionMapper;
import by.nortin.model.Transaction;
import by.nortin.repository.TransactionRepository;
import by.nortin.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    {
        transactionRepository = (TransactionRepository) getInstance(TransactionRepository.class);
        transactionMapper = (TransactionMapper) getInstance(TransactionMapper.class);
    }

    @Override
//    public TransactionDto saveTransaction(
//            OperationType operationType,
//            String recipientBank,
//            String sendingBank,
//            Long recipientBankAccount,
//            Long sendingBankAccount,
//            BigDecimal depositedMoney) {
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        if (transactionDto.getSendingBankDto() == null) {
            transactionDto.setSendingBankDto(transactionDto.getRecipientBankDto());
        }
        if (transactionDto.getSendingBankAccountDto() == null) {
            transactionDto.setSendingBankAccountDto(transactionDto.getRecipientBankAccountDto());
        }
//        return transactionMapper.convertToDto(transactionRepository.saveTransaction(
//                operationType,
//                recipientBank,
//                sendingBank,
//                recipientBankAccount,
//                sendingBankAccount,
//                depositedMoney));
        Transaction transaction = transactionRepository.saveTransaction(transactionMapper.convertToModel(transactionDto));
        return transactionMapper.convertToDto(transaction);
    }
}
