package by.nortin.service.impl;

import static by.nortin.util.DrawUIUtils.drawAuthenticateRequest;
import static by.nortin.util.DrawUIUtils.drawErrorAuthenticateRequest;
import static by.nortin.util.DrawUIUtils.drawGreetingUser;
import static by.nortin.util.DrawUIUtils.drawSelection;
import static by.nortin.util.DrawUIUtils.drawSelectionOne;
import static by.nortin.util.DrawUIUtils.drawWelcomeMenu;
import static by.nortin.util.InputUtils.readIntFromConsole;
import static by.nortin.util.InputUtils.readStringFromConsole;
import static by.nortin.util.InputUtils.waitEnterKeyPressed;

import by.nortin.model.CleverBankEnvironment;
import by.nortin.service.EnvironmentUIService;
import by.nortin.service.UserService;
import by.nortin.util.InjectObjectsFactory;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class EnvironmentUIServiceImpl implements EnvironmentUIService {

    private final CleverBankEnvironment bankEnvironment;
    private final UserService userService;

    public EnvironmentUIServiceImpl(CleverBankEnvironment bankEnvironment) {
        this.bankEnvironment = bankEnvironment;
        this.userService = (UserService) InjectObjectsFactory.getInstance(UserService.class);
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
        if (userService.checkAuthentificate(login, password)) {
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
        drawSelection();
        int bound = 5;
        int menuPoint = readIntFromConsole("Select an operation by entering a number from 1 to " + bound, bound);
        switch (menuPoint) {
            case 1 -> {
                System.out.println("its 1!!!");
                getSelectedOneSome();
                waitEnterKeyPressed();
            }
//                sortingPage();
            case 2 -> {
                System.out.println("its 2!!!");
                waitEnterKeyPressed();
            }
//                addProductPage();
            case 3 -> {
                System.out.println("its 3!!!");
                waitEnterKeyPressed();
            }
//                editProductPage();
            case 4 -> {
                System.out.println("its 4!!!");
                waitEnterKeyPressed();
            }
//                deleteProductPage();
            case 5 -> {
                System.out.println("its 5!!!");
                waitEnterKeyPressed();
            }
//                service.closeShop();
        }
    }

    private void getSelectedOneSome() {
        System.out.println();
        drawWelcomeMenu();
        drawGreetingUser(bankEnvironment.getActiveUser());
        drawSelectionOne();
        int bound = 2;
        int menuPoint = readIntFromConsole("Select an operation by entering a number from 1 to " + bound, bound);
        switch (menuPoint) {
            case 1 -> {
                System.out.println("its 1!!!");
                waitEnterKeyPressed();
            }
//                sortingPage();
            case 2 -> System.out.println("go home!!!");
        }
    }
}
