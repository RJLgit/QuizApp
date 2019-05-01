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
    /*private ArrayList<String> topUserScores;
    private ArrayList<String> topGlobalScores;*/
    final private ListItemClickListener mListItemClickListener;
    FirebaseFirestore db;
    private DocumentReference myRef;
    private CollectionReference globalRef;
    private FirebaseAuth mFirebaseAuth;
    private String uniqueUserId;

    public CategoryAdapter(Context c, ArrayList<String> cat, ListItemClickListener listener) {
        this.mContext = c;
        this.categories = cat;
        /*this.topUserScores = topUser;
        this.topGlobalScores = topGlobal;*/
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
        /*db = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();*/

        /*FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            uniqueUserId = user.getUid();
            myRef = db.collection("TopScores").document(uniqueUserId);
        }
        globalRef = db.collection("TopScores");*/
        View view = inflater.inflate(layoutForListItem, viewGroup, false);

        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder, final int i) {

       /* myRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            TopScores myScores = documentSnapshot.toObject(TopScores.class);
                            int scor = myScores.getScoreByCategory(categories.get(i));
                            final String myScore = scor + "";
                            DocumentReference globalDocRef = globalRef.document(categories.get(i).toLowerCase());
                            globalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        GlobalScores gblScores = documentSnapshot.toObject(GlobalScores.class);
                                        int gblScore = gblScores.getScore();
                                        String globalScore = gblScore + "";*/
                                        catViewHolder.bind(categoriesToDisplayInRecyclerView.get(i));
                                    /*}
                                }
                            });

                        } else {
                            TopScores newScores = new TopScores(0, 0, 0, 0, 0, 0, 0 , 0, 0 , 0, 0);
                            myRef.set(newScores);
                            DocumentReference globalDocRef = globalRef.document(categories.get(i).toLowerCase());
                            globalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        GlobalScores gblScores = documentSnapshot.toObject(GlobalScores.class);
                                        int gblScore = gblScores.getScore();
                                        String globalScore = gblScore + "";
                                        catViewHolder.bind(categories.get(i), "0", globalScore);
                                    }
                                }
                            });

                        }

                    }
                });*/

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
       /* private TextView mUserHighScoreTextView;
        private TextView mGlobalHighScoreTextView;*/
        private ProgressBar mProgressBar;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryTextView = itemView.findViewById(R.id.quizNameTextView);
            /*mUserHighScoreTextView = itemView.findViewById(R.id.userHighScoreValueTextView);
            mGlobalHighScoreTextView = itemView.findViewById(R.id.globalHighScoreValueTextView);*/
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
            /*mUserHighScoreTextView.setText(userHigh);
            mGlobalHighScoreTextView.setText(globalHigh);*/
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mCategoryTextView.setVisibility(View.VISIBLE);
            /*mUserHighScoreTextView.setVisibility(View.VISIBLE);
            mGlobalHighScoreTextView.setVisibility(View.VISIBLE);*/
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
