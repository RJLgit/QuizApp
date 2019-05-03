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
        res.add("Ultimate");
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
    public static ArrayList<String> getQuizNames() {
        ArrayList<String> res = new ArrayList<>();
        res.add("The Ultimate Quiz Experience");
        res.add("Sport Quiz");
        res.add("Music Quiz");
        res.add("Nature Quiz");
        res.add("History Quiz");
        res.add("Geography Quiz");
        res.add("Technology Quiz");
        res.add("People Quiz");
        res.add("Pictures Quiz");
        res.add("Films Quiz");
        res.add("TV Quiz");
        return res;
    }

    public static ArrayList<String> getUserHighScores() {
        ArrayList<String> res = new ArrayList<>();
        res.add("33");
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
        res.add("35");
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
        res.add("19th Feb");
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
                return 20;

            case "Sport":
                return 20;
            case "Nature":
                return 10;
            case "Geography":
                return 20;
            case "Films":
                return 20;
            case "Pictures":
                return 5;
            case "Music":
                return 11;
            case "Technology":
                return 7;
            case "People":
                return 20;
            case "TV":
                return 20;

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
        QuizQuestion q6 = new QuizQuestion("Who wrote this music?", "Pachelbel", "Bach", "Handel", "Albinoni");
        QuizQuestion q7 = new QuizQuestion("Who wrote this music?", "Debussy", "Revel", "Chopin", "Brahms");
        QuizQuestion q8 = new QuizQuestion("Who wrote this music?", "Brahms", "Schumann", "Beethoven", "Mozart");
        QuizQuestion q9 = new QuizQuestion("Who wrote this music?", "Chopin", "Wagner", "Schumann", "Mozart");
        QuizQuestion q10 = new QuizQuestion("Who wrote this music?", "Mozart", "Verdi", "Schoenberg", "Bach");
        QuizQuestion q11 = new QuizQuestion("Who wrote this music?", "Bach", "Schubert", "Brahms", "Handel");
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
        res.add(q11);

        return res;
    }
    public static ArrayList<QuizQuestion> getTVQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("Who was the president in the west wing?", "President Bartlet", "President Hampton", "President Breakit", "President Jones");
        QuizQuestion q2 = new QuizQuestion("Who played Ned Stark in game of thrones?", "Sean Bean", "Kit Harrington", "Ian Glenn", "Peter Dinklage");
        QuizQuestion q3 = new QuizQuestion("What was the name of the android in Star Trek: The next generation?", "Data", "REM", "C924P", "Crunch");
        QuizQuestion q4 = new QuizQuestion("Who played the title role in the BBC series Merlin?", "Colin Morgan", "Bradley James", "Richard Wilson", "Anthony Head");
        QuizQuestion q5 = new QuizQuestion("Who is Larry's manager in Curb Your Enthusiasm?", "Jeff", "Richie", "Joe", "George");
        QuizQuestion q6 = new QuizQuestion("Where did Buffy the vampire slayer take place?", "Sunnydale", "Yorktown", "Camden", "Huntsville");
        QuizQuestion q7 = new QuizQuestion("Who played the first Dr Who?", "William Hartnell", "Patrick Troughton", "Jon Pertwee", "Tom Baker");
        QuizQuestion q8 = new QuizQuestion("Where is Fawlty Towers set?", "Torquay", "Brighton", "Bournemouth", "Grimsby");
        QuizQuestion q9 = new QuizQuestion("Who Played Emily Bishop In Coronation Street?", "Eileen Derbyshire", "Barbara Knox", "Betty Driver", "Thelma Barlow");
        QuizQuestion q10 = new QuizQuestion("Where is Cheers set?", "Boston", "New York", "LA", "Baltimore");
        QuizQuestion q11 = new QuizQuestion("What was the name of the pub in Only Fools and Horses?", "The Nag's Head", "The Black Swan", "The Two Black Cats", "The Elephant and the Castle");
        QuizQuestion q12 = new QuizQuestion("In Dad's Army, what was Private Fraser's occupation?", "Undertaker", "Butcher", "Bank Manager", "Green Grocer");
        QuizQuestion q13 = new QuizQuestion("Which TV series title music was Wheels on Fire?", "Absolutely Fabulous", "Men Behaving Badly", "Bottom", "As time goes by");
        QuizQuestion q14 = new QuizQuestion("Who hosted the cerebral & action quiz The Krypton Factor??", "Gordon Burns", "Roger Johnson", "Bob Greaves", "Tom Little");
        QuizQuestion q15 = new QuizQuestion("Which of these was NOT one of the FOUR original hosts of Game for A Laugh?", "Leslie Crowther", "Jeremy Beadle", "Henry Kelly", "Sarah Kennedy");
        QuizQuestion q16 = new QuizQuestion("Tony Green assisted Jim Bowen on which TV quiz show?", "Bullseye", "The price is right", "Blankety Blank", "Going for gold");
        QuizQuestion q17 = new QuizQuestion("Suicide is painless - was the theme song of which TV show?", "MASH", "Roll Out", "House Calls", "All in the Family");
        QuizQuestion q18 = new QuizQuestion("Who went to school at Greyfriars?", "Billy Bunter", "William Brown", "Desperate Dan", "Oliver Twist");
        QuizQuestion q19 = new QuizQuestion("what type of dog was lassie?", "Collie", "Labrador", "Sheepdog", "Lurcher");
        QuizQuestion q20 = new QuizQuestion("What ship did Captain Pugwash have?", "Black Pig", "Black Beetle", "Flying Horse", "Green Turtle");


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
        res.add(q11);
        res.add(q12);
        res.add(q13);
        res.add(q14);
        res.add(q15);
        res.add(q16);
        res.add(q17);
        res.add(q18);
        res.add(q19);
        res.add(q20);

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
        QuizQuestion q11 = new QuizQuestion("Which city was formerly known as Stalingrad?", "Volgograd", "Saint Petersburg", "Krasnodar", "Rostov-on-Don");
        QuizQuestion q12 = new QuizQuestion("In which state did the Battle of Little Bighorn take place?", "Montana", "Dakota", "Texas", "Washington");
        QuizQuestion q13 = new QuizQuestion("Which EU country has the most borders with other countries?", "Germany", "France", "Austria", "Poland");
        QuizQuestion q14 = new QuizQuestion("On what sea does Croatia sit?", "Adriatic Sea", "Ionian Sea", "Tyrrhenian Sea", "Red Sea");
        QuizQuestion q15 = new QuizQuestion("Which country is Sabena Airlines from?", "Belgium", "France", "Denmark", "Netherlands");
        QuizQuestion q16 = new QuizQuestion("Where is the european court of justice?", "Luxembourg", "Strasbourg", "Brussels", "Versailles");
        QuizQuestion q17 = new QuizQuestion("What is the largest island in the Mediterranean Sea?", "Sicily", "Sardinia", "Cyprus", "Crete");
        QuizQuestion q18 = new QuizQuestion("Whic capital city sits on The Tagus?", "Lisbon", "Madrid", "Warsaw", "Copenhagan");
        QuizQuestion q19 = new QuizQuestion("Where is Australia did Capt. Cook land?", "Botany Bay", "Shark Bay", "Port Jackson", "Aldinga Bay");
        QuizQuestion q20 = new QuizQuestion("What nationality were the members of the first successful expedition to the South Pole?", "Norwegian", "British", "Finnish", "Russian");


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
        res.add(q11);
        res.add(q12);
        res.add(q13);
        res.add(q14);
        res.add(q15);
        res.add(q16);
        res.add(q17);
        res.add(q18);
        res.add(q19);
        res.add(q20);
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
        QuizQuestion q9 = new QuizQuestion("The Andrea Gail is an ill-fated ship in which Hollywood film?", "The Perfect Storm", "Jaws", "All is Lost", "Life of Pi");
        QuizQuestion q10 = new QuizQuestion("Marion Crane is the tragic figure in which film?", "Psycho", "Vertigo", "Silence of the Lambs", "Halloween");
        QuizQuestion q11 = new QuizQuestion("Who was the first man to win the Academy Award for best actor two years in a row?", "Spencer Tracy", "Charles Laughton", "James Stewart", "Clark Gable");
        QuizQuestion q12 = new QuizQuestion("In which animated Disney films would one find Edna E. Mode?", "The Incredibles", "101 Dalmations", "The Lady and the Tramp", "Cinderella");
        QuizQuestion q13 = new QuizQuestion("In which animated Disney films would one find Pongo?", "101 Dalmations", "The lady and the Tramp", "Cinderella", "The Lion King");
        QuizQuestion q14 = new QuizQuestion("In the Tarzan films, what was Jane's last name?", "Parker", "Hambly", "Jones", "Straw");
        QuizQuestion q15 = new QuizQuestion("Ursula was the evil antagonists in which Disney film?", "The Little Mermaid", "Beauty and the Beast", "The Hunchback of Notre Dame", "Sleeping Beauty");
        QuizQuestion q16 = new QuizQuestion("Maleficent was the evil antagonists in which Disney film?", "Sleeping Beauty", "Pinnocchio", "The Hunchback of Notre Dame", "Beauty and the Beast");
        QuizQuestion q17 = new QuizQuestion("Who played the president in Love Actually?", "Billy Bob Thornton", "Ben Kingsley", "Gene Hackman", "Nigel Hawthorne");
        QuizQuestion q18 = new QuizQuestion("Which big screen cartoon character was voiced by Charles Fleischer?", "Roger Rabbit", "Bugs Bunny", "Daffy Duck", "Goofy");
        QuizQuestion q19 = new QuizQuestion("Who sang the theme song for Rocky III?", "Survivor", "Doris Day", "Journey", "Foreigner");
        QuizQuestion q20 = new QuizQuestion("Who said - Of all the gin joints in all the world, she walks into mine?", "Humphrey Bogart", "Clark Gable", "James Cagney", "Cary Grant");

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
        res.add(q11);
        res.add(q12);
        res.add(q13);
        res.add(q14);
        res.add(q15);
        res.add(q16);
        res.add(q17);
        res.add(q18);
        res.add(q19);
        res.add(q20);


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
    public static ArrayList<QuizQuestion> getPeopleQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("Which modern day country was Florence Nightingale born?", "Italy", "Ireland", "England", "Germany");
        QuizQuestion q2 = new QuizQuestion("What club did Geoff Boycott and Dickie Bird both play for?", "Barnsley", "Harragote", "Pickering", "Leeds");
        QuizQuestion q3 = new QuizQuestion("In which European city was actress Zsa Zsa Gabor born?", "Budapest", "Prague", "Florence", "Belgrade");
        QuizQuestion q4 = new QuizQuestion("Who introduced the policies of glasnost?", "Gorbachev", "Chernenko", "Lenin", "Putin");
        QuizQuestion q5 = new QuizQuestion("Which woman fameously campaigned for prison reform?", "Elizabeth Fry", "Jane Austen", "Emily Pankhurst", "Marie Stopes");
        QuizQuestion q6 = new QuizQuestion("Where was Mother Teresa born (modern day country)?", "Macedonia", "Greece", "Turkey", "Serbia");
        QuizQuestion q7 = new QuizQuestion("Where was Stalin born (modern day country)?", "Georgia", "Ukraine", "Russia", "Latvia");
        QuizQuestion q8 = new QuizQuestion("How many Labour party prime ministers did the UK have in the 20th century?", "5", "4", "6", "7");
        QuizQuestion q9 = new QuizQuestion("Who said: 'Never change horses in mid-stream'?", "Abraham Lincoln", "George Washington", "General Lee", "General Patton");
        QuizQuestion q10 = new QuizQuestion("Who was Buonaparte Ignace Gallia better known as in the James Bond world?", "Mr. Big", "Oddjob", "Jaws", "Necros");
        QuizQuestion q11 = new QuizQuestion("Who married Mary Weedon?", "Jeffrey Archer", "John Grisham", "John Major", "Freddie Mercury");
        QuizQuestion q12 = new QuizQuestion("Whom did Mehmet Ali Aga attempt to assassinate on 13th May 1981?", "Pope John Paul II", "Jimmy Carter", "William Webster", "Queen Elizabeth II");
        QuizQuestion q13 = new QuizQuestion("Which landscape gardener designed the grounds of Blenheim Palace?", "Lancelot Capability Brown", "Humphry Repton", "John Nash", "John Claudis Loudon");
        QuizQuestion q14 = new QuizQuestion("What was the profession of Alexander Graham Bell?", "Teacher", "Train driver", "Accountant", "Lawyer");
        QuizQuestion q15 = new QuizQuestion("Who first developed vaccines for rabies and cholera?", "Louis Pasteur", "Alexander Fleming", "Robert Koch", "Marie Curie");
        QuizQuestion q16 = new QuizQuestion("Who falls in love with Heathcliff in Wuthering Heights?", "Catherine Earnshaw", "Isabella Linton", "Nelly Dean", "Frances Earnshaw");
        QuizQuestion q17 = new QuizQuestion("Who did Castro overthrow in 1959?", "Fulgencio Batista", "Raymond Grau San Martin", "Miguel Mariano Gómez", "Camilo Cienfuegos Gorriarán");
        QuizQuestion q18 = new QuizQuestion("Who became Australian Prime Minister in 1992?", "Paul Keating", "Bob Hawke", "John Howard", "Malcolm Fraser");
        QuizQuestion q19 = new QuizQuestion("The worlds first woman Prime Minister was appointed by which nation in 1960?", "Sri Lanka", "Luxembourg", "New Zealand", "France");
        QuizQuestion q20 = new QuizQuestion("Which actress was the mother of actress Mia Farrow?", "Maureen O'Sullivan", "Maureen O'Hara", "Myrna Loy", "Loretta Young");


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
        res.add(q11);
        res.add(q12);
        res.add(q13);
        res.add(q14);
        res.add(q15);
        res.add(q16);
        res.add(q17);
        res.add(q18);
        res.add(q19);
        res.add(q20);


        return res;
    }
    public static ArrayList<QuizQuestion> getHistoryQuestions() {
        ArrayList<QuizQuestion> res = new ArrayList<>();
        QuizQuestion q1 = new QuizQuestion("What year did William 1 invade?", "1066", "1056", "1076", "1086");
        QuizQuestion q2 = new QuizQuestion("What year was the Munich Agreement?", "1938", "1937", "1936", "1933");
        QuizQuestion q3 = new QuizQuestion("Who commanded the British at the battle of Blenheim?", "Malborough", "Nelson", "Montgomery", "Wellington");
        QuizQuestion q4 = new QuizQuestion("Who made christainity the religion of the roman empire?", "Constantine", "Caesar", "Nero", "Hadrian");
        QuizQuestion q5 = new QuizQuestion("Whre was Andrew Carnegie Born?", "Dunfermline", "New York", "Philadelphia", "Dundee");
        QuizQuestion q6 = new QuizQuestion("Who won the nobel peace prize in 1919?", "Wilson", "La Fontaine", "Llyod-George", "Foch");
        QuizQuestion q7 = new QuizQuestion("Who was the first chancellor of the re-united Germany in the 20th century?", "Kohl", "Schmidt", "Schroder", "Brandt");
        QuizQuestion q8 = new QuizQuestion("Who commanded the British Expeditionary Force in 1914?", "John French", "Douglas Haig", "Herbert Kitchener", "Fredrick Roberts");
        QuizQuestion q9 = new QuizQuestion("Who was the french commander in chief at the start of world war 1?", "Joseph Joffre", "Ferdinand Foch", "Philippe Petain", "Joseph Gallieni");
        QuizQuestion q10 = new QuizQuestion("Who was the first chancellor of the German Empire?", "Otto von Bismarck", "Leo von Caprivi", "Kurt von Schleicher", "Paul von Hindenburg");
        QuizQuestion q11 = new QuizQuestion("Who sacked Rome in 410?", "Alaric", "Hannibal", "Athaulf", "Spartacus");
        QuizQuestion q12 = new QuizQuestion("Who Russian won the nobel prize for literature in 1970?", "Aleksandr Solzhenitsyn", "Boris Pasternak", "Mikhail Sholokhov", "Joseph Brodsky");
        QuizQuestion q13 = new QuizQuestion("Who wrote Resurrection in the 19th Century?", "Leo Tolstoy", "Oscar Wilde", "Fyodor Dostoyevsky", "Anton Chekhov");
        QuizQuestion q14 = new QuizQuestion("Who became secretary general of the UN in 1961?", "U Thant", "Kurt Waldheim", "Javier Perez de Cuellar", "Dag Hammarskjold");
        QuizQuestion q15 = new QuizQuestion("When was the Sharpsville massacre?", "1960", "1970", "1980", "1950");
        QuizQuestion q16 = new QuizQuestion("Which English town did the Romans call Eboracum?", "York", "Bath", "Chester", "London");
        QuizQuestion q17 = new QuizQuestion("Who in 800 AD was crowned by Pope Leo III in Rome as the first Holy Roman emperor?", "Charlemagne", "Charles Martel", "Clovis", "King Alfred");
        QuizQuestion q18 = new QuizQuestion("What was the first major battle in the english civil war?", "Edgehill", "Marston Moor", "Naseby", "Newbury");
        QuizQuestion q19 = new QuizQuestion("Who was monarch when The Crystal Palace was opened?", "Victoria", "Elizabeth I", "Elizabeth II", "George I");
        QuizQuestion q20 = new QuizQuestion("Who did William of Orange defeat at The Battle Of Boyne?", "James II", "Bonnie Prince Charlie", "Charles II", "Charles I");


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
        res.add(q11);
        res.add(q12);
        res.add(q13);
        res.add(q14);
        res.add(q15);
        res.add(q16);
        res.add(q17);
        res.add(q18);
        res.add(q19);
        res.add(q20);


        return res;
    }
}
