package by.nortin.util;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.TransactionDto;
import by.nortin.model.OperationType;
import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BuildUtils {

    public static TransactionDto buildTransactionDtoWithoutRecipientBankInfo(BankAccountDto bankAccountDto, BigDecimal depositedMoney) {
        return TransactionDto.builder()
                .currencyId(bankAccountDto.getCurrencyId())
                .currency(bankAccountDto.getCurrency())
                .monies(depositedMoney)
                .operationType(OperationType.REPLENISHMENT)
                .sendingBankDto(bankAccountDto.getBankDto())
                .sendingBankAccountDto(bankAccountDto)
                .build();
    }
}
