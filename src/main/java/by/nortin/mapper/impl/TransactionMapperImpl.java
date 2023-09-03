package by.nortin.mapper.impl;

import by.nortin.dto.TransactionDto;
import by.nortin.mapper.BankAccountMapper;
import by.nortin.mapper.BankMapper;
import by.nortin.mapper.TransactionMapper;
import by.nortin.model.Transaction;
import by.nortin.util.InjectObjectsFactory;
import org.modelmapper.ModelMapper;

public class TransactionMapperImpl implements TransactionMapper {

    private final BankMapper bankMapper;
    private final BankAccountMapper bankAccountMapper;
    private final ModelMapper modelMapper;

    {
        bankMapper = (BankMapper) InjectObjectsFactory.getInstance(BankMapper.class);
        bankAccountMapper = (BankAccountMapper) InjectObjectsFactory.getInstance(BankAccountMapper.class);
        modelMapper = (ModelMapper) InjectObjectsFactory.getInstance(ModelMapper.class);
    }

    /**
     * Implementation of a method that converts the Transaction object to the TransactionDto object.
     *
     * @param transaction - object to convert
     * @return TransactionDto object
     */
    @Override
    public TransactionDto convertToDto(Transaction transaction) {
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        fillFieldsOfDtoIfEmpty(transaction, transactionDto);
        return transactionDto;
    }

    /**
     * Implementation of a method that converts the TransactionDto object to the Transaction object.
     *
     * @param transactionDto - object to convert
     * @return Transaction object
     */
    @Override
    public Transaction convertToModel(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        fillFieldsOfModelIfEmpty(transactionDto, transaction);
        return transaction;
    }

    /**
     * The method assigns the appropriate value to the recipient's fields if they are empty.
     *
     * @param transaction    - object source of values
     * @param transactionDto - value recipient object
     */
    private void fillFieldsOfDtoIfEmpty(Transaction transaction, TransactionDto transactionDto) {
        if (transaction.getRecipientBank() != null) {
            transactionDto.setRecipientBankDto(bankMapper.convertToDto(transaction.getRecipientBank()));
        }
        if (transaction.getSendingBank() != null) {
            transactionDto.setSendingBankDto(bankMapper.convertToDto(transaction.getSendingBank()));
        }
        if (transaction.getRecipientBankAccount() != null) {
            transactionDto.setRecipientBankAccountDto(bankAccountMapper.convertToDto(transaction.getRecipientBankAccount()));
        }
        if (transaction.getSendingBankAccount() != null) {
            transactionDto.setSendingBankAccountDto(bankAccountMapper.convertToDto(transaction.getSendingBankAccount()));
        }
    }

    /**
     * The method assigns the appropriate value to the recipient's fields if they are empty.
     *
     * @param transactionDto - object source of values
     * @param transaction    - value recipient object
     */
    private void fillFieldsOfModelIfEmpty(TransactionDto transactionDto, Transaction transaction) {
        if (transactionDto.getRecipientBankDto() != null) {
            transaction.setRecipientBank(bankMapper.convertToModel(transactionDto.getRecipientBankDto()));
        }
        if (transactionDto.getSendingBankDto() != null) {
            transaction.setSendingBank(bankMapper.convertToModel(transactionDto.getSendingBankDto()));
        }
        if (transactionDto.getRecipientBankAccountDto() != null) {
            transaction.setRecipientBankAccount(bankAccountMapper.convertToModel(transactionDto.getRecipientBankAccountDto()));
        }
        if (transactionDto.getSendingBankAccountDto() != null) {
            transaction.setSendingBankAccount(bankAccountMapper.convertToModel(transactionDto.getSendingBankAccountDto()));
        }
    }
}
