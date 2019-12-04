package com.example.budgetappdemo;

import java.util.ArrayList;
import java.util.List;

public class MakeList {

    public static List makeList(Category category){
        //small class for the multiple-map component of the data, so that we
        // can just re-use this instead of re-writing code every time
        List <Category> list = new ArrayList<>();
        list.add(category);
        return list;
    }
}