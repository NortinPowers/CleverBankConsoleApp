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

    /**
     * Implementation of save the user transactionDto and returns the modified transactionDto.
     *
     * @param transactionDto TransactionDto
     * @return modified transactionDto
     */
    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        checkAndFillFieldsIfEmpty(transactionDto);
        Transaction transaction = transactionRepository.saveTransaction(transactionMapper.convertToModel(transactionDto));
        return transactionMapper.convertToDto(transaction);
    }

    /**
     * The method fills in the missing information about the bank and bank account.
     *
     * @param transactionDto TransactionDto
     */
    private static void checkAndFillFieldsIfEmpty(TransactionDto transactionDto) {
        if (transactionDto.getSendingBankDto() == null) {
            transactionDto.setSendingBankDto(transactionDto.getRecipientBankDto());
        }
        if (transactionDto.getSendingBankAccountDto() == null) {
            transactionDto.setSendingBankAccountDto(transactionDto.getRecipientBankAccountDto());
        }
        if (transactionDto.getRecipientBankDto() == null) {
            transactionDto.setRecipientBankDto(transactionDto.getSendingBankDto());
        }
        if (transactionDto.getRecipientBankAccountDto() == null) {
            transactionDto.setRecipientBankAccountDto(transactionDto.getSendingBankAccountDto());
        }
    }
}
