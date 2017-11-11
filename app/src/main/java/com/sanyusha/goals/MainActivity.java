package com.sanyusha.goals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;
    private SharedPreferences sPref;
    Button saveButton, loadButton;

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                saveText("12", "mewmew");
                break;
            case R.id.load_button:
                mTextMessage.setText(loadText("12"));
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        loadButton = (Button) findViewById(R.id.load_button);
        loadButton.setOnClickListener(this);
    }

    void saveText(String key, String savedText) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(key, savedText);
        ed.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    String loadText(String key) {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(key, "");
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
        return savedText;
    }

}
