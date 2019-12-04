package com.example.budgetappdemo.ui.gallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetappdemo.R;

import java.text.NumberFormat;

import static java.security.AccessController.getContext;

public class EditTransactions extends AppCompatActivity {

    private String category;
    private double number;
    private EditText editText;
    private EditText editText2;
    final String toastConfirmed = "Category successfully added";
    final String toastFail = "Category was not added";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transactions);
        display();
    }
    public void display() {
        Button button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.editText);
                editText2 = (EditText) findViewById(R.id.editText2);
                category = editText.getText().toString();
                number = Double.parseDouble(editText2.getText().toString());
                NumberFormat currency = NumberFormat.getCurrencyInstance();

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(Html.fromHtml("<b>" + "Please Confirm Transaction"
                        + "<br>" + "</b>" + "Category: " + category +
                        "<br>" + "\nAmount: " +
                        currency.format(number))).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 80);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setBackgroundColor(Color.GRAY);
                        tv.setTextColor(Color.WHITE);
                        tv.setText(toastConfirmed);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(tv);
                        toast.show();
                        editText.getText().clear();
                        editText2.getText().clear();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 80);
                        TextView tv = new TextView(getApplicationContext());
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
        });


        final TextView textView1 = findViewById(R.id.text_gallery_2);
        textView1.setText("----View/Edit your categories----");
    }
}
