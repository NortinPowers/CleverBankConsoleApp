package by.nortin.service.impl;

import static by.nortin.util.InjectObjectsFactory.getInstance;

import by.nortin.dto.BankDto;
import by.nortin.mapper.BankMapper;
import by.nortin.repository.BankRepository;
import by.nortin.service.BankService;
import java.util.List;

public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    {
        bankRepository = (BankRepository) getInstance(BankRepository.class);
        bankMapper = (BankMapper) getInstance(BankMapper.class);
    }

    /**
     * Implementation of the method returns a list of all banks.
     *
     * @return List of BankDto
     */
    @Override
    public List<BankDto> getAll() {
        return bankRepository.getAll().stream()
                .map(bankMapper::convertToDto)
                .toList();
    }
}
