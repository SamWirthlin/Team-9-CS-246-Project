package com.example.budgetappdemo.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetappdemo.Category;
import com.example.budgetappdemo.MakeList;
import com.example.budgetappdemo.R;
import com.example.budgetappdemo.Transaction;
import com.example.budgetappdemo.UserData;
import com.example.budgetappdemo.displayTransactions;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static com.example.budgetappdemo.UserData.map;

public class GalleryFragment extends Fragment {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean confirmed = false;
    private GalleryViewModel galleryViewModel;
    private String category;
    private String description;
    private double number;
    private boolean isIncome = false;
    private EditText editText = null;
    private EditText editText2 = null;
    private EditText editText3 = null;
    private EditText editText5 = null;
    public static int month;
    //use a set so that a category doesn't get added twice
    private Set<String> set = new HashSet<>();
    private Date now;
    private static View root;
    public static List <String> list = new LinkedList<>();


    //gets the month corresponding to the month number passed
    public static String getMonth(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }
        return null;
    }

    //for debug purposes
    public static String printBorder()
    {
        return "\n***************************\n***************************\n";
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
                Button button = (Button) root.findViewById(R.id.button);
        final String toastConfirmed = "Transaction successfully added";
        final String toastFail = "Transaction was not added";
        final ListView listView = (ListView) root.findViewById(R.id.listView);
        //add the categories to the set
        for (int month : map.keySet())
        {
            for (Category cat : map.get(month)){
                set.add(cat.getName());
            }
        }

        //ADD A WAY TO SEE HOW MICH MONEY YOU CURRENTLY HAVE IN YOUR ACCOUNT!
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText3 = (EditText) root.findViewById(R.id.editText3);
                editText = (EditText) root.findViewById(R.id.editText);
                editText2 = (EditText) root.findViewById(R.id.editText2);
                category = editText3.getText().toString();

                now = new Date();


                String num = editText2.getText().toString();

                if (num.isEmpty() || editText == null || editText3 == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(Html.fromHtml("<font color='#FF0000'><b> ERROR: PLEASE FILL OUT ALL " +
                            "FIELDS</font>"));
                    AlertDialog alert = builder.create();

                    alert.show();
                } else {
                    number = Double.parseDouble(editText2.getText().toString());


                RadioGroup radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup);
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = root.findViewById(radioId);
                if (radioButton.getId() == R.id.rincome)
                    isIncome = true;
                else
                    isIncome = false;

                    description = editText.getText().toString();
                    NumberFormat currency = NumberFormat.getCurrencyInstance();

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(Html.fromHtml("<b>" + "Please Confirm Transaction"
                            + "<br>" + "</b>" + "Category: " + category +
                            "<br>" + "<br>" + "\nAmount:  " + currency.format(number) +
                            "<br>" + "Description:  " + description + "<br>" + "Time:  " + now))
                            .setPositiveButton(Html.fromHtml("<font color='#0066FF'>Confirm</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UserData.setMostRecent(category, number, description, isIncome);
                                    Toast toast = new Toast(root.getContext());
                                    toast.setGravity(Gravity.CENTER, 0, 80);
                                    TextView tv = new TextView(root.getContext());
                                    tv.setBackgroundColor(Color.GRAY);
                                    tv.setTextColor(Color.WHITE);
                                    tv.setText(toastConfirmed);
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(tv);
                                    toast.show();
                                    //get the month number
                                    month = now.getMonth();

                                    //check first if the map already contains the category the user entered
                                    if (map.containsKey(month)) { //check first for the month
                                        if (!map.containsValue(category)) //if the month doesn't have the category, add it
                                            map.get(month).add(new Category(category, description, number, isIncome, now));
                                        else {
                                            for (Category cat : map.get(month))
                                            { //if the category is already in the map, add it to
                                                if (cat.getName().equals(category))//the transaction list
                                                {
                                                    cat.tList.add(new Transaction(number, true, description, now));
                                                }
                                            }
                                        }
                                    }
                                    else { //if not yet in map, add that son uva gun
                                        map.put(month, MakeList.makeList(new Category(category, description, number, isIncome, now)));
                                    }

                            //add it to the set. No need to check if already there since it's a set
                                    set.add(category);
                                    if (isIncome)
                                    {
                                        UserData.amount += number;
                                    }
                                    else
                                        UserData.amountSpent += number;

                                    //clear all the text fields
                                    editText.getText().clear();
                                    editText2.getText().clear();
                                    editText3.getText().clear();
                                    list.clear();
                                    for (String str : set)
                                        list.add(str);
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(),android.R.layout.simple_list_item_1, list);
                                    listView.setAdapter(arrayAdapter);
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast toast = new Toast(root.getContext());
                            toast.setGravity(Gravity.CENTER, 0, 80);
                            TextView tv = new TextView(root.getContext());
                            tv.setBackgroundColor(Color.GRAY);
                            tv.setTextColor(Color.WHITE);
                            tv.setText(toastFail);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(tv);
                            toast.show();
                        }
                    });
                    AlertDialog alert = builder.create();

                    alert.show();
                }

            }

        });

        list.clear();
        //for the list view
        for (String str : set)
            list.add(str);
        ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(),android.R.layout.simple_list_item_1, list);
        listView.setBackgroundColor(Color.argb(200,86,86,103));
        listView.setAdapter(arrayAdapter);




        /**************************text above the input boxes*************/
        final TextView textView1 = root.findViewById(R.id.text_gallery_2);
        textView1.setText("----Your current categories----");
        final TextView textView2 = root.findViewById(R.id.textView2);
        textView2.setTextColor(Color.GRAY);
        textView2.setText("  Category");
        final TextView textView4 = root.findViewById(R.id.textView4);
        textView4.setTextColor(Color.GRAY);
        textView4.setText("  Amount in $");
        final TextView textView5 = root.findViewById(R.id.textView5);
        textView5.setTextColor(Color.GRAY);
        textView5.setText("  Description");

        return root;

        }
}