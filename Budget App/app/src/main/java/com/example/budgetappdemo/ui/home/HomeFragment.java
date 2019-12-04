package com.example.budgetappdemo.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetappdemo.Category;
import com.example.budgetappdemo.R;
import com.example.budgetappdemo.Transaction;
import com.example.budgetappdemo.UserData;
import com.example.budgetappdemo.UserGoal;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View view;
    public static final int[] CHART_COLORS= {
            Color.rgb(0, 37, 100), Color.rgb(0, 102, 100), Color.rgb(12, 199, 200),
            Color.rgb(106, 150, 100), Color.rgb(50, 5, 100), Color.rgb(23,23,200),
            Color.rgb(89,234,12), Color.rgb(24,102,34), Color.rgb(23,102,102)
    };

    //holds the amount of money in each category
    List<Float> amount = new LinkedList<>();

    //holds the actual names of the categories
    List<String> categories = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //tells whether the category is already in the list
        boolean addOn = false;

        //clear the lists so that entries aren't added in twice when ever called
        amount.clear();
        categories.clear();
        TextView tv10 = (TextView) root.findViewById(R.id.textView10);
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        double total = (UserData.amount - UserData.amountSpent);

        if (total > 0)
            tv10.setTextColor(Color.GREEN);
        else
            tv10.setTextColor(Color.RED);
        String displayBalance = (total > 0 ? "+" : "") + currency.format(total);

        tv10.setText(displayBalance);
        TextView tv6 = (TextView) root.findViewById(R.id.textView6);
        tv6.setTextColor(Color.WHITE);
        tv6.setText("You have spent " + currency.format((UserData.amountSpent)) + " so far this month. ");
        TextView tvR = (TextView) root.findViewById(R.id.textView12);
        tvR.setTextColor(Color.WHITE);
        tvR.setText("Most recent");
        TextView tvR2 = (TextView) root.findViewById(R.id.tv_recent);
        tvR2.setText(UserData.print());


        //iterate through and add teh data into the proper lists
        for (int month : UserData.map.keySet())
        {
            for (Category cats : UserData.map.get(month))
            {
                //if the category is already in the lists, set addOn to true, else add it
                if (categories.contains(cats.getName()) == false)
                    categories.add(cats.getName());
                else {
                    addOn = true;
                }

                for (Transaction trans : cats.tList)
                {
                    if (addOn)
                    {   //if the category is in the list, find the index of it, and add
                        //the amount spent to the corresponding index slot which contains the current amount
                        int i = new ArrayList<>(categories).indexOf(cats.getName()); //find index
                        float update = amount.get(i) + (float)trans.getAmount(); //update it!
                        amount.set(i, update);
                    }
                    else //not there so just add it in
                        amount.add((float)trans.getAmount());
                }
                addOn = false;
            }
        }

        //now convert the lists to arrays, since the Pie chart expects 2 arrays
        Float[] amountArray = amount.toArray(new Float[amount.size()]);
        String[] catArray = categories.toArray(new String[categories.size()]);

        //use the pie chart from github repository
        List<PieEntry> p = new ArrayList<>();
        //add in all the crap
        for (int i = 0; i < amount.size(); i++) {
            p.add(new PieEntry(amountArray[i], catArray[i]));
        }
        //various housekeeping stuff to make the pie chart look the way we want it to
         /**/   PieDataSet dataSet = new PieDataSet(p, "");          /**/
         /**/   dataSet.setColors(CHART_COLORS);                           /**/
         /**/                                                              /**/
         /**/   PieData data = new PieData(dataSet);                       /**/
         /**/   data.setDrawValues(false);                                 /**/
         /**/   PieChart chart = (PieChart) root.findViewById(R.id.chart); /**/
         /**/   chart.setDrawSliceText(false);                             /**/
         /**/   chart.setEntryLabelColor(Color.WHITE);                     /**/
         /**/   chart.setDrawHoleEnabled(false);                           /**/
         /**/   chart.setData(data);                                       /**/
         /**/   Legend legend = chart.getLegend();                         /**/
         /**/   legend.setTextColor(Color.WHITE);                          /**/
         /**/   chart.getDescription().setEnabled(false);                  /**/
         /**/   chart.invalidate();                                        /**/

        return root;
    }
}