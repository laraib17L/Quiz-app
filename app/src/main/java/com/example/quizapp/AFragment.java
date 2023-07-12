package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class AFragment extends Fragment {

    private TextView letterTextView;
    private final char[] skyLetters = {'b', 'd', 'f', 'h', 'k', 'l', 't'};
    private final char[] grassLetters = {'g', 'j', 'p', 'q', 'y'};
    private final char[] rootLetters = {'a', 'c', 'e', 'i', 'm', 'n', 'o', 'r', 's', 'u', 'v', 'w', 'x', 'z'};
    private String answerString = "";

    private DatabaseHelper databaseHelper;
    private int questionCount;

    public AFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        letterTextView = view.findViewById(R.id.letter_text_view);


        Button skyButton = view.findViewById(R.id.sky_button);
        Button grassButton = view.findViewById(R.id.grass_button);
        Button rootButton = view.findViewById(R.id.root_button);

        databaseHelper = new DatabaseHelper(requireContext());

        skyButton.setOnClickListener(v -> checkAnswer("Sky Letter"));
        grassButton.setOnClickListener(v -> checkAnswer("Grass Letter"));
        rootButton.setOnClickListener(v -> checkAnswer("Root Letter"));

        questionCount = 0;

        displayRandomLetter();

        // Clear the answerTextView when coming back from QuizResultFragment

    }

    private void displayRandomLetter() {
        Random random = new Random();
        int category = random.nextInt(3);
        char letter;
        switch (category) {
            case 0:
                letter = skyLetters[random.nextInt(skyLetters.length)];
                answerString = "Sky Letter";
                break;
            case 1:
                letter = grassLetters[random.nextInt(grassLetters.length)];
                answerString = "Grass Letter";
                break;
            default:
                letter = rootLetters[random.nextInt(rootLetters.length)];
                answerString = "Root Letter";
                break;
        }
        letterTextView.setText(String.valueOf(letter));
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer(String selectedAnswer) {
        questionCount++;
        boolean isCorrect = selectedAnswer.equals(answerString);
        String message;
        if (isCorrect) {
            //message = "Awesome! Your answer is correct.";
            insertQuestionScore(1); // Insert the score of 1 for a correct answer
        } else {
           // message = "Incorrect! The answer is " + answerString + ".";
            insertQuestionScore(0); // Insert the score of 0 for an incorrect answer
        }
        //Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

        // Wait for 2 seconds and display a new letter
        new Handler().postDelayed(() -> {
            if (questionCount < 5) {
                displayRandomLetter();
            } else {
                finishQuiz();
            }
        }, 2000);
    }

    private void insertQuestionScore(int score) {
       // databaseHelper.deleteAllQuizResults();
        databaseHelper.insertQuizResult(score);
    }


    private void finishQuiz() {
        String completionMessage = "Quiz completed!";
        Toast.makeText(requireContext(), completionMessage, Toast.LENGTH_SHORT).show();

        // Start the MainActivity
        Intent intent = new Intent(requireContext(), MainActivity.class);

        startActivity(intent);

        // Finish the current activity (QuizActivity)
        requireActivity().finish();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        databaseHelper.close();
    }
}
