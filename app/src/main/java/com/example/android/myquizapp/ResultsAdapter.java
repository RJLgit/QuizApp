package com.example.android.myquizapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {

    private Context mContext;
    private ArrayList<String> questions;
    private ArrayList<String> userAnswers;
    private ArrayList<String> correctAnswers;


    public ResultsAdapter(Context c, ArrayList<String> ques, ArrayList<String> userAns, ArrayList<String> corrAns) {
        this.mContext = c;
        this.questions = ques;
        this.userAnswers = userAns;
        this.correctAnswers = corrAns;
    }



    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutForListItem = R.layout.results_recycler_view_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForListItem, viewGroup, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultsViewHolder catViewHolder, final int i) {

        catViewHolder.bind(questions.get(i), userAnswers.get(i), correctAnswers.get(i));
    }

    @Override
    public int getItemCount() {
        if (questions == null) {
            return 0;
        }
        return questions.size();
    }

    public class ResultsViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuestionsTextView;
        private TextView mUserAnswersTextView;
        private ProgressBar mProgressBar;
        private TextView mCorrectAnswersTextView;
        private LinearLayout mBackLayout;
        private ConstraintLayout mBackLayoutCon;
        private Button mButton;

        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuestionsTextView = itemView.findViewById(R.id.questionAsked);
            mUserAnswersTextView = itemView.findViewById(R.id.userAnswerText);
            mCorrectAnswersTextView = itemView.findViewById(R.id.correctAnswerText);
            mProgressBar = itemView.findViewById(R.id.progressBarResults);
            mBackLayout = itemView.findViewById(R.id.backgroundForResultsItems);
            mBackLayoutCon = itemView.findViewById(R.id.backgroundForResultsItemsConst);
            mButton = itemView.findViewById(R.id.report_button);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Clicked Report", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bind(String quest, String userAns, String corrAns) {
            mQuestionsTextView.setText(quest);
            mUserAnswersTextView.setText(userAns);
            mCorrectAnswersTextView.setText(corrAns);
            if (userAns.equals(corrAns)) {

                mBackLayoutCon.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
            } else {
                mBackLayoutCon.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_light));
            }
            mButton.setVisibility(View.VISIBLE);
            mButton.setEnabled(true);
            mQuestionsTextView.setVisibility(View.VISIBLE);
            mUserAnswersTextView.setVisibility(View.VISIBLE);
            mCorrectAnswersTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);

        }


    }
}
