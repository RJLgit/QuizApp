package com.example.android.myquizapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {

    private Context mContext;
    private ArrayList<String> categories;
    private ArrayList<String> categoriesToDisplayInRecyclerView;
    final private ListItemClickListener mListItemClickListener;


    public CategoryAdapter(Context c, ArrayList<String> cat, ListItemClickListener listener) {
        this.mContext = c;
        this.categories = cat;
        mListItemClickListener = listener;
        categoriesToDisplayInRecyclerView = QuizQuestionClass.getQuizNames();
    }
    public interface ListItemClickListener {
        void onListItemCLick (String cat);
        void showRecyclerView();
    }


    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutForListItem = R.layout.main_recycler_view_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForListItem, viewGroup, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder, final int i) {

        catViewHolder.bind(categoriesToDisplayInRecyclerView.get(i));
    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mCategoryTextView;
        private TextView mDescriptionTextView;
        private ProgressBar mProgressBar;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryTextView = itemView.findViewById(R.id.quizNameTextView);
            mDescriptionTextView = itemView.findViewById(R.id.quizDescriptionTextView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
            itemView.setOnClickListener(this);
        }

        void bind(String cat) {
            mCategoryTextView.setText(cat);
            if (cat.equals("The Ultimate Quiz Experience")) {
                mDescriptionTextView.setText("20 questions from many quiz categories");
            } else {
                mDescriptionTextView.setText("5 random " + cat.toLowerCase() + " questions");
            }
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mCategoryTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mListItemClickListener.showRecyclerView();
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            String categoryClicked = categories.get(clickedPosition);
            mListItemClickListener.onListItemCLick(categoryClicked);
        }
    }
}
