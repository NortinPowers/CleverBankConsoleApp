package by.nortin.mapper;

import by.nortin.dto.BankDto;
import by.nortin.model.Bank;

public interface BankMapper {

    /**
     * The method converts the Bank object to the bankDto object.
     *
     * @param bank - object to convert
     * @return BankDto object
     */
    BankDto convertToDto(Bank bank);

    /**
     * The method converts the BankDto object to the bank object.
     *
     * @param bankDto - object to convert
     * @return Bank object
     */
    Bank convertToModel(BankDto bankDto);
}
