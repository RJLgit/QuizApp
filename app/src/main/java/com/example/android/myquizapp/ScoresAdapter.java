package com.example.android.myquizapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder> {

    private Context mContext;
    private ArrayList<String> categories;
    private ArrayList<String> topUserScores;
    private ArrayList<String> dates;
    final private ScoresAdapter.ListItemClickListener mListItemClickListener;

    public ScoresAdapter(Context c, ArrayList<String> cat, ArrayList<String> topUser, ArrayList<String> d, ScoresAdapter.ListItemClickListener listener) {
        this.mContext = c;
        this.categories = cat;
        this.topUserScores = topUser;
        this.dates = d;
        mListItemClickListener = listener;
    }
    public interface ListItemClickListener {
        void onListItemCLick (String cat);
    }

    @NonNull
    @Override
    public ScoresAdapter.ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutForListItem = R.layout.top_scores_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForListItem, viewGroup, false);
        return new ScoresAdapter.ScoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoresAdapter.ScoresViewHolder scoresViewHolder, int i) {
        scoresViewHolder.bind(categories.get(i), topUserScores.get(i), dates.get(i));
    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    public class ScoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mCategoryTextView;
        private TextView mUserHighScoreTextView;
        private TextView mDateTextView;

        public ScoresViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryTextView = itemView.findViewById(R.id.CategoryScoresNameTextView);
            mUserHighScoreTextView = itemView.findViewById(R.id.userScoresHighScoreValueTextView);
            mDateTextView = itemView.findViewById(R.id.dateHighScoreValueTextView);
            itemView.setOnClickListener(this);
        }

        void bind(String cat, String userHigh, String date) {
            mCategoryTextView.setText(cat);
            mUserHighScoreTextView.setText(userHigh);
            mDateTextView.setText(date);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            String categoryClicked = categories.get(clickedPosition);

            mListItemClickListener.onListItemCLick(categoryClicked);
        }
    }
}
