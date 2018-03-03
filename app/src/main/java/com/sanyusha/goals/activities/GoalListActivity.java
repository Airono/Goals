package com.sanyusha.goals.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sanyusha.goals.adapters.GoalsAdapter;
import com.sanyusha.goals.R;
import com.sanyusha.goals.models.Goal;
import com.sanyusha.goals.network.GoalsBuilder;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalListActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    ArrayList<Goal> goals = new ArrayList<>();
    GoalsAdapter mAdapter;
    ListView lstTask;
    private SharedPreferences sPref;
    private static final int CM_DELETE_ID = 1;
    private SwipeRefreshLayout mSwipeLayout;

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

        lstTask = findViewById(R.id.lstTask);

        loadTaskList();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mSwipeLayout = findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                R.color.blueSwipe, R.color.greenSwipe,
                R.color.orangeSwipe, R.color.redSwipe);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        sPref = getSharedPreferences("vk", MODE_PRIVATE);
        String userId = sPref.getString("user_id", "");
        String accessToken = sPref.getString("access_token", "");
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем инфу о пункте списка
            final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // удаляем, используя позицию пункта в списке
            Call<ResponseBody> call = GoalsBuilder.getApi().deleteTarget(userId, goals.get(acmi.position).gettId(), accessToken);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // уведомляем, что данные изменились
                    mAdapter.notifyDataSetChanged();
                    goals.remove(acmi.position);
                    Log.d("test", "deleting success");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("test", "deleting failure");
                }
            });
            return true;
        }
        return super.onContextItemSelected(item);
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
                    registerForContextMenu(lstTask);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Goal>> call, Throwable throwable) {
                Log.d("test", "failure");
                Log.d("test", call.request().url().toString());
            }
        });

    }

    @Override
    public void onRefresh() {
        Log.d("test", "refresh");
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                loadTaskList();
                mSwipeLayout.setRefreshing(false);
            }
        }, 7000);
    }
}