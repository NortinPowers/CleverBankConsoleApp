package by.nortin.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String CLEVER_BANK = "Clever-Bank";
    public static final String TEXT_READ_INT_FROM_CONSOLE = "Select an operation by entering a number from 1 to ";
    public static final String INCORRECT_INPUT = "The entered amount is less than or equal to 0";
    public static final String INCORRECT_DETAILS = "Bank account details are incorrect";
    public static final String SELECT_TRANSFER_BANK = "Select the bank account\n from which you want to transfer funds";
    public static final String SUCCESSFUL_TRANSFER = "The transfer of funds has been successfully carried out";
    public static final String LOW_MONEY = "You don't have enough funds \nto perform this operation";
    public static final String INCORRECT_CURRENCY = "Transfer between accounts\n in different currencies is prohibited";
    public static final String EMPTY_BANK_ACCOUNT = "You don't have any open bank accounts";
    public static final String PRESS_ENTER = " and press Enter:";

    @UtilityClass
    public class QueryVariables {
        public static final String ID = "id";
        public static final String CODE = "code";
        public static final String NUMBER = "number";
        public static final String BALANCE = "balance";
        public static final String CURRENCY_ID = "currency_id";
        public static final String ACCOUNT_OPEN_DATE = "account_open_date";
        public static final String DATE_OF_LAST_SERVICE = "date_of_last_service";

    }

    @UtilityClass
    public class MethodName {
        public static final String SAVE = "save()";
        public static final String FIND_ALL = "findAll()";
        public static final String CLOSE_CONNECTION = "connectionPool.closeConnection()";
        public static final String SET_AUTO_COMMIT = "connection.setAutoCommit()";
        public static final String ROLLBACK = "connection.rollback()";
        public static final String GET_USER_BANK_ACCOUNT_BY_NUMBER_FOR_SPECIFIC_BANK = "getUserBankAccountByNumberForSpecificBank()";
        public static final String CHECK_AVAILABILITY_OF_FUNDS = "checkAvailabilityOfFunds()";
        public static final String TRANSFER_MONEY_BETWEEN_ACCOUNTS = "transferMoneyBetweenAccounts()";
        public static final String TOP_UP_BANK_ACCOUNT = "topUpBankAccount()";
        public static final String GET_USER_BANK_ACCOUNTS = "getUserBankAccounts()";
        public static final String GET_ALL = "getAll()";
        public static final String SAVE_TRANSACTION = "saveTransaction()";
        public static final String CHECK_AUTHENTICATION = "checkAuthentication()";
    }
}
