package by.nortin.service.impl;

import static by.nortin.util.BuildUtils.buildSimpleTransactionDto;
import static by.nortin.util.BuildUtils.buildTransactionDto;
import static by.nortin.util.Constants.CLEVER_BANK;
import static by.nortin.util.Constants.EMPTY_BANK_ACCOUNT;
import static by.nortin.util.Constants.INCORRECT_CURRENCY;
import static by.nortin.util.Constants.INCORRECT_DETAILS;
import static by.nortin.util.Constants.INCORRECT_INPUT;
import static by.nortin.util.Constants.LOW_MONEY;
import static by.nortin.util.Constants.SELECT_TRANSFER_BANK;
import static by.nortin.util.Constants.SUCCESSFUL_TRANSFER;
import static by.nortin.util.Constants.TEXT_READ_INT_FROM_CONSOLE;
import static by.nortin.util.DrawUIUtils.drawAuthenticateRequest;
import static by.nortin.util.DrawUIUtils.drawBankAccountSelection;
import static by.nortin.util.DrawUIUtils.drawCurrentBalanceOfBankAccount;
import static by.nortin.util.DrawUIUtils.drawExitLine;
import static by.nortin.util.DrawUIUtils.drawGreetingUser;
import static by.nortin.util.DrawUIUtils.drawMessage;
import static by.nortin.util.DrawUIUtils.drawReplenishmentBalanceSelection;
import static by.nortin.util.DrawUIUtils.drawReturnLine;
import static by.nortin.util.DrawUIUtils.drawSelectionLine;
import static by.nortin.util.DrawUIUtils.drawSelectionMenu;
import static by.nortin.util.DrawUIUtils.drawTransferBankSelection;
import static by.nortin.util.DrawUIUtils.drawTransferTypeSelection;
import static by.nortin.util.DrawUIUtils.drawWelcomeMenu;
import static by.nortin.util.DrawUIUtils.drawWithdrawalBalanceSelection;
import static by.nortin.util.InjectObjectsFactory.getInstance;
import static by.nortin.util.InputUtils.getMenuPoint;
import static by.nortin.util.InputUtils.readBigDecimalFromConsole;
import static by.nortin.util.InputUtils.readIntFromConsole;
import static by.nortin.util.InputUtils.readLongFromConsole;
import static by.nortin.util.InputUtils.readStringFromConsole;
import static by.nortin.util.InputUtils.waitQKeyPressed;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.BankDto;
import by.nortin.dto.TransactionDto;
import by.nortin.dto.UserDto;
import by.nortin.model.CleverBankEnvironment;
import by.nortin.model.OperationType;
import by.nortin.service.CleverBankEnvironmentService;
import by.nortin.service.EnvironmentUIService;
import by.nortin.service.OperationManagerService;
import by.nortin.service.UserService;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class EnvironmentUIServiceImpl implements EnvironmentUIService {

    private final CleverBankEnvironment bankEnvironment;
    private final UserService userService;
    private final OperationManagerService operationManagerService;
    private final CleverBankEnvironmentService cleverBankEnvironmentService;

    public EnvironmentUIServiceImpl(CleverBankEnvironment bankEnvironment) {
        this.bankEnvironment = bankEnvironment;
        this.userService = (UserService) getInstance(UserService.class);
        this.operationManagerService = (OperationManagerService) getInstance(OperationManagerService.class);
        this.cleverBankEnvironmentService = new CleverBankEnvironmentServiceImpl(bankEnvironment);
    }

    /**
     * Implementation of the method returns the start page.
     */
    @Override
    public void openBankEnvironment() {
        getWelcomePage();
    }

    /**
     * The method returns an identification request or 1 page of the application.
     */
    private void getWelcomePage() {
        if (bankEnvironment.getActiveUser() == null) {
            drawWelcomeMenu();
            drawAuthenticateRequest();
            getAuthenticatePage();
        } else {
            getStartPageOfSelection();
        }
    }

    /**
     * The method is intended for user authentication.
     */
    private void getAuthenticatePage() {
        System.out.println();
        String login = readStringFromConsole("Enter your login");
        String password = readStringFromConsole("Enter your password");
        UserDto userDto = new UserDto(login, password);
        if (userService.checkAuthentication(userDto)) {
            bankEnvironment.setActiveUser(login);
        } else {
            System.out.println();
            drawMessage("*The user was not found. Check the input data and try again*");
            getAuthenticatePage();
        }
    }

    /**
     * The method displays the operation selection menu in the application.
     */
    private void getStartPageOfSelection() {
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawSelectionMenu();
        int bound = 5;
        int menuPoint = readIntFromConsole(TEXT_READ_INT_FROM_CONSOLE + bound, bound);
        switch (menuPoint) {
            case 1 -> getReplenishmentBalanceSelectionPage();
            case 2 -> getWithdrawalBalanceSelectionPage();
            case 3 -> getTransferBalanceSelectionPage();
            case 4 -> {
                System.out.println("Sorry. Not ready yet");
                waitQKeyPressed();
            }
            case 5 -> cleverBankEnvironmentService.closeApp();
            default -> getStartPageOfSelection();
        }
    }

    /**
     * The method for displaying the menu withdrawal of funds from a bank account.
     */
    private void getWithdrawalBalanceSelectionPage() {
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawWithdrawalBalanceSelection();
        int menuPoint = getMenuPoint(2);
        switch (menuPoint) {
            case 1 -> {
                drawBankAccountSelection();
                List<BankAccountDto> bankAccountDtos = operationManagerService.getUserBankAccounts(bankEnvironment.getActiveUser()).stream()
                        .sorted(Comparator.comparing(BankAccountDto::getNumber))
                        .toList();
                BankAccountDto bankAccount = getSelectedBankAccountNumber(bankAccountDtos, 20);
                withdrawFundsFromBankAccount(bankAccount);
            }
            default -> getStartPageOfSelection();
        }
    }

    /**
     * The method for withdrawing funds from a bank account.
     *
     * @param bankAccountDto BankAccountDto
     */
    private void withdrawFundsFromBankAccount(BankAccountDto bankAccountDto) {
        Long bankAccountNumber = bankAccountDto.getNumber();
        BigDecimal money = readBigDecimalFromConsole("Enter the amount you want to withdraw\nfrom the account " + bankAccountNumber);
        if (isGreaterThanZero(money)) {
            if (operationManagerService.checkAvailabilityOfFunds(bankAccountNumber, money)) {
                BigDecimal updatedBalance = operationManagerService.changeBankAccountBalance(bankAccountNumber, money, false);
                TransactionDto transactionDto = buildSimpleTransactionDto(bankAccountDto, OperationType.DEBITING, money);
                TransactionDto savedTransaction = operationManagerService.saveTransaction(transactionDto);
                operationManagerService.saveReceiptTxt(savedTransaction);
                showResult(bankAccountNumber, updatedBalance);
                waitQKeyPressed();
            } else {
                displaysRefundMenuByIncorrectInput(20, "There are not enough funds\non the selected account to withdraw");
            }
        } else {
            displaysRefundMenuByIncorrectInput(20, INCORRECT_INPUT);
        }
    }

    /**
     * The method for comparing the entered amount of funds with 0.
     *
     * @param money BigDecimal
     * @return boolean result
     */
    private boolean isGreaterThanZero(BigDecimal money) {
        return money.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * The method for displaying the return and exit menu.
     *
     * @param level   int
     * @param message String
     */
    private void displaysRefundMenuByIncorrectInput(int level, String message) {
        drawMessage(message);
        getReturnExitSelection(level);
    }

    /**
     * The method for replenishing the bank account balance.
     */
    private void getReplenishmentBalanceSelectionPage() {
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawReplenishmentBalanceSelection();
        int menuPoint = getMenuPoint(2);
        switch (menuPoint) {
            case 1 -> {
                drawBankAccountSelection();
                List<BankAccountDto> bankAccountDtos = operationManagerService.getUserBankAccounts(bankEnvironment.getActiveUser()).stream()
                        .sorted(Comparator.comparing(BankAccountDto::getNumber))
                        .toList();
                BankAccountDto bankAccount = getSelectedBankAccountNumber(bankAccountDtos, 10);
                performReplenishFundsToBankAccount(bankAccount);
            }
            default -> getStartPageOfSelection();
        }
    }

    /**
     * The method for implementing a choice between return and exit items.
     *
     * @param returnLevel int
     */
    private void getReturnExitSelection(int returnLevel) {
        drawReturnLine(0);
        drawExitLine(0);
        int menuPoint = getMenuPoint(2);
        int level = 0;
        if (menuPoint == 1) {
            level = returnLevel;
        }
        getPageByLevel(level);
    }

    /**
     * The method for implementing a return to a certain menu level.
     *
     * @param level int
     */
    private void getPageByLevel(int level) {
        switch (level) {
            case 10 -> getReplenishmentBalanceSelectionPage();
            case 20 -> getWithdrawalBalanceSelectionPage();
            case 30 -> getTransferBalanceSelectionPage();
            case 300 -> transferBetweenYourBankAccounts();
            case 301 -> transferToCleverBankClient();
            case 302 -> transferToClientOfAnotherBank();
            default -> getStartPageOfSelection();
        }
    }

    /**
     * The method for displaying the bank transfer type menu.
     */
    private void getTransferBalanceSelectionPage() {
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawTransferTypeSelection();
        int menuPoint = getMenuPoint(4);
        switch (menuPoint) {
            case 1 -> transferBetweenYourBankAccounts();
            case 2 -> transferToCleverBankClient();
            case 3 -> transferToClientOfAnotherBank();
            default -> getStartPageOfSelection();
        }
    }

    /**
     * The method for making a transfer to a Clever-Bank client.
     */
    private void transferToClientOfAnotherBank() {
        drawTransferBankSelection();
        List<BankDto> bankDtos = operationManagerService.getAll().stream()
                .sorted(Comparator.comparing(BankDto::getName))
                .toList();
        BankDto bankDtoOfAnotherUser = getSelectedBank(bankDtos, 30);
        Long accountNumberOfAnotherUser = readLongFromConsole("Enter the  account number \nof the user of " + bankDtoOfAnotherUser.getName());
        BankAccountDto targetBankAccount = operationManagerService.getUserBankAccountByNumberForSpecificBank(accountNumberOfAnotherUser, bankDtoOfAnotherUser.getName());
        if (checkTargetBankAccount(targetBankAccount)) {
            displaysRefundMenuByIncorrectInput(30, INCORRECT_DETAILS);
        } else {
            drawMessage(SELECT_TRANSFER_BANK);
            List<BankAccountDto> bankAccountDtos = operationManagerService.getUserBankAccounts(bankEnvironment.getActiveUser()).stream()
                    .sorted(Comparator.comparing(BankAccountDto::getNumber))
                    .toList();
            BankAccountDto sourceBankAccount = getSelectedBankAccountNumber(bankAccountDtos, 302);
            BigDecimal transferredMoney = readBigDecimalFromConsole("Enter the amount that will be transferred\nto the "
                    + bankDtoOfAnotherUser.getName() + "'s bank account\n"
                    + targetBankAccount.getNumber() + "\n with account " + sourceBankAccount.getNumber());
            tryingMakeTransfer(transferredMoney, targetBankAccount, sourceBankAccount);
        }
    }

    /**
     * The method checks the balance for the availability of funds.
     *
     * @param sourceBankAccount BankAccountDto
     * @param transferredMoney  BigDecimal
     * @return boolean result
     */
    private static boolean checkBalance(BankAccountDto sourceBankAccount, BigDecimal transferredMoney) {
        return sourceBankAccount.getBalance().subtract(transferredMoney).compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * The method performs the transfer of funds from one bank account to another that.
     *
     * @param sourceBankAccount BankAccountDto
     * @param targetBankAccount BankAccountDto
     * @param transferredMoney  BigDecimal
     */
    private void doTransfer(BankAccountDto sourceBankAccount, BankAccountDto targetBankAccount, BigDecimal transferredMoney) {
        operationManagerService.transferMoneyBetweenAccounts(sourceBankAccount, targetBankAccount, transferredMoney);
        TransactionDto transactionDto = buildTransactionDto(sourceBankAccount, targetBankAccount, OperationType.TRANSFER, transferredMoney);
        TransactionDto savedTransaction = operationManagerService.saveTransaction(transactionDto);
        operationManagerService.saveReceiptTxt(savedTransaction);
        System.out.println(SUCCESSFUL_TRANSFER);
        waitQKeyPressed();
    }

    /**
     * The method for making a transfer to a Clever-Bank client.
     */
    private void transferToCleverBankClient() {
        Long accountNumberOfAnotherUser = readLongFromConsole("Enter the account number \nof the Clever-Bank user");
        BankAccountDto targetBankAccount = operationManagerService.getUserBankAccountByNumberForSpecificBank(accountNumberOfAnotherUser, CLEVER_BANK);
        if (checkTargetBankAccount(targetBankAccount)) {
            displaysRefundMenuByIncorrectInput(30, INCORRECT_DETAILS);
        } else {
            drawMessage(SELECT_TRANSFER_BANK);
            List<BankAccountDto> bankAccountDtos = operationManagerService.getUserBankAccounts(bankEnvironment.getActiveUser()).stream()
                    .sorted(Comparator.comparing(BankAccountDto::getNumber))
                    .toList();
            BankAccountDto sourceBankAccount = getSelectedBankAccountNumber(bankAccountDtos, 301);
            BigDecimal transferredMoney = readBigDecimalFromConsole("Enter the amount that will be transferred \nto the account "
                    + targetBankAccount.getNumber() + " \nwith account " + sourceBankAccount.getNumber());
            tryingMakeTransfer(transferredMoney, targetBankAccount, sourceBankAccount);
        }
    }

    /**
     * The method validates the bank account.
     *
     * @param targetBankAccount BankAccountDto
     * @return boolean result
     */
    private static boolean checkTargetBankAccount(BankAccountDto targetBankAccount) {
        return targetBankAccount.getId() == null || targetBankAccount.getNumber() == null;
    }

    /**
     * The method of wrapping the transfer operation of funds.
     *
     * @param transferredMoney  transferredMoney
     * @param targetBankAccount BankAccountDto
     * @param sourceBankAccount BankAccountDto
     */
    private void tryingMakeTransfer(BigDecimal transferredMoney, BankAccountDto targetBankAccount, BankAccountDto sourceBankAccount) {
        if (isGreaterThanZero(transferredMoney)) {
            if (targetBankAccount.getCurrency().equals(sourceBankAccount.getCurrency())) {
                if (checkBalance(sourceBankAccount, transferredMoney)) {
                    doTransfer(sourceBankAccount, targetBankAccount, transferredMoney);
                } else {
                    displaysRefundMenuByIncorrectInput(30, LOW_MONEY);
                }
            } else {
                displaysRefundMenuByIncorrectInput(30, INCORRECT_CURRENCY);
            }
        } else {
            displaysRefundMenuByIncorrectInput(30, INCORRECT_INPUT);
        }
    }

    /**
     * The method for transferring funds between your bank accounts.
     */
    private void transferBetweenYourBankAccounts() {
        drawMessage("Select the bank account to which you want to transfer funds");
        List<BankAccountDto> bankAccountDtos = operationManagerService.getUserBankAccounts(bankEnvironment.getActiveUser()).stream()
                .sorted(Comparator.comparing(BankAccountDto::getNumber))
                .toList();
        BankAccountDto targetBankAccount = getSelectedBankAccountNumber(bankAccountDtos, 30);
        drawMessage(SELECT_TRANSFER_BANK);
        BankAccountDto sourceBankAccount = getSelectedBankAccountNumber(bankAccountDtos, 300);
        BigDecimal transferredMoney = readBigDecimalFromConsole("Enter the amount that will be transferred\n to the account "
                + targetBankAccount.getNumber() + "\n with account " + sourceBankAccount.getNumber());
        tryingMakeTransfer(transferredMoney, targetBankAccount, sourceBankAccount);
    }

    /**
     * The method for selecting a bank account`s number.
     *
     * @param bankAccountDtos List of BankAccountDto
     * @param returnLevel     int
     * @return BankAccountDto
     */
    private BankAccountDto getSelectedBankAccountNumber(List<BankAccountDto> bankAccountDtos, int returnLevel) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        if (bankAccountDtos.isEmpty()) {
            drawMessage(EMPTY_BANK_ACCOUNT);
            getReturnExitSelection(returnLevel);
        } else {
            displayBankAccountsSelection(bankAccountDtos);
            int numberPoint = getMenuPoint(bankAccountDtos.size() + 2);
            if (numberPoint > bankAccountDtos.size()) {
                int menuPoint = numberPoint - bankAccountDtos.size();
                selectBackOrExit(menuPoint, returnLevel);
            } else {
                bankAccountDto = bankAccountDtos.get(numberPoint - 1);
            }
        }
        return bankAccountDto;
    }

    /**
     * The method for selecting a bank.
     *
     * @param bankDtos    List of BankDto
     * @param returnLevel int
     * @return BankDto
     */
    private BankDto getSelectedBank(List<BankDto> bankDtos, int returnLevel) {
        BankDto bankDto = new BankDto();
        if (bankDtos.isEmpty()) {
            drawMessage(EMPTY_BANK_ACCOUNT);
            getReturnExitSelection(returnLevel);
        } else {
            displayBankSelection(bankDtos);
            int numberPoint = getMenuPoint(bankDtos.size() + 2);
            if (numberPoint > bankDtos.size()) {
                int menuPoint = numberPoint - bankDtos.size();
                selectBackOrExit(menuPoint, returnLevel);
            } else {
                bankDto = bankDtos.get(numberPoint - 1);
            }
        }
        return bankDto;
    }

    /**
     * The method for adding funds to a bank account.
     *
     * @param bankAccountDto BankAccountDto
     */
    private void performReplenishFundsToBankAccount(BankAccountDto bankAccountDto) {
        Long bankAccountNumber = bankAccountDto.getNumber();
        BigDecimal depositedMoney = readBigDecimalFromConsole("Enter the amount to be deposited \nto the account " + bankAccountNumber);
        if (isGreaterThanZero(depositedMoney)) {
            BigDecimal updatedBalance = operationManagerService.changeBankAccountBalance(bankAccountNumber, depositedMoney, true);
            TransactionDto transactionDto = buildSimpleTransactionDto(bankAccountDto, OperationType.REPLENISHMENT, depositedMoney);
            TransactionDto savedTransaction = operationManagerService.saveTransaction(transactionDto);
            operationManagerService.saveReceiptTxt(savedTransaction);
            showResult(bankAccountNumber, updatedBalance);
            waitQKeyPressed();
        } else {
            displaysRefundMenuByIncorrectInput(10, INCORRECT_INPUT);
        }
    }

    /**
     * The method of selecting the transition level by application or output.
     *
     * @param level       int
     * @param returnLevel int
     */
    private void selectBackOrExit(int level, int returnLevel) {
        if (level == 1) {
            getPageByLevel(returnLevel);
        } else {
            getStartPageOfSelection();
        }
    }

    /**
     * The method for displaying the results of operations.
     *
     * @param bankAccountNumber Long
     * @param updatedBalance    BigDecimal
     */
    private void showResult(Long bankAccountNumber, BigDecimal updatedBalance) {
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawCurrentBalanceOfBankAccount(bankAccountNumber, updatedBalance);
    }

    /**
     * The method of displaying bank accounts.
     *
     * @param bankAccountDtos List of BankAccountDto
     */
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

    /**
     * The method of displaying banks.
     *
     * @param bankDtos List of BankDto
     */
    private void displayBankSelection(List<BankDto> bankDtos) {
        int menuPoint;
        BankDto bankDto;
        for (int i = 0; i < bankDtos.size(); i++) {
            menuPoint = i + 1;
            bankDto = bankDtos.get(i);
            drawSelectionLine(menuPoint, bankDto.getName());
        }
        drawReturnLine(bankDtos.size());
        drawExitLine(bankDtos.size());
    }
}
