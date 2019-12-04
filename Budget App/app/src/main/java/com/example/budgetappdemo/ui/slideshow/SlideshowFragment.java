package com.example.budgetappdemo.ui.slideshow;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetappdemo.Category;
import com.example.budgetappdemo.R;
import com.example.budgetappdemo.Transaction;

import java.util.LinkedList;
import java.util.List;

import static com.example.budgetappdemo.UserData.map;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private static List <String> list = new LinkedList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        list.clear();
        for (int month : map.keySet())
        {
            for (Category cat : map.get(month))
            {
                for (Transaction t : cat.tList)
                {
                    list.add(cat.toString() + t.toString());
                }
            }
        }

        ListView listView = (ListView) root.findViewById(R.id.textView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        final TextView textView9 = root.findViewById(R.id.textView9);
        textView9.setTextColor(Color.BLACK);
        textView9.setText("A summary of all your transactions");

        return root;
    }
}