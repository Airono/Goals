package com.sanyusha.goals.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sanyusha.goals.R;
import com.sanyusha.goals.models.Goal;
import com.sanyusha.goals.network.GoalsBuilder;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewGoalActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText titleText, descriptionText;
    Button saveButton, cancelButton;
    ArrayList<String> types = new ArrayList<>();
    int typePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);
        setTitle(R.string.newGoal);

        titleText = (EditText) findViewById(R.id.titleText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);

        Resources res = getResources();
        for (Goal.Types type: Goal.Types.values()) {
            if (type != Goal.Types.unknown) {
                Integer resID = res.getIdentifier(type.name(), "string", getPackageName());
                String typeName = getString(resID);
                types.add(typeName);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Type");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typePosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                saveGoal();
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


    void saveGoal() {
        SharedPreferences sPref = getSharedPreferences("vk", MODE_PRIVATE);
        String userId = sPref.getString("user_id", "");
        String accessToken = sPref.getString("access_token", "");
        final Activity activity = this;

        Call<ResponseBody> call = GoalsBuilder.getApi().postTargets(
                userId,
                accessToken,
                titleText.getText().toString(),
                descriptionText.getText().toString(),
                typePosition,
                System.currentTimeMillis() / 1000);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, R.string.saved, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
                Log.d("test", call.request().url().toString());
            }
        });
    }

}
