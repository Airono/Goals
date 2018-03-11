package com.sanyusha.goals.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sanyusha.goals.R;
import com.sanyusha.goals.adapters.GoalsAdapter;
import com.sanyusha.goals.models.Goal;
import com.sanyusha.goals.network.GoalsBuilder;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "test";

    ArrayList<Goal> goals = new ArrayList<>();
    GoalsAdapter mAdapter;
    SwipeMenuListView lstTask;
    ProgressBar progressBar;
    TextView textView;
    private SharedPreferences sPref;
    private static final int CM_DELETE_ID = 1;
    private SwipeRefreshLayout mSwipeLayout;

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem doneItem = new SwipeMenuItem(
                    getApplicationContext());
            doneItem.setBackground(R.color.yellowArchive);
            doneItem.setWidth(convertDpToPixel(56, getApplicationContext()));
//            doneItem.setTitle("Return");
            doneItem.setTitleSize(18);
            doneItem.setTitleColor(Color.WHITE);
            doneItem.setIcon(R.drawable.return_button);

            menu.addMenuItem(doneItem);
        }
    };

    public static int convertDpToPixel(int dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        mAdapter = new GoalsAdapter(this, goals);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        textView = (TextView) findViewById(R.id.error2);
        textView.setVisibility(TextView.INVISIBLE);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe2);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.blueSwipe, R.color.greenSwipe);

        lstTask = (SwipeMenuListView) findViewById(R.id.listArchive);
        lstTask.setMenuCreator(creator);
        lstTask.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        lstTask.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    //выполнение и перемещение в архив
                    sPref = getSharedPreferences("vk", MODE_PRIVATE);
                    String userId = sPref.getString("user_id", "");
                    String accessToken = sPref.getString("access_token", "");
                    Call<ResponseBody> call = GoalsBuilder.getApi().moveFromArchive(userId, goals.get(position).gettId(), accessToken);
                    final int id = position;
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            // уведомляем, что данные изменились
                            mAdapter.notifyDataSetChanged();
                            goals.remove(id);
                            Log.d("test", "moving from archive success");
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d("test", "moving from archive failure");
                        }
                    });
                }
                return false;
            }
        });

        loadTaskList();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_bar, menu);

        MenuItem item = menu.findItem(R.id.action_filter);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.getFilter().filter(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;
    }

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
                    intent = new Intent(getApplicationContext(), NewGoalActivity.class);
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
        Call<ArrayList<Goal>> call = GoalsBuilder.getApi().getArchiveTargets(userId, accessToken);
        call.enqueue(new Callback<ArrayList<Goal>>() {
            @Override
            public void onResponse(Call<ArrayList<Goal>> call, Response<ArrayList<Goal>> response) {
                if (response.isSuccessful()) {
                    Log.d("test", "success");
                    goals = response.body();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    lstTask.setVisibility(SwipeMenuListView.VISIBLE);
                    textView.setVisibility(TextView.INVISIBLE);
                    mAdapter = new GoalsAdapter(activity, goals);
                    lstTask.setAdapter(mAdapter);
                    registerForContextMenu(lstTask);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Goal>> call, Throwable throwable) {
                Log.d("test", "failure");
                Log.d("test", call.request().url().toString());
                lstTask.setVisibility(SwipeMenuListView.INVISIBLE);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                textView.setVisibility(TextView.VISIBLE);
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
                                  },
                                    //time of loading
                                    3000);
    }
}