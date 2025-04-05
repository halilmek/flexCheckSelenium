package com.test.utilities;

import com.github.javafaker.Faker;
import java.util.Locale;

/**
 * TestDataGenerator, Utility-Klasse für die Generierung von Testdaten
 */
public class TestDataGenerator {
    /**
     * Faker, Utility-Klasse für die Generierung von Testdaten
     */
    private static final Faker faker = new Faker(new Locale("de"));

    /**
     * Generiert eine zufällige Postleitzahl
     * @return Postleitzahl
     */
    public static String generatePostalCode() {
        return faker.number().digits(5);
    }

    /**
     * Generiert eine zufällige Kaufpreis
     * @return Kaufpreis
     */
    public static String generatePurchasePrice() {
        // Generate a random price between 100000 and 1000000
        return String.valueOf(faker.number().numberBetween(100000, 500000));
    }

    /**
     * Generiert eine zufällige Darlehensbetrag
     * @param purchasePrice Kaufpreis
     * @return Darlehensbetrag
     */
    public static String generateLoanAmount(String purchasePrice) {
        int maxLoan = Integer.parseInt(purchasePrice);
        // Generate a loan amount between 50000 and maxLoan
        int minLoan = Math.max(50000, maxLoan / 2);
        return String.valueOf(faker.number().numberBetween(minLoan, maxLoan));
    }

    /**
     * Generiert eine zufällige Tilgungsprozentsatz
     * @return Tilgungsprozentsatz
     */
    public static String generateRepaymentPercentage() {
        // Generate a random percentage between 1 and 10
        //return String.valueOf(faker.number().numberBetween(1, 10));
        return String.valueOf(faker.number().numberBetween(1, 7));
    }

    /**
     * Generiert eine zufällige Monatliche Rate
     * @return Monatliche Rate
     */
    public static String generateMonthlyPayment() {
        // Generate a random monthly payment between 1000 and 10000
        return String.valueOf(faker.number().numberBetween(1000, 4000));
    }

    /**
     * Generiert eine zufällige Option
     * @return Option
     */
    public static String generateOptionNumber() {
        // Generate a random percentage between 1 and 10
        return String.valueOf(faker.number().numberBetween(1, 4));
    }

    /**
     * Generiert eine zufällige Jahre für Gesamtlaufzeit
     * @return Jahre für Gesamtlaufzeit
     */
    public static String generateJahreFürGesamtlaufzeit() {
        // Generate a random percentage between 1 and 10
        return String.valueOf(faker.number().numberBetween(6, 30));
    }

    /**
     * Generiert eine zufällige Monate für Gesamtlaufzeit
     * @return Monate für Gesamtlaufzeit
     */ 
    public static String generateMonateFürGesamtlaufzeit() {
        // Generate a random percentage between 1 and 10
        return String.valueOf(faker.number().numberBetween(1, 12));
    }
}
