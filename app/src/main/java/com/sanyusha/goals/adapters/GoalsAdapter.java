package com.sanyusha.goals.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sanyusha.goals.R;
import com.sanyusha.goals.models.Goal;

import java.util.ArrayList;


/**
 * Created by Alexandra on 10.02.2018.
 */

public class GoalsAdapter extends BaseAdapter {
    public Context ctx;
    LayoutInflater lInflater;
    ArrayList<Goal> objects;

    public GoalsAdapter(Context context, ArrayList<Goal> goals) {
        ctx = context;
        objects = goals;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_view, parent, false);
        }

        Goal p = getGoal(position);

        ((TextView) view.findViewById(R.id.title)).setText(p.getTitle());
        ((TextView) view.findViewById(R.id.description)).setText(p.getDescription());
        view.findViewById(R.id.typeView).setBackgroundColor(ContextCompat.getColor(ctx, p.getType().getColor()));
        return view;
    }

    public ArrayList<Goal> getList(){
        return objects;
    }

    Goal getGoal(int position) {
        return ((Goal) getItem(position));
    }

}