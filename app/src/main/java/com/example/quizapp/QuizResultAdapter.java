package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.ViewHolder> {

    private final Context context;
    private final List<QuizResult> quizResults;
     static int count = 1;

    public QuizResultAdapter(Context context, List<QuizResult> quizResults) {
        this.context = context;
        this.quizResults = quizResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quiz_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizResult quizResult = quizResults.get(position);
        holder.bind(quizResult);
    }

    @Override
    public int getItemCount() {
        return quizResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView scoreTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scoreTextView = itemView.findViewById(R.id.score_text_view);
        }

        @SuppressLint("SetTextI18n")
        public void bind(QuizResult quizResult) {
            int score = quizResult.getScore();

            scoreTextView.setText("ques "+count+" Score: "+ score);
            count++;

        }
    }
}

