package com.sanyusha.goals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity  {

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
                    intent = new Intent(getApplicationContext(), SettingActiivity.class);
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

        ListView listView = (ListView) findViewById(R.id.listView);
        final EditText editText = (EditText) findViewById(R.id.editText);

        final ArrayList<String> goals= new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_list_item, goals);
        listView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goals.add(0, editText.getText().toString());
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
