package by.nortin.service.impl;

import static by.nortin.util.Constants.TEXT_READ_INT_FROM_CONSOLE;
import static by.nortin.util.DrawUIUtils.drawAuthenticateRequest;
import static by.nortin.util.DrawUIUtils.drawBankAccountSelection;
import static by.nortin.util.DrawUIUtils.drawEmptyForSelectionLine;
import static by.nortin.util.DrawUIUtils.drawErrorAuthenticateRequest;
import static by.nortin.util.DrawUIUtils.drawGreetingUser;
import static by.nortin.util.DrawUIUtils.drawReplenishmentBalanceSelection;
import static by.nortin.util.DrawUIUtils.drawSelectionLine;
import static by.nortin.util.DrawUIUtils.drawSelectionMenu;
import static by.nortin.util.DrawUIUtils.drawSelectionOne;
import static by.nortin.util.DrawUIUtils.drawWelcomeMenu;
import static by.nortin.util.InjectObjectsFactory.getInstance;
import static by.nortin.util.InputUtils.readBigDecimalFromConsole;
import static by.nortin.util.InputUtils.readIntFromConsole;
import static by.nortin.util.InputUtils.readStringFromConsole;
import static by.nortin.util.InputUtils.waitQKeyPressed;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.TransactionDto;
import by.nortin.dto.UserDto;
import by.nortin.model.Bank;
import by.nortin.model.BankAccount;
import by.nortin.model.CleverBankEnvironment;
import by.nortin.model.OperationType;
import by.nortin.model.User;
import by.nortin.service.BankAccountService;
import by.nortin.service.EnvironmentUIService;
import by.nortin.service.ReceiptSavingService;
import by.nortin.service.TransactionService;
import by.nortin.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class EnvironmentUIServiceImpl implements EnvironmentUIService {

    private final CleverBankEnvironment bankEnvironment;
    private final UserService userService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final ReceiptSavingService receiptSavingService;

    //test
    private final List<BankAccount> bankAccounts;

    {
        User userTest1 = new User();
        userTest1.setId(1L);
        BankAccount bankAccountTest1 = new BankAccount();
        bankAccountTest1.setId(1L);
        bankAccountTest1.setNumber(12345678912345678L);
        userTest1.setLogin("TEST");
        bankAccountTest1.setUser(userTest1);
        bankAccountTest1.setBalance(new BigDecimal(300));
        bankAccountTest1.setCurrency(Currency.getInstance("USD"));
        bankAccountTest1.setAccountOpeningDate(LocalDate.of(2023, 8, 23));
        Bank bankTest1 = new Bank();
        bankTest1.setId(1L);
        bankTest1.setName("BankTest");
        bankAccountTest1.setBank(bankTest1);
        BankAccount bankAccountTest2 = new BankAccount();
        bankAccountTest2.setId(2L);
        bankAccountTest2.setNumber(98765432109876543L);
        bankAccountTest2.setUser(userTest1);
        bankAccountTest2.setBalance(new BigDecimal(1300));
        bankAccountTest2.setCurrency(Currency.getInstance("BYN"));
        bankAccountTest2.setAccountOpeningDate(LocalDate.of(2023, 8, 22));
        bankAccountTest2.setBank(bankTest1);
        bankAccounts = List.of(bankAccountTest1, bankAccountTest2);
    }

    public EnvironmentUIServiceImpl(CleverBankEnvironment bankEnvironment) {
        this.bankEnvironment = bankEnvironment;
        this.userService = (UserService) getInstance(UserService.class);
        this.bankAccountService = (BankAccountService) getInstance(BankAccountService.class);
        this.transactionService = (TransactionService) getInstance(TransactionService.class);
        this.receiptSavingService = (ReceiptSavingService) getInstance(ReceiptSavingService.class);
    }

    @Override
    public void openBankEnvironment() {
        System.out.println();
        getWelcomePage();
    }

    private void getWelcomePage() {
        drawWelcomeMenu();
        if (bankEnvironment.getActiveUser() == null) {
            drawAuthenticateRequest();
            getAuthenticatePage();

//            System.out.println();

        } else {
            getStartPageOfSelection();
        }
    }

    private void getAuthenticatePage() {
        System.out.println();
        String login = readStringFromConsole("Enter your login");
        String password = readStringFromConsole("Enter your password");
//        UserDto userDto = createUserDto(login, password);
        UserDto userDto = new UserDto(login, password);
//        if (userService.checkAuthentication(login, password)) {
        if (userService.checkAuthentication(userDto)) {
            bankEnvironment.setActiveUser(login);
//            getStartPageOfSelection();
        } else {
            System.out.println();
            drawErrorAuthenticateRequest();
            getAuthenticatePage();
        }
    }

    private void getStartPageOfSelection() {
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawSelectionMenu();
        int bound = 5;
        int menuPoint = readIntFromConsole(TEXT_READ_INT_FROM_CONSOLE + bound, bound);
        switch (menuPoint) {
            case 1 -> {
                getReplenishmentBalanceSelectionPage();
//                drawReplenishmentBalanceSelection();
//                System.out.println("Replenishment of the bank account balance");
//                getSelectedOneSome();
//                waitEnterKeyPressed();
            }
//                sortingPage();
            case 2 -> {
                System.out.println("its 2!!!");
                waitQKeyPressed();
            }
//                addProductPage();
            case 3 -> {
                System.out.println("its 3!!!");
                waitQKeyPressed();
            }
//                editProductPage();
            case 4 -> {
                System.out.println("its 4!!!");
                waitQKeyPressed();
            }
//                deleteProductPage();
            case 5 -> {
                System.out.println("its 5!!!");
                waitQKeyPressed();
            }
//                service.closeShop();
        }
    }

    private void getReplenishmentBalanceSelectionPage() {
        System.out.println();
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawReplenishmentBalanceSelection();
        int menuPoint = getMenuPoint(2);
        switch (menuPoint) {
            case 1 -> {
                drawBankAccountSelection();
                List<BankAccountDto> bankAccountDtos = bankAccountService.getUserBankAccounts(bankEnvironment.getActiveUser());
                if (bankAccountDtos.isEmpty()) {
                    drawEmptyForSelectionLine();
                    waitQKeyPressed();
                } else {
                    displayBankAccountsSelection(bankAccountDtos);
                    int numberPoint = getMenuPoint(bankAccountDtos.size());
                    BankAccountDto bankAccountDto = bankAccountDtos.get(numberPoint - 1);
                    Long bankAccountNumber = bankAccountDto.getNumber();
                    BigDecimal depositedMoney = readBigDecimalFromConsole("Enter the amount to be deposited to the account " + bankAccountNumber);
                    BigDecimal updatedBalance = bankAccountService.topUpBankAccount(bankAccountNumber, depositedMoney);

                    //db transaction
//                    OperationType replenishment = OperationType.REPLENISHMENT;
//                    TransactionDto transactionDto = transactionService.saveTransaction(
//                            replenishment,
//                            bankAccountDto.getBank().getName(),
//                            null,
//                            bankAccountDto.getNumber(),
//                            null,
//                            depositedMoney);

//                    TransactionDto transactionDto = new TransactionDto(
//                            LocalDate.now(),
//                            bankAccountDto.getCurrencyId(),
//                            bankAccountDto.getCurrency(),
//                            depositedMoney,
//                            replenishment,
//                            bankAccountDto.getBankDto(),
//                            bankAccountDto,
//                            null,
//                            null);

                    TransactionDto transactionDto = TransactionDto.builder()
                            .currencyId(bankAccountDto.getCurrencyId())
                            .currency(bankAccountDto.getCurrency())
                            .monies(depositedMoney)
                            .operationType(OperationType.REPLENISHMENT)
                            .sendingBankDto(bankAccountDto.getBankDto())
                            .sendingBankAccountDto(bankAccountDto)
                            .build();

                    TransactionDto savedTransaction = transactionService.saveTransaction(transactionDto);

                    receiptSavingService.saveReceipt(savedTransaction);

                    showResult(bankAccountNumber, updatedBalance);
                    waitQKeyPressed();
                }

            }
//                sortingPage();
            case 2 -> System.out.println("go home!!!");
        }
    }

    private static int getMenuPoint(int bound) {
        int menuPoint = readIntFromConsole(TEXT_READ_INT_FROM_CONSOLE + bound, bound);
        return menuPoint;
    }

    private void showResult(Long bankAccountNumber, BigDecimal updatedBalance) {
        System.out.println();
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        if (updatedBalance.compareTo(BigDecimal.ZERO) > 0) {
            //in ui
            System.out.println("***");
            System.out.println("The account balance " + bankAccountNumber + " is now: " + updatedBalance);
            System.out.println("***");
        } else {
            System.out.println("Error");
        }

    }

    private BigDecimal topUpBankAccount(Long bankAccountNumber, BigDecimal depositedMoney) {
        Optional<BankAccount> account = bankAccounts.stream()
                .filter(bankAccount -> bankAccount.getNumber().equals(bankAccountNumber))
                .findFirst();
        BigDecimal balance = BigDecimal.ZERO;
        if (account.isPresent()) {
            balance = account.get().getBalance();
            account.get().setBalance(balance.add(depositedMoney));
            balance = account.get().getBalance();
        }
        return balance;
    }

    //    private void displayBankAccountsSelection(List<BankAccount> bankAccounts) {
    private void displayBankAccountsSelection(List<BankAccountDto> bankAccountDtos) {
        int menuPoint;
        BankAccountDto bankAccountDto;
        for (int i = 0; i < bankAccountDtos.size(); i++) {
            menuPoint = i + 1;
            bankAccountDto = bankAccountDtos.get(i);
            drawSelectionLine(menuPoint, bankAccountDto.getNumber(), bankAccountDto.getCurrency());
        }
    }

    private void getSelectedOneSome() {
        System.out.println();
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawSelectionOne();
        int bound = 2;
        int menuPoint = readIntFromConsole(TEXT_READ_INT_FROM_CONSOLE + bound, bound);
        switch (menuPoint) {
            case 1 -> {
                System.out.println("its 1!!!");
                waitQKeyPressed();
            }
//                sortingPage();
            case 2 -> System.out.println("go home!!!");
        }
    }
}
