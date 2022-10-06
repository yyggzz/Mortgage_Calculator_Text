package com.yige;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {

    final static byte PERCENT = 100;
    final static byte MONTHS_IN_YEAR = 12;

    public static void main(String[] args) {

        int principal = (int) readNumber("What is your principal in dollars? ($1K - $1M): ", 1000, 1_000_000);
        double annualRate = readNumber("What is your annual interest rate? (in %): ", 0, 30);
        int years = (int) readNumber("How long is your mortgage? (in years): ", 0, 100);

        printMonthlyDue(principal, annualRate, years);
        printRemainBalance(principal, annualRate, years);
    }

    private static void printMonthlyDue(int principal, double annualRate, int years) {
        System.out.println("----------------------------------");
        System.out.println("Monthly Mortgage Due");
        System.out.println("----------------------------------");
        double monthlyDue = calculateMonthlyMortgage(principal, annualRate, years);
        String monthlyDueFormatted = NumberFormat.getCurrencyInstance().format(monthlyDue);
        System.out.println("Your monthly Mortgage is: " + monthlyDueFormatted);
    }

    private static void printRemainBalance(int principal, double annualRate, int years) {
        System.out.println("----------------------------------");
        System.out.println("Schedule of Remaining Balance");
        System.out.println("----------------------------------");
        for (int monthPaid = 0; monthPaid <= years * MONTHS_IN_YEAR; monthPaid++) {
            double remainBalance = calculateRemainBalance(principal, annualRate, years, monthPaid);
            System.out.println("After " + monthPaid + " months: " + NumberFormat.getCurrencyInstance().format(remainBalance));
        }
    }

    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;
        System.out.print(prompt);
        while (true) {
            value = scanner.nextDouble();
            if (value >= min && value <= max)
                break;
            else
                System.out.print("Please Enter a value between " + min + " and " + max + ": ");
        }
        return value;
    }

    public static double calculateMonthlyMortgage(
            int principal,
            double annualRate,
            int years) {

        int numberOfPayments = years * MONTHS_IN_YEAR;
        double monthlyRate = annualRate / PERCENT / MONTHS_IN_YEAR;

        double monthlyDue = principal *
                monthlyRate * Math.pow((1 + monthlyRate), numberOfPayments)
                / (Math.pow((1 + monthlyRate), numberOfPayments) - 1);
        return monthlyDue;
    }

    public static double calculateRemainBalance(
            int principal,
            double annualRate,
            int years,
            int numberOfPaymentsMade) {

        int monthOfPayments = years * MONTHS_IN_YEAR;
        double monthlyRate = annualRate / PERCENT / MONTHS_IN_YEAR;

        double remainBalance = principal
                * (Math.pow(1 + monthlyRate, monthOfPayments) - Math.pow(1 + monthlyRate, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyRate, monthOfPayments) - 1);
        return remainBalance;
        }
}
