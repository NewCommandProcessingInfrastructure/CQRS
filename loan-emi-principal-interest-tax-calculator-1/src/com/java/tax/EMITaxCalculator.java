package com.java.tax;

public class EMITaxCalculator {

	public static void main(String[] args) {
		double principal = 1000;         				// Loan amount
    double annualInterestRate = 3;    // Annual interest rate (%)
    int tenureMonths = 12;             				// Loan duration in months
    double gstRate = 10;               				// GST rate on interest (%)

    double monthlyInterestRate = annualInterestRate / tenureMonths / 100;

    // EMI formula
    double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths))
               / (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

    System.out.printf("Fixed EMI (before GST): $%.2f%n", emi);
    System.out.println("-----------------------------------------------------------");
    System.out.printf("%-6s %-12s %-12s %-10s %-12s%n", "Month", "Principal", "Interest", "GST", "Total EMI");
    System.out.println("-----------------------------------------------------------");

    double balance = principal;

    for (int month = 1; month <= tenureMonths; month++) {
        double interest = balance * monthlyInterestRate;
        double principalPart = emi - interest;
        double gst = interest * gstRate / 100;
        double totalMonthlyOutflow = emi + gst;

        System.out.printf("%-6d $%-11.2f $%-11.2f $%-9.2f $%-11.2f%n",
                month, principalPart, interest, gst, totalMonthlyOutflow);

        balance -= principalPart;
    }
	}
}
