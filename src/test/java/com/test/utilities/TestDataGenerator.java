package com.test.utilities;

import com.github.javafaker.Faker;
import java.util.Locale;

public class TestDataGenerator {
    private static final Faker faker = new Faker(new Locale("de"));

    public static String generatePostalCode() {
        return faker.number().digits(5);
    }

    public static String generatePurchasePrice() {
        // Generate a random price between 100000 and 1000000
        return String.valueOf(faker.number().numberBetween(100000, 1000000));
    }

    public static String generateLoanAmount(String purchasePrice) {
        int maxLoan = Integer.parseInt(purchasePrice);
        // Generate a loan amount between 50000 and maxLoan
        int minLoan = Math.max(50000, maxLoan / 2);
        return String.valueOf(faker.number().numberBetween(minLoan, maxLoan));
    }

    public static String generateRepaymentPercentage() {
        // Generate a random percentage between 1 and 10
        return String.valueOf(faker.number().numberBetween(1, 10));
    }

    public static String generateMonthlyPayment() {
        // Generate a random monthly payment between 1000 and 10000
        return String.valueOf(faker.number().numberBetween(1000, 4000));
    }

    public static String generateOptionNumber() {
        // Generate a random percentage between 1 and 10
        return String.valueOf(faker.number().numberBetween(1, 4));
    }
}
