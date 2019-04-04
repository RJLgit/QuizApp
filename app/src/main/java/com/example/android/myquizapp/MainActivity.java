package com.example.android.myquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.ListItemClickListener {
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mCategoryAdapter = new CategoryAdapter(MainActivity.this, QuizQuestionClass.getCategories(), QuizQuestionClass.getUserHighScores(), QuizQuestionClass.getGlobalHighScores(), this);
        mRecyclerView.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onListItemCLick(String cat) {
        Toast.makeText(this, "Category clicked " + cat, Toast.LENGTH_SHORT).show();
    }
}
