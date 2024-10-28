// MainActivity.java
package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerCategory;
    private Spinner spinnerLevel;
    private Button startQuizButton;
    private TextView selectLevelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerLevel = findViewById(R.id.spinnerLevel);
        startQuizButton = findViewById(R.id.startQuizButton);
        selectLevelText = findViewById(R.id.selectLevelText);

        // Set up the category spinner with default item
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.quiz_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Set up the level spinner
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this,
                R.array.quiz_levels, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(levelAdapter);

        // Listener for category selection
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // A category is selected
                    selectLevelText.setVisibility(View.VISIBLE); // Show level text
                    spinnerLevel.setVisibility(View.VISIBLE); // Show level spinner
                } else {
                    selectLevelText.setVisibility(View.GONE); // Hide level text
                    spinnerLevel.setVisibility(View.GONE); // Hide level spinner
                    startQuizButton.setVisibility(View.GONE); // Hide start button
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectLevelText.setVisibility(View.GONE);
                spinnerLevel.setVisibility(View.GONE);
                startQuizButton.setVisibility(View.GONE);
            }
        });

        // Listener for level selection
        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // A level is selected
                    startQuizButton.setVisibility(View.VISIBLE); // Show start button
                } else {
                    startQuizButton.setVisibility(View.GONE); // Hide start button
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                startQuizButton.setVisibility(View.GONE);
            }
        });

        // Handle start quiz button click
        startQuizButton.setOnClickListener(v -> {
            // Start the quiz activity
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("CATEGORY", spinnerCategory.getSelectedItem().toString());
            intent.putExtra("LEVEL", spinnerLevel.getSelectedItem().toString());
            startActivity(intent);
        });
    }
}
