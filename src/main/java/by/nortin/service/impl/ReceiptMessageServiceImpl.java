package by.nortin.service.impl;

import by.nortin.dto.TransactionDto;
import by.nortin.service.ReceiptMessageService;
import java.time.format.DateTimeFormatter;

public class ReceiptMessageServiceImpl implements ReceiptMessageService {

    /**
     * Implementation of the method generates a receipt based on data from the TransactionDto object.
     *
     * @param transactionDto - current transaction
     * @return String
     */
    @Override
    public String formReceipt(TransactionDto transactionDto) {
        return "__________________________________________________\n"
                + String.format("|%31s                 |\n", "Bank Receipt")
                + String.format(getFieldFormat(), "Receipt:", transactionDto.getId())
                + String.format(getFieldFormat(), transactionDto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), transactionDto.getDate().format(DateTimeFormatter.ofPattern("hh:mm")))
                + String.format(getFieldFormat(), "Transaction type", transactionDto.getOperationType().name().toLowerCase())
                + String.format(getFieldFormat(), "Sending bank", transactionDto.getSendingBankDto().getName())
                + String.format(getFieldFormat(), "Recipient bank", transactionDto.getRecipientBankDto().getName())
                + String.format(getFieldFormat(), "Sending bank account", transactionDto.getSendingBankAccountDto().getNumber())
                + String.format(getFieldFormat(), "Recipient bank account", transactionDto.getRecipientBankAccountDto().getNumber())
                + String.format(getFieldFormat(), "   Total:", transactionDto.getMonies() + " " + transactionDto.getCurrency().getCurrencyCode() + "   ")
                + "|________________________________________________|\n";
    }

    /**
     * The method returns the format of the receipt line.
     *
     * @return String pattern
     */
    private static String getFieldFormat() {
        return "|%1$-22s %2$25s|\n";
    }
}
