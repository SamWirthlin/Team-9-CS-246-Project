package com.example.budgetappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import static com.example.budgetappdemo.UserData.map;

public class displayTransactions extends AppCompatActivity {
private static ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_transactions);
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        List<String> list = new LinkedList<>();
        for (int month : map.keySet())
        {
            for (Category cat : map.get(month))
            {
                if (cat.getName().equals(category))
                {
                    for (Transaction t : cat.tList)
                    {
                        list.add(t.toString());
                    }
                }
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this ,android.R.layout.simple_list_item_1, list);
        listView.setBackgroundColor(Color.argb(200,86,86,103));
        listView.setAdapter(arrayAdapter);
    }
}
