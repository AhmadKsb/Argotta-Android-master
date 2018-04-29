package com.example.ahmad.chat_3.generalUtils;

import com.example.ahmad.chat_3.models.requests.Account;

import java.util.List;

public class GeneralUtils {

    public enum validResult {emptyPassword, unvalidUsername, valid}

    public enum validSignUp {emptyUserName, unValidPassword, valid, alreadyFound}


    public static validResult validate(String username, String password) {
        boolean valid = false;

        if (isEmpty(username, password)) {
            return validResult.emptyPassword;
        } else if (!validUsername(username)) {
            return validResult.unvalidUsername;
        } else {
            return validResult.valid;
        }


    }

    public static boolean isEmpty(String username, String password) {

        boolean isEmpty = false;
        if ((username.equals("")) || (password.equals(""))) {
            isEmpty = true;
        } else {
            isEmpty = false;
        }
        return isEmpty;
    }


    public static boolean validUsername(String username) {

        boolean valid;
        if (username.equals("")) {
            valid = false;
        } else if (username.length() < 4) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public static boolean validPassword(String password) {
        return password.length() > 5;
    }
}
