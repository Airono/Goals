package com.sanyusha.goals;

import android.content.res.Resources;
import android.widget.Filter;

import com.sanyusha.goals.adapters.GoalsAdapter;
import com.sanyusha.goals.models.Goal;

import java.util.ArrayList;

import static com.sanyusha.goals.models.Goal.Type.life;
import static com.sanyusha.goals.models.Goal.Type.month;
import static com.sanyusha.goals.models.Goal.Type.week;
import static com.sanyusha.goals.models.Goal.Type.year;

/**
 * Created by Alexandra on 03.03.2018.
 */

public class GoalsFilter extends Filter {

    private final GoalsAdapter adapter;

    public GoalsFilter(GoalsAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint == null || constraint.length() == 0) {
            //show all
            results.values = adapter.getList();
            results.count = adapter.getCount();
        } else {
            //filtered
            Resources res = Application.getAppContext().getResources();
            ArrayList<Goal> newList = new ArrayList<>();
            if (constraint.equals('1')) {
                for (Goal.Type type: Goal.Type.values()) {
                    if (type == week) {
                        //add week
                    }
                }
            } else if (constraint.equals('2')) {
                for (Goal.Type type: Goal.Type.values()) {
                    if (type == month) {
                        //add month
                    }
                }
            } else if (constraint.equals('3')) {
                for (Goal.Type type: Goal.Type.values()) {
                    if (type == year) {
                        //add year
                    }
                }
            } else if (constraint.equals('4')) {
                for (Goal.Type type: Goal.Type.values()) {
                    if (type == life) {
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
        //написать вот этот метод
        //adapter.setFilteredList();
        if (results.count == 0) {
            adapter.notifyDataSetInvalidated();
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
