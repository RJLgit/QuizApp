package com.example.android.myquizapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;

public class GlobalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_score);

        GlobalScoreFragment globalScoreFragment = new GlobalScoreFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.globalScoresFragmentContainer, globalScoreFragment).commit();
        Intent i = getIntent();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_global_scores);
        if (i.hasExtra("Category")) {
            myToolbar.setTitle("Here is the top global score for " + i.getStringExtra("Category"));
        }
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        if (i.hasExtra("Username")) {
            ab.setSubtitle("Logged in as " + i.getStringExtra("Username").toString());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                Intent i = new Intent(GlobalScoreActivity.this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.settings_menu:
                Intent intentTwo = new Intent(GlobalScoreActivity.this, SettingsActivity.class);
                intentTwo.putExtra("Username", getIntent().getStringExtra("Username"));
                startActivity(intentTwo);
                return true;
            case R.id.top_scores_menu:
                Intent intent = new Intent(GlobalScoreActivity.this, ScoresActivity.class);
                intent.putExtra("Username", getIntent().getStringExtra("Username"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
