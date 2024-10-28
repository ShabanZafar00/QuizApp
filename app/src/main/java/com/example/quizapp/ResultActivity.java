// ResultActivity.java
package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreTextView, resultMessageTextView;
    private Button retryQuizButton;
    private CardView resultMessageCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreTextView = findViewById(R.id.scoreTextView);
        resultMessageTextView = findViewById(R.id.resultMessageTextView);
        retryQuizButton = findViewById(R.id.retryQuizButton);
        resultMessageCardView = findViewById(R.id.resultMessageCardView);

        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0);
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);

        // Display the score as "correct/total"
        scoreTextView.setText("Score: " + correctAnswers + "/" + totalQuestions);

        // Set the result message and color based on the score
        if (correctAnswers >= totalQuestions / 2) {
            resultMessageTextView.setText("Congratulations!");
            resultMessageCardView.setCardBackgroundColor(Color.parseColor("#4CAF50")); // Green color
        } else {
            resultMessageTextView.setText("Better Luck Next Time!");
            resultMessageCardView.setCardBackgroundColor(Color.parseColor("#F44336")); // Red color
        }
    }

    public void gotomain(View view) {
        Intent retryIntent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(retryIntent);
        finish(); // Optional: close the ResultActivity
    }
}
