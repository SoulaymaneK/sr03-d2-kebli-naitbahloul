package com.example.sr03_d2_kebli_naitbahloul.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    public static void validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password.length() < 15) {
            errors.add("Le mot de passe doit contenir au moins 8 caractères.");
        }

        int uppercaseCount = countMatches(password, "[A-Z]");
        if (uppercaseCount < 1) {
            errors.add("Le mot de passe doit contenir au moins 2 lettres majuscules.");
        }

        int digitCount = countMatches(password, "\\d");
        if (digitCount < 3) {
            errors.add("Le mot de passe doit contenir au moins un chiffre.");
        }

        int specialCharCount = countMatches(password, "[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]");
        if (specialCharCount < 2) {
            errors.add("Le mot de passe doit contenir au moins un caractère spécial.");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }

    private static int countMatches(String input, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
