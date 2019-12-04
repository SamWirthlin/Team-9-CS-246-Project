package com.example.budgetappdemo;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.Date;

public class Transaction {
    private double amount;
    private boolean isIncome;
    private String description;
    private Date today;


    public Transaction(double amount, boolean isIncome, String description, Date today) {
        this.amount = amount;
        this.isIncome = isIncome;
        this.description = description;
        this.today = today;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount;   }

    public boolean isIncome() {
        return isIncome;
    }
    @Override
    public String toString() {
        return "\nDescription: " + description + "\nAmount: " + amount
                + (isIncome ? "\nIncome                                         "
                            : "\nExpense                                       ")
                + (today.getMonth() + 1) + "/" + (today.getDay() + 1);
    }
}
