package com.example.budgetappdemo.ui.tools;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetappdemo.R;
import com.example.budgetappdemo.UserData;
import com.example.budgetappdemo.UserGoal;

import java.text.NumberFormat;

public class ToolsFragment extends Fragment {
    EditText editText_add;
    EditText editText_goal;
    EditText editText_update_goal;
    EditText editText_take_goal;
    static TextView tvGoal;
    static TextView tvGoalStatus;
    static NumberFormat currency = NumberFormat.getCurrencyInstance();


    private ToolsViewModel toolsViewModel;
    public String printSpaces(String goal)
    {
        String spaces = "";
        for (int i = 0; i < 39 - goal.length(); i++)
            spaces += ".";
        return spaces;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        tvGoalStatus = (TextView) root.findViewById(R.id.tv_goalStatus);
        tvGoal = (TextView) root.findViewById(R.id.textView14);
        String tvGoalText = (UserGoal.getAmountTotal() == 0 ? "No goal yet" : UserGoal.getName())
                + "     " + (UserGoal.getAmountTotal() == 0 ? "           " : currency.format(UserGoal.getAmountTotal()))
                + (UserGoal.getAmountTotal() == 0 ? "" : "\nAmount contributed:       ")
                + currency.format(UserGoal.getAmountSoFar());
        tvGoal.setText(tvGoalText);
        double goalAmount;
        Button bAddGoal = (Button) root.findViewById(R.id.button2);
        bAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_goal = (EditText) root.findViewById(R.id.editText_goal);
                editText_add = (EditText) root.findViewById(R.id.editText_add);
                String goalName = editText_goal.getText().toString();
                double goalAmount = Double.parseDouble(editText_add.getText().toString());
                UserGoal.setName(goalName);
                UserGoal.setAmountTotal(goalAmount);
                Toast toast = new Toast(root.getContext());
                toast.setGravity(Gravity.CENTER, 0, 80);
                TextView tv = new TextView(root.getContext());
                tv.setBackgroundColor(Color.GRAY);
                tv.setTextColor(Color.WHITE);
                tv.setText("Goal successfully Added");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(tv);
                toast.show();
                UserGoal.setAmountSoFar(0);
                tvGoal = (TextView) root.findViewById(R.id.textView14);
                tvGoal.clearComposingText();
                tvGoalStatus.setText("");
                String tvGoalText = UserGoal.getName();
                tvGoal.setText(tvGoalText);
                TextView tv_Goal = (TextView) root.findViewById(R.id.tv_goal);
                tv_Goal.setText(currency.format(UserGoal.getAmountTotal()));
                editText_add.getText().clear();
                editText_goal.getText().clear();

            }
        });

        Button bEditGoalTake = (Button) root.findViewById(R.id.button4);
        Button bEditGoal = (Button) root.findViewById(R.id.button_take_goal);
        bEditGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_update_goal = (EditText) root.findViewById(R.id.edit_goal_take);
                double amount = Double.parseDouble(editText_update_goal.getText().toString());
                UserGoal.addMoney(amount);
                tvGoal = (TextView) root.findViewById(R.id.textView14);
                String tvGoalText = UserGoal.getName() +  "\n\nAmount contributed:       ";
                TextView tv_goal = (TextView) root.findViewById(R.id.tv_goal);
                tv_goal.setText(currency.format(UserGoal.getAmountTotal()));
                tvGoal.setText(tvGoalText);
                TextView tvGoalStatus = (TextView) root.findViewById(R.id.tv_goalStatus);
                tvGoalStatus.setText(currency.format(UserGoal.getAmountSoFar()));
                editText_update_goal.getText().clear();
            }
        });
        bEditGoalTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_take_goal = (EditText) root.findViewById(R.id.edit_goal_add);
                double amount = Double.parseDouble(editText_take_goal.getText().toString());
                UserGoal.takeMoney(amount);
                tvGoal = (TextView) root.findViewById(R.id.textView14);
                String tvGoalText = UserGoal.getName() +  "\n\nAmount contributed:     ";
                TextView tv_Goal = (TextView) root.findViewById(R.id.tv_goal);
                tv_Goal.setText(currency.format(UserGoal.getAmountTotal()));
                tvGoalStatus.setText(currency.format(UserGoal.getAmountSoFar()));
                tvGoal.setText(tvGoalText);
                editText_take_goal.getText().clear();
            }
        });
        TextView tvAddGoal = (TextView) root.findViewById(R.id.tv_addGoal);
        tvAddGoal.setTextColor(Color.GRAY);
        tvAddGoal.setText("Add a new Goal");
        TextView tvEdit = (TextView) root.findViewById(R.id.tv_edit);
        tvEdit.setTextColor(Color.GRAY);
        tvEdit.setText("Edit your Goal");
        TextView tv15 = (TextView) root.findViewById(R.id.textView15);
        tv15.setTextColor(Color.GRAY);
        tv15.setText("Your current Goal");

        return root;
    }
}