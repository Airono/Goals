package com.sanyusha.goals.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sanyusha.goals.R;
import com.sanyusha.goals.adapters.GoalsAdapter;
import com.sanyusha.goals.models.Goal;

import java.util.ArrayList;

public class ArchiveActivity extends Activity {

    ArrayList<Goal> archive = new ArrayList<>();
    GoalsAdapter mAdapter;
    ListView archiveLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        archiveLv = findViewById(R.id.archive_lv);

        final Activity activity = this;
        mAdapter = new GoalsAdapter(activity, archive);
        archiveLv.setAdapter(mAdapter);
        registerForContextMenu(archiveLv);
    }
}
