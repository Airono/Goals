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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class NewGoalActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText titleText, descriptionText;
    private SharedPreferences sPref;
    Button saveButton, cancelButton;
    final String SAVED_TEXT = "saved_text";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_item1:
                    Intent intent = new Intent(getApplicationContext(), GoalListActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_item2:
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
        setContentView(R.layout.activity_new_goal);

        titleText = (EditText) findViewById(R.id.titleText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                saveText();
                backToGoalList();
                break;
            case R.id.cancel_button:
                backToGoalList();
                break;
            default:
                break;
        }
    }


    void backToGoalList() {
        Intent intent = new Intent(getApplicationContext(), GoalListActivity.class);
        startActivity(intent);
    }


    void saveText() {
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putString(titleText.getText().toString(), descriptionText.getText().toString());
        ed.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

}
