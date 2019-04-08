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
import com.google.firebase.auth.FirebaseAuth;

public class ScoresActivity extends AppCompatActivity implements ScoresAdapter.ListItemClickListener {

    private boolean mSplitScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        if (findViewById(R.id.globalScoresFragmentContainer) != null) {
            mSplitScreen = true;
        } else {
            mSplitScreen = false;
        }

        TopScoresFragment topScoresFragment = new TopScoresFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frag_container, topScoresFragment).commit();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_scores);
        myToolbar.setTitle("Here are your top scores");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        Intent i = getIntent();
        if (i.hasExtra("Username") && (i.getStringExtra("Username") != null)) {
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
    public void onListItemCLick(String cat) {
        if (mSplitScreen) {
            GlobalScoreFragment gbf = new GlobalScoreFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.globalScoresFragmentContainer, gbf)
                    .commit();
        } else {
            Intent intent = new Intent(this, GlobalScoreActivity.class);
            intent.putExtra("Category", cat);
            intent.putExtra("Username", getIntent().getStringExtra("Username"));
            startActivity(intent);
        }
        //Toast.makeText(getActivity(), "Category clicked " + cat, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                Intent i = new Intent(ScoresActivity.this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.top_scores_menu:
                Intent intent = new Intent(this, ScoresActivity.class);
                intent.putExtra("Username", getIntent().getStringExtra("Username"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
