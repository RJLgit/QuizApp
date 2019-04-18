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

    public static int getNumberQuestions(String category) {
        switch (category) {
            case "History":
                return 7;

            case "Sport":
                return 10;
            case "Nature":
                return 10;
            case "Geography":
                return 10;
            case "Films":
                return 8;
            case "Pictures":
                return 5;
            case "Music":
                return 5;
            case "Technology":
                return 7;

            default:
                return 0;


        }

    }

    public static ArrayList<QuizQuestion> getNatureQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("How many legs does a spider have?", "8", "6", "4", "10");
        QuizQuestion q2 = new QuizQuestion("What is the fastest land animal?", "Cheetah", "Pronghorn", "Spingbok", "Lion");
        QuizQuestion q3 = new QuizQuestion("Dry Ice is a form of which gas?", "Carbon Dioxide", "Nitrogen", "Oxygen", "Helium");
        QuizQuestion q4 = new QuizQuestion("What is the brightest star?", "Sirius", "Canopus", "Rigil Kentaurus", "Arcturus");
        QuizQuestion q5 = new QuizQuestion("What is the outermost layer of the atmosphere?", "Thermosphere", "Stratosphere", "Troposphere", "Mesosphere");
        QuizQuestion q6 = new QuizQuestion("What is the second tallest mountain in the world?", "K2", "Kangchenjunga", "Lhotse", "Broad Peak");
        QuizQuestion q7 = new QuizQuestion("Where is the world's largest freshwater lake?", "Russia", "Congo", "Canada", "Brazil");
        QuizQuestion q8 = new QuizQuestion("What of these is the biggest by area?", "Lincolnshire", "Cumbria", "Devon", "Norfolk");
        QuizQuestion q9 = new QuizQuestion("Who was the third man to walk on the moon?", "Charles Conrad", "Alan Shepard", "Alan Bean", "Edgar Mitchell");
        QuizQuestion q10 = new QuizQuestion("What is the largest fish in the world?", "Whale Shark", "Basking Shark", "Great White Shark", "Tiger shark");
        res.add(q1);
        res.add(q2);
        res.add(q3);
        res.add(q4);
        res.add(q5);
        res.add(q6);
        res.add(q7);
        res.add(q8);
        res.add(q9);
        res.add(q10);
                return res;
    }

    public static ArrayList<QuizQuestion> getPictureQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("Where is this?", "Copenhagen", "Oslo", "Stockholm", "Vienna");
        QuizQuestion q2 = new QuizQuestion("Where is this?", "Bavaria", "Just outside paris", "Bilboa", "Japan");
        QuizQuestion q3 = new QuizQuestion("Where is this?", "Edinburugh", "Warwick", "Dover", "Durham");
        QuizQuestion q4 = new QuizQuestion("Where is this?", "Florence", "Rome", "Milan", "Marseille");
        QuizQuestion q5 = new QuizQuestion("where is this??", "London", "Paris", "Berlin", "St.Petersburg");

        res.add(q1);
        res.add(q2);
        res.add(q3);
        res.add(q4);
        res.add(q5);

        return res;
    }

    public static ArrayList<QuizQuestion> getMusicQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("Who wrote this music?", "Wagner", "Schubert", "Brahms", "Chopin");
        QuizQuestion q2 = new QuizQuestion("Who wrote this music?", "Beethoven", "Bach", "Brahms", "Haydn");
        QuizQuestion q3 = new QuizQuestion("Who wrote this music?", "Tchaikovsky", "Handel", "Beethoven", "Mozart");
        QuizQuestion q4 = new QuizQuestion("Who wrote this music?", "Vivaldi", "Debussy", "Schumann", "Mahler");
        QuizQuestion q5 = new QuizQuestion("Who wrote this music?", "Beethoven", "Verdi", "Schoenberg", "Gershwin");

        res.add(q1);
        res.add(q2);
        res.add(q3);
        res.add(q4);
        res.add(q5);

        return res;
    }

    public static ArrayList<QuizQuestion> getGeographyQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("What does the suez canal link with the Meditarrean Sea?", "Red sea", "Dead sea", "Black sea", "Caspian sea");
        QuizQuestion q2 = new QuizQuestion("What is the capital of Bolivia?", "Sucre", "La Paz", "Santa Cruz de la Sierra", "Montero");
        QuizQuestion q3 = new QuizQuestion("Which of these is biggest?", "Gobi desert", "Kalahari desert", "Great Victoria Desert", "Patagonian desert");
        QuizQuestion q4 = new QuizQuestion("Which of these is oldest?", "The cairn of Barnenez", "Knap of Howar", "Pyramid of Djoser", "Stone henge");
        QuizQuestion q5 = new QuizQuestion("Which of these is largest?", "Colombia", "Ethiopia", "Bolivia", "Egypt");
        QuizQuestion q6 = new QuizQuestion("Which of these had the highest population in 2017?", "Germany", "Iran", "Turkey", "South Africa");
        QuizQuestion q7 = new QuizQuestion("Which of these islands is largest?", "Great Britain", "Sulawesi", "South island - nz", "Java");
        QuizQuestion q8 = new QuizQuestion("What of these is the biggest by area?", "Illinois", "Iowa", "New york", "North Carolina");
        QuizQuestion q9 = new QuizQuestion("Which of these has the most people?", "Milton Keynes", "Northampton", "Luton", "York");
        QuizQuestion q10 = new QuizQuestion("Which of these has the most people?", "Stuttgart", "Dusseldorf", "Dortmound", "Essen");
        res.add(q1);
        res.add(q2);
        res.add(q3);
        res.add(q4);
        res.add(q5);
        res.add(q6);
        res.add(q7);
        res.add(q8);
        res.add(q9);
        res.add(q10);
        return res;
    }

    public static ArrayList<QuizQuestion> getFilmsQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("Who directed GoodWill hunting?", "Gus Van Sant", "Matt Damon", "Ben Affleck", "Lawrence Bender");
        QuizQuestion q2 = new QuizQuestion("Which of these de Niro films came first?", "Taxi Driver", "New York, New York", "The Deer Hunter", "Raging bull");
        QuizQuestion q3 = new QuizQuestion("Who has won the most acting oscars?", "Katharine Hepburn", "Jack Nicholson", "Meryl Streep", "Ingrid Bergman");
        QuizQuestion q4 = new QuizQuestion("What is the film with the largest ever budget After inflation?", "Pirates of the caribeean: On Stranger tides", "Titanic", "Spider-man 3", "Tangled");
        QuizQuestion q5 = new QuizQuestion("In the Wizard of Oz, what is Dorothy's surname?", "Gale", "Hank", "Green", "Smith");
        QuizQuestion q6 = new QuizQuestion("Francis Gumm is the real name of who?", "Judy Garland", "Meryl Streep", "Katherine Hepburn", "Ingrid Bergman");
        QuizQuestion q7 = new QuizQuestion("Which of these was not a Corleone in the Godfather?", "Luca", "Sonny", "Michael", "Fredo");
        QuizQuestion q8 = new QuizQuestion("Which disney film came first?", "Snow White", "Pinnocchio", "Fantasia", "Bambi");

        res.add(q1);
        res.add(q2);
        res.add(q3);
        res.add(q4);
        res.add(q5);
        res.add(q6);
        res.add(q7);
        res.add(q8);

        return res;
    }
    public static ArrayList<QuizQuestion> getTechnologyQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("What programming language is used to give webstie's behaviour?", "Javascript", "Python", "C#", "Java");
        QuizQuestion q2 = new QuizQuestion("What programming language is used to make android products?", "Java", "Python", "C++", "C#");
        QuizQuestion q3 = new QuizQuestion("What scripting language is used to style web pages?", "CSS", "HTML", "javascript", "Java");
        QuizQuestion q4 = new QuizQuestion("How many bits in a byte?", "1024", "1000", "16", "640");
        QuizQuestion q5 = new QuizQuestion("Who invented the logarithm table?", "John Napier", "George Friend", "Henry Simpson", "Joe Fraser");
        QuizQuestion q6 = new QuizQuestion("What is the biggest tech company in the world by market cap (as of March 2019)?", "Microsoft", "Apple", "Amazon", "Alphabet Inc (google)");
        QuizQuestion q7 = new QuizQuestion("Who published their thesis on jet engines in 1929?", "Frank Whittle", "Wilhelm Pape", "Hans Van Ohain", "Maxime Guillaume");


        res.add(q1);
        res.add(q2);
        res.add(q3);
        res.add(q4);
        res.add(q5);
        res.add(q6);
        res.add(q7);


        return res;
    }
}
