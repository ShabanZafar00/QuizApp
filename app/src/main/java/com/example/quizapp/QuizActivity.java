package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup answerGroup;
    private RadioButton answer1, answer2, answer3, answer4;
    private Button submitAnswerButton;
    private TextView timerTextView;

    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION = 30000; // 30 seconds
    private long timeRemaining = TIMER_DURATION;

    private HashMap<String, ArrayList<Question>> questions;
    private String selectedCategory, selectedLevel;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.questionText);
        answerGroup = findViewById(R.id.answerGroup);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        timerTextView = findViewById(R.id.timerTextView);

        selectedCategory = getIntent().getStringExtra("CATEGORY");
        selectedLevel = getIntent().getStringExtra("LEVEL");

        // Set the text color to black
        answer1.setTextColor(Color.BLACK);
        answer2.setTextColor(Color.BLACK);
        answer3.setTextColor(Color.BLACK);
        answer4.setTextColor(Color.BLACK);

        loadQuestions();
        displayNextQuestion(); // Display the first question

        submitAnswerButton.setOnClickListener(v -> checkAnswer());
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                skipQuestion();
            }
        }.start();
    }

    private void updateTimer() {
        int secondsRemaining = (int) (timeRemaining / 1000);
        timerTextView.setText(String.valueOf(secondsRemaining));
    }

    private void loadQuestions() {
        questions = new HashMap<>();

        // Sample Questions for History: Beginner
        ArrayList<Question> historyQuestionsBeginner = new ArrayList<>();
        historyQuestionsBeginner.add(new Question("Who was the first President of the United States?",
                new String[]{"George Washington", "Thomas Jefferson", "Abraham Lincoln", "John Adams"}, 0));
        historyQuestionsBeginner.add(new Question("What year did World War II begin?",
                new String[]{"1939", "1945", "1914", "1920"}, 0));
        historyQuestionsBeginner.add(new Question("Who was known as the 'Iron Lady'?",
                new String[]{"Margaret Thatcher", "Angela Merkel", "Golda Meir", "Indira Gandhi"}, 0));
        historyQuestionsBeginner.add(new Question("What is the capital of ancient Rome?",
                new String[]{"Rome", "Athens", "Carthage", "Constantinople"}, 0));
        historyQuestionsBeginner.add(new Question("In what year did the Titanic sink?",
                new String[]{"1912", "1905", "1920", "1898"}, 0));
        historyQuestionsBeginner.add(new Question("What was the first country to grant women the right to vote?",
                new String[]{"New Zealand", "Finland", "USA", "UK"}, 0));

        // Sample Questions for History: Moderate
        ArrayList<Question> historyQuestionsModerate = new ArrayList<>();
        historyQuestionsModerate.add(new Question("Who was the first female Prime Minister of the United Kingdom?",
                new String[]{"Margaret Thatcher", "Theresa May", "Angela Merkel", "Golda Meir"}, 0));
        historyQuestionsModerate.add(new Question("What major event did the Emancipation Proclamation lead to?",
                new String[]{"American Civil War", "World War I", "World War II", "The Great Depression"}, 0));
        historyQuestionsModerate.add(new Question("Which ancient civilization is known for the pyramids?",
                new String[]{"Egyptians", "Greeks", "Romans", "Babylonians"}, 0));
        historyQuestionsModerate.add(new Question("Which war was fought between the North and South regions in the U.S.?",
                new String[]{"American Civil War", "World War I", "World War II", "Vietnam War"}, 0));
        historyQuestionsModerate.add(new Question("Who was the famous civil rights leader who delivered the 'I Have a Dream' speech?",
                new String[]{"Martin Luther King Jr.", "Malcolm X", "Nelson Mandela", "Rosa Parks"}, 0));
        historyQuestionsModerate.add(new Question("What treaty ended World War I?",
                new String[]{"Treaty of Versailles", "Treaty of Paris", "Treaty of Ghent", "Treaty of Tordesillas"}, 0));

        // Sample Questions for History: Expert
        ArrayList<Question> historyQuestionsExpert = new ArrayList<>();
        historyQuestionsExpert.add(new Question("Which event marked the start of the French Revolution?",
                new String[]{"Storming of the Bastille", "Signing of the Magna Carta", "Fall of the Berlin Wall", "Boston Tea Party"}, 0));
        historyQuestionsExpert.add(new Question("What was the primary cause of the Cold War?",
                new String[]{"Ideological conflict between the USA and USSR", "Economic competition", "Territorial disputes", "Technological advancements"}, 0));
        historyQuestionsExpert.add(new Question("Which empire was known for its road systems and military strategies?",
                new String[]{"Roman Empire", "Ottoman Empire", "Mongol Empire", "British Empire"}, 0));
        historyQuestionsExpert.add(new Question("What was the primary objective of the Crusades?",
                new String[]{"Control of the Holy Land", "Trade routes", "Spread of democracy", "Establishing empires"}, 0));
        historyQuestionsExpert.add(new Question("Who was the principal author of the Declaration of Independence?",
                new String[]{"Thomas Jefferson", "George Washington", "Benjamin Franklin", "John Adams"}, 0));
        historyQuestionsExpert.add(new Question("What was the name of the first artificial satellite launched into space?",
                new String[]{"Sputnik 1", "Apollo 11", "Voyager 1", "Hubble Space Telescope"}, 0));

        // Populate the questions map
        questions.put("History: Beginner", historyQuestionsBeginner);
        questions.put("History: Moderate", historyQuestionsModerate);
        questions.put("History: Expert", historyQuestionsExpert);

        // Sample Questions for Science: Beginner
        ArrayList<Question> scienceQuestionsBeginner = new ArrayList<>();
        scienceQuestionsBeginner.add(new Question("What is the chemical symbol for water?",
                new String[]{"H2O", "O2", "CO2", "NaCl"}, 0));
        scienceQuestionsBeginner.add(new Question("What planet is known as the Red Planet?",
                new String[]{"Mars", "Earth", "Jupiter", "Venus"}, 0));
        scienceQuestionsBeginner.add(new Question("What is the powerhouse of the cell?",
                new String[]{"Mitochondria", "Nucleus", "Ribosome", "Endoplasmic Reticulum"}, 0));
        scienceQuestionsBeginner.add(new Question("What gas do plants absorb from the atmosphere?",
                new String[]{"Carbon Dioxide", "Oxygen", "Nitrogen", "Hydrogen"}, 0));
        scienceQuestionsBeginner.add(new Question("What is the speed of light?",
                new String[]{"299,792 km/s", "150,000 km/s", "1,000 km/s", "300,000 km/s"}, 0));
        scienceQuestionsBeginner.add(new Question("What is the largest organ in the human body?",
                new String[]{"Skin", "Liver", "Heart", "Brain"}, 0));

        // Sample Questions for Science: Moderate
        ArrayList<Question> scienceQuestionsModerate = new ArrayList<>();
        scienceQuestionsModerate.add(new Question("What is the main gas found in the air we breathe?",
                new String[]{"Nitrogen", "Oxygen", "Carbon Dioxide", "Hydrogen"}, 0));
        scienceQuestionsModerate.add(new Question("What is the process by which plants make their food?",
                new String[]{"Photosynthesis", "Respiration", "Transpiration", "Digestion"}, 0));
        scienceQuestionsModerate.add(new Question("Which planet is known for its rings?",
                new String[]{"Saturn", "Jupiter", "Mars", "Earth"}, 0));
        scienceQuestionsModerate.add(new Question("What is the smallest unit of life?",
                new String[]{"Cell", "Tissue", "Organ", "Organism"}, 0));
        scienceQuestionsModerate.add(new Question("What is the boiling point of water?",
                new String[]{"100°C", "0°C", "50°C", "25°C"}, 0));
        scienceQuestionsModerate.add(new Question("What type of bond forms between water molecules?",
                new String[]{"Hydrogen bond", "Ionic bond", "Covalent bond", "Metallic bond"}, 0));

        // Sample Questions for Science: Expert
        ArrayList<Question> scienceQuestionsExpert = new ArrayList<>();
        scienceQuestionsExpert.add(new Question("What is the most abundant gas in the Earth's atmosphere?",
                new String[]{"Nitrogen", "Oxygen", "Argon", "Carbon Dioxide"}, 0));
        scienceQuestionsExpert.add(new Question("What is the primary function of the mitochondria?",
                new String[]{"Energy production", "Protein synthesis", "Photosynthesis", "Cell division"}, 0));
        scienceQuestionsExpert.add(new Question("What is the unit of measurement for electric current?",
                new String[]{"Ampere", "Volt", "Ohm", "Watt"}, 0));
        scienceQuestionsExpert.add(new Question("Which physicist developed the theory of relativity?",
                new String[]{"Albert Einstein", "Isaac Newton", "Niels Bohr", "Stephen Hawking"}, 0));
        scienceQuestionsExpert.add(new Question("What is the term for an organism that makes its own food?",
                new String[]{"Autotroph", "Heterotroph", "Decomposer", "Consumer"}, 0));
        scienceQuestionsExpert.add(new Question("What is the chemical formula for table salt?",
                new String[]{"NaCl", "KCl", "CaCl2", "MgCl2"}, 0));

        // Populate the questions map for Science
        questions.put("Science: Beginner", scienceQuestionsBeginner);
        questions.put("Science: Moderate", scienceQuestionsModerate);
        questions.put("Science: Expert", scienceQuestionsExpert);

        // Sample Questions for General Knowledge: Beginner
        ArrayList<Question> generalKnowledgeQuestionsBeginner = new ArrayList<>();
        generalKnowledgeQuestionsBeginner.add(new Question("What is the capital of France?",
                new String[]{"Paris", "London", "Berlin", "Madrid"}, 0));
        generalKnowledgeQuestionsBeginner.add(new Question("Which planet is known as the Blue Planet?",
                new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 0));
        generalKnowledgeQuestionsBeginner.add(new Question("Who wrote 'Hamlet'?",
                new String[]{"William Shakespeare", "Charles Dickens", "Mark Twain", "J.K. Rowling"}, 0));
        generalKnowledgeQuestionsBeginner.add(new Question("What is the largest continent?",
                new String[]{"Asia", "Africa", "North America", "Europe"}, 0));
        generalKnowledgeQuestionsBeginner.add(new Question("How many colors are in a rainbow?",
                new String[]{"7", "6", "8", "5"}, 0));
        generalKnowledgeQuestionsBeginner.add(new Question("What is the hardest natural substance on Earth?",
                new String[]{"Diamond", "Gold", "Iron", "Quartz"}, 0));

        // Sample Questions for General Knowledge: Moderate
        ArrayList<Question> generalKnowledgeQuestionsModerate = new ArrayList<>();
        generalKnowledgeQuestionsModerate.add(new Question("What is the smallest country in the world?",
                new String[]{"Vatican City", "Monaco", "San Marino", "Liechtenstein"}, 0));
        generalKnowledgeQuestionsModerate.add(new Question("In which year did the Titanic sink?",
                new String[]{"1912", "1905", "1920", "1898"}, 0));
        generalKnowledgeQuestionsModerate.add(new Question("Which famous scientist developed the theory of general relativity?",
                new String[]{"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Nikola Tesla"}, 0));
        generalKnowledgeQuestionsModerate.add(new Question("What is the currency of Japan?",
                new String[]{"Yen", "Won", "Dollar", "Rupee"}, 0));
        generalKnowledgeQuestionsModerate.add(new Question("Which gas is most abundant in the Earth's atmosphere?",
                new String[]{"Nitrogen", "Oxygen", "Carbon Dioxide", "Helium"}, 0));
        generalKnowledgeQuestionsModerate.add(new Question("What is the main ingredient in guacamole?",
                new String[]{"Avocado", "Tomato", "Onion", "Pepper"}, 0));

        // Sample Questions for General Knowledge: Expert
        ArrayList<Question> generalKnowledgeQuestionsExpert = new ArrayList<>();
        generalKnowledgeQuestionsExpert.add(new Question("What is the capital of Australia?",
                new String[]{"Canberra", "Sydney", "Melbourne", "Brisbane"}, 0));
        generalKnowledgeQuestionsExpert.add(new Question("Who painted the Mona Lisa?",
                new String[]{"Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Claude Monet"}, 0));
        generalKnowledgeQuestionsExpert.add(new Question("What is the largest planet in our solar system?",
                new String[]{"Jupiter", "Saturn", "Earth", "Mars"}, 0));
        generalKnowledgeQuestionsExpert.add(new Question("In what year did World War I begin?",
                new String[]{"1914", "1918", "1939", "1945"}, 0));
        generalKnowledgeQuestionsExpert.add(new Question("What is the chemical formula for table salt?",
                new String[]{"NaCl", "KCl", "CaCl2", "MgCl2"}, 0));
        generalKnowledgeQuestionsExpert.add(new Question("Who was the first man to walk on the moon?",
                new String[]{"Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "John Glenn"}, 0));

        // Populate the questions map for General Knowledge
        questions.put("General Knowledge: Beginner", generalKnowledgeQuestionsBeginner);
        questions.put("General Knowledge: Moderate", generalKnowledgeQuestionsModerate);
        questions.put("General Knowledge: Expert", generalKnowledgeQuestionsExpert);
    }

    private void displayNextQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        ArrayList<Question> selectedQuestions = questions.get(selectedCategory + ": " + selectedLevel);

        if (currentQuestionIndex < selectedQuestions.size()) {
            Question currentQuestion = selectedQuestions.get(currentQuestionIndex);
            questionText.setText(currentQuestion.getQuestion());
            answer1.setText(currentQuestion.getAnswers()[0]);
            answer2.setText(currentQuestion.getAnswers()[1]);
            answer3.setText(currentQuestion.getAnswers()[2]);
            answer4.setText(currentQuestion.getAnswers()[3]);

            answerGroup.clearCheck(); // Clear previous selection

            startTimer(); // Start the timer for the new question
        } else {
            showResult();
        }
    }


    private void checkAnswer() {
        int selectedId = answerGroup.getCheckedRadioButtonId();

        if (selectedId != -1) { // Ensure an option is selected
            ArrayList<Question> selectedQuestions = questions.get(selectedCategory + ": " + selectedLevel);
            int correctAnswerIndex = selectedQuestions.get(currentQuestionIndex).getCorrectAnswerIndex();

            // Check if the selected RadioButton ID matches the correct answer index
            if ((selectedId == answer1.getId() && correctAnswerIndex == 0) ||
                    (selectedId == answer2.getId() && correctAnswerIndex == 1) ||
                    (selectedId == answer3.getId() && correctAnswerIndex == 2) ||
                    (selectedId == answer4.getId() && correctAnswerIndex == 3)) {

                correctAnswers++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong! The correct answer is: " +
                        selectedQuestions.get(currentQuestionIndex).getAnswers()[correctAnswerIndex], Toast.LENGTH_LONG).show();
            }

            currentQuestionIndex++;
            displayNextQuestion();
        } else {
            Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
        }
    }

    private void skipQuestion() {
        currentQuestionIndex++;
        Toast.makeText(this, "Time's up! Moving to the next question.", Toast.LENGTH_SHORT).show();
        displayNextQuestion();
    }


    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("SCORE", correctAnswers);
        resultIntent.putExtra("TOTAL", currentQuestionIndex);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    private void showResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("CORRECT_ANSWERS", correctAnswers);
        intent.putExtra("TOTAL_QUESTIONS", questions.get(selectedCategory + ": " + selectedLevel).size());
        startActivity(intent);
        finish(); // Close the QuizActivity
    }
}
