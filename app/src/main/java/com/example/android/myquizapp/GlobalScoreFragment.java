package com.example.android.myquizapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class GlobalScoreFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private ImageView mImageView;
    public GlobalScoreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_global_scores, container, false);
        final TextView value = fView.findViewById(R.id.globalScoreValue);
        final TextView person = fView.findViewById(R.id.globalScorePerson);
        final TextView date = fView.findViewById(R.id.globalScoreDate);
        mImageView = (ImageView) fView.findViewById(R.id.imageView3);
        Picasso.get().load(R.drawable.top_score_image).into(mImageView);

        Button backToMain = fView.findViewById(R.id.backToMainActButt);
        Button startQuizRound = fView.findViewById(R.id.startQuizButton);
        Intent i = getActivity().getIntent();
        final String category = i.getStringExtra("Category");
        final String mUsername = i.getStringExtra("Username");

        documentReference = db.collection("TopScores").document(i.getStringExtra("Category").toLowerCase());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    GlobalScores glbScores = documentSnapshot.toObject(GlobalScores.class);
                    value.setText(glbScores.getScore() + "");
                    person.setText("by " + glbScores.getUser());
                    date.setText("Set on " + glbScores.getShortDate());
                } else {
                    GlobalScores glbScoress = new GlobalScores("Not set", 0, "Not set");
                    documentReference.set(glbScoress);
                    value.setText(glbScoress.getScore() + "");
                    person.setText("by " + glbScoress.getUser());
                    date.setText("Set on " + glbScoress.getDate());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        startQuizRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentQuiz = new Intent(getContext(), QuestionActivity.class);
                intentQuiz.putExtra("CategoryClicked", category);
                intentQuiz.putExtra("Username", mUsername);
                startActivity(intentQuiz);
            }
        });

        return fView;
    }
}
