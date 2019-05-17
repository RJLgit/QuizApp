package com.example.android.myquizapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;

public class SettingsActivity extends AppCompatActivity {

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        if (intent.hasExtra("Username")) {
            mUsername = intent.getStringExtra("Username");
        } else {
            mUsername = "User";
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_settings_toolbar);
        myToolbar.setTitle("The Ultimate Quiz App");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setSubtitle("Logged in as " + mUsername);
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
                return true;
            case R.id.top_scores_menu:
                Intent intent = new Intent(SettingsActivity.this, ScoresActivity.class);
                intent.putExtra("Username", mUsername);
                startActivity(intent);
                overridePendingTransition(R.transition.slide_in_bot, R.transition.slide_out_top);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
