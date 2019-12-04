package com.example.budgetappdemo;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {
    //holds the map of data so it is accessible from anywhere in the program
    //                 month     category
    public static Map<Integer, List<Category>> map = new HashMap<>();
    public static double amount = 0;
    public static double amountSpent = 0;
    public static String category;
    public static double cost;
    public static String description;
    public static boolean isIncome;

    public static void setMostRecent(String cat, double amount, String des, boolean income)
    {
        category = cat;
        cost = amount;
        isIncome = income;
        description = des;
    }


    public static String print() {
        return "Category: " + category + "\nDescription: " + description
                + "\nAmount: " + cost + (isIncome ? "\nIncome" : "\nExpense");
    }
}
