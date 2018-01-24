package com.sanyusha.goals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity  {

    ArrayList<String> goals;
    ListView listView;
    ArrayAdapter<String> adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_item1:
                    return true;
                case R.id.action_item2:
                    Intent intent = new Intent(getApplicationContext(), NewGoalActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_item3:
                    intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        listView = (ListView) findViewById(R.id.listView);
        goals = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, R.layout.my_list_item, goals);

        listView.setAdapter(adapter);
        Button button = (Button) findViewById(R.id.button);
        adapter.notifyDataSetChanged();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanList();
            }
        });

        updateList();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    void updateList() {
        // update list
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        for (String key : sharedPreferences.getAll().keySet()) {
            //String title = sharedPreferences.getString(key, "");
            goals.add(key);
            adapter.notifyDataSetChanged();
        }
    }

    void cleanList() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
        goals.clear();
        adapter.notifyDataSetChanged();
    }
}
