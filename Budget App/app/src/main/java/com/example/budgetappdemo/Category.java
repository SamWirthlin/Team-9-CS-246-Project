package com.example.budgetappdemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Category {
    private String name;
    //list so that each category can have multiple transactions
    public List<Transaction> tList = new ArrayList<>();

    public Category(String name, String description, double amount, boolean isIncome, Date today) {
        this.name = name;
        tList.add(new Transaction(amount,isIncome,description, today));
    }

    @Override
    public String toString() {
        return "Category: " +
                name;

    }
    public double getAmount()
    {
        double amount = 0;
        for (Transaction t : this.tList)
        {
            amount += t.getAmount();
        }
        return amount;
    }

    public String getName() {
        return name;
    }

}
