package com.konna.testhistory.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.konna.testhistory.R;
import com.konna.testhistory.objects.Questions;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Konna on 14.11.2017.
 */

public class TestActivity extends AppCompatActivity {

    String age;

    Questions questions;

    Button answer1Button;
    Button answer2Button;
    Button answer3Button;
    Button answer4Button;
    Button nextButton;

    TextView questionTextView;

    int currentQuestion = 0;

    int[] mixedAnswers;

    boolean next = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_test);

        age = getIntent().getStringExtra("age");

        questions = new Questions(this, age);

        answer1Button = (Button) findViewById(R.id.answer1Button);
        answer2Button = (Button) findViewById(R.id.answer2Button);
        answer3Button = (Button) findViewById(R.id.answer3Button);
        answer4Button = (Button) findViewById(R.id.answer4Button);
        nextButton = (Button) findViewById(R.id.nextButton);

        nextButton.setVisibility(View.INVISIBLE);

        questionTextView = (TextView) findViewById(R.id.questionTextView);

        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(0);
            }
        });

        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });

        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });

        answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next && currentQuestion + 1 != questions.getCount()) {
                    currentQuestion++;
                    init();
                } else if (currentQuestion + 1 == questions.getCount()) {
                    Toast.makeText(TestActivity.this, "Test finished", Toast.LENGTH_SHORT).show();
                }
            }
        });

        init();
    }

    public void init() {

        nextButton.setVisibility(View.INVISIBLE);
        next = false;

        String question = questions.getQuestion(currentQuestion);
        ArrayList<String> answers = questions.getAnswers(currentQuestion);

        mixedAnswers = mixAnswers();

        questionTextView.setText(question);

        answer1Button.setText(answers.get(mixedAnswers[0]));
        answer2Button.setText(answers.get(mixedAnswers[1]));
        answer3Button.setText(answers.get(mixedAnswers[2]));
        answer4Button.setText(answers.get(mixedAnswers[3]));
    }

    public void checkAnswer(int buttonPosition) {

        if (next) return;

        if (answerIsRight(buttonPosition)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        nextButton.setVisibility(View.VISIBLE);
        next = true;
    }

    public boolean answerIsRight(int buttonPosition) {
        if (mixedAnswers[buttonPosition] == 0) return true;
        return false;
    }

    public int[] mixAnswers() {
        Random random = new Random();

        int[] newArray = new int[4];

        switch (random.nextInt(4)) {
            case 0:
                newArray[0] = 0;
                newArray[1] = 1;
                newArray[2] = 2;
                newArray[3] = 3;
                return newArray;
            case 1:
                newArray[0] = 1;
                newArray[1] = 0;
                newArray[2] = 2;
                newArray[3] = 3;
                return newArray;
            case 2:
                newArray[0] = 1;
                newArray[1] = 2;
                newArray[2] = 0;
                newArray[3] = 3;
                return newArray;
            case 3:
                newArray[0] = 1;
                newArray[1] = 2;
                newArray[2] = 3;
                newArray[3] = 0;
                return newArray;
        }

        return newArray;
    }

}
