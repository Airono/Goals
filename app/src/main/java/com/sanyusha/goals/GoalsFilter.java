package com.sanyusha.goals;

import android.content.res.Resources;
import android.util.Log;
import android.widget.Filter;

import com.sanyusha.goals.adapters.GoalsAdapter;
import com.sanyusha.goals.models.Goal;

import java.util.ArrayList;


/**
 * Created by Alexandra on 03.03.2018.
 */

public class GoalsFilter extends Filter {

    private final GoalsAdapter adapter;
    private ArrayList<Goal> goals = new ArrayList<>();
    private static final String TAG = "test";

    public GoalsFilter(GoalsAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        goals = adapter.objectsConst;
        if (constraint == null || constraint.length() == 0 || constraint.equals("0")) {
            //show all
            results.values = adapter.objectsConst;
            results.count = adapter.objectsConst.size();
        } else {
            //filtered
            ArrayList<Goal> newList = new ArrayList<>();

            if (constraint.equals("1")) {
                newList.clear();
                for (Goal goal: goals) {
                    if (goal.getType() == Goal.Types.week) {
                        newList.add(goal);
                        //add week
                    }
                }
            } else if (constraint.equals("2")) {
                newList.clear();
                for (Goal goal: goals) {
                    if (goal.getType() == Goal.Types.month) {
                        newList.add(goal);
                        //add month
                    }
                }
            } else if (constraint.equals("3")) {
                newList.clear();
                for (Goal goal: goals) {
                    if (goal.getType() == Goal.Types.year) {
                        newList.add(goal);
                        //add year
                    }
                }
            } else if (constraint.equals("4")) {
                newList.clear();
                for (Goal goal: goals) {
                    if (goal.getType() == Goal.Types.life) {
                        newList.add(goal);
                        //add life
                    }
                }
            }
            results.values = newList;
            results.count = newList.size();
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.objects = (ArrayList<Goal>) results.values;
        if (results.count == 0) {
            adapter.notifyDataSetInvalidated();
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
