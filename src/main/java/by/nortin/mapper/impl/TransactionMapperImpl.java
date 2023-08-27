package by.nortin.mapper.impl;

import by.nortin.dto.TransactionDto;
import by.nortin.mapper.BankAccountMapper;
import by.nortin.mapper.BankMapper;
import by.nortin.mapper.TransactionMapper;
import by.nortin.model.Transaction;
import by.nortin.util.InjectObjectsFactory;
import by.nortin.util.MapperUtils;
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

    @Override
    public TransactionDto convertToDto(Transaction transaction) {
//        TransactionDto transactionDto = new TransactionDto();
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
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
//        return MapperUtils.setValue(transaction, transactionDto);
        return transactionDto;
    }

    @Override
    public Transaction convertToModel(TransactionDto transactionDto) {
//        Transaction transaction = new Transaction();
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
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
//        return MapperUtils.setValue(transactionDto, transaction);
        return transaction;
    }
}
