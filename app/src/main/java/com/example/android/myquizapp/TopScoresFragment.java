package com.example.android.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TopScoresFragment extends Fragment implements ScoresAdapter.ListItemClickListener {

    public TopScoresFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_your_top_scores, container, false);
        RecyclerView mRecyclerView = fView.findViewById(R.id.your_top_scores_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        ScoresAdapter mScoresAdapter = new ScoresAdapter(getActivity().getApplicationContext(), QuizQuestionClass.getCategories(), QuizQuestionClass.getUserHighScores(), QuizQuestionClass.getDates(), this);
        mRecyclerView.setAdapter(mScoresAdapter);
        return fView;
    }

    @Override
    public void onListItemCLick(String cat) {
        Intent intent = new Intent(getActivity(), GlobalScoreActivity.class);
        startActivity(intent);
        //Toast.makeText(getActivity(), "Category clicked " + cat, Toast.LENGTH_SHORT).show();
    }
}
