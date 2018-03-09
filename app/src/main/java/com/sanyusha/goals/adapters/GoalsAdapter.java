package com.sanyusha.goals.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sanyusha.goals.GoalsFilter;
import com.sanyusha.goals.R;
import com.sanyusha.goals.models.Goal;

import java.util.ArrayList;

import static java.lang.Math.ceil;


/**
 * Created by Alexandra on 10.02.2018.
 */

public class GoalsAdapter extends BaseAdapter implements Filterable {
    private Context ctx;
    private LayoutInflater lInflater;
    public ArrayList<Goal> objects;
    public ArrayList<Goal> objectsConst;
    private GoalsFilter filter;

    public GoalsAdapter(Context context, ArrayList<Goal> goals) {
        ctx = context;
        objects = goals;
        objectsConst = goals;
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


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_view, parent, false);
        }

        Goal p = getGoal(position);

        ((TextView) view.findViewById(R.id.title)).setText(p.getTitle());
        ((TextView) view.findViewById(R.id.description)).setText(p.getDescription());
//        ((TextView) view.findViewById(R.id.date)).setText(R.string.time);

        long dateAchievement = p.getDate();
        switch (p.getType()) {
            case week:
                dateAchievement = dateAchievement + 604800;
                if (dateAchievement - System.currentTimeMillis() / 1000 < 0) {
                    ((TextView) view.findViewById(R.id.date)).setText(R.string.time);
                    break;
                }
                else {
                    ((TextView) view.findViewById(R.id.date)).setText(Integer.toString(
                            secToDays(dateAchievement - System.currentTimeMillis() / 1000) + 1));
                    break;
                }
            case month:
                dateAchievement = dateAchievement + 2592000;
                if (dateAchievement - System.currentTimeMillis() / 1000 < 0) {
                    ((TextView) view.findViewById(R.id.date)).setText(R.string.time);
                    break;
                }
                else {
                    ((TextView) view.findViewById(R.id.date)).setText(Integer.toString(
                            secToDays(dateAchievement - System.currentTimeMillis() / 1000) + 1));
                    break;
                }
            case year:
                dateAchievement = dateAchievement + 31536000;
                if (dateAchievement - System.currentTimeMillis() / 1000 < 0) {
                    ((TextView) view.findViewById(R.id.date)).setText(R.string.time);
                    break;
                }
                else {
                    ((TextView) view.findViewById(R.id.date)).setText(Integer.toString(
                            secToDays(dateAchievement - System.currentTimeMillis() / 1000) + 1));
                    break;
                }
            case life:
                ((TextView) view.findViewById(R.id.date)).setText("life");
                break;
            default:
                ((TextView) view.findViewById(R.id.date)).setText("unknown");
        }

        view.findViewById(R.id.typeView).setBackgroundColor(ContextCompat.getColor(ctx, p.getType().getColor()));
        return view;
    }

    public ArrayList<Goal> getList(){
        return objects;
    }

    private Goal getGoal(int position) {
        return ((Goal) getItem(position));
    }

    private int secToDays(long secs) {
        int days;
        double diff = ceil(secs / 86400);
        days = (int) diff;
        return days;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new GoalsFilter(this);
        }
        return filter;
    }
}