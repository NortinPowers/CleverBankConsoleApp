package by.nortin.service.impl;

import static by.nortin.util.BuildUtils.buildTransactionDtoWithoutRecipientBankInfo;
import static by.nortin.util.Constants.TEXT_READ_INT_FROM_CONSOLE;
import static by.nortin.util.DrawUIUtils.drawAuthenticateRequest;
import static by.nortin.util.DrawUIUtils.drawBankAccountSelection;
import static by.nortin.util.DrawUIUtils.drawEmptyForSelectionLine;
import static by.nortin.util.DrawUIUtils.drawErrorAuthenticateRequest;
import static by.nortin.util.DrawUIUtils.drawExitLine;
import static by.nortin.util.DrawUIUtils.drawGreetingUser;
import static by.nortin.util.DrawUIUtils.drawReplenishmentBalanceSelection;
import static by.nortin.util.DrawUIUtils.drawReturnLine;
import static by.nortin.util.DrawUIUtils.drawSelectionLine;
import static by.nortin.util.DrawUIUtils.drawSelectionMenu;
import static by.nortin.util.DrawUIUtils.drawSelectionOne;
import static by.nortin.util.DrawUIUtils.drawTransferBankSelection;
import static by.nortin.util.DrawUIUtils.drawWelcomeMenu;
import static by.nortin.util.DrawUIUtils.getMenuPoint;
import static by.nortin.util.InjectObjectsFactory.getInstance;
import static by.nortin.util.InputUtils.readBigDecimalFromConsole;
import static by.nortin.util.InputUtils.readIntFromConsole;
import static by.nortin.util.InputUtils.readStringFromConsole;
import static by.nortin.util.InputUtils.waitQKeyPressed;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.BankDto;
import by.nortin.dto.TransactionDto;
import by.nortin.dto.UserDto;
import by.nortin.model.CleverBankEnvironment;
import by.nortin.service.BankAccountService;
import by.nortin.service.BankService;
import by.nortin.service.EnvironmentUIService;
import by.nortin.service.ReceiptSavingService;
import by.nortin.service.TransactionService;
import by.nortin.service.UserService;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class EnvironmentUIServiceImpl implements EnvironmentUIService {

    private final CleverBankEnvironment bankEnvironment;
    private final UserService userService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final ReceiptSavingService receiptSavingService;
    private final BankService bankService;

    public EnvironmentUIServiceImpl(CleverBankEnvironment bankEnvironment) {
        this.bankEnvironment = bankEnvironment;
        this.userService = (UserService) getInstance(UserService.class);
        this.bankAccountService = (BankAccountService) getInstance(BankAccountService.class);
        this.transactionService = (TransactionService) getInstance(TransactionService.class);
        this.receiptSavingService = (ReceiptSavingService) getInstance(ReceiptSavingService.class);
        this.bankService = (BankService) getInstance(BankService.class);
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
            case 1 -> getReplenishmentBalanceSelectionPage();
            case 2 -> {
//                getDebitingBalanceSelectionPage();
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
        int menuPoint = getMenuPoint(3);
        switch (menuPoint) {
            case 1 -> {
                drawBankAccountSelection();
                List<BankAccountDto> bankAccountDtos = bankAccountService.getUserBankAccounts(bankEnvironment.getActiveUser()).stream()
                        .sorted(Comparator.comparing(BankAccountDto::getNumber))
                        .toList();
//                handleList(bankAccountDtos, "doReplenishmentBalance");
                if (bankAccountDtos.isEmpty()) {
                    drawEmptyForSelectionLine();
                    waitQKeyPressed();
                } else {
                    replenishmentBalance(bankAccountDtos);
                }
            }
            case 2 -> {
                drawTransferBankSelection();
                List<BankDto> bankDtos = bankService.getAll();
//                handleList(bankDtos, "doTransferBalance");
                if (bankDtos.isEmpty()) {
                    drawEmptyForSelectionLine();
                    waitQKeyPressed();
                } else {
                    transferBalance(bankDtos);
                }
            }
            default -> getPreviousPage(0);
        }
    }

//    private void handleList(List<?> list, String method) {
//        if (list.isEmpty()) {
//            drawEmptyForSelectionLine();
//            waitQKeyPressed();
//        } else {
//            switch (method) {
//                case "doReplenishmentBalance" -> replenishmentBalance((List<BankAccountDto>) list);
//                case "doTransferBalance" -> transferBalance((List<BankDto>) list);
//            }
//        }
//    }

    private void replenishmentBalance(List<BankAccountDto> bankAccountDtos) {
        displayBankAccountsSelection(bankAccountDtos);
        int numberPoint = getMenuPoint(bankAccountDtos.size() + 2);
        if (numberPoint > bankAccountDtos.size()) {
            int level = numberPoint - bankAccountDtos.size();
            selectBackOrExit(level);
        } else {
            performReplenishFundsToBankAccount(bankAccountDtos, numberPoint);
        }
    }

    private void transferBalance(List<BankDto> bankDtos) {
        //TODO
    }

    private void performReplenishFundsToBankAccount(List<BankAccountDto> bankAccountDtos, int numberPoint) {
        BankAccountDto bankAccountDto = bankAccountDtos.get(numberPoint - 1);
        Long bankAccountNumber = bankAccountDto.getNumber();
        BigDecimal depositedMoney = readBigDecimalFromConsole("Enter the amount to be deposited to the account " + bankAccountNumber);
        BigDecimal updatedBalance = bankAccountService.topUpBankAccount(bankAccountNumber, depositedMoney);
        TransactionDto transactionDto = buildTransactionDtoWithoutRecipientBankInfo(bankAccountDto, depositedMoney);
        TransactionDto savedTransaction = transactionService.saveTransaction(transactionDto);
        receiptSavingService.saveReceipt(savedTransaction);
        showResult(bankAccountNumber, updatedBalance);
        waitQKeyPressed();
    }

    private void selectBackOrExit(int level) {
        if (level == 1) {
            getPreviousPage(1);
        } else {
            getPreviousPage(0);
        }
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

//    private BigDecimal topUpBankAccount(Long bankAccountNumber, BigDecimal depositedMoney) {
//        Optional<BankAccount> account = bankAccounts.stream()
//                .filter(bankAccount -> bankAccount.getNumber().equals(bankAccountNumber))
//                .findFirst();
//        BigDecimal balance = BigDecimal.ZERO;
//        if (account.isPresent()) {
//            balance = account.get().getBalance();
//            account.get().setBalance(balance.add(depositedMoney));
//            balance = account.get().getBalance();
//        }
//        return balance;
//    }

    //    private void displayBankAccountsSelection(List<BankAccount> bankAccounts) {
    private void displayBankAccountsSelection(List<BankAccountDto> bankAccountDtos) {
        int menuPoint;
        BankAccountDto bankAccountDto;
        for (int i = 0; i < bankAccountDtos.size(); i++) {
            menuPoint = i + 1;
            bankAccountDto = bankAccountDtos.get(i);
            drawSelectionLine(menuPoint, bankAccountDto.getNumber(), bankAccountDto.getCurrency());
        }
        drawReturnLine(bankAccountDtos.size());
        drawExitLine(bankAccountDtos.size());
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

    private void getPreviousPage(int level) {
        switch (level) {
            case 0 -> getStartPageOfSelection();
            case 1 -> getReplenishmentBalanceSelectionPage();
            case 11 -> System.out.println();
        }

    }
}
