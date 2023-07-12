package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz_results.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUIZ_RESULTS = "quiz_results";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_QUIZ_RESULTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SCORE + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_QUIZ_RESULTS;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
    public void insertQuizResult(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        long insertedId = db.insert(TABLE_QUIZ_RESULTS, null, values);
        db.close();

        if (insertedId != -1) {
            Log.d("DatabaseHelper", "Quiz result inserted successfully. Score: " + score);
        } else {
            Log.d("DatabaseHelper", "Failed to insert quiz result. Score: " + score);
        }
    }
    public int getTotalScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SCORE + ") FROM " + TABLE_QUIZ_RESULTS, null);
        int totalScore = 0;
        if (cursor.moveToFirst()) {
            totalScore = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return totalScore;
    }


    public List<QuizResult> getAllQuizResults() {
        List<QuizResult> quizResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUIZ_RESULTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
                QuizResult quizResult = new QuizResult(id, score);
                quizResults.add(quizResult);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return quizResults;
    }
}