package com.example.android.myquizapp;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class QuizQuestionClass {
    private static final String TAG = "QuizQuestionClass";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_CORRECT = "CorrectAnswer";
    private static final String KEY_FALSE_ONE = "FalseAnswerOne";
    private static final String KEY_FALSE_TWO = "FalseAnswerTwo";
    private static final String KEY_FALSE_THREE = "FalseAnswerThree";
    private static ArrayList<QuizQuestion> result = new ArrayList<>();

    public static ArrayList<String> getCategories() {
        ArrayList<String> res = new ArrayList<>();
        res.add("Sport");
        res.add("Music");
        res.add("Nature");
        res.add("History");
        res.add("Geography");
        res.add("Technology");
        res.add("People");
        res.add("Pictures");
        res.add("Films");
        res.add("TV");
        return res;
    }
    public static ArrayList<String> getUserHighScores() {
        ArrayList<String> res = new ArrayList<>();
        res.add("65");
        res.add("54");
        res.add("76");
        res.add("88");
        res.add("23");
        res.add("46");
        res.add("87");
        res.add("56");
        res.add("67");
        res.add("78");
        return res;
    }
    public static ArrayList<String> getGlobalHighScores() {
        ArrayList<String> res = new ArrayList<>();
        res.add("12");
        res.add("34");
        res.add("56");
        res.add("67");
        res.add("78");
        res.add("89");
        res.add("93");
        res.add("19");
        res.add("53");
        res.add("34");
        return res;
    }
    public static ArrayList<String> getDates() {
        ArrayList<String> res = new ArrayList<>();
        res.add("12th Feb");
        res.add("16th Mar");
        res.add("23rd Sep");
        res.add("1st Apr");
        res.add("6th May");
        res.add("24th Dec");
        res.add("18th Jul");
        res.add("26th Jan");
        res.add("13th Feb");
        res.add("28th Oct");
        return res;
    }

    public static ArrayList<QuizQuestion> getQuizQuestions() {
        result.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference myRef = db.collection("QuizQuestions").document("Sport").collection("SportQuestions");
        //final ArrayList<QuizQuestion> res = new ArrayList<>();
        //put this in a loop


                myRef.document("SportQuestion1").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    QuizQuestion quizQuestion = documentSnapshot.toObject(QuizQuestion.class);
                            /*String q = documentSnapshot.getString(KEY_QUESTION);
                            String correctA = documentSnapshot.getString(KEY_CORRECT);
                            String falseOne = documentSnapshot.getString(KEY_FALSE_ONE);
                            String falseTwo = documentSnapshot.getString(KEY_FALSE_TWO);
                            String falseThree = documentSnapshot.getString(KEY_FALSE_THREE);
                            QuizQuestion quizQuestion = new QuizQuestion(q, correctA, falseOne, falseTwo, falseThree);*/
                                    result.add(quizQuestion);

                                    Log.d(TAG, "onSuccess: happened" + result);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: faileddd");
                            }
                        });

                myRef.document("SportQuestion2").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    QuizQuestion quizQuestion = documentSnapshot.toObject(QuizQuestion.class);
                            /*String q = documentSnapshot.getString(KEY_QUESTION);
                            String correctA = documentSnapshot.getString(KEY_CORRECT);
                            String falseOne = documentSnapshot.getString(KEY_FALSE_ONE);
                            String falseTwo = documentSnapshot.getString(KEY_FALSE_TWO);
                            String falseThree = documentSnapshot.getString(KEY_FALSE_THREE);
                            QuizQuestion quizQuestion = new QuizQuestion(q, correctA, falseOne, falseTwo, falseThree);*/
                                    result.add(quizQuestion);

                                    Log.d(TAG, "onSuccess: happened" + result);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                myRef.document("SportQuestion3").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    QuizQuestion quizQuestion = documentSnapshot.toObject(QuizQuestion.class);
                            /*String q = documentSnapshot.getString(KEY_QUESTION);
                            String correctA = documentSnapshot.getString(KEY_CORRECT);
                            String falseOne = documentSnapshot.getString(KEY_FALSE_ONE);
                            String falseTwo = documentSnapshot.getString(KEY_FALSE_TWO);
                            String falseThree = documentSnapshot.getString(KEY_FALSE_THREE);
                            QuizQuestion quizQuestion = new QuizQuestion(q, correctA, falseOne, falseTwo, falseThree);*/
                                    result.add(quizQuestion);

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                Log.d(TAG, "getQuizQuestions: " + result);

        return result;
        /*ArrayList<String> res = new ArrayList<>();
        res.add("Who did this");
        res.add("Who said this");
        res.add("Who saw this");
        res.add("Who landed here");
        res.add("Who ran there");
        res.add("Why did this happen");
        res.add("How many of this");
        res.add("What is the point of this");
        res.add("How many did this");
        res.add("Where was this");
        return res;*/
    }


    public static ArrayList<String> getCorrectAnswers() {
        ArrayList<String> res = new ArrayList<>();
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");
        res.add("Correct answer");

        return res;
    }
    public static ArrayList<String> getFirstFalseAnswer() {
        ArrayList<String> res = new ArrayList<>();

        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");
        res.add("false answer 1");


        return res;
    }
    public static ArrayList<String> getSecondFalseAnswer() {
        ArrayList<String> res = new ArrayList<>();

        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");
        res.add("false answer 2");


        return res;
    }
    public static ArrayList<String> getThirdFalseAnswer() {
        ArrayList<String> res = new ArrayList<>();

        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");
        res.add("false answer 3");


        return res;
    }
}
