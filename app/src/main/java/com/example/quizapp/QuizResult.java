package com.example.quizapp;

public class QuizResult {
    private int id;
    private int score;


    public QuizResult(int id, int score) {
        this.id = id;
        this.score = score;

    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

}
