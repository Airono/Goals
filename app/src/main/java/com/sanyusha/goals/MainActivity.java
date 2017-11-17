package com.sanyusha.goals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextMessage;

    String[] mews = { "Mew", "MewMew", "MewMewMew"};

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_item1:
                    mTextMessage.setText("We already in Goals");
                    return true;
                case R.id.action_item2:
                    Intent intent = new Intent(getApplicationContext(), Mew.class);
                    startActivity(intent);
                    return true;
                case R.id.action_item3:
                    intent = new Intent(getApplicationContext(), Kus.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView MewMain = (ListView) findViewById(R.id.MewMain);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_list_item, mews);
        MewMain.setAdapter(adapter);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
