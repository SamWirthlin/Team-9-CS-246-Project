package com.example.budgetappdemo;

public class UserGoal {
    private static String goalName;
    private static double amountTotal;
    private static double amountSoFar;
    private static double amountLeft = (amountTotal - amountSoFar);

    /****************SETTERS*********************************/
    public static void   setAmountTotal(double amount) { amountTotal =  amount;      }
    public static void   addMoney(double amount)       { amountSoFar += amount;      }
    public static void   takeMoney(double amountTaken) { amountSoFar -= amountTaken; }
    public static void   setAmountSoFar(double amount) { amountSoFar =  amount;      }
    public static void   setName(String goal)          { goalName    =  goal;        }

    /****************GETTERS********************************/
    public static double getAmountLeft()               { return amountLeft;          }
    public static double getAmountSoFar()              { return amountSoFar;         }
    public static String getName()                     { return goalName;            }
    public static double getAmountTotal()              { return amountTotal;         }
}
