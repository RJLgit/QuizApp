package com.example.android.myquizapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {

    private Context mContext;
    private ArrayList<String> categories;
    private ArrayList<String> topUserScores;
    private ArrayList<String> topGlobalScores;

    public CategoryAdapter(Context c, ArrayList<String> cat, ArrayList<String> topUser, ArrayList<String> topGlobal) {
        this.mContext = c;
        this.categories = cat;
        this.topUserScores = topUser;
        this.topGlobalScores = topGlobal;

    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutForListItem = R.layout.recycler_view_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForListItem, viewGroup, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder catViewHolder, int i) {
        catViewHolder.bind(categories.get(i), topUserScores.get(i), topGlobalScores.get(i));
    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        private TextView mCategoryTextView;
        private TextView mUserHighScoreTextView;
        private TextView mGlobalHighScoreTextView;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryTextView = itemView.findViewById(R.id.CategoryNameTextView);
            mUserHighScoreTextView = itemView.findViewById(R.id.userHighScoreValueTextView);
            mGlobalHighScoreTextView = itemView.findViewById(R.id.globalHighScoreValueTextView);
        }

        void bind(String cat, String userHigh, String globalHigh) {
            mCategoryTextView.setText(cat);
            mUserHighScoreTextView.setText(userHigh);
            mGlobalHighScoreTextView.setText(globalHigh);
        }


    }
}
