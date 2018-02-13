package com.sanyusha.goals;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.sanyusha.goals.models.Goal;
import com.sanyusha.goals.network.GoalsBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalListActivity extends Activity {

    ArrayList<Goal> goals = new ArrayList<>();
    GoalsAdapter mAdapter;
    ListView lstTask;
    private SharedPreferences sPref;

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

        lstTask = (ListView) findViewById(R.id.lstTask);

        loadTaskList();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void loadTaskList() {

        sPref = getSharedPreferences("vk", MODE_PRIVATE);
        String userId = sPref.getString("user_id", "");
        String accessToken = sPref.getString("access_token", "");

        final Activity activity = this;
        Call<ArrayList<Goal>> call = GoalsBuilder.getApi().getTargets(userId, accessToken);
        call.enqueue(new Callback<ArrayList<Goal>>() {
            @Override
            public void onResponse(Call<ArrayList<Goal>> call, Response<ArrayList<Goal>> response) {
                if (response.isSuccessful()) {
                    Log.d("test", "success");
                    goals = response.body();
                    mAdapter = new GoalsAdapter(activity, goals);
                    lstTask.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Goal>> call, Throwable throwable) {
                Log.d("test", "failure");
                Log.d("test", call.request().url().toString());
            }
        });

    }

}
