package by.nortin.mapper;

import by.nortin.dto.BankAccountDto;
import by.nortin.model.BankAccount;

public interface BankAccountMapper {

    /**
     * The method converts the BankAccount object to the bankAccountDto object.
     *
     * @param bankAccount - object to convert
     * @return BankAccountDto object
     */
    BankAccountDto convertToDto(BankAccount bankAccount);

    /**
     * The method converts the BankAccountDto object to the bankAccount object.
     *
     * @param bankAccountDto - object to convert
     * @return BankAccount object
     */
    BankAccount convertToModel(BankAccountDto bankAccountDto);
}
