package by.nortin.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.nortin.dto.BankAccountDto;
import by.nortin.mapper.BankAccountMapper;
import by.nortin.model.BankAccount;
import by.nortin.repository.BankAccountRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountMapper bankAccountMapper;

    @Test
    void test_getUserBankAccounts() {
        BankAccountDto bankAccountDto = new BankAccountDto();
        Long number = 1L;
        bankAccountDto.setNumber(number);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(number);
        String login = "user";
        List<BankAccountDto> bankAccountDtos = List.of(bankAccountDto);
        List<BankAccount> bankAccounts = List.of(bankAccount);
        bankAccountService.setBankAccountMapper(bankAccountMapper);
        bankAccountService.setBankAccountRepository(bankAccountRepository);

        given(bankAccountMapper.convertToDto(bankAccount)).willReturn(bankAccountDto);
        given(bankAccountRepository.getUserBankAccounts(login)).willReturn(bankAccounts);

        List<BankAccountDto> userBankAccounts = bankAccountService.getUserBankAccounts(login);

        assertEquals(userBankAccounts, bankAccountDtos);
        verify(bankAccountRepository, atLeastOnce()).getUserBankAccounts(login);
        verify(bankAccountMapper, atLeastOnce()).convertToDto(bankAccount);
    }

    @Test
    void test_transferMoneyBetweenAccounts() {
        Long number = 1L;
        BankAccount account = new BankAccount();
        account.setNumber(number);
        BankAccountDto accountDto = new BankAccountDto();
        accountDto.setNumber(number);
        BigDecimal money = BigDecimal.ONE;
        bankAccountService.setBankAccountMapper(bankAccountMapper);
        bankAccountService.setBankAccountRepository(bankAccountRepository);

        given(bankAccountMapper.convertToModel(accountDto)).willReturn(account);
        doNothing().when(bankAccountRepository).transferMoneyBetweenAccounts(account, account, money);

        bankAccountService.transferMoneyBetweenAccounts(accountDto, accountDto, money);

        verify(bankAccountMapper, atMostOnce()).convertToDto(account);
        verify(bankAccountRepository, atLeastOnce()).transferMoneyBetweenAccounts(account, account, money);
    }

    @Test
    void test_checkAvailabilityOfFunds() {
        Long number = 1L;
        BigDecimal money = BigDecimal.ONE;
        boolean flag = true;
        bankAccountService.setBankAccountRepository(bankAccountRepository);

        when(bankAccountRepository.checkAvailabilityOfFunds(number, money)).thenReturn(flag);

        assertTrue(bankAccountService.checkAvailabilityOfFunds(number, money));
        verify(bankAccountRepository, atLeastOnce()).checkAvailabilityOfFunds(number, money);
    }

    @Test
    void test_getUserBankAccountByNumberForSpecificBank() {
        Long number = 1L;
        String name = "test";
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(number);
        BankAccountDto expectedBankAccountDto = new BankAccountDto();
        expectedBankAccountDto.setId(number);
        bankAccountService.setBankAccountRepository(bankAccountRepository);
        bankAccountService.setBankAccountMapper(bankAccountMapper);

        when(bankAccountRepository.getUserBankAccountByNumberForSpecificBank(number, name)).thenReturn(bankAccount);
        when(bankAccountMapper.convertToDto(bankAccount)).thenReturn(expectedBankAccountDto);

        BankAccountDto bankAccountDto = bankAccountService.getUserBankAccountByNumberForSpecificBank(number, name);

        assertEquals(bankAccountDto, expectedBankAccountDto);
        verify(bankAccountRepository, atLeastOnce()).getUserBankAccountByNumberForSpecificBank(number, name);
        verify(bankAccountMapper, atLeastOnce()).convertToDto(bankAccount);
    }

    @Test
    void test_findAll() {
        BankAccountDto bankAccountDto = new BankAccountDto();
        Long number = 1L;
        bankAccountDto.setNumber(number);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(number);
        List<BankAccountDto> expectedBankAccountDtos = List.of(bankAccountDto);
        List<BankAccount> bankAccounts = List.of(bankAccount);
        bankAccountService.setBankAccountMapper(bankAccountMapper);
        bankAccountService.setBankAccountRepository(bankAccountRepository);

        when(bankAccountMapper.convertToDto(bankAccount)).thenReturn(bankAccountDto);
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);

        List<BankAccountDto> bankAccountDtos = bankAccountService.findAll();

        assertEquals(bankAccountDtos, expectedBankAccountDtos);
        verify(bankAccountRepository, atLeastOnce()).findAll();
        verify(bankAccountMapper, atLeastOnce()).convertToDto(bankAccount);
    }

    @Test
    void save() {
        BankAccountDto bankAccountDto = new BankAccountDto();
        Long number = 1L;
        bankAccountDto.setNumber(number);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber(number);
        bankAccountService.setBankAccountMapper(bankAccountMapper);
        bankAccountService.setBankAccountRepository(bankAccountRepository);

        doNothing().when(bankAccountRepository).save(bankAccount);
        when(bankAccountMapper.convertToModel(bankAccountDto)).thenReturn(bankAccount);

        bankAccountService.save(bankAccountDto);

        verify(bankAccountRepository, atLeastOnce()).save(bankAccount);
        verify(bankAccountMapper, atLeastOnce()).convertToModel(bankAccountDto);
    }

    @Nested
    class TestChangeBankAccountBalance {

        @Test
        void test_changeBankAccountBalance_success() {
            Long id = 1L;
            BigDecimal one = BigDecimal.ONE;
            boolean flag = true;
            BigDecimal expectedBalance = BigDecimal.TEN;
            bankAccountService.setBankAccountRepository(bankAccountRepository);

            given(bankAccountRepository.changeBankAccountBalance(id, one, flag)).willReturn(expectedBalance);

            BigDecimal accountBalance = bankAccountService.changeBankAccountBalance(id, one, flag);

            assertEquals(accountBalance, expectedBalance);
            verify(bankAccountRepository, atLeastOnce()).changeBankAccountBalance(id, one, flag);

        }

        @Test
        void test_changeBankAccountBalance_exception() {
            Long id = 1L;
            BigDecimal one = BigDecimal.ONE;
            boolean flag = true;
            bankAccountService.setBankAccountRepository(bankAccountRepository);

            when(bankAccountRepository.changeBankAccountBalance(id, one, flag)).thenAnswer(invocation -> {
                throw new Exception();
            });

            assertThrows(Exception.class, () -> bankAccountService.changeBankAccountBalance(id, one, flag));
            verify(bankAccountRepository, atLeastOnce()).changeBankAccountBalance(id, one, flag);

        }
    }
}
