package com.noman.accounts.random;

import java.util.Random;

public class TenDigitNumberGenerator {
    private static final Random random = new Random();

    public static String getTenDigitNumber() {
        return String.format("%010d", random.nextInt(1_000_000_000));
    }

    public static void main(String[] args) {
        // Example of how to use the method
        String number = getTenDigitNumber();
        System.out.println("Generated 10-digit number: " + number);
    }
}
